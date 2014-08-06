/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moduli;

/**
 *
 * @author simone
 */
public abstract class Updater {
        
    public static boolean update(Updater up){
        
        return false;
    }
    
    public static void reboot(){
        
    }
    
    public abstract boolean checkVersion();
    public abstract boolean download();
    public abstract boolean install();
}
