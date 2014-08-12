/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.content;

import gui.LauncherPanel;
import gui.MainWindow;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.border.EmptyBorder;
import moduli.Controller;

/**
 *
 * @author Simone
 */
class ImagePanel extends LauncherPanel {
    
    private Controller controller;
    private Image bgImage, img;
    private String baseUrl = "https://dl.dropboxusercontent.com/u/238575247/minslayer/";

    public ImagePanel(Controller controller) {
        super();
        this.controller = controller;
        this.setBorder(new EmptyBorder(10,10,10,10));
        
        Thread t = new Thread(){
            @Override
            public void run(){
                try {
                    bgImage = ImageIO.read(new URL(baseUrl + "main.jpg")).getScaledInstance(377, 427, 32);
                    repaint();
                } catch (IOException e) {
                    System.out.println("Error reading main image from url\n" + e.toString());
                }
            }
        };
        t.start();
    }
    
    @Override
        public void paintComponent(Graphics g2) {
            if(bgImage==null) return;
            int w = getWidth();
            int h = getHeight();
            if ((this.img == null) || (this.img.getWidth(null) != w) || (this.img.getHeight(null) != h)) {
                this.img = createImage(w, h);

                Graphics g = this.img.getGraphics();
                
                //tiled
                /*for (int x = 0; x <= w / 32; x++) {
                    for (int y = 0; y <= h / 32; y++) {
                        g.drawImage(this.bgImage, x * 32, y * 32, null);
                    }
                }*/
                
                //full
                g.drawImage(this.bgImage, 0, 0, w, h, null);
                
                //effects
                /*if ((g instanceof Graphics2D)) {
                    Graphics2D gg = (Graphics2D) g;
                    int gh = 450;
                    gg.setPaint(new GradientPaint(new Point2D.Float(0.0F, 0.0F), new Color(150,150,150,10), new Point2D.Float(0.0F, gh), new Color(255,255,255,60)));
                    gg.fillRect(0, 0, w, gh);

                    gh = h;
                    gg.setPaint(new GradientPaint(new Point2D.Float(0.0F, 0.0F), new Color(200,200,200,0), new Point2D.Float(0.0F, gh), new Color(0,0,0,190)));
                    gg.fillRect(0, 0, w, gh);
                }*/
                g.dispose();
            }
            g2.drawImage(this.img, 10, 10, w-20, h-20, null);
        }
    
}
