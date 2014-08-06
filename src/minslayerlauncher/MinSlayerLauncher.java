/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minslayerlauncher;

import moduli.ProcessLauncher;

/**
 *
 * @author simone
 */
public class MinSlayerLauncher {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ProcessLauncher pc = new ProcessLauncher();
        pc.setUsername("seniorsimo");
        //pc.setSessionKey("294c588d44ea4324a389587f0e7f9d02");
        pc.setSessionKey("e2d273b483aa466d9cd85f2ad47dc036");
        pc.launch();
    }
}
