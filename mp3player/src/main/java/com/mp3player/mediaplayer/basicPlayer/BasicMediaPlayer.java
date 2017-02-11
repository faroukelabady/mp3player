/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mp3player.mediaplayer.basicPlayer;

import java.io.File;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.FloatControl;

/**
 * define the basic methods that every CodecPlayer class must support
 * @author farouk
 */
public interface BasicMediaPlayer {

    void init(File file);
    void play();
    void stop();
    void resume();
    void pause();
    FloatControl getVolumeControl();
    BooleanControl getMuteControl();
    boolean isRunning();
    void playFile(File file);
}
