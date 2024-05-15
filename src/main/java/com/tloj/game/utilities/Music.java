package com.tloj.game.utilities;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.*;

public class Music {
    public void playMusic(){
        try {

        String LOOP = "./musicFile/loop.wav"; 
        String INTRO = "./musicFile/intro.wav";

        File loopFile = new File(LOOP);
        File introFile = new File(INTRO);
        if(loopFile.exists() && introFile.exists()){ 
                
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(introFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
            Thread.sleep(clip.getMicrosecondLength());
            clip.close();
            audioInput = AudioSystem.getAudioInputStream(loopFile);
            clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            //JOptionPane.showMessageDialog(null,"Press ok to stop playing");
            }
        else{
                System.out.println("Couldn't find Music file");
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void stopMusic(){
        try {
            Clip clip = AudioSystem.getClip();
            clip.stop();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
