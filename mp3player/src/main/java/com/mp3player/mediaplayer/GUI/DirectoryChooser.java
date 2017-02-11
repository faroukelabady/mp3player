/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mp3player.mediaplayer.GUI;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import com.mp3player.mediaplayer.basicPlayer.CodecHandler;

/**
 * This class is responsible for getting the audio files from the the specified
 * directory or directories
 * @author farouk
 */
public class DirectoryChooser {

    private JFileChooser dirChooser;
    private ArrayList<File> audioFiles;
    
    /**
     * constructor
     */
    DirectoryChooser() {
        
        dirChooser = new JFileChooser();
        audioFiles = new ArrayList<File>();
        dirChooser.setMultiSelectionEnabled(true);
        dirChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    }

    /**
     * show the file chooser and iterate through the chosen elements to get
     * the files from it also we can choose files with directories not
     * directories only finally we add the files inside the audioFiles List.
     * @return
     * List<File>
     */
    public List<File> getFiles() {
     
       int returnVal = dirChooser.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File[] list = dirChooser.getSelectedFiles();
            for (File file : list) {
                if (file.isDirectory()) {
                    getFilesFromDirectory(file);
                } else if(file.isFile() &&
                        (CodecHandler.getFileType(file).equals("mp3"))) {
                    audioFiles.add(file);
                }
            }            
        }
       return audioFiles;
    }

    /**
     * get the files inside the directory and add it to the audioFiles List.
     * Hint: this method is recursive
     * @param temp
     */
    public void getFilesFromDirectory(File temp) {

        File[] list = temp.listFiles();

        for (int i = 0; i < list.length; i++) {

                if (list[i].isFile() &&
                        (CodecHandler.getFileType(list[i]).equals("mp3"))) {

                        audioFiles.add(list[i]);

                } else if(list[i].isDirectory()){
                    getFilesFromDirectory(list[i]);
                }
            }

    }
}
