package com.rodcastro.karaokesonglist;

import com.rodcastro.karaokesonglist.models.Song;
import com.rodcastro.karaokesonglist.utils.FileUtils;
import com.rodcastro.karaokesonglist.utils.OSValidator;
import com.rodcastro.karaokesonglist.utils.Settings;
import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javax.swing.JOptionPane;

/**
 *
 * @author Rodrigo
 */
public class KaraokePlayer {

    private static KaraokePlayer instance;
    private LinkedList<Song> songQueue;

    private KaraokePlayer() {
        songQueue = new LinkedList<Song>();
    }

    public static KaraokePlayer getInstance() {
        if (instance == null) {
            instance = new KaraokePlayer();
        }
        return instance;
    }

    public void addToQueue(Song song) {
        songQueue.offer(song);
    }

    public boolean hasNext() {
        return songQueue.peek() != null;
    }

    public void playNext() {
        if (hasNext()) {
            Song next = songQueue.poll();
            playSong(next);
        }
    }

    private boolean playSong(Song song) {
        try {
            String playerFilename = Settings.getKaraokePath();
            if (FileUtils.isFileValid(playerFilename)) {
                File playerExe = new File(playerFilename);
                String playCmd;
                if (OSValidator.isWindows()) {
                    playCmd = "\"" + playerExe.getAbsolutePath() + "\" -f \"" + song.getAbsolutePath() + "\"";
                    System.out.println("Executando música: " + playCmd);
                    Runtime.getRuntime().exec(playCmd);
                } else {
                    playCmd = playerExe.getAbsolutePath() + " -f \"" + song.getAbsolutePath() + "\"";
                    System.out.println("Executando música: " + playCmd);
                    Runtime.getRuntime().exec(new String[] { "/bin/bash", "-c", playCmd});
                }
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "O executável do player não foi encontrado!", "Sunfly Karaoke Browser", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            System.out.println("Error trying to play song!");
            ex.printStackTrace();
        }
        return false;
    }

    public LinkedList<Song> getSongs() {
        return songQueue;
    }
}
