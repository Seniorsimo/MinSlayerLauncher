/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.bar;

import gui.LauncherPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import moduli.Controller;

/**
 *
 * @author simone
 */
public class LaunchPanel extends LauncherPanel{
    
    private Controller controller;
    private JButton editButton, newButton, launchButton;
    private JComboBox<String> profileSelect;
    
    public LaunchPanel(Controller controller){
        super();
        this.controller = controller;
        
        //costruzione
        editButton = new JButton("Edit");
        newButton = new JButton("Create new");
        String[] temp = controller.getProfileList();
        if(temp.length==0){
            temp = new String[]{"Please create a profile"};
        }
        profileSelect = new JComboBox(temp);
        profileSelect.setSelectedItem(controller.getAccount().getAccount());
        LauncherPanel row1 = new LauncherPanel();
        row1.setLayout(new FlowLayout());
        row1.add(profileSelect);
        row1.add(editButton);
        row1.add(newButton);
        
        launchButton = new JButton("Launch");
        LauncherPanel row2 = new LauncherPanel();
        row2.setLayout(new BorderLayout());
        row2.add(launchButton, BorderLayout.CENTER);
        
        this.setLayout(new GridLayout(2,1));
        this.add(row1);
        this.add(row2);
        
    }
    
}
