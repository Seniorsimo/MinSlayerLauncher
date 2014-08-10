/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.bar;

import gui.LauncherPanel;
import gui.bar.dialog.EditProfileDialog;
import gui.bar.dialog.NewProfileDialog;
import gui.bar.dialog.OptionDialog;
import java.awt.BorderLayout;
import java.awt.Dimension;
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
    private JButton editButton, newButton, optionButton,  launchButton;
    private JComboBox<String> profileSelect;
    private LauncherPanel row1;
    
    public LaunchPanel(final Controller controller, int width){
        super();
        this.controller = controller;
        
        //costruzione
        this.setPreferredSize(new Dimension(280, 100));
        row1 = new LauncherPanel();
        
        editButton = new JButton("Edit");
        editButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                new EditProfileDialog(controller, (String)profileSelect.getSelectedItem());
                refreshList();
            }
        });
        newButton = new JButton("New");
        newButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                new NewProfileDialog(controller);
                refreshList();
            }
        });
        
        optionButton = new JButton("Setting");
        optionButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                new OptionDialog(controller);
            }
        });
        
        
        row1.setLayout(new FlowLayout());
        row1.setPreferredSize(new Dimension(280,50));
        
        refreshList();
        
        
        
        launchButton = new JButton("Launch");
        LauncherPanel row2 = new LauncherPanel();
        row2.setLayout(new BorderLayout());
        row2.add(launchButton, BorderLayout.CENTER);
        
        this.setLayout(new GridLayout(2,1));
        this.add(row1);
        this.add(row2);
        
    }
    
    private void refreshList(){
        String[] temp = controller.getProfileList();
        if(temp.length==0){
            temp = new String[]{"<empty>"};
        }
        profileSelect = new JComboBox(temp);
        profileSelect.setSelectedItem(controller.getAccount().getAccount());
        profileSelect.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                controller.selectProfile((String)profileSelect.getSelectedItem());
            }
        });
        row1.removeAll();
        row1.add(profileSelect);
        row1.add(editButton);
        row1.add(newButton);
        row1.add(optionButton);
        row1.revalidate();
    }
    
}
