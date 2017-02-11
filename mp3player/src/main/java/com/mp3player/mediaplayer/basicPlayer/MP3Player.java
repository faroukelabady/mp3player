/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mp3player.mediaplayer.basicPlayer;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;

/**
 * a MP3 Codec player class implements the BasicMediaPlayer interface
 * @author farouk
 */
public class MP3Player implements BasicMediaPlayer {

    private AudioInputStream audioInput;
    private AudioFormat baseFormat;
    private AudioFormat decodedFormat;
    private AudioInputStream decodedInput;
    private DataLine.Info info;
    private SourceDataLine line;
    private final Object lock;
    private boolean paused;
    private boolean stop;
    private boolean changeIndex;
    private int index;

    MP3Player() {
        lock = new Object();
        paused = false;
        stop = false;
        changeIndex = true;
        index = 0;
    }

    public void init(File file) {

        try {
            // Get an input stream for the mp3 File
            audioInput = AudioSystem.getAudioInputStream(file);
            // Get audio format for decoding the audio to the output device
            // Typically a sound System
            // 1. get the audio input stream format
            baseFormat = audioInput.getFormat();
            // 2. get the decoded audio format
            decodedFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED, // Encoding to use
                    baseFormat.getSampleRate(), // sample rate same as baseFormat
                    16, // sample size in bits
                    baseFormat.getChannels(), // # of channels
                    baseFormat.getChannels() * 2, // Frame Size
                    baseFormat.getSampleRate(), // Frame Rate
                    false // Big Endian
                    );
            // 3. convert our audio input stream to the decoded format so that
            // the sound System can deal with it
            decodedInput = AudioSystem.getAudioInputStream(decodedFormat, audioInput);
            // 4. get a Dataline for the user audio out and send the audio
            // input stream with decoded format to it here we sill send the data
            // as a clip not sourceDataLine the difference
            info = new DataLine.Info(SourceDataLine.class, decodedFormat);
            //DataLine.Info info2 = new DataLine.Info(Clip.class, decodedFormat);
            line = (SourceDataLine) AudioSystem.getLine(info);
            //Clip line2 =(Clip) AudioSystem.getLine(info2);
        } catch (LineUnavailableException lue) {
            lue.printStackTrace();
        } catch (UnsupportedAudioFileException uafe) {
            uafe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    public void play() {
        //throw new UnsupportedOperationException("Not supported yet.");
        Runtime.getRuntime().gc();
        stop = false;
        if (CodecHandler.getIndex() == -1) {
            index = 0;
        } else {
            index = CodecHandler.getIndex();
        }
        //paused = false;


        System.out.println(index);
        while (!stop) {
            init(CodecHandler.getPlayList().getFileList().get(index));

            try {
                if (line != null) {
                    // open the output line and send 4K of data as a buffer
                    line.open(decodedFormat);
                    // specify buffer size
                    byte[] data = new byte[8192];
                    // start playing
                    //line.start();
                    System.out.println(getVolumeControl().getMaximum());
                    System.out.println(getVolumeControl().getMinimum());
                    int numBytesRead;
//                    while ((numBytesRead = decodedInput.read(data, 0, data.length)) != -1) {
//                        line.write(data, 0, numBytesRead);
//                    }
                    synchronized (lock) {
                        while ((numBytesRead = decodedInput.read(data, 0, data.length)) != -1) {
                            while (paused) {
                                if (line.isRunning()) {
                                    line.drain();
                                    line.stop();
                                }
                                try {
                                    lock.wait();
                                } catch (InterruptedException e) {
                                }
                            }

                            if (!line.isRunning()) {
                                line.start();
                            }
                            line.write(data, 0, numBytesRead);
                        }
                    }
                    if (!stop) {
                        index++;
                    }

                    // stop track
                    line.drain();
                    line.stop();
                    line.close();
                    decodedInput.close();

                    if (index >= CodecHandler.getPlayList().getFileList().size()) {
                        index = 0;
                    }
                    CodecHandler.setIndex(index);

                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } catch (LineUnavailableException lue) {
                lue.printStackTrace();
            }
        }
        System.out.println(index);
        Runtime.getRuntime().gc();
    }

    public void stop() {
        //throw new UnsupportedOperationException("Not supported yet.");
        stop = true;
        //index--;
        line.flush();
        line.close();
        try {
            decodedInput.close();
        } catch (IOException ex) {
            Logger.getLogger(MP3Player.class.getName()).log(Level.SEVERE, null, ex);
        }
        Runtime.getRuntime().gc();
    }

    public boolean isRunning() {
        return line.isRunning();
    }

    public void playFile(File file) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void pause() {
        // throw new UnsupportedOperationException("Not supported yet.");
        paused = true;
        Runtime.getRuntime().gc();

    }

    public void resume() {
        synchronized (lock) {
            paused = false;
            lock.notifyAll();
        }
        Runtime.getRuntime().gc();
    }

    public FloatControl getVolumeControl() {

        FloatControl temp;
        try {
            return (FloatControl) line.getControl(FloatControl.Type.VOLUME);
        } catch (IllegalArgumentException iae) {
            temp = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
            temp.setValue(6);
            return temp;

        }

    }

    public BooleanControl getMuteControl() {
        return (BooleanControl) line.getControl(BooleanControl.Type.MUTE);
    }
}
