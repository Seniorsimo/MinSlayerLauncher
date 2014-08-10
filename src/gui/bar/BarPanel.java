/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.bar;

import gui.LauncherPanel;
import java.awt.BorderLayout;
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
        System.out.println(this.getWidth());
        
        this.setLayout(new BorderLayout());
        this.add(update, BorderLayout.CENTER);
        this.add(launch, BorderLayout.EAST);
        
    }
}
