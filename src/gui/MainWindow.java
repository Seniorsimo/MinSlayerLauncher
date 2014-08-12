/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import gui.bar.BarPanel;
import gui.content.ContentPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import moduli.Controller;
import moduli.Style;

/**
 *
 * @author simone
 */
public class MainWindow extends JFrame{
    
    private LauncherPanel panel; 
    private Controller controller;
    
    public MainWindow(Controller controller){
        super();
        this.controller = controller;
        //setting varie
        this.setSize(800, 600);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new MainPanel(controller);
        this.getContentPane().add("Center", panel);
        this.setVisible(true);
    }
    
    public class MainPanel extends LauncherPanel{
        
        private Controller controller;
        private LauncherPanel content, bar;
        private Image img;
        private Image bgImage;
        
        public MainPanel(Controller controller){
            super();
            this.controller = controller;
            this.setOpaque(true);
            this.setBackground(Style.mainBackground);
            
            try {
                this.bgImage = ImageIO.read(MainWindow.class.getResource("bg.png")).getScaledInstance(32, 32, 16);
            } catch (IOException e) {
                System.out.println("Error reading image file: bg.png\n" + e.toString());
            }
            
            //parametri vari
            
            //costruzione
            content = new ContentPanel(controller);
            bar = new BarPanel(controller);
            
            this.setLayout(new BorderLayout());
            this.add(content, BorderLayout.CENTER);
            this.add(bar, BorderLayout.SOUTH);
        }
        
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
                        g.drawImage(this.bgImage, x * 32, y * 32, null);
                    }
                }
                
                //full
                //g.drawImage(this.bgImage, 0, 0, w, h, null);
                
                //effects
                if ((g instanceof Graphics2D)) {
                    Graphics2D gg = (Graphics2D) g;
                    int gh = 450;
                    gg.setPaint(new GradientPaint(new Point2D.Float(0.0F, 0.0F), new Color(150,150,150,10), new Point2D.Float(0.0F, gh), new Color(255,255,255,60)));
                    gg.fillRect(0, 0, w, gh);

                    gh = h;
                    gg.setPaint(new GradientPaint(new Point2D.Float(0.0F, 0.0F), new Color(200,200,200,0), new Point2D.Float(0.0F, gh), new Color(0,0,0,190)));
                    gg.fillRect(0, 0, w, gh);
                }
                g.dispose();
            }
            g2.drawImage(this.img, 0, 0, w, h, null);
        }
        
    }
    
}
