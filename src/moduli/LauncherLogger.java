/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moduli;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Simone
 */
public class LauncherLogger {
    
    private static File outFile = new File(ProcessLauncher.getWorkingDirectory() + File.separator + "minslayer_log.txt");
    private PrintStream oldOut;
    public ArrayList<String> logList;
    private int logEntry = 2000;
    private LoggerListener listener;
    
    public LauncherLogger(LoggerListener l){
        listener = l;
        logList = new ArrayList<String>();
        LoggerPrintStream lps = new LoggerPrintStream();
        PrintStream myOut = new PrintStream(lps);
        oldOut = System.out;
        System.setOut(myOut);
        System.setErr(myOut);
    }
    
    private void add(String s){
        //filtro le new line esagerate
        if(s.length()==2&&(s.toCharArray()[0]=='\n'||s.toCharArray()[1]=='\n')) return;
        logList.add(s);
        int size = logList.size();
        if(size>logEntry){
            int rem = size-logEntry;
            for(int i=0; i<rem; i++){
                logList.remove(0);
            }
        }
        //stampo anche sul vecchio out
        oldOut.println(s);
        if(listener!=null) listener.refreshGUI(logList);
    }
    
//    public void registerListener(LoggerListener l){
//        listener = l;
//    }
    
    
    //L' output stream definito da noi
    private class LoggerPrintStream extends ByteArrayOutputStream{
        private BufferedOutputStream out = null;
        public LoggerPrintStream(){
            super();
            try {
                out = new BufferedOutputStream(new PrintStream(new FileOutputStream(outFile)));
            } catch (FileNotFoundException ex) {
                java.util.logging.Logger.getLogger(LauncherLogger.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        @Override
        public void write(byte[] b, int off, int len) {
            final String text = new String(b, off, len);
            
            //write to file
            try {
                out.write(b, off, len);
                out.flush();
            } catch (IOException ex) {
                Logger.getLogger(LauncherLogger.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //add to list
            add(text);

        }

        @Override
        public void write(int b) {
            write(new byte[]{(byte) b}, 0, 1);
        }
        @Override
        public void close() throws IOException{
            super.close();
            out.flush();
            out.close();
        }
    }
    
}
