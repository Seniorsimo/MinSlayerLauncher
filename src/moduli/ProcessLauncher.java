/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moduli;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Logger;
//import org.lwjgl.LWJGLUtil;
//import org.lwjgl.openal.AL;

/**
 *
 * @author simone
 */
public class ProcessLauncher {
    private String username;
    private String sessionKey;
    private Controller controller;
    
    private static String applicationName = "minslayer";
    
    public ProcessLauncher(Controller controller){
        this.controller = controller;
    }
    
    public void launch(){
        if(username==null||sessionKey==null){
            Logger.getLogger(ProcessLauncher.class.getName()).warning("Unable to launch application: username or session Key not set.");
            return;
        }
        
        //reading setting
        OptionManager om = controller.getAccount().getOption();
        
        //optifine selection
        String optifine = om.getOptifineVersion();
        
        //fullscreen
        boolean fullscreen = om.isFullscreen();
        
        //width, height
        int width = om.getX();
        int height = om.getY();
        
        //memory
        String memory = om.getMemory();
        
        //generate all classPath for launch minecraft
        String classPath = "";
        String path = getWorkingDirectory().toURI().getPath();
        
        //add here class that need to be loaded before libraries
        classPath += "" + path + "core/OptiFine_"+ optifine +".jar";
        classPath += ";" + path + "core/minecraftforge.jar";
        //classPath += ";" + path + "core/OptiFine.jar";
        
        //libraries
        File f = new File(path + "libraries");
        classPath += ";" + f.toURI().getPath();
        classPath += addLibrary(f);
        
        //add here class that need to be loaded before minecraft but after libraries
        
        //minecraft
        classPath += ";" + path + "1.6.4.jar";
        //classPath += ";" + path + "minecraft.jar";
                
        //start a new process
        ArrayList<String> params = new ArrayList<String>();
        params.add("java");
        params.add("-Djava.library.path=" + getWorkingDirectory().toURI().getPath() + "natives/");
        params.add("-Dfml.ignoreInvalidMinecraftCertificates=true");
        params.add("-Dfml.ignorePatchDiscrepancies=true");
        
        params.add("-XX:ParallelGCThreads=2");
        params.add("-XX:+AggressiveOpts");
        params.add("-XX:MaxPermSize=256M");
        
        params.add("-Xmx"+ memory+"m");
        params.add("-Xms256m");
        
        params.add("-classpath");
        params.add(classPath);
        //params.add("net.minecraft.client.main.Main");
        params.add("net.minecraft.launchwrapper.Launch");
        /*params.add("-jar");
        params.add(path + "1.6.4.jar");*/
        
        params.add("-username="+username);
        params.add("-session="+sessionKey);
        params.add("-version 1.6.4");
        params.add("-gameDir="+getWorkingDirectory().getPath()+"\\minecraft");
        params.add("-assetsDir="+getWorkingDirectory().getPath()+"\\assets");
        params.add("-tweakClass=cpw.mods.fml.common.launcher.FMLTweaker");
        
        //params.add("-resourcePackDir " + dir);
        //params.add("-width " + dir);
        //params.add("-height " + dir);
        //params.add("-fullscreen");
        if(fullscreen){
            params.add("-fullscreen");
        }
        else{
            params.add("-w" + width);
            params.add("-h" + height);
        }
            
        ////params.add("-userProperties {}");
        ////params.add("-uuid 294c588d44ea4324a389587f0e7f9d02");
        
        //System.out.println(params.toString());
        final ProcessBuilder pb = new ProcessBuilder(params);
        pb.redirectErrorStream(true);
        
//        System.out.println(LWJGLUtil.getPlatform());
//        String[] libname = new String[] { "OpenAL64.dll", "OpenAL32.dll" };
//        String[] test = LWJGLUtil.getLibraryPaths("OpenAL32", libname, AL.class.getClassLoader());
//        for(String s:test){
//            System.out.println("--" + s);
//        }
//        
//       
        Thread t = new Thread(){
            @Override
            public void run(){
                Process process;
                try {
                    process = pb.start();
                    if (process == null) {
                        System.out.println("Unable to cread process");
                    }
                    BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

                    String line = null;
                    while ( (line = br.readLine()) != null) {
                       System.out.println(line);
                    }
                } catch (IOException ex) {
                    System.out.println("Error: " + ex.toString());
                }
                System.exit(0);
            }
        };
        t.setDaemon(true);
        t.start();
        
        
    }
    
    private String addLibrary(File dir){
        String out = "";
        File[] list = dir.listFiles();
        for(File f:list){
            if(f.isDirectory()){
                out += addLibrary(f);
            }
            else{
                out += ";" + f.toURI().getPath();
            }
        }
        return out;
    }
    
    public static File getWorkingDirectory() {
        
        String userHome = System.getProperty("user.home", ".");
        File workingDirectory;
        switch (getPlatform()) {
            case solaris:
            case linux:
                workingDirectory = new File(userHome, '.' + applicationName + '/');
                break;
            case windows:
                String applicationData = System.getenv("APPDATA");
                if (applicationData != null) {
                    workingDirectory = new File(applicationData, "." + applicationName + '/');
                } else {
                    workingDirectory = new File(userHome, '.' + applicationName + '/');
                }
                break;
            case macos:
                workingDirectory = new File(userHome, "Library/Application Support/" + applicationName);
                break;
            default:
                workingDirectory = new File(userHome, applicationName + '/');
        }
        if ((!workingDirectory.exists()) && (!workingDirectory.mkdirs())) {
            throw new RuntimeException("The working directory could not be created: " + workingDirectory);
        }
        return workingDirectory;
    }
    
    private static OS getPlatform() {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("win")) {
            return OS.windows;
        }
        if (osName.contains("mac")) {
            return OS.macos;
        }
        if (osName.contains("solaris")) {
            return OS.solaris;
        }
        if (osName.contains("sunos")) {
            return OS.solaris;
        }
        if (osName.contains("linux")) {
            return OS.linux;
        }
        if (osName.contains("unix")) {
            return OS.linux;
        }
        return OS.unknown;
    }

    private static enum OS {
        linux, solaris, windows, macos, unknown;
    }
    
    
    

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }
}
