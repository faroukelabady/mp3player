/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mp3player.mediaplayer.GUI;

import java.io.File;

import java.util.*;
import javax.swing.DefaultListModel;

/**
 *
 * @author farouk
 */
public class PlayList {

    private DefaultListModel GUIList;
    private List<File> fileList;
    private int numOfFilesAdded;

    PlayList(){

        fileList = new ArrayList<File>();
        GUIList = new DefaultListModel();

    }

    // configure getter and setter methods

    /**
     * get a reference for the Default list model to use it in JList
     * @return
     * DefaultListModel GUIList
     */
    public DefaultListModel getGUIList() {
        return GUIList;
    }

    /**
     * get a reference for the File list Object
     * @return
     * List<File> fileList
     */
    public List<File> getFileList() {
        return fileList;
    }

    /**
     * get the Number of recently added files to the play list
     * @return
     * number of files added
     */
     public int getNumOfFilesAdded() {
        return numOfFilesAdded;
    }

   // public void setGUIList(DefaultListModel list) {
   //     GUIList = list;
    //}

    //public void setFileList(List<File> file) {
   //     fileList = file;
   // }

   // public void setNumOfFilesAdded(int numOfFilesAdded) {
   //     this.numOfFilesAdded = numOfFilesAdded;
   // }

    /**
     * convert the List<File> object to DefaultListModel so we can use it
     * inside JList
     * @return
     * DefaultListModel
     */
    public DefaultListModel convertListToModel() {

        int i = fileList.size() - numOfFilesAdded;
        for( ; i < fileList.size(); i++) {

            GUIList.addElement(fileList.get(i).getName());
        }
        return GUIList;
    }

    /**
     * add the files selected from JFileChooser to List<File> for dynamic
     * manipulation and changing
     * @param File
     * @return
     * List<File>
     */
    public List<File> addToFileList(File[] file) {

        // 1.Convert the Array to list for dynamic control and add it
        // to the main fileList Object
        List<File> tempList = Arrays.asList(file);
        numOfFilesAdded = 0;
        // 2.Check first if original list is empty
        if(fileList.isEmpty()) {
            fileList.addAll(tempList);
            numOfFilesAdded = tempList.size();
        }
        // 3.Check for duplicate elements
        if(!Collections.disjoint(fileList, tempList) &&
                !fileList.containsAll(tempList)) {
            checkAndAdd(fileList, tempList);
        } else if(Collections.disjoint(fileList, tempList)) {
            fileList.addAll(tempList);
            numOfFilesAdded = tempList.size();
        }

        return fileList;
    }

    /**
     * add the files selected from JFileChooser to List<File> for dynamic
     * manipulation and changing
     * @param List<File>
     * @return
     * List<File>
     */
    public List<File> addToFileList(List<File> list) {

        
        numOfFilesAdded = 0;
        // 1.Check first if original list is empty
        if(fileList.isEmpty()) {
            fileList.addAll(list);
            numOfFilesAdded = list.size();
        }
        // 2.Check for duplicate elements
        if(!Collections.disjoint(fileList, list) &&
                !fileList.containsAll(list)) {
            checkAndAdd(fileList, list);
        } else if(Collections.disjoint(fileList, list)) {
            fileList.addAll(list);
            numOfFilesAdded = list.size();
        }
        return fileList;
    }

    /**
     * remove the selected file from the default model and the List<File>
     * @param index
     */
    public void removeFromFileList(int index) {

        GUIList.remove(index);
        fileList.remove(index);
    }

    /**
     * add the files to the main List but remove duplicated file
     * during the process
     * @param destList
     * @param sourceList
     */
    private void checkAndAdd(List<File> destList,List<File> sourceList) {

        // compare every element inside sourceList to all elements inside
        // destList if there is equality don't add it if not add it to the
        // destList.
        boolean equal = false; // assume the file is new
        for(int i=0; i < sourceList.size(); i++) {
            equal = false; // reset to initial value
            for(int j = 0; j < destList.size(); j++){
                if(sourceList.get(i).equals(destList.get(j))){
                    equal = true;
                }
            }

            if(!equal) {
                destList.add(sourceList.get(i));
                numOfFilesAdded++;

            }
        }
        //return destList;
    }


}
