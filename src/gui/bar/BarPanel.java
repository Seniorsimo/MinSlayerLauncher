/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.bar;

import gui.LauncherPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;
import moduli.Controller;

/**
 *
 * @author simone
 */
public class BarPanel extends LauncherPanel{
    
    private Controller controller;
    private LauncherPanel update, launch;
    
    public BarPanel(Controller controller){
        super();
        this.controller = controller;
        
        //cotruzione
        update = new UpdatePanel(controller);
        launch = new LaunchPanel(controller, this.getWidth());
        Border b1,b2,b3;
        b1 = new MatteBorder(2,0,0,0,new Color(0,0,0));
        b2 = new MatteBorder(1,0,0,0,new Color(30,30,30));
        b3 = new MatteBorder(1,0,0,0,new Color(40,40,40));
        
        this.setBorder(new CompoundBorder(b1,new CompoundBorder(b2,b3)));
        
        this.setLayout(new BorderLayout());
        this.add(update, BorderLayout.CENTER);
        this.add(launch, BorderLayout.EAST);
        
    }
}
