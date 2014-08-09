/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.bar.dialog;

import gui.LauncherPanel;
import gui.MainDialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JTextField;
import moduli.Controller;

/**
 *
 * @author simone
 */
public class EditProfileDialog extends MainDialog{
    
    private Controller controller;
    JTextField name;
    
    public EditProfileDialog(Controller controller, String email){
        super();
        this.controller = controller;

        this.setTitle("Edit account");
        this.setLayout(new GridLayout(2,1));
        name = new JTextField(40);
        name.setText(email);
        name.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                confirm();
            }
        });
        this.add(name);
        JButton ok = new JButton("Ok");
        ok.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                confirm();
            }
        });
        JButton delete = new JButton("Delete");
        delete.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                delete();
            }
        });
        LauncherPanel temp = new LauncherPanel();
        temp.setLayout(new GridLayout(1,2));
        temp.add(ok);
        temp.add(delete);
        this.add(temp);
        this.pack();
        this.setVisible(true);
    }
    
    private void confirm(){
        controller.editProfile(name.getText());
        this.setVisible(false);
    }
    private void delete(){
        controller.deleteProfile();
        this.setVisible(false);
    }
    
}
