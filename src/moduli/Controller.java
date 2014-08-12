/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moduli;

import gui.MainDialog;
import gui.MainWindow;
import gui.bar.dialog.PasswordDialog;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import moduli.updater.GameUpdater;
import moduli.updater.LauncherUpdater;

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
        if(accounts==null)
            accounts = new ArrayList<>();
        String lastUser = (String) DataManager.getDataManager().load("last_user");
        if(lastUser!=null)
            account = (Account) DataManager.getDataManager().load(lastUser);
        if(account==null)
            account = new Account(defaultOption, "guest");
        
        //infine avvia la gui
        new MainWindow(this);
        
        
        
    }
    
    public void checkUpdate(UpdateListener lp){
        //launcher
        if(!Updater.update(new LauncherUpdater(lp))){
            System.out.println("Error occurred while trying to donwload launcher update.");
        }
        
        //client
        if(!Updater.update(new GameUpdater(lp))){
            System.out.println("Error occurred while trying to donwload game update.");
        }
        
    }
    
    public void launch(){
        
        ProcessLauncher pl = new ProcessLauncher(this);
        
        LoginManager lm = new LoginManager();
        Account a = getAccount();
        lm.setUsername(a.getAccount());
        lm.setAccessToken(a.getAccessToken());
        lm.setClientToken(a.getClientToken());
        
        boolean success = false;
        if(lm.keyLogin()){
            success = true;
        }
        
        else{
            //richiedi password
            String pass = askPassword();
            
            lm.setPassword(pass);
            if(lm.passLogin()){
                success = true;
            }
        }
        
        if(success){
            a.setAccessToken(lm.getAccessToken());
            a.setClientToken(lm.getClientToken());
            DataManager.getDataManager().save(a, a.getAccount());
            
            pl.setUsername(lm.getMinecraftName());
            pl.setSessionKey(lm.getAccessToken());
            
            pl.launch();
        }
        else{
            System.out.println("Errore: impossibile avviare il client: errore nel login.");
        }
    }
    
    private String askPassword() {
        PasswordDialog pd = new PasswordDialog();
        return pd.getAnswer();
    }
    
    public void editProfile(String username){
        DataManager.getDataManager().remove(account.getAccount());
        accounts.remove(account.getAccount());
        account.setAccount(username);
        DataManager.getDataManager().save(account, account.getAccount());
        accounts.add(account.getAccount());
        DataManager.getDataManager().save(accounts, "saved_accounts");
        DataManager.getDataManager().save(account.getAccount(), "last_user");
    }
    
    public void deleteProfile(){
        DataManager.getDataManager().remove(account.getAccount());
        accounts.remove(account.getAccount());
        DataManager.getDataManager().save(accounts, "saved_accounts");
        if(accounts.size()>0)
            account = (Account) DataManager.getDataManager().load(accounts.get(0));
        else
            account = new Account(defaultOption, "guest");
        DataManager.getDataManager().save(account.getAccount(), "last_user");
    }
    
    public void createProfile(String username){
        account = new Account(defaultOption, username);
        DataManager.getDataManager().save(account, account.getAccount());
        DataManager.getDataManager().save(account.getAccount(), "last_user");
        accounts.add(account.getAccount());
        DataManager.getDataManager().save(accounts, "saved_accounts");
        
        //aggiornare???
        
    }
    
    public void selectProfile(String username){
        account = (Account) DataManager.getDataManager().load(username);
        DataManager.getDataManager().save(account.getAccount(), "last_user");
    }
    
    public void saveSetting(){
        DataManager.getDataManager().save(account, account.getAccount());
    }
    
    public String[] getProfileList(){
        return accounts.toArray(new String[accounts.size()]);
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
