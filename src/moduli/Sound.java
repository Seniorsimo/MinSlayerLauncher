/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moduli;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
 
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import static javax.sound.sampled.AudioSystem.getAudioInputStream;
import static javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.SourceDataLine;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 *
 * @author simone
 */
public class Sound {
    
    private static AudioStream playing = null;
    
    public static void stopSound() {
        AudioPlayer.player.stop(playing);
    }

    public static void playSound(final String url) throws UnsupportedAudioFileException {
        stopSound();
        try {
            //        new Thread(new Runnable() {
            //            @Override
            //            public void run() {
            //                try {
            //                    Clip clip = AudioSystem.getClip();
            //                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(LoginPanel.class.getResourceAsStream(url));
            //                    clip.open(inputStream);
            //                    clip.start();
            //                } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            //                    System.out.println(e.getMessage());
            //                }
            //            }
            //        }).start();
            playing = new AudioStream(new FileInputStream(ProcessLauncher.getWorkingDirectory().toURI().getPath() + "main_music.wav"));
            AudioPlayer.player.start(playing);
                
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    
}
