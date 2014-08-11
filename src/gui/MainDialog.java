/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javax.swing.JDialog;
import moduli.Style;

/**
 *
 * @author simone
 */
public class MainDialog extends JDialog {
    public MainDialog(){
        super();
        this.setModal(true);
        this.getContentPane().setBackground(Style.mainBackground);
        
        //setting dei dialog

    }
}
