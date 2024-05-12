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


/**
 * Class that represents a music player in the game<br>
 * Used to play music files in the game<br>
 */
/**
 * The MusicPlayer class is responsible for playing music files.
 */
public class MusicPlayer {
    // The clip that is currently playing
    private Clip playingClip;
    // The file that is being played
    private File file;
    // The thread that is playing the music
    private Thread playingThread;
    // The action to be performed when the music ends
    private Runnable onEnd;
    // The listener that listens to the music clip
    private LineListener listener;

    /**
     * Constructs a MusicPlayer object with the specified file path.
     * 
     * @param pathName the path of the music file to be played
     */
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

    /**
     * Constructs a MusicPlayer object with the specified file path and action to be performed when the music ends.
     * 
     * @param pathName the path of the music file to be played
     * @param onEnd the action to be performed when the music ends
     */
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

    /**
     * Sets a new file to be played.
     * 
     * @param pathName the path of the new music file
     */
    public void setNewFile(String pathName) {
        this.file = new File(pathName);
    }

    /**
     * Stops the currently playing music.
     */
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

    /**
     * Increases the volume of the currently playing music.
     * 
     * @param amount the amount by which to increase the volume
     */
    public void increaseVolume(float amount) {
        if (this.playingClip == null) return;

        FloatControl volumeControl = (FloatControl) this.playingClip.getControl(FloatControl.Type.MASTER_GAIN);
        volumeControl.setValue(amount);
    }

    /**
     * Plays the music file.
     */
    public void playMusic() {
        this.playMusic(false);
    }

    /**
     * Plays the music file with the option to loop.
     * 
     * @param loop true if the music should loop, false otherwise
     */
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
