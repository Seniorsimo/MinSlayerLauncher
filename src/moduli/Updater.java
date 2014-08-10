/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moduli;

import java.util.HashMap;

/**
 *
 * @author simone
 */
public abstract class Updater {
            
    public static boolean update(Updater up){
        if(up.checkVersion()){
            if(!up.download()) return false;
            if(!up.install()) return false;
        }
        
        return true;
    }
    
    public static void reboot(){
        
    }
    
    public abstract boolean checkVersion();
    public abstract boolean download();
    public abstract boolean install();
}
