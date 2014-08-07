/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minslayerlauncher;

import moduli.LauncherLogger;
import moduli.LoginManager;
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
        /*ProcessLauncher pc = new ProcessLauncher();
        pc.setUsername("seniorsimo");
        //pc.setSessionKey("294c588d44ea4324a389587f0e7f9d02");
        pc.setSessionKey("e2d273b483aa466d9cd85f2ad47dc036");
        pc.launch();*/
        
        LauncherLogger lg = new LauncherLogger();
        
        /*LoginManager lm = new LoginManager();
        lm.setUsername("seniorsimo@hotmail.it");
        //lm.setPassword("");
        lm.setAccessToken("51edfbcf43d04b018ca054db927ae7b0");
        lm.setClientToken("dd8265022d194285baa9469590d99276");
        
        //lm.passLogin();
        //lm.keyLogin();
        
        System.out.println("accessToken: "+lm.getAccessToken());
        System.out.println("clientToken: "+lm.getClientToken());
        System.out.println("minecraftName: "+lm.getMinecraftName());
        
        ProcessLauncher pc = new ProcessLauncher();
        pc.setUsername("Seniorsimo");
        //pc.setSessionKey("294c588d44ea4324a389587f0e7f9d02");
        pc.setSessionKey(lm.getAccessToken());
        pc.launch();
        * */
        
        
    }
}
