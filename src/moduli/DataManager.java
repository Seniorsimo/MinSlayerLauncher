/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moduli;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author simone
 */
public class DataManager {
    
    private static DataManager dm;
    private String path;
    
    private DataManager(){
        path = ProcessLauncher.getWorkingDirectory().toURI().getPath() +  "launcherData/";
        File dir = new File(path);
        if(!dir.exists())
            dir.mkdir();
        
    }
    
    public <S extends Serializable> void save(S data, String key){
        try {
            ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(path + key)));
            out.writeObject(data);
            out.flush();
            out.close();
            System.out.println("Saved " + key);
        } catch (IOException ex) {
            System.out.println("Unable to save. key: " + key);
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Object load(String key){
        try {
            ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(path + key)));
            Object data = in.readObject();
            in.close();
            System.out.println("Loaded " + key);
            return data;
        } catch (IOException ex) {
            System.out.println("Unable to load. key: " + key);
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            System.out.println("Unable to read data: Class not found. key: " + key);
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void remove(String key){
        try{
            File file = new File(path + key);
            if(file.delete()){
                System.out.println(file.getName() + " is deleted!");
            }else{
                System.out.println("Delete operation is failed.");
            }
    	}catch(Exception e){
            e.printStackTrace();
    	}
    }
    
    public static DataManager getDataManager(){
        if(dm==null)
            dm = new DataManager();
        return dm;
    }
}
