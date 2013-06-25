package com.rodcastro.karaokesonglist.visitors;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.rodcastro.karaokesonglist.utils.StringUtils;

/**
 *
 * @author rodcastro
 */
public class CorrectCheckVisitor implements FileVisitor<Path> {

    private Path startingPath;
    private Scanner scanner;
    private List<Path> changes;

    public CorrectCheckVisitor(String path) {
        startingPath = Paths.get(path);
        scanner = new Scanner(System.in);
        changes = new ArrayList<Path>();
    }

    public void findSongs() {
        try {
            Files.walkFileTree(startingPath, this);
        } catch (IOException ex) {
        }
    }

    public void applyPendingChanges() {
        for (Path change : changes) {
            String directory = change.toString();
            File oldDir = new File(directory);
            File newDir = new File(directory.replace("OK", "").trim());
            oldDir.renameTo(newDir);
            System.out.println("Marked directory '" + oldDir + "' as not yet corrected");
        }
    }

    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        String folderName = dir.toFile().getName();
        if (!folderName.contains("OK") && !dir.toString().equals(startingPath.toString())) {
            System.out.println("Directory '" + dir.toString() + "' not yet corrected, skipping...");
            return FileVisitResult.SKIP_SUBTREE;
        }
        System.out.println("Listing folder '" + dir.toString() + "':");
        return FileVisitResult.CONTINUE;
    }

    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        String filename = file.getFileName().toString().replace("__", " - ").replace("_", " ");
        String[] partsDot = filename.split("\\.");
        if (partsDot.length > 0) {
            String[] partsDash = filename.split("-");
            String print = "";
            String tag, number, artist, song;
            tag = partsDash[0].trim();
            number = partsDash[1].trim();
            artist = StringUtils.formatName(partsDash[2]);
            song = StringUtils.formatName(partsDash[3]);
            print += "Tag: '" + StringUtils.addTrailingWhitespace(tag + "', ", 10);
            print += "Number: '" + StringUtils.addTrailingWhitespace(number + "', ", 7);
            print += "Artist: '" + StringUtils.addTrailingWhitespace(artist + "', ", 40);
            print += "Song: '" + song + "', ";
            System.out.println(print.trim());
        }
        return FileVisitResult.CONTINUE;
    }

    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        System.out.println("Is this folder correct? If it is, press Enter, and if not, type in anything");
        System.out.print("> ");
        String response = scanner.nextLine();
        if (response.equals("break")) {
            return FileVisitResult.TERMINATE;
        } else if (response.length() > 0) {
            // Mark for name alteration
            changes.add(dir);
            System.out.println("Directory '" + dir.toString() + "' marked for recorrecting");
        }
        return FileVisitResult.CONTINUE;
    }
}
