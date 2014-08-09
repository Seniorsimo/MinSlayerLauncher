/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.bar;

import gui.LauncherPanel;
import gui.bar.dialog.EditProfileDialog;
import gui.bar.dialog.NewProfileDialog;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    
    public LaunchPanel(final Controller controller){
        super();
        this.controller = controller;
        
        //costruzione
        editButton = new JButton("Edit");
        editButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                new EditProfileDialog(controller, (String)profileSelect.getSelectedItem());
            }
        });
        newButton = new JButton("Create new");
        newButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                new NewProfileDialog(controller);
            }
        });
        String[] temp = controller.getProfileList();
        if(temp.length==0){
            temp = new String[]{"Please create a profile"};
        }
        profileSelect = new JComboBox(temp);
        profileSelect.setSelectedItem(controller.getAccount().getAccount());
        profileSelect.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                controller.selectProfile((String)profileSelect.getSelectedItem());
            }
        });
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
