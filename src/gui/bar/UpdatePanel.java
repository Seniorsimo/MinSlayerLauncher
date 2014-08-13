/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.bar;

import gui.LauncherPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.geom.Point2D;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ProgressBarUI;
import javax.swing.plaf.basic.BasicProgressBarUI;
import moduli.Controller;
import moduli.Style;
import moduli.UpdateListener;

/**
 *
 * @author simone
 */
public class UpdatePanel extends LauncherPanel implements UpdateListener{
    
    private Controller controller;
    private JLabel string1, string2;
    private JProgressBar bar1, bar2;
    private String totalFile;
    private boolean downloading = false;
    /*
     * gestire le due barre:
     * barra1 70% download, 20 install, 10 cleaning
     * barra2 currentDownl, installAll, CleanAll
     */
    
    public UpdatePanel(final Controller controller){
        super();
        this.controller = controller;
        
        //inserire upload
        this.setLayout(new GridLayout(4,1));
        
        string1 = new JLabel("");
        string1.setForeground(Style.mainForeground);
        string2 = new JLabel("");
        string2.setForeground(Style.mainForeground);
        bar1 = new LauncherBar();
        bar2 = new LauncherBar();
        
        
        this.setBorder(new EmptyBorder(10,10,10,10));
        
        showPanel();
        final UpdatePanel up = this;

        Thread t = new Thread(){
         @Override
         public void run(){
             controller.checkUpdate(up);
         }
        };
        //SwingUtilities.invokeLater(t);
        t.start();
    }
    
    private class LauncherBar extends JProgressBar{
        public LauncherBar(){
            super(0,100);
            
            this.setValue(0);
            this.setStringPainted(true);
            this.setOpaque(false);
            //bar1.setSize(280, 20);
            //current.setLocation(10, 140);
            this.setForeground(Style.barForeground);
            this.setBackground(Style.barBackground);
            
            ProgressBarUI pb = new BasicProgressBarUI(){
                
                @Override
                public void paint(Graphics g, JComponent u){
                    super.paint(g, u);
                    Insets insets = super.progressBar.getInsets();
                    int w = super.getAmountFull(insets, super.progressBar.getWidth(), super.progressBar.getHeight());
                    if ((g instanceof Graphics2D)) {
                    Graphics2D gg = (Graphics2D) g;
                    
                    gg.setPaint(new GradientPaint(new Point2D.Float(0.0F, 0.0F), new Color(0,0,0,100), new Point2D.Float(0.0F, getHeight()/2), u.getBackground()));
                    gg.fillRect(0, 0, w, getHeight()/2);

                    gg.setPaint(new GradientPaint(new Point2D.Float(0.0F, getHeight()/2), u.getBackground(), new Point2D.Float(0.0F, getHeight()), new Color(0,0,0,100)));
                    gg.fillRect(0, getHeight()/2, w, getHeight()/2);
                    }
                }
            };
            this.setUI(pb);
        }
        
        @Override
            protected void paintComponent(Graphics g)
            {
                g.setColor( getBackground() );
                g.fillRect(0, 0, getWidth(), getHeight());
                
                if ((g instanceof Graphics2D)) {
                    Graphics2D gg = (Graphics2D) g;
                    
                    gg.setPaint(new GradientPaint(new Point2D.Float(0.0F, 0.0F), new Color(0,0,0,200), new Point2D.Float(0.0F, getHeight()/2), getBackground()));
                    gg.fillRect(0, 0, getWidth(), getHeight()/2);

                    gg.setPaint(new GradientPaint(new Point2D.Float(0.0F, getHeight()/2), getBackground(), new Point2D.Float(0.0F, getHeight()), new Color(0,0,0,200)));
                    gg.fillRect(0, getHeight()/2, getWidth(), getHeight()/2);
                }
                
                super.paintComponent(g);
            }
    }

    @Override
    public void refreshStatus(String status, String currentIndex, String total, String nameFile, double percent) {
        int x,y,z,temp;
        z = 0;
        switch(status){
            case "cheking":
                downloading = false;
                string1.setText("Looking for update...");
                string2.setText("");
                bar1.setValue(0);
                bar2.setValue(0);
                totalFile = "";
                break;
            case "setup":
                downloading = false;
                totalFile = total;
                break;
            case "download":
                downloading = true;
                string1.setText("Download "+ currentIndex + "/"+ total);
                x = (int) ((int)(Integer.parseInt(currentIndex)*100.0/Integer.parseInt(total))/100.0*90);
                if(x<0) x = 0;
                if(x>90) x = 90;
                bar1.setValue(x);
                string2.setText("Downloading "+nameFile);
                y = (int) percent;
                if(y<0) y = 0;
                if(y>100) y = 100;
                bar2.setValue(y);
                break;
            case "download2":
                downloading = true;
                y = (int) percent;
                if(y<0) y = 0;
                if(y>100) y = 100;
                bar2.setValue(y);
                break;
            case "install":
                downloading = true;
                z = 1;
                string1.setText("Installation "+ z + "/" + totalFile);
                bar1.setValue(90);
                string2.setText("");
                bar2.setValue(0);
                break;
            case "install2":
                downloading = true;
                z++;
                string1.setText("Installation "+ z + "/" + totalFile);
                temp = (int) (z*100.0/Integer.parseInt(totalFile));
                x = (int) (temp/100.0*8);
                if(x<0) x = 0;
                if(x>8) x = 8;
                bar1.setValue(90+x);
                string2.setText("Installing: "+ nameFile);
                bar2.setValue(temp);
                break;
            case "ending":
                downloading = true;
                string1.setText("Removing temporary files ");
                bar1.setValue(98);
                string2.setText("Tuning your installation....");
                bar2.setValue(0);
                break;
            case "ending2":
                downloading = true;
                string1.setText("Removing temporary files: " + currentIndex + "/" + total);
                temp = (int) (Integer.parseInt(currentIndex)*100.0/Integer.parseInt(totalFile));
                x = (int) (temp/100.0*2);
                if(x<0) x = 0;
                if(x>2) x = 2;
                bar1.setValue(98+x);
                bar2.setValue(temp);
                break;
            case "end":
                downloading = false;
                string1.setText("Your Client is up to date!");
                bar1.setValue(100);
                string2.setText("");
                bar2.setValue(0);
                break;
            case "closing":
                downloading = true;
                string1.setText("Closing...");
                break;
            default:
                System.out.println("Error updating interface during downloading!");

        }
        showPanel();
    }

    private void showPanel() {
        this.removeAll();
        this.add(string1);
        if(downloading){
            this.add(bar1);
            this.add(string2);
            this.add(bar2);
        }
        this.revalidate();
    }
    
}
