package com.tloj.game.utilities;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;


public class MusicPlayer {
    private Clip playingClip;
    private File file;
    private Thread playingThread;
    private Runnable onEnd;
    private LineListener listener;

    public MusicPlayer(String pathName) {
        this.file = new File(pathName);
        this.listener = new LineListener() {
            @Override
            public void update(LineEvent event) {
                if (event.getType() == LineEvent.Type.STOP) 
                    if (onEnd != null) onEnd.run();
            }
        };
    }

    public MusicPlayer(String pathName, Runnable onEnd) {
        this.file = new File(pathName);
        this.onEnd = onEnd;
        this.listener = new LineListener() {
            @Override
            public void update(LineEvent event) {
                if (event.getType() == LineEvent.Type.STOP) 
                    if (onEnd != null) onEnd.run();
            }
        };
    }

    public void setNewFile(String pathName) {
        this.file = new File(pathName);
    }

    public void stop() {
        if (this.playingClip == null) return;

        this.playingClip.removeLineListener(this.listener);

        if (this.playingClip.isRunning()) this.playingClip.stop();        
        this.playingClip.close();

        if (this.playingThread != null) {
            try {
                this.playingThread.interrupt();
                this.playingThread.join(); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                this.playingThread = null;
            }
        }
    }

    public void increaseVolume(float amount) {
        if (this.playingClip == null) return;

        FloatControl volumeControl = (FloatControl) this.playingClip.getControl(FloatControl.Type.MASTER_GAIN);
        volumeControl.setValue(amount);
    }

    public void playMusic() {
        this.playMusic(false);
    }

    public void playMusic(boolean loop) {
        this.stop();
        this.playingThread = new Thread(() -> {
            if (!this.file.exists()) {
                System.out.println("Required music file \"" + this.file.getName() + "\" not found. Please check the file path.");
                return;
            }

            try {
                this.playingClip = AudioSystem.getClip();
                try (AudioInputStream stream = AudioSystem.getAudioInputStream(this.file)) {
                    this.playingClip.open(stream);

                    this.playingClip.addLineListener(this.listener);
    
                    this.playingClip.start();
                    if (loop) this.playingClip.loop(Clip.LOOP_CONTINUOUSLY);
                }

            } catch (LineUnavailableException e) {
                System.out.println("Error playing music file");
                e.printStackTrace();
            } catch (IOException e){
                System.out.println("Error reading music file");
                e.printStackTrace();
            } catch (UnsupportedAudioFileException e){
                System.out.println("Music file format not supported");
                e.printStackTrace();
            }
        });

        this.playingThread.start();
    }
}
