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
import javax.swing.border.EmptyBorder;
import moduli.Controller;
import moduli.Style;

/**
 *
 * @author simone
 */
public class LaunchPanel extends LauncherPanel{
    
    private Controller controller;
    private JButton editButton, newButton, optionButton,  launchButton;
    private JComboBox<String> profileSelect;
    private LauncherPanel row1, row1Bis;
    
    public LaunchPanel(final Controller controller, int width){
        super();
        this.controller = controller;
        this.setBorder(new EmptyBorder(10,10,10,10));
        
        //costruzione
        this.setPreferredSize(new Dimension(280, 120));
        row1 = new LauncherPanel();
        
        editButton = new JButton("Edit");
        editButton.setFont(Style.buttonFont);
        editButton.setBackground(Style.buttonBackground);
        editButton.setForeground(Style.buttonForeground);
        editButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                new EditProfileDialog(controller, (String)profileSelect.getSelectedItem());
                refreshList();
            }
        });
        newButton = new JButton("New");
        newButton.setFont(Style.buttonFont);
        newButton.setBackground(Style.buttonBackground);
        newButton.setForeground(Style.buttonForeground);
        newButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                new NewProfileDialog(controller);
                refreshList();
            }
        });
        
        optionButton = new JButton("Setting");
        optionButton.setFont(Style.buttonFont);
        optionButton.setBackground(Style.buttonBackground);
        optionButton.setForeground(Style.buttonForeground);
        optionButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                new OptionDialog(controller);
            }
        });
        
        row1Bis = new LauncherPanel();
        FlowLayout fl = new FlowLayout(FlowLayout.LEADING);
        fl.setHgap(0);
        row1Bis.setLayout(fl);
        row1Bis.add(editButton);
        row1Bis.add(newButton);
        row1Bis.add(optionButton);
        
        row1.setLayout(new GridLayout(2,1));
        //row1.setPreferredSize(new Dimension(280,20));
        
        refreshList();
        
        
        
        launchButton = new JButton("Launch");
        launchButton.setFont(Style.buttonFont);
        launchButton.setBackground(Style.buttonBackground);
        launchButton.setForeground(Style.buttonForeground);
        launchButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                controller.launch();
            }
        });
        LauncherPanel row2 = new LauncherPanel();
        row2.setLayout(new BorderLayout());
        row2.add(launchButton, BorderLayout.CENTER);
        
        this.setLayout(new BorderLayout());
        this.add(row1,BorderLayout.CENTER);
        this.add(row2,BorderLayout.SOUTH);
        
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
        row1.add(row1Bis);
        row1.revalidate();
    }
    
}
