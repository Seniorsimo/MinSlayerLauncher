/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.content;

import gui.LauncherPanel;
import gui.content.sreamer.ChangePanel;
import gui.content.sreamer.NewsPanel;
import java.awt.BorderLayout;
import java.util.List;
import moduli.Controller;
import moduli.StreamerListener;
import moduli.streamer.NewsStreamer;
import moduli.streamer.NewsStreamer.Post;

/**
 *
 * @author Simone
 */
public class StreamerPanel extends LauncherPanel{
    
    private Controller controller;
    private LauncherPanel bar, news, change;
    private int showing = 0;
    
    public StreamerPanel(Controller controller){
        super();
        this.controller = controller;
        
        bar = new LauncherPanel();
        news = new NewsPanel(controller);
        change = new ChangePanel(controller);
        
        this.setLayout(new BorderLayout());
        
        refreshView(0);
        
        
    }

    private void refreshView(int i) {
        this.removeAll();
        this.add(bar, BorderLayout.NORTH);
        if(showing==0) this.add(news, BorderLayout.CENTER);
        else this.add(change, BorderLayout.CENTER);
        revalidate();
    }

}
