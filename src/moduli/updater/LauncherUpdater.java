/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moduli.updater;

import moduli.Updater;

/**
 *
 * @author simone
 */
public class LauncherUpdater extends Updater{
    
    private long launcherVersion = 111;
    
    public LauncherUpdater(){
        
    }

    @Override
    public boolean checkVersion() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean download() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean install() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
