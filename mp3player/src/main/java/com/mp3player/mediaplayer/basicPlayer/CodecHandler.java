/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mp3player.mediaplayer.basicPlayer;

import java.io.File;
import javax.swing.JOptionPane;
import com.mp3player.mediaplayer.GUI.MainPlayerFrame;
import com.mp3player.mediaplayer.GUI.PlayList;

/**
 *
 * @author farouk
 */
public class CodecHandler {

    CodecHandler() {
    }

    private static PlayList playList;
    private static BasicMediaPlayer player;
   

    public static BasicMediaPlayer getBasicMediaPlayer() {

        player = new MP3Player();
        return player;
    }

    public static BasicMediaPlayer getBasicMediaPlayer(File file) {

        if(getFileType(file).equals("mp3")) {
            player = new MP3Player();
            return player;
        } else {
            JOptionPane.showMessageDialog(null, "Not supported format");
            player = new MP3Player();
            return player;
        }
    }

    public static PlayList getPlayList() {

        return playList;
    }

    public static void setPlayList(PlayList list) {

        playList = list;
    }


    public static int getIndex(){
       return MainPlayerFrame.getIndex();
    }

    public static void setIndex(int currentIndex) {
        MainPlayerFrame.setIndex(currentIndex);
    }

    public static String getFileType(File file) {

        String fileName = file.getName();
        //int index = fileName.lastIndexOf(".");
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

        return extension;
    }

}
