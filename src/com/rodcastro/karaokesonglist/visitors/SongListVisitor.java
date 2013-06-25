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
import com.rodcastro.karaokesonglist.models.Song;
import com.rodcastro.karaokesonglist.utils.StringUtils;

/**
 *
 * @author rodcastro
 */
public class SongListVisitor implements FileVisitor<Path> {

    private int numFolders = 0;
    private int numFiles = 0;
    private int numSongs = 0;
    private Path startingPath;
    private int totalFiles = 0;
    private int validFiles = 0;
    private int zipFiles = 0;
    private List<String> invalidDirectories;
    private List<String> semiInvalidDirectories;
    private List<String> zipDirectories;

    public SongListVisitor(String path) {
        startingPath = Paths.get(path);
        invalidDirectories = new ArrayList<String>();
        semiInvalidDirectories = new ArrayList<String>();
        zipDirectories = new ArrayList<String>();
    }

    public void listSongs() {
        try {
            Files.walkFileTree(startingPath, this);
        } catch (IOException ex) {
        }
    }

    public void showInvalidDirectories() {
        for (String invalid : invalidDirectories) {
            System.out.println("Directory is completely invalid: '" + invalid + "'");
        }
        System.out.println("");
    }

    public void showSemiInvalidDirectories() {
        for (String semiInvalid : semiInvalidDirectories) {
            System.out.println("Directory contains invalid songs: '" + semiInvalid + "'");
        }
        System.out.println("");
    }

    public void showZipDirectories() {
        for (String zipDirectory : zipDirectories) {
            System.out.println("Directory contains zip files: '" + zipDirectory + "'");
        }
        System.out.println("");
    }

    public void showStatistics() {
        System.out.println("Total number of songs: " + numSongs);
        System.out.println("Total number of files: " + numFiles);
        System.out.println("Total number of folders: " + numFolders);
        System.out.println("Total number of invalid folders: " + invalidDirectories.size());
        System.out.println("Total number of semi invalid folders: " + semiInvalidDirectories.size());
    }

    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        System.out.println("Listing folder '" + dir.toString() + "':");
        numFolders++;
        totalFiles = 0;
        validFiles = 0;
        zipFiles = 0;
        return FileVisitResult.CONTINUE;
    }

    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        String filename = file.getFileName().toString().replace("__", " - ").replace("_", " ");
        String[] partsDot = filename.split("\\.");
        String extension = partsDot[partsDot.length - 1];
        if (extension.equals("cdg")) {
            Song song = new Song(file.getParent().toString(), file.toFile().getName());
            System.out.println(song.toString());
            if (song.isValid()) {
                validFiles++;
            }
            totalFiles++;
            numSongs++;
        }
        if (extension.equals("zip") | extension.equals("rar")) {
            zipFiles++;
        }
        numFiles++;
        return FileVisitResult.CONTINUE;
    }

    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        System.out.println("");
        if (validFiles == 0) {
            invalidDirectories.add(dir.toString());
        }
        if (validFiles < totalFiles && validFiles >= 1) {
            semiInvalidDirectories.add(dir.toString());
        }
        if (zipFiles > 0) {
            zipDirectories.add(dir.toString());
        }
        return FileVisitResult.CONTINUE;
    }
}
