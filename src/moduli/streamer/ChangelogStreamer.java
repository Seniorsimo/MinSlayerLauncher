/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moduli.streamer;

import moduli.Streamer;
import moduli.StreamerListener;

/**
 *
 * @author simone
 */
public class ChangelogStreamer extends Streamer{
    
    private StreamerListener gui;

    public ChangelogStreamer(StreamerListener gui) {
        this.gui = gui;
    }
    
    

    @Override
    public void getData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
