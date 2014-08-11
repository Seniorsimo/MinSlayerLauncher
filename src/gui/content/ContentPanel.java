/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.content;

import gui.LauncherPanel;
import java.awt.GridLayout;
import moduli.Controller;

/**
 *
 * @author simone
 */
public class ContentPanel extends LauncherPanel{
    
    private Controller controller;
    
    public ContentPanel(Controller controller){
        super();
        this.controller = controller;
        
        //provvadere all'aggiunta di qualcosa di utile
        this.setLayout(new GridLayout(1,2));
        this.add(new ImagePanel(controller));
        this.add(new StreamerPanel(controller));
    }
    
}
