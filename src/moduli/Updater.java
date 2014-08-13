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
    
    private static Updater current;
    private static boolean interrupted = false;
            
    public static boolean update(Updater up){
        current = up;
        boolean error = false;
        if(up.checkVersion()){
            //trovato aggiornamento
            if(!up.download()){
                //download fallito
                if(!interrupted){
                    error = true;
                }
            }
            //se non ci sono errori installo
            if(!error && !up.install()){
                //installazione fallita
                error = true;
            }
        }
        if(interrupted){
            //bloccato
            System.out.println("Update interrupted by user");
            error = false;
        }
        return !error;
    }
    
    public static void closeUp(){
        interrupted = true;
        if(current!=null){
            current.close();
        }
    }
    
    public abstract boolean checkVersion();
    public abstract boolean download();
    public abstract boolean install();
    public abstract void close();
}
