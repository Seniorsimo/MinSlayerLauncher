/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import gui.bar.BarPanel;
import gui.content.ContentPanel;
import java.awt.BorderLayout;
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
        
        public MainPanel(Controller controller){
            super();
            this.controller = controller;
            this.setOpaque(true);
            this.setBackground(Style.mainBackground);
            
            //parametri vari
            
            //costruzione
            content = new ContentPanel(controller);
            bar = new BarPanel(controller);
            
            this.setLayout(new BorderLayout());
            this.add(content, BorderLayout.CENTER);
            this.add(bar, BorderLayout.SOUTH);
        }
        
    }
    
}
