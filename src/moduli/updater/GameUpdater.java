/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moduli.updater;

import java.util.HashMap;
import moduli.DataManager;
import moduli.Updater;

/**
 *
 * @author simone
 */
public class GameUpdater extends Updater{
    
    private HashMap versions;
    
    public GameUpdater(){
        versions = (HashMap) DataManager.getDataManager().load("versions");
        if(versions==null){
            versions = new HashMap();
        }
    }

    @Override
    public boolean checkVersion() {
        //scaricare il file versions
        
        //parsificarne il contenuto in una lista di oggetti "versione"?
        
        //verificare
        //se non presente in versions || presente con una versione inferiore
            //inserisci in tabella daaggiornare
        
        //se daaggiornare è vuoto restituisci false, altrimenti true
        return false;
    }

    @Override
    public boolean download() {
        //se daaggiornare è vuoto restituisci true
        
        //per ogni elemento scaricalo e salvalo come temp.
        
        //se errori false, altrimenti true.
        
        return false;
    }

    @Override
    public boolean install() {
        //per ogni elemento in attesa di installazione aggungilo
        
        //come fare x file "da rimuovere" se vechi e non più necessari?
        
        //se errori false altriemnti true
        
        return false;
    }
    
}
