package com.rodcastro.karaokesonglist;

import com.rodcastro.karaokesonglist.models.Song;
import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import javax.swing.JOptionPane;

/**
 *
 * @author Rodrigo
 */
public class KaraokePlayer {

    public static KaraokePlayer instance;
    private Queue<Song> songQueue;

    private KaraokePlayer() {
        songQueue = new ArrayDeque<Song>();
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
            File programFilesPath = new File("C:/Program Files (x86)");
            if (!programFilesPath.exists()) {
                programFilesPath = new File(System.getenv("ProgramFiles"));
            }
            File playerExe = new File(programFilesPath.getAbsolutePath() + "/Karaoke Builder Player/kbplayer.exe");
            if (playerExe.exists()) {
                String playCmd = "\"" + playerExe.getAbsolutePath() + "\" \"" + song.getAbsolutePath() + "\"";
                System.out.println("Executando música: " + song.toString());
                Runtime.getRuntime().exec(playCmd);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "O executável do player não foi encontrado!", "Sunfly Karaoke Browser", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
        }
        return false;
    }
    
    public Queue<Song> getSongs() {
        return songQueue;
    }
}
