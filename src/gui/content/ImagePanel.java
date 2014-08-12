/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.content;

import gui.LauncherPanel;
import javax.swing.border.EmptyBorder;
import moduli.Controller;

/**
 *
 * @author Simone
 */
class ImagePanel extends LauncherPanel {
    
    private Controller controller;

    public ImagePanel(Controller controller) {
        super();
        this.controller = controller;
        this.setBorder(new EmptyBorder(10,10,10,10));
    }
    
}
