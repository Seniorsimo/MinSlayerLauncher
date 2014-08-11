/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.bar;

import gui.LauncherPanel;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ProgressBarUI;
import javax.swing.plaf.basic.BasicProgressBarUI;
import javax.swing.plaf.metal.MetalProgressBarUI;
import javax.swing.plaf.multi.MultiProgressBarUI;
import javax.swing.plaf.synth.SynthProgressBarUI;
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
        bar1 = new JProgressBar(0, 100);
        bar1.setValue(0);
        bar1.setStringPainted(true);
        bar1.setOpaque(false);
        //bar1.setSize(280, 20);
        //current.setLocation(10, 140);
        bar1.setForeground(Style.barForeground);
        bar1.setBackground(Style.barBackground);
        bar2 = new JProgressBar(0, 100);
        bar2.setValue(0);
        bar2.setStringPainted(true);
        bar2.setOpaque(false);
        //bar1.setSize(280, 20);
        //current.setLocation(10, 140);
        bar2.setForeground(Style.barForeground);
        bar2.setBackground(Style.barBackground);
        
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

    @Override
    public void refreshStatus(String status, String currentIndex, String total, String nameFile, double percent) {
        int x,y,z,temp;
        z = 0;
        switch(status){
            case "cheking":
                string1.setText("Looking for update...");
                string2.setText("");
                bar1.setValue(0);
                bar2.setValue(0);
                totalFile = "";
                break;
            case "setup":
                totalFile = total;
                break;
            case "download":
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
                y = (int) percent;
                if(y<0) y = 0;
                if(y>100) y = 100;
                bar2.setValue(y);
                break;
            case "install":
                z = 1;
                string1.setText("Installation "+ z + "/" + totalFile);
                bar1.setValue(90);
                string2.setText("");
                bar2.setValue(0);
                break;
            case "install2":
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
                string1.setText("Removing temporary files ");
                bar1.setValue(98);
                string2.setText("Tuning your installation....");
                bar2.setValue(0);
                break;
            case "ending2":
                string1.setText("Removing temporary files: " + currentIndex + "/" + total);
                temp = (int) (Integer.parseInt(currentIndex)*100.0/Integer.parseInt(totalFile));
                x = (int) (temp/100.0*2);
                if(x<0) x = 0;
                if(x>2) x = 2;
                bar1.setValue(98+x);
                bar2.setValue(temp);
                break;
            case "end":
                string1.setText("Your Client is up to date!");
                bar1.setValue(100);
                string2.setText("");
                bar2.setValue(0);
                break;
            default:
                System.out.println("Error updating interface during downloading!");

        }
    }

    private void showPanel() {
        this.removeAll();
        this.add(string1);
        this.add(bar1);
        this.add(string2);
        this.add(bar2);
        this.revalidate();
    }
    
}
