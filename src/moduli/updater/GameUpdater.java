/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moduli.updater;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import moduli.DataManager;
import moduli.ProcessLauncher;
import moduli.UpdateListener;
import moduli.Updater;

/**
 *
 * @author simone
 */
public class GameUpdater extends Updater{
    
    private HashMap versions;
    private HashMap toUpdate, fileList;
    private ArrayList<String> toUpdateUrls;
    private String baseUrl = "https://dl.dropboxusercontent.com/u/238575247/minslayer/";
    private UpdateListener gui;
    private boolean interrupted = false;
    
    public GameUpdater(UpdateListener lp){
        this.gui = lp;
        versions = (HashMap) DataManager.getDataManager().load("versions");
        if(versions==null){
            versions = new HashMap();
        }
        toUpdate = new HashMap();
        fileList = new HashMap();
        toUpdateUrls = new ArrayList<>();
    }

    @Override
    public boolean checkVersion() {
        gui.refreshStatus("cheking", "0", "0", "", 0);
        try {
            //scaricare il file versions
            URL versionUrl = new URL(baseUrl + "version.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(versionUrl.openStream()));
            //parsificarne il contenuto in una lista di oggetti "versione"?
            if(interrupted) return false;
            ArrayList<String> newVersions = new ArrayList<>();
            String line;
            while((line = in.readLine())!=null){
                newVersions.add(line);
            }

        //verificare
        //se non presente in versions || presente con una versione inferiore
            for(String newVersion:newVersions){
            //inserisci in tabella daaggiornare
                String path = newVersion.substring(0, newVersion.lastIndexOf("#"));
                String version = newVersion.substring(newVersion.lastIndexOf("#")+1);
                //System.out.println(path + "  " + version);
                if(!versions.containsKey(path)||Long.parseLong((String)versions.get(path))<Long.parseLong(version)){
                    toUpdate.put(path, version);
                    toUpdateUrls.add(path);
                }
                fileList.put(path, version);
                if(interrupted) return false;
            }
        //se daaggiornare è vuoto restituisci false, altrimenti true
        } catch (MalformedURLException ex) {
            Logger.getLogger(GameUpdater.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(GameUpdater.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        if(toUpdateUrls.isEmpty()) gui.refreshStatus("end", "0", "0", "", 100);
        else gui.refreshStatus("setup", "0", ""+toUpdateUrls.size(), "", 0);
        return !toUpdateUrls.isEmpty();
    }

    @Override
    public boolean download() {
        boolean error = false;
        //se daaggiornare è vuoto restituisci true
        if(toUpdateUrls.isEmpty()) return true;
        //gui.refreshStatus("download", "0", "0", "", 100);
        int counter = 1;
        int total = toUpdateUrls.size();
        //per ogni elemento scaricalo e salvalo come temp.
        while(!toUpdateUrls.isEmpty()){
            String url = toUpdateUrls.remove(0);
            gui.refreshStatus("download", ""+counter, total+"", url, 0);
            if(!download(url))
                error = true;
            //versions.remove(url);
            gui.refreshStatus("download", ""+counter, total+"", url, 100);
            counter++;
            if(interrupted) return false;
        }
        
        //se errori false, altrimenti true.
        
        return !error;
    }

    @Override
    public boolean install() {
        boolean error = false;
        //per ogni elemento in attesa di installazione aggungilo
        gui.refreshStatus("install", "", "", "", 0);
        if(!install(ProcessLauncher.getWorkingDirectory())) error = true;
        
        //come fare x file "da rimuovere" se vechi e non più necessari?
        Iterator index = fileList.entrySet().iterator();
        HashMap fileList2 = new HashMap();
        while(index.hasNext()){
            Map.Entry pairs = (Map.Entry) index.next();
            versions.remove((String)pairs.getKey());
            fileList2.put((String)pairs.getKey(), (String)pairs.getValue());
            index.remove();
        }
        
        gui.refreshStatus("ending", "", "", "", 0);
        if(!versions.isEmpty()){
            Iterator it = versions.entrySet().iterator();
            int total = versions.size();
            int current = 1;
            while (it.hasNext()) {
                gui.refreshStatus("ending2", ""+current, ""+total, "", 0);
                Map.Entry pairs = (Map.Entry)it.next();
                //System.out.println(pairs.getKey() + " = " + pairs.getValue());
                remove((String)pairs.getKey());
                current++;
                it.remove(); // avoids a ConcurrentModificationException
            }
        }
        
        //no interruzione: salvo lista completa
        versions = fileList2;
        
        //se interrotto version deve contenere solo i realmente scaricati, quindi rimuovo i non scaricati
        if(interrupted){
            if(toUpdateUrls!=null){
                while(!toUpdateUrls.isEmpty()){
                    String temp = toUpdateUrls.remove(0);
                    if(versions.containsKey(temp))
                        versions.remove(temp);
                }
            }
        }
        
        DataManager.getDataManager().save(versions, "versions");
        gui.refreshStatus("end", "0", "0", "", 100);
        //se errori false altriemnti true
        
        return !error;
    }
    
    private boolean download(String u){
        boolean errorOccurred = false;
        try {
            URL url = new URL(baseUrl + "versioned/"+ u);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            int filesize = connection.getContentLength();
            float totalDataRead = 0;
            BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
            File dest = new File(ProcessLauncher.getWorkingDirectory().toURI().getPath() + u + ".temp");
            dest.getParentFile().mkdirs();
            if(!dest.exists()) dest.createNewFile();
            FileOutputStream fos = new FileOutputStream(dest);
            BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
            byte[] data = new byte[1024];
            int i = 0;
            while ((i = in.read(data, 0, 1024)) >= 0) {
                totalDataRead = totalDataRead + i;
                bout.write(data, 0, i);
                float Percent = (totalDataRead * 100) / filesize;
                gui.refreshStatus("download2", "", "", "", Percent);
            }
            bout.close();
            in.close();
            
        } catch (MalformedURLException ex) {
            System.out.println("Error: wrong url.\n" + ex.toString());
            errorOccurred = true;
        } catch (IOException ex) {
            System.out.println("Error: I/O.\n" + ex.toString());
            errorOccurred = true;
        }
        
        System.out.println("Successfully downloaded: " + baseUrl + u);
        return !errorOccurred;
    }
    
    private boolean install(File dir){
        boolean error = false;
        if(!dir.exists()) dir.mkdir();
        File[] list = dir.listFiles();
        for(File f:list){
            if(f.isDirectory())install(f);
            else if(f.getName().contains(".temp")){
                File old = new File(f.toURI().getPath().substring(0, f.toURI().getPath().lastIndexOf(".temp")));
                if(old.exists()) if(!old.delete()) error = true;
                f.renameTo(old);
                gui.refreshStatus("install2", "", "", old.toURI().getPath(), 0);
                System.out.println("Installed: " + old.toURI().getPath());
            }
        }
        return !error;
    }
    
    private void remove(String path){
        File f = new File(ProcessLauncher.getWorkingDirectory().toURI().getPath() + path);
        if(f.exists()) f.delete();
    }

    @Override
    public void close() {
        interrupted = true;
        gui.refreshStatus("closing", "0", "0", "", 100);
    }
}
