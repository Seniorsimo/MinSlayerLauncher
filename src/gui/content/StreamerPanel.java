/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.content;

import gui.LauncherPanel;
import gui.content.sreamer.ChangePanel;
import gui.content.sreamer.NewsPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import moduli.Controller;
import moduli.Style;

/**
 *
 * @author Simone
 */
public class StreamerPanel extends LauncherPanel{
    
    private Controller controller;
    private LauncherPanel bar, news, change;
    private int showing = 0;
    private JButton bt;
    
    public StreamerPanel(Controller controller){
        super();
        this.controller = controller;
        
        bar = new LauncherPanel();
        bar.setLayout(new BorderLayout());
        bt = new JButton();
        bt.setFont(Style.buttonFont);
        bt.setBackground(Style.buttonBackground);
        bt.setForeground(Style.buttonForeground);
        bt.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanel();
            }
        });
        bar.add(bt, BorderLayout.EAST);
        news = new NewsPanel(controller);
        change = new ChangePanel(controller);
        
        this.setLayout(new BorderLayout());
        
        refreshView();
        
        
    }

    private void refreshView() {
        this.removeAll();
        this.add(bar, BorderLayout.NORTH);
        if(showing==0){
            this.add(news, BorderLayout.CENTER);
            bt.setText("Changelog");
        }
        else{
            this.add(change, BorderLayout.CENTER);
            bt.setText("News");
        }
        this.revalidate();
        this.repaint();
        
        //news.revalidate();
    }
    
    private void switchPanel(){
        if(showing==0) showing=1;
        else showing=0;
        refreshView();
    }

}
