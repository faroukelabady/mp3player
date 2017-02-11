/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mp3player.mediaplayer.GUI;

import com.mp3player.mediaplayer.basicPlayer.BasicMediaPlayer;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;



/**
 *
 * @author farouk
 */
public class PlayListFrame extends JFrame {

    // Declare components references
    private JButton addToPlayListBtn;
    private JButton removeFromPlayListBtn;
    private JPanel playListPnl;
    private JPanel buttonsPnl;
    private JList playListLst;
    //private DefaultListModel listMdl;
    private PlayList playList;
    private JFileChooser fileChooser;
    private ButtonsActionHandler BtnHandler;
    private FileNameExtensionFilter fExtension;
    private BasicMediaPlayer player;
    private MainPlayerFrame mainframe;

    // Constructor
    PlayListFrame(PlayList playList, BasicMediaPlayer player,
            MainPlayerFrame frame) {
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                MainPlayerFrame.setIndex(getSelectedIndex());

                setVisible(false);
                
            }
        });
        Runtime.getRuntime().gc();
        this.playList = playList;
        this.player = player;
        this.mainframe = frame;
        addToPlayListBtn = new JButton("Add to PlayList");
        removeFromPlayListBtn = new JButton("Remove From PlayList");
        addToPlayListBtn.setActionCommand("add");
        removeFromPlayListBtn.setActionCommand("remove");
        BtnHandler = new ButtonsActionHandler();
        fileChooser = new JFileChooser();
        playListPnl = new JPanel();
        buttonsPnl = new JPanel();
        // Configure the file chooser
        fExtension = new FileNameExtensionFilter("music file", "mp3");
        fileChooser.addChoosableFileFilter(fExtension);
        fileChooser.setMultiSelectionEnabled(true);
        // Configure the Jlist
        playListLst = new JList(playList.getGUIList());
        if(MainPlayerFrame.getIndex() == -1){
            playListLst.setSelectedIndex(0);
        } else {
            playListLst.setSelectedIndex(MainPlayerFrame.getIndex());
        }
        playListLst.setLayoutOrientation(playListLst.VERTICAL);
        playListLst.setVisibleRowCount(-1);
        playListLst.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // add the JList into JScrollPane
        JScrollPane listScroller = new JScrollPane(playListLst);
        listScroller.setPreferredSize(new Dimension(500, 500));

        // organize components inside the panels
        buttonsPnl.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0;
        g.gridy = 0;
        g.gridwidth = 1;
        g.insets = new Insets(2, 2, 2, 2);
        g.weightx = 0.25;
        buttonsPnl.add(addToPlayListBtn, g);
        g.gridx = 1;
        g.gridy = 0;
        buttonsPnl.add(removeFromPlayListBtn, g);
        playListPnl.setLayout(new BorderLayout());
        playListPnl.add(listScroller, BorderLayout.CENTER);

        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        c.add(playListPnl, BorderLayout.CENTER);
        c.add(buttonsPnl, BorderLayout.SOUTH);

        // Add listeners to Buttons and list
        addToPlayListBtn.addActionListener(BtnHandler);
        removeFromPlayListBtn.addActionListener(BtnHandler);
        playListLst.addMouseListener(new MouseClickHandler());
        setSize(400, 400);
        setVisible(false);
        setLocation(400, 200);
    }

    public int getSelectedIndex() {
        return playListLst.getSelectedIndex();
    }

    public void setSelectedIndex(int i) {
        playListLst.setSelectedIndex(i);
    }

    public int getPlayListSize() {
        return playList.getGUIList().getSize();
    }
//    public static void main(String[] args) {
//
//        new PlayListFrame(new PlayList());
//    }

    class ButtonsActionHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            if (e.getActionCommand().equals("add")) {

                int returnVal = fileChooser.showOpenDialog(rootPane);

                if (returnVal == fileChooser.APPROVE_OPTION) {
                    playList.addToFileList(fileChooser.getSelectedFiles());
                    playList.convertListToModel();

                    if(MainPlayerFrame.getIndex() < 0){
                        setSelectedIndex(0);
                    }
                }
            } else if (e.getActionCommand().equals("remove")) {
                playList.removeFromFileList(playListLst.getSelectedIndex());
            }
        }
    }

    class MouseClickHandler extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                int index = playListLst.locationToIndex(e.getPoint());
                if(index != -1) {
                //System.out.println("Double clicked on Item " + index);
                //System.out.println(playList.getFileList().get(index));
                //System.out.println(playList.getGUIList().get(index));
                  MainPlayerFrame.setIndex(index);
                  mainframe.setPLayButton();
                  new Thread(new Runnable() {

                        public void run() {
                            player.play();
                        }
                    }).start();
                }
            }
        }
    }
}
