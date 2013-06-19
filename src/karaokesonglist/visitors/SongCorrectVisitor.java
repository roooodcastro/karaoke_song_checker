package karaokesonglist.visitors;

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
import karaokesonglist.FilenameChange;
import karaokesonglist.StringUtils;

/**
 *
 * @author rodcastro
 */
public class SongCorrectVisitor implements FileVisitor<Path> {

    private int numFolders = 0;
    private int numFiles = 0;
    private Path startingPath;
    private Scanner scanner;
    private List<FilenameChange> changes;

    public SongCorrectVisitor(String path) {
        startingPath = Paths.get(path);
        scanner = new Scanner(System.in);
        changes = new ArrayList<FilenameChange>();
    }

    public void findSongs() {
        try {
            Files.walkFileTree(startingPath, this);
        } catch (IOException ex) {
        }
    }

    public void applyPendingChanges() {
        for (FilenameChange change : changes) {
            change.apply();
        }
    }

    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        String folderName = dir.toFile().getName();
        if (folderName.contains("OK")) {
            System.out.println("Directory '" + dir.toString() + "' already corrected, skipping...");
            return FileVisitResult.SKIP_SUBTREE;
        }
        System.out.println("Listing folder '" + dir.toString() + "':");
        numFolders++;
        return FileVisitResult.CONTINUE;
    }

    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        String filename = file.getFileName().toString().replace("__", " - ").replace("_", " ");
        String[] partsDot = filename.split("\\.");
//        String[] partsUnderline = filename.split("__");
//        if (partsUnderline.length == 3) {
//            String artist = partsUnderline[1].replace("_", " ").trim();
//            String songName = partsUnderline[2].replace("_", " ").trim().split("\\.")[0];
//            System.out.println("Artist: '" + artist + "', Song: '" + songName + "'");
//        }
        if (partsDot.length > 0) {
            String extension = partsDot[partsDot.length - 1];
//            if (extension.equals("CDG") || extension.equals("cdg")) {
            String[] partsDash = filename.split("-");

            String print = "";
//            String artist = StringUtils.formatName(partsDash[partsDash.length - 2]);
//            String songName = StringUtils.formatName(partsDash[partsDash.length - 1].split("\\.")[0]);
            int i = 1;
            for (String part : partsDash) {
                print += StringUtils.addTrailingWhitespace("Part" + i++ + ": '" + part + "', ", 40);
            }
            System.out.println(print.trim());
//            System.out.println("SF tag: '" + partsDash[0] + "', Artist: '" + artist + "', Song: '" + songName + "'");
            numFiles++;
//            }
        }
        return FileVisitResult.CONTINUE;
    }

    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        System.out.println("T - tag, N - song number, A - artist, S - song name, M - number and song name, R - number and artist, E - number, artist and song name");
        String response = scanner.nextLine();
        if (response.equals("break")) {
            return FileVisitResult.TERMINATE;
        } else if (response.length() > 0) {
            // Mark for name alteration
            changes.add(new FilenameChange(dir.toString(), response));
            System.out.println("Directory '" + dir.toString() + "' marked for name change");
        }
        return FileVisitResult.CONTINUE;
    }
}
