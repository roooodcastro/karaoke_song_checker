package karaokesonglist.visitors;

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

/**
 *
 * @author rodcastro
 */
public class NameDifferenceVisitor implements FileVisitor<Path> {

    private Path startingPath;
    private List<String> songsWithoutMp3;
    private List<String> songsWithoutCdg;

    public NameDifferenceVisitor(String path) {
        startingPath = Paths.get(path);
        songsWithoutMp3 = new ArrayList<String>();
        songsWithoutCdg = new ArrayList<String>();
    }

    public void findDiffs() {
        try {
            Files.walkFileTree(startingPath, this);
        } catch (IOException ex) {
        }
    }

    public void showSongsWithoutMp3() {
        if (songsWithoutMp3.size() > 0) {
            for (String invalid : songsWithoutMp3) {
                System.out.println("MP3 file not found for song: '" + invalid + "'");
            }
        } else {
            System.out.println("There are no songs without corresponding MP3 files");
        }
    }
    
    public void showSongsWithoutCdg() {
        if (songsWithoutCdg.size() > 0) {
            for (String invalid : songsWithoutCdg) {
                System.out.println("CD-G file not found for song: '" + invalid + "'");
            }
        } else {
            System.out.println("There are no songs without corresponding CD-G files");
        }
    }

    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        String filename = file.getFileName().toString().replace("__", " - ").replace("_", " ");
        String[] partsDot = filename.split("\\.");
        String extension = partsDot[partsDot.length - 1];
        if (extension.equals("cdg")) {
            String preExtension = filename.substring(0, filename.length() - 4);
            String mp3Filename = preExtension + ".mp3";
            File mp3File = new File(file.getParent().toString() + "/" + mp3Filename);
            if (!mp3File.exists()) {
                songsWithoutMp3.add(file.toString());
            }
        }
        if (extension.equals("mp3")) {
            String preExtension = filename.substring(0, filename.length() - 4);
            String cdgFilename = preExtension + ".cdg";
            File cdgFile = new File(file.getParent().toString() + "/" + cdgFilename);
            if (!cdgFile.exists()) {
                songsWithoutCdg.add(file.toString());
            }
        }
        return FileVisitResult.CONTINUE;
    }

    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }
}