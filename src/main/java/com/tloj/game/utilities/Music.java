package com.tloj.game.utilities;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;

import java.io.*;


public class Music {
    public void playMusic(){
        new Thread(() -> {
            try {
                
                String LOOP = "src\\main\\java\\com\\tloj\\game\\utilities\\musicFile\\loop.wav";
                String INTRO = "src\\main\\java\\com\\tloj\\game\\utilities\\musicFile\\intro.wav";

                File loopFile = new File(LOOP);
                File introFile = new File(INTRO);

                if(loopFile.exists() && introFile.exists()){ 

                    AudioInputStream intro = AudioSystem.getAudioInputStream(introFile);
                    
                    Clip introClip = AudioSystem.getClip();
                    introClip.open(intro);
                    FloatControl introVolumeControl = (FloatControl) introClip.getControl(FloatControl.Type.MASTER_GAIN);
                    introVolumeControl.setValue(-20.0f); // Decrease volume by 10 decibels
                    introClip.start();

                    introClip.addLineListener(event -> {
                        if(event.getType() == LineEvent.Type.STOP){
                            try {
                                AudioInputStream loop = AudioSystem.getAudioInputStream(loopFile);
                                Clip loopClip = AudioSystem.getClip();
                                loopClip.open(loop);
                                FloatControl loopVolumeControl = (FloatControl) loopClip.getControl(FloatControl.Type.MASTER_GAIN);
                                loopVolumeControl.setValue(-20.0f); // Decrease volume by 10 decibels
                                loopClip.loop(Clip.LOOP_CONTINUOUSLY);

                            } 
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                        }   
                    });
                }
                else{
                    System.out.println("Couldn't find Music file");
                }
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }).start();
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
