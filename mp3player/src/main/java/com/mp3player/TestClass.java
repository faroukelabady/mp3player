package com.mp3player;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;
import javax.sound.sampled.Mixer.Info;
/**
 *
 * @author farouk
 */
public class TestClass {

    public static void main(String[] args) {

        File file = new File("D:\\Trading Yesterday- More Than This"
                + "\\Trading_Yesterday_01_Revolution.mp3");
        try {
            // Get an input stream for the mp3 File
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(file);
            // Get audio format for decoding the audio to the output device
            // Typically a sound System
            // 1. get the audio input stream format
            AudioFormat baseFormat = audioInput.getFormat();
            // 2. get the decoded audio format
            AudioFormat decodedFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,  // Encoding to use
                    baseFormat.getSampleRate(),       // sample rate same as baseFormat
                    16,                               // sample size in bits
                    baseFormat.getChannels(),         // # of channels
                    baseFormat.getChannels() * 2,     // Frame Size
                    baseFormat.getSampleRate(),       // Frame Rate
                    false                             // Big Endian
                    );
            // 3. convert our audio input stream to the decoded format so that
            // the sound System can deal with it
            AudioInputStream decodedInput = AudioSystem.getAudioInputStream
                    (decodedFormat, audioInput);
            // 4. get a Dataline for the user audio out and send the audio
            // input stream with decoded format to it here we sill send the data
            // as a clip not sourceDataLine the difference
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, decodedFormat);
            //DataLine.Info info2 = new DataLine.Info(Clip.class, decodedFormat);
            SourceDataLine line =(SourceDataLine) AudioSystem.getLine(info);
            //Clip line2 =(Clip) AudioSystem.getLine(info2);

            if(line != null) {
                // open the output line and send 4K of data as a buffer
                line.open(decodedFormat);
                // specify buffer size
                byte[] data = new byte[4096];
                // start playing
                line.start();
                AudioSystem.getMixer(null);
                Control[] c = AudioSystem.getMixer(null).getControls();
                for(int i = 0; i < c.length; i++){
                    System.out.println(c[i].getType());
                }
                int numBytesRead;
                while((numBytesRead = decodedInput.read
                        (data, 0, data.length)) != -1)
                {
                    line.write(data,0, numBytesRead);                   
                }
                // stop track
                line.drain();
                line.stop();
                line.close();
                decodedInput.close();


            }



        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(TestClass.class.getName()).
                    log(Level.SEVERE, null, ex);
        } catch(LineUnavailableException ex) {
            Logger.getLogger(TestClass.class.getName()).
                    log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TestClass.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }

}
