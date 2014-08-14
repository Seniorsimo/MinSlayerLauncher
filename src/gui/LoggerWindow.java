/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import moduli.LauncherLogger;
import moduli.LoggerListener;
import moduli.Style;

/**
 *
 * @author simone
 */
public class LoggerWindow extends JFrame implements LoggerListener{
    
    JEditorPane text;
    Image bgImage;
    JScrollPane scroll;

    public LoggerWindow(/*LauncherLogger lg*/) {
        super("Log");
        
        try {
                this.bgImage = ImageIO.read(MainWindow.class.getResource("bg.png")).getScaledInstance(32, 32, 16);
            } catch (IOException e) {
                System.out.println("Error reading image file: bg.png\n" + e.toString());
            }
        
        LauncherPanel panel = new LauncherPanel(){
            private Image img;
            
            @Override
            public void paintComponent(Graphics g2) {
                int w = getWidth();
                int h = getHeight();
                if ((this.img == null) || (this.img.getWidth(null) != w) || (this.img.getHeight(null) != h)) {
                    this.img = createImage(w, h);

                    Graphics g = this.img.getGraphics();

                    //tiled
                    for (int x = 0; x <= w / 32; x++) {
                        for (int y = 0; y <= h / 32; y++) {
                            g.drawImage(bgImage, x * 32, y * 32, null);
                        }
                    }

                    //full
                    //g.drawImage(this.bgImage, 0, 0, w, h, null);

                    //effects
                    if ((g instanceof Graphics2D)) {
                        Graphics2D gg = (Graphics2D) g;
                        int gh = 450;
                        /*gg.setPaint(new GradientPaint(new Point2D.Float(0.0F, 0.0F), new Color(150,150,150,10), new Point2D.Float(0.0F, gh), new Color(255,255,255,60)));
                        gg.fillRect(0, 0, w, gh);*/

                        gh = h;
                        gg.setPaint(new GradientPaint(new Point2D.Float(0.0F, 0.0F), new Color(200,200,200,0), new Point2D.Float(0.0F, gh), new Color(0,0,0,190)));
                        gg.fillRect(0, 0, w, gh);
                    }
                    g.dispose();
                }
                g2.drawImage(this.img, 0, 0, w, h, null);
            }
        };
        panel.setLayout(new BorderLayout());
        panel.setOpaque(true);
        
        JLabel title = new JLabel("Log:");
        title.setFont(title.getFont().deriveFont(20.0F));
        title.setForeground(Style.mainForeground);
        panel.add(title, BorderLayout.NORTH);
        
        text = new JEditorPane("text/html","");
        text.setEditable(false);
        text.setOpaque(false);
        scroll = new JScrollPane(text);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.getViewport().setBackground(new Color(255,255,255,50));
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setViewportBorder(new EmptyBorder(10,10,10,10));
        panel.add(scroll, BorderLayout.CENTER);
        
        LauncherPanel control = new LauncherPanel();
        control.setLayout(new FlowLayout());
        JButton copyButton = new JButton("copy");
        copyButton.setBackground(Style.buttonBackground);
        copyButton.setForeground(Style.buttonForeground);
        copyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringSelection ss = new StringSelection(text.getText());
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
                JOptionPane.showMessageDialog(null,"Log copied to clipboard.");
            }
        });
        control.add(copyButton);
        
        panel.add(control, BorderLayout.SOUTH);
        
        //lg.registerListener(this);
        
        final LoggerWindow i = this;
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent arg0) {
                int answer = JOptionPane.showConfirmDialog(null,"Close log window? You can't open it again until launcher restart!","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if(answer!=JOptionPane.YES_OPTION){
                } else {
                    i.setVisible(false);
                }
            }
        });
        this.setSize(800, 400);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.getContentPane().add("Center", panel);
        this.setVisible(true);
    }
    
    

    @Override
    public void refreshGUI(List<String> list) {
        String out = Style.preLog;
        for(String s:list){
            out+=s+"<br>";
        }
        out += Style.postLog;
        text.setText(out);
        
        text.setCaretPosition(text.getDocument().getLength()-1);
        //scroll.setViewportView(text);
//        JScrollBar bar = scroll.getVerticalScrollBar();
//        bar.setValue(bar.getMaximum());
    }
    
    
    
}
