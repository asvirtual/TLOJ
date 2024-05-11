package com.tloj.game.utilities;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;


public class Music {
    private Clip playingClip;
    private File file;

    public Music(String pathName) {
        this.file = new File(pathName);
    }

    public void loopMusic() {
        
    }

    public void stop() {
        if (this.playingClip != null && this.playingClip.isRunning())
            this.playingClip.stop();
    }

    public void increaseVolume(float amount) {
        if (this.playingClip == null) return;

        FloatControl volumeControl = (FloatControl) this.playingClip.getControl(FloatControl.Type.MASTER_GAIN);
        volumeControl.setValue(amount);
    }

    public void playMusic(boolean loop) {
        if (!this.file.exists()) {
            System.out.println("Required music file \"" + this.file.getName() + "\" not found. Please check the file path.");
            return;
        }

        try {
            this.playingClip = AudioSystem.getClip();
            AudioInputStream stream = AudioSystem.getAudioInputStream(this.file);
            this.playingClip.open(stream);

            if (loop) this.playingClip.loop(Clip.LOOP_CONTINUOUSLY);
            else this.playingClip.start();
        } catch (LineUnavailableException e){
            System.out.println("Error playing music file");
            e.printStackTrace();
        } catch (IOException e){
            System.out.println("Error reading music file");
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e){
            System.out.println("Music file format not supported");
            e.printStackTrace();
        }
    }
}
