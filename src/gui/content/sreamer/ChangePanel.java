/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.content.sreamer;

import gui.LauncherPanel;
import java.util.List;
import moduli.Controller;
import moduli.StreamerListener;
import moduli.streamer.NewsStreamer;

/**
 *
 * @author Simone
 */
public class ChangePanel extends LauncherPanel implements StreamerListener{
    
    private Controller controller;

    public ChangePanel(Controller controller) {
        this.controller = controller;
    }
    
    
    
    @Override
    public void refreshData(List l) {
        
        
    }
}
