/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.bar.dialog;

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
public class NewProfileDialog extends MainDialog{
    
    private Controller controller;
    JTextField name;
    
    public NewProfileDialog(Controller controller){
        super();
        this.controller = controller;

        this.setTitle("Insert email address");
        this.setLayout(new GridLayout(2,1));
        name = new JTextField(40);
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
        this.add(ok);
        this.pack();
        this.setVisible(true);
    }
    
    private void confirm(){
        controller.createProfile(name.getText());
        this.setVisible(false);
    }
    
}
