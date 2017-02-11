/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mp3player.mediaplayer.GUI;

import com.mp3player.mediaplayer.GUI.DirectoryChooser;
import com.mp3player.mediaplayer.basicPlayer.BasicMediaPlayer;
import com.mp3player.mediaplayer.basicPlayer.CodecHandler;
import com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * the BasicMediaPlayer interface to the user we define and use the following
 * buttons play, stop, next, previous
 * @version 1
 * @author farouk
 */
public class MainPlayerFrame extends JFrame {

    // Declare JFrame Components
    private JButton playPauseBtn;
    private JButton stopBtn;
    private JButton nextBtn;
    private JButton previousBtn;
    private JButton playListBtn;
    private JPanel buttonsPnl;
    private JButton sourceFolderBtn;
    private JButton muteBtn;
    private JSlider volumeControl;
    private ButtonsHandler handler;
    private PlayList playList;
    private BasicMediaPlayer player;
    private static PlayListFrame playListFrame;
    private static int currentIndex = -1;
    private String currentDir;
    private ImageIcon[] icons = new ImageIcon[10];
    private DirectoryChooser dirChooser;

    MainPlayerFrame() {
        //
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                dispose();
                System.exit(0);
            }
        });


        icons[0] = new ImageIcon(this.getClass().getResource("/icons/Gnome-Media-Playback-Start-24.png"));
        icons[1] = new ImageIcon(MainPlayerFrame.class.getResource("/icons/Gnome-Media-Playback-Stop-24.png"));
        icons[2] = new ImageIcon(MainPlayerFrame.class.getResource("/icons/Gnome-Media-Playback-Pause-24.png"));
        icons[3] = new ImageIcon(MainPlayerFrame.class.getResource("/icons/Gnome-Media-Skip-Forward-24.png"));
        icons[4] = new ImageIcon(MainPlayerFrame.class.getResource("/icons/Gnome-Media-Skip-Backward-24.png"));
        icons[5] = new ImageIcon(MainPlayerFrame.class.getResource("/icons/Gnome-Folder-Open-24.png"));
        icons[6] = new ImageIcon(MainPlayerFrame.class.getResource("/icons/Gnome-Audio-Volume-High-24.png"));
        icons[7] = new ImageIcon(MainPlayerFrame.class.getResource("/icons/Gnome-Audio-Volume-Medium-24.png"));
        icons[8] = new ImageIcon(MainPlayerFrame.class.getResource("/icons/Gnome-Audio-Volume-Low-24.png"));
        icons[9] = new ImageIcon(MainPlayerFrame.class.getResource("/icons/Gnome-Audio-Volume-Muted-24.png"));
        //icons[7] = new ImageIcon(currentDir);
        dirChooser = new DirectoryChooser();

        // intialize the components
        playPauseBtn = new JButton(icons[0]);
        stopBtn = new JButton(icons[1]);
        nextBtn = new JButton(icons[3]);
        previousBtn = new JButton(icons[4]);
        playListBtn = new JButton("PlayList");
        sourceFolderBtn = new JButton(icons[5]);
        muteBtn = new JButton(icons[7]);
        handler = new ButtonsHandler();
        buttonsPnl = new JPanel();
        playList = new PlayList();
        // intialize the voumeControl
        volumeControl = new JSlider(JSlider.VERTICAL, -20, 13, -3);
        //volumeControl.set

        // set action commands for buttons
        playPauseBtn.setActionCommand("Play");
        stopBtn.setActionCommand("Stop");
        nextBtn.setActionCommand("Next");
        previousBtn.setActionCommand("Previous");
        playListBtn.setActionCommand("PlayList");
        sourceFolderBtn.setActionCommand("Source Folder");
        muteBtn.setActionCommand("Mute");
        playPauseBtn.setBackground(new Color(0Xa2620f));
        stopBtn.setBackground(new Color(0Xb3a087));
        nextBtn.setBackground(new Color(255, 130, 50));
        previousBtn.setBackground(new Color(0Xefbb34));


        // initialize the codec the default is mp3
        player = CodecHandler.getBasicMediaPlayer();

        // pass a refrence of the playlist to the codec to gain access
        // to the files
        CodecHandler.setPlayList(playList);
        // Create a PlayListFrame object (invisible)
        playListFrame = new PlayListFrame(playList, player, MainPlayerFrame.this);
        // set the panel layout and organize buttons  inside it
        buttonsPnl.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0;
        g.gridy = 0;
        g.insets = new Insets(3, 3, 3, 3);
        g.gridheight = 2;
        g.fill = GridBagConstraints.BOTH;
        buttonsPnl.add(stopBtn, g);
        g.gridx = 1;
        g.gridy = 0;
        g.gridheight = 2;
        g.fill = GridBagConstraints.BOTH;
        buttonsPnl.add(playPauseBtn, g);
        g.gridx = 2;
        g.gridy = 0;
        g.gridheight = 1;
        g.fill = GridBagConstraints.BOTH;
        buttonsPnl.add(nextBtn, g);
        g.gridx = 2;
        g.gridy = 1;
        g.gridheight = 1;
        g.fill = GridBagConstraints.BOTH;
        buttonsPnl.add(previousBtn, g);
        g.gridx = 3;
        g.gridy = 0;
        g.gridheight = 2;
        g.fill = GridBagConstraints.BOTH;
        buttonsPnl.add(volumeControl, g);
        g.gridx = 0;
        g.gridy = 3;
        g.gridwidth = 2;
        g.fill = GridBagConstraints.BOTH;
        buttonsPnl.add(playListBtn, g);
        g.gridx = 2;
        g.gridy = 3;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.BOTH;
        buttonsPnl.add(sourceFolderBtn, g);
        g.gridx = 3;
        g.gridy = 3;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.BOTH;
        buttonsPnl.add(muteBtn, g);
        // add the panel to the container
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        c.add(buttonsPnl, BorderLayout.CENTER);
        //c.add(volumeControl, BorderLayout.EAST);
        setSize(230, 180);
        // assign listener class to buttons
        playPauseBtn.addActionListener(handler);
        stopBtn.addActionListener(handler);
        nextBtn.addActionListener(handler);
        previousBtn.addActionListener(handler);
        playListBtn.addActionListener(handler);
        sourceFolderBtn.addActionListener(handler);
        muteBtn.addActionListener(handler);
        volumeControl.addChangeListener(new ChangeHandler());


        //pack();
        setVisible(true);
    }

    /**
     * get the current song index to process and play it
     * @return
     *  currentIndex
     */
    public static int getIndex() {
        return currentIndex;
    }

    /**
     * Set the index to reference to the song that we will play
     * @param Index
     */
    public static void setIndex(int Index) {

        currentIndex = Index;
        if (playListFrame != null) {
            playListFrame.setSelectedIndex(Index);
        }
    }

    /**
     * Set the playPauseBtn to the pause action used to connect the button
     * with other frames currently the PlayListFrame
     */
    public void setPLayButton() {
        //playPauseBtn.setText("pause");
        playPauseBtn.setIcon(icons[2]);
        playPauseBtn.setActionCommand("pause");

    }

    public void setSoundButton(int value) {

        int max = volumeControl.getMaximum();
        int min = volumeControl.getMinimum();
        if (value <= max && value >= (max-10)) {
            muteBtn.setIcon(icons[6]);
        } else if (value >= min && value < (min + 10)) {
            muteBtn.setIcon(icons[8]);
        } else {
            muteBtn.setIcon(icons[7]);
        }
    }

    /**
     * the entry point of the program
     * @param args
     */
    public static void main(String[] args) {

        //String currentDir = new File("").getAbsolutePath();
       // System.out.println(CodecHandler.getFileType(new File("j:\\a7bek.mp3")));

        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
            // new NimbusLookAndFeel()
            //UIManager.getSystemLookAndFeelClassName()
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(MainPlayerFrame.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            Logger.getLogger(MainPlayerFrame.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            Logger.getLogger(MainPlayerFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
        }

        new MainPlayerFrame();
    }

    /**
     * the class the handles all buttons actions
     */
    class ButtonsHandler implements ActionListener {

        Runnable r = null;
        Thread t = null;

        public void actionPerformed(ActionEvent e) {
            try {
                if (e.getActionCommand().equals("Play")) {

                    if (playList.getFileList().isEmpty()) {
                        throw new Exception("empty List");
                    }
                    playPauseBtn.setActionCommand("Pause");
                    //playPauseBtn.setText("Pause");
                    playPauseBtn.setIcon(icons[2]);


                    r = new Runnable() {

                        public void run() {
                            player.play();
                        }
                    };
                    t = new Thread(r);
                    t.start();


                } else if (e.getActionCommand().equals("Pause")) {

                    playPauseBtn.setActionCommand("Resume");
                    // playPauseBtn.setText("Resume");
                    playPauseBtn.setIcon(icons[0]);
                    player.pause();

                } else if (e.getActionCommand().equals("Resume")) {

                    playPauseBtn.setActionCommand("Pause");
                    //playPauseBtn.setText("Pause");
                    playPauseBtn.setIcon(icons[2]);
                    player.resume();

                } else if (e.getActionCommand().equals("Stop")) {

                    playPauseBtn.setActionCommand("Play");
                    //playPauseBtn.setText("Play");
                    playPauseBtn.setIcon(icons[0]);
                    player.stop();

                } else if (e.getActionCommand().equals("Next")) {

                    currentIndex = playListFrame.getSelectedIndex() + 1;
                    if (currentIndex >= playList.getFileList().size()) {
                        currentIndex = 0;
                    }
                    playListFrame.setSelectedIndex(currentIndex);
                    r = new Runnable() {

                        public void run() {

                            player.play();
                        }
                    };
                    t = new Thread(r);
                    t.start();
                    Runtime.getRuntime().gc();

                } else if (e.getActionCommand().equals("Previous")) {

                    currentIndex = playListFrame.getSelectedIndex() - 1;
                    if (currentIndex == -1) {
                        currentIndex = playList.getFileList().size() - 1;
                    }
                    playListFrame.setSelectedIndex(currentIndex);
                    r = new Runnable() {

                        public void run() {

                            player.play();
                        }
                    };
                    t = new Thread(r);
                    t.start();
                    Runtime.getRuntime().gc();
                } else if (e.getActionCommand().equals("PlayList")) {

                    playListFrame.setVisible(true);

                } else if (e.getActionCommand().equals("Source Folder")) {

                    playList.addToFileList(dirChooser.getFiles());
                    playList.convertListToModel();

                    if (MainPlayerFrame.getIndex() < 0) {
                        playListFrame.setSelectedIndex(0);
                    }

                } else if (e.getActionCommand().equals("Mute")) {

                    muteBtn.setIcon(icons[9]);
                    muteBtn.setActionCommand("Unmute");
                    volumeControl.setEnabled(false);
                    player.getMuteControl().setValue(true);
                } else if (e.getActionCommand().equals("Unmute")) {



                    setSoundButton((int) volumeControl.getValue());
                    muteBtn.setActionCommand("Mute");
                    volumeControl.setEnabled(true);
                    player.getMuteControl().setValue(false);
                }
            } catch (Exception ex) {
                ex.getMessage();
            }
        }
    }

    class ChangeHandler implements ChangeListener {

        public void stateChanged(ChangeEvent e) {

            JSlider source = (JSlider) e.getSource();
            if (//!source.getValueIsAdjusting()
                     !playList.getFileList().isEmpty()) {
                source.setMaximum((int) Math.
                        floor(player.getVolumeControl().getMaximum()));
                source.setMinimum((int) Math.
                        ceil(player.getVolumeControl().getMinimum()));


                int fps = (int) source.getValue();
                setSoundButton((int) source.getValue());
                player.getVolumeControl().setValue(fps);
            }
        }
    }
}
