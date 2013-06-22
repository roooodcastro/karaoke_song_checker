package com.rodcastro.karaokesonglist.visitors;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import com.rodcastro.karaokesonglist.models.Pack;
import com.rodcastro.karaokesonglist.models.Song;

/**
 *
 * @author rodcastro
 */
public class SongLoaderVisitor implements FileVisitor<Path> {

    private TreeSet<Song> songs;
    private List<Song> invalidSongs;
    private TreeSet<Pack> packs;
    private Path startingPath;
    private boolean verbose = false;

    public SongLoaderVisitor(String path) {
        startingPath = Paths.get(path);
        songs = new TreeSet<Song>();
        packs = new TreeSet<Pack>();
        invalidSongs = new ArrayList<Song>();
    }

    public void loadSongs() {
        try {
            Files.walkFileTree(startingPath, this);
        } catch (IOException ex) {
        }
    }

    public TreeSet getSongs() {
        return songs;
    }

    public List<Song> getInvalidSongs() {
        return invalidSongs;
    }

    public TreeSet getPacks() {
        return packs;
    }

    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        if (verbose) {
            System.out.println("Listing folder '" + dir.toString() + "':");
        }
        return FileVisitResult.CONTINUE;
    }

    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        String filename = file.getFileName().toString().replace("__", " - ").replace("_", " ");
        String[] partsDot = filename.split("\\.");
        String extension = partsDot[partsDot.length - 1];
        if (extension.equals("cdg")) {
            Song song = new Song(file.getParent().toString(), file.toFile().getName());
            if (song.isValid()) {
                if (verbose) {
                    System.out.println("Song loaded! " + song.toFilename(null));
                }
                songs.add(song);
                addToOrCreatePack(song);
            } else {
                invalidSongs.add(song);
                if (verbose) {
                    System.out.println("Invalid Song! " + song.toFilename(null));
                }
            }
        }
        return FileVisitResult.CONTINUE;
    }

    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        if (verbose) {
            System.out.println("");
        }
        return FileVisitResult.CONTINUE;
    }

    private void addToOrCreatePack(Song song) {
        if (song.isValid()) {
            try {
                Pack newPack = new Pack(song.getPackInfo());
                if (packs.contains(newPack)) {
                    Pack pack = packs.floor(newPack);
                    pack.addSong(song);
                } else {
                    packs.add(newPack);
                    newPack.setSeries();
                    newPack.addSong(song);
                }
            } catch (Exception ex) {
                if (verbose) {
                    System.out.println("Error trying to assign a song to a pack");
                }
            }
        }
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }
}
