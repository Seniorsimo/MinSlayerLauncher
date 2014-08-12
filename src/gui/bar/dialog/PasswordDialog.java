/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.bar.dialog;

import gui.MainDialog;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

/**
 *
 * @author simone
 */
public class PasswordDialog extends MainDialog{
    
    private final String[] pass = new String[1];
    
    public PasswordDialog(){
        this.setModal(true);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        this.add(mainPanel);
        
        mainPanel.add(new JLabel("Please type your password."), BorderLayout.NORTH);
        final JPasswordField password = new JPasswordField(20);
        mainPanel.add(password, BorderLayout.CENTER);
        JButton button = new JButton("Login");
        mainPanel.add(button, BorderLayout.SOUTH);
        final PasswordDialog pd = this;
        button.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                pass[0] = String.copyValueOf(password.getPassword());
                pd.setVisible(false);
            }
        
        });
        password.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                pass[0] = String.copyValueOf(password.getPassword());
                pd.setVisible(false);
            }
        
        });
        this.pack();
        this.setVisible(true);
        this.dispose();
    }
    
    public String getAnswer(){
        return pass[0];
    }
    
}
