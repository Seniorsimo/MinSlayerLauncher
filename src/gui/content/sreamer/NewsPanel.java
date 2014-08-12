/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.content.sreamer;

import gui.LauncherPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import moduli.Controller;
import moduli.StreamerListener;
import moduli.Style;
import moduli.streamer.NewsStreamer;
import moduli.streamer.NewsStreamer.Post;

/**
 *
 * @author Simone
 */
public class NewsPanel extends LauncherPanel implements StreamerListener{
    
    private Controller controller;
    private LauncherPanel contentPane;

    public NewsPanel(Controller controller) {
        this.controller = controller;
        this.setLayout(new GridLayout(1,1));
        
        final NewsPanel np = this;
        
        build(null);
        
        
        //custom font
//        try {
//             GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//             ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("A.ttf"));
//        } catch (IOException|FontFormatException e) {
//             //Handle exception
//        }
        
        
        
        
        
        Thread t = new Thread(){
         @Override
         public void run(){
             NewsStreamer sl = new NewsStreamer(np);
             sl.getData();
         }
        };
        //SwingUtilities.invokeLater(t);
        t.start();
        
//        Font[] list = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
//        for(Font f:list){
//            System.out.println(f.getFontName());
//        }
    }
    
    
    
    @Override
    public void refreshData(List l) {
        List<Post>  list = (List<Post>) l;
        LauncherPanel[] pList = new LauncherPanel[list.size()];
        for(int i=0; i<pList.length; i++){
            LauncherPanel panel = new LauncherPanel();
            panel.setLayout(new BorderLayout());
            final Post p = list.remove(0);
            LauncherPanel header = new LauncherPanel();
            header.setLayout(new BorderLayout());
            JLabel title = new JLabel(p.getTitle());
            
            title.setForeground(Style.titleNewsColor);
            title.setFont(Style.titleNewsFont);
            
            title.addMouseListener(new MouseListener(){

                @Override
                public void mouseClicked(MouseEvent e) {
                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (Desktop.isDesktopSupported()) {
                      try {
                        Desktop.getDesktop().browse(new URI(p.getLink()));
                      } catch (IOException ex) { /* TODO: error handling */ } catch (URISyntaxException ex) {
                            Logger.getLogger(NewsPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else { /* TODO: error handling */ }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }
            });
            JLabel date = new JLabel(p.getDate().toString());
            
            date.setForeground(Style.dateNewsColor);
            date.setFont(Style.dateFont);
            
            header.add(title, BorderLayout.CENTER);
            header.add(date, BorderLayout.SOUTH);
            
            LauncherPanel text = new LauncherPanel();
            text.setLayout(new GridLayout(1,1));
            //JTextArea tp = new JTextArea(p.getContent());
            JEditorPane tp = new JEditorPane("text/html",Style.preNewsPosts+p.getContent()+ Style.postNewsPosts);
            //tp.setText(p.getContent());
            tp.setEditable(false);
            tp.setBackground(new Color(0,0,0,0));
            tp.setOpaque(false);
            
            //tp.setLineWrap(true);
            //tp.setWrapStyleWord(true);
            text.add(tp);
            
            panel.add(header, BorderLayout.NORTH);
            panel.add(text, BorderLayout.CENTER);
            
            pList[i] = panel;
            
        }
        build(pList);
    }

    private void build(LauncherPanel[] list) {
        this.removeAll();
        if(list!=null){
            
            contentPane = new LauncherPanel();
            //contentPane.setPreferredSize(new Dimension(500, 400));
            //contentPane.add(scrollPane);
            
            
        
            //FlowLayout l = new FlowLayout(FlowLayout.LEFT);
            contentPane.setAutoscrolls(true);
            contentPane.setBorder(new EmptyBorder(10,10,10,10));
            contentPane.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.anchor = GridBagConstraints.NORTHWEST;
            gbc.weightx = 0.0;
            gbc.weighty = 0.0;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.ipady = 10;
            
            
            for(LauncherPanel p:list){
                contentPane.add(p, gbc);
            }
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            contentPane.add(new JLabel(), gbc);
            
            JScrollPane scrollPane = new JScrollPane(contentPane);
            //scrollPane.setBounds(0, 0, 50, 100);
            scrollPane.setOpaque(false);
            //scrollPane.getViewport().setOpaque(false);
            scrollPane.getViewport().setBackground(new Color(0,0,0,70));
            scrollPane.setBorder(null);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            
            this.add(scrollPane);
        }
        //contentPane.revalidate();
        revalidate();
        //repaint();
    }
}
