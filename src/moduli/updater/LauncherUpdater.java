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
import java.util.logging.Level;
import java.util.logging.Logger;
import moduli.ProcessLauncher;
import moduli.UpdateListener;
import moduli.Updater;

/**
 *
 * @author simone
 */
public class LauncherUpdater extends Updater{
    
    private UpdateListener gui;
    private String version = "2.0.1";
    private String baseUrl = "https://dl.dropboxusercontent.com/u/238575247/minslayer/";
    private boolean interrupted = false;
    
    public LauncherUpdater(UpdateListener lp) {
        gui = lp;
        
    }

    @Override
    public boolean checkVersion() {
        try {
            URL versionUrl = new URL(baseUrl + "launcherVersion.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(versionUrl.openStream()));
            if(interrupted) return false;
            String remoteVersion = in.readLine().trim();
            System.out.println("client Launcher Version : " + version);
            System.out.println("server Launcher Version : " + remoteVersion);
            in.close();
            
            if (version.compareTo(remoteVersion) >= 0) {
                System.out.println("No update needed");
                gui.refreshStatus("end", "0", "0", "", 100);
                return false;
            } else {
                System.out.println("New version found. Starting update...");
                gui.refreshStatus("setup", "0", "1", "", 0);
                return true;
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(LauncherUpdater.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(LauncherUpdater.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean download() {
        boolean errorOccurred = false;
        if(interrupted) return false;
        gui.refreshStatus("download", "1", "1", baseUrl + "MinSlayerLauncher.jar", 0);
        //gui.refreshStatus("download", "1", "2", baseUrl + "MinSlayerLauncher.jar", 0);
        try {
            URL url = new URL(baseUrl + "MinSlayerLauncher.jar");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            int filesize = connection.getContentLength();
            float totalDataRead = 0;
            BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
            File dest = new File(ProcessLauncher.getWorkingDirectory().toURI().getPath()+"MinSlayerLauncher.jar.temp");
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
                //current.setValue((int) Percent);
                gui.refreshStatus("download2", "", "", "", Percent);
                if(interrupted) return false;
            }
            bout.close();
            in.close();
            
            gui.refreshStatus("download", "1", "1", baseUrl + "MinSlayerLauncher.jar", 0);
            //gui.refreshStatus("download", "2", "2", baseUrl + "MinSlayerUpdater.jar", 0);
            /*
            url = new URL(baseUrl + "MinSlayerUpdater.jar");
            connection = (HttpURLConnection) url.openConnection();
            filesize = connection.getContentLength();
            totalDataRead = 0;
            in = new BufferedInputStream(connection.getInputStream());
            dest = new File(ProcessLauncher.getWorkingDirectory().toURI().getPath()+"MinSlayerUpdater.jar.temp");
            dest.getParentFile().mkdirs();
            if(!dest.exists()) dest.createNewFile();
            fos = new FileOutputStream(dest);
            bout = new BufferedOutputStream(fos, 1024);
            data = new byte[1024];
            i = 0;
            while ((i = in.read(data, 0, 1024)) >= 0) {
                totalDataRead = totalDataRead + i;
                bout.write(data, 0, i);
                float Percent = (totalDataRead * 100) / filesize;
                //current.setValue((int) Percent);
                gui.refreshStatus("download2", "", "", "", Percent);
            }
            bout.close();
            in.close();*/
        } catch (MalformedURLException ex) {
            System.out.println("Error: wrong url.\n" + ex.toString());
            errorOccurred = true;
        } catch (IOException ex) {
            System.out.println("Error: I/O.\n" + ex.toString());
            errorOccurred = true;
        }
        gui.refreshStatus("download", "1", "1", baseUrl + "MinSlayerLauncher.jar", 100);
        //gui.refreshStatus("download", "2", "2", baseUrl + "MinSlayerUpdater.jar", 100);
        return !errorOccurred;
    }

    @Override
    public boolean install() {
        if(interrupted) return false;
        boolean error = false;
        gui.refreshStatus("install", "", "", "", 0);
        
        File dir = new File(ProcessLauncher.getWorkingDirectory().toURI().getPath()+"MinSlayerLauncher.jar.temp");
        //if(!dir.exists()) dir.mkdir();
        File old = new File(dir.toURI().getPath().substring(0, dir.toURI().getPath().lastIndexOf(".temp")));
        if(old.exists()) if(!old.delete()) error = true;
        dir.renameTo(old);
        gui.refreshStatus("install2", "", "", old.toURI().getPath(), 0);
        System.out.println("Installed: " + old.toURI().getPath());
        
        /*dir = new File(ProcessLauncher.getWorkingDirectory().toURI().getPath()+"MinSlayerUpdater.jar.temp");
        //if(!dir.exists()) dir.mkdir();
        old = new File(dir.toURI().getPath().substring(0, dir.toURI().getPath().lastIndexOf(".temp")));
        if(old.exists()) if(!old.delete()) error = true;
        dir.renameTo(old);
        gui.refreshStatus("install2", "", "", old.toURI().getPath(), 0);
        System.out.println("Installed: " + old.toURI().getPath());*/
        
        
        gui.refreshStatus("ending", "", "", "", 0);
            
        gui.refreshStatus("end", "0", "0", "", 100);
        
        restart();
        
        return !error;
    }

    private void restart() {
        ArrayList<String> params = new ArrayList<>();
        params.add("java");
        params.add("-jar");
        params.add(ProcessLauncher.getWorkingDirectory()+ File.separator + "MinSlayerLauncher.jar");
        ProcessBuilder pb = new ProcessBuilder(params);
        try {
            Process process = pb.start();
            if (process == null) {
                System.out.println("Unable to restart!\nPlease restart Launcher.");
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        System.exit(0);
    }

    @Override
    public void close() {
        interrupted = true;
    }

    
    
}
