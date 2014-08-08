/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moduli;

import java.util.ArrayList;

/**
 *
 * @author simone
 */
public class Controller {
    private state status = state.loading;
    private Account account;
    private boolean showingChangelog = false;
    private ArrayList<String> accounts;
    private OptionManager defaultOption = new OptionManager(false, 800, 600, "ultra", "1024");
    
    public Controller(){
        accounts = (ArrayList<String>) DataManager.getDataManager().load("saved_accounts");
        if(account==null)
            accounts = new ArrayList<>();
    }
    
    public void launch(){
        //rivedi process launcher in modo che prenda tutti i parametri veramete richiesti
    }
    
    public void editProfile(){
        
    }
    
    public void createProfile(String username){
        account = new Account(defaultOption, username);
        DataManager.getDataManager().save(account, account.getAccount());
        accounts.add(account.getAccount());
        DataManager.getDataManager().save(accounts, "saved_accounts");
        
        //aggiornare???
        
    }
    
    public void showNews(){
        
    }
    
    public void showChangelog(){
        
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
    
    private static enum state{
        loading, idle, logging, waiting, option, running;
    }
}
