package com.rodcastro.karaokesonglist;

import java.io.File;
import java.util.List;
import com.rodcastro.karaokesonglist.models.SongRepository;
import com.rodcastro.karaokesonglist.visitors.SongCorrectVisitor;
import java.util.Scanner;
import java.util.TreeSet;
import javax.swing.UIManager;
import com.rodcastro.karaokesonglist.models.Pack;
import com.rodcastro.karaokesonglist.models.Series;
import com.rodcastro.karaokesonglist.ui.MainWindow;
import com.rodcastro.karaokesonglist.visitors.CorrectCheckVisitor;
import com.rodcastro.karaokesonglist.visitors.NameDifferenceVisitor;
import com.rodcastro.karaokesonglist.visitors.SongListVisitor;
import com.rodcastro.karaokesonglist.visitors.SongLoaderVisitor;
import java.util.prefs.Preferences;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author rodcastro
 */
public class Main {

    private static SongRepository repository;
    private static String searchFolder;
    private static Scanner scanner = new Scanner(System.in);
    private static final boolean START_WITH_UI = true;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            System.out.println("Unable to load native look and feel");
        }
        if (START_WITH_UI) {
            searchFolder = Settings.getWorkingPath();
            System.out.println(searchFolder);
            if (searchFolder.equals(".")) {
                JFileChooser fileChooser = new JFileChooser("/");
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int result = fileChooser.showDialog(null, "Selecionar");
                if (result == JFileChooser.APPROVE_OPTION) {
                    searchFolder = fileChooser.getSelectedFile().getAbsolutePath();
                    System.out.println("Chosen path: " + searchFolder + "\n");
                    Settings.setWorkingPath(searchFolder);
                } else {
                    System.exit(0);
                }
            }
            repository = new SongRepository(searchFolder);
            MainWindow frame = new MainWindow();
            frame.loadSongs();
            frame.setVisible(true);
            System.out.println("Songs loaded: " + repository.getSongCount());
        } else {
            chooseFolder();
            while (true) {
                System.out.println("What do you want to do?");
                System.out.println("C - correct filenames in folders");
                System.out.println("D - check for filename differences and missing mp3 or cdg files");
                System.out.println("F - re-choose current folder");
                System.out.println("I - open user interface for manual verification");
                System.out.println("L - list songs and display some statistics");
                System.out.println("M - check for missing packs");
                System.out.println("R - recheck corrected folders for errors");
                System.out.println("Q or break - Exit program");
                System.out.print("> ");
                String response = scanner.next();
                if (response.equals("C") || response.equals("c")) {
                    SongCorrectVisitor songVisitor = new SongCorrectVisitor(searchFolder);
                    songVisitor.findSongs();
                    songVisitor.applyPendingChanges();
                } else if (response.equals("R") || response.equals("r")) {
                    CorrectCheckVisitor checkVisitor = new CorrectCheckVisitor(searchFolder);
                    checkVisitor.findSongs();
                    checkVisitor.applyPendingChanges();
                } else if (response.equals("L") || response.equals("l")) {
                    SongListVisitor listVisitor = new SongListVisitor(searchFolder);
                    listVisitor.listSongs();
                    listVisitor.showInvalidDirectories();
                    listVisitor.showSemiInvalidDirectories();
                    listVisitor.showZipDirectories();
                    listVisitor.showStatistics();
                } else if (response.equals("M") || response.equals("m")) {
                    SongLoaderVisitor loader = new SongLoaderVisitor(searchFolder);
                    loader.setVerbose(true);
                    loader.loadSongs();
                    TreeSet<Pack> packs = loader.getPacks();
                    System.out.println("Missing packs in this series:");
                    Series series = Series.getSeries(Series.getPrefixFromPackInfo(packs.first().getPackInfo()));
                    List<Integer> missing = series.getMissingPacks();
                    for (int i : missing) {
                        System.out.println("Missing: " + i);
                    }
                    System.out.println("");
                } else if (response.equals("I") || response.equals("i")) {
                    MainWindow frame = new MainWindow();
                    frame.loadSongs();
                    frame.setVisible(true);
                    System.out.println("Songs loaded: " + repository.getSongCount());
                } else if (response.equals("Q") || response.equals("q") || response.equals("break")) {
                    System.exit(0);
                } else if (response.equals("D") || response.equals("d")) {
                    NameDifferenceVisitor nameDiff = new NameDifferenceVisitor(searchFolder);
                    nameDiff.findDiffs();
                    nameDiff.showSongsWithoutMp3();
                    nameDiff.showSongsWithoutCdg();
                } else if (response.equals("F") || response.equals("f")) {
                    chooseFolder();
                }
                System.out.println("\n");
            }
        }
    }

    public static void chooseFolder() {
        searchFolder = Settings.getWorkingPath();
//        searchFolder = "/media/rodcastro/SAMSUNG/Karaoke songs/";
        System.out.println("Choose what folders will be included to review:\n");
        File rootFolder = new File(searchFolder);
        String[] packsFolders = rootFolder.list();
        int index = 0;
        for (String folder : packsFolders) {
            System.out.println(++index + " - " + folder);
        }
        System.out.println(++index + " - All of the above");
        System.out.println(index + 1 + " - Change working path");
        System.out.println(index + 2 + " - Exit\n");
        System.out.println("Please type the chosen option:");
        int chosen = 0;
        while (chosen == 0) {
            try {
                System.out.print("> ");
                chosen = scanner.nextInt();
                if (chosen > index + 2) {
                    chosen = 0;
                    System.out.println("Please choose a number in the list:");
                }
            } catch (Exception ex) {
                scanner.next(); // Throw away the invalid input
                System.out.println("Please type a number:");
            }
        }
        if (chosen < index) {
            searchFolder = rootFolder.listFiles()[chosen - 1].getAbsolutePath();
        } else if (chosen == index) {
            searchFolder = "/media/rodcastro/SAMSUNG/Karaoke songs/";
        } else if (chosen == index + 1) {
            JFileChooser fileChooser = new JFileChooser(searchFolder);
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = fileChooser.showDialog(null, "Selecionar");
            if (result == JFileChooser.APPROVE_OPTION) {
                searchFolder = fileChooser.getSelectedFile().getAbsolutePath();
            } else {
                chooseFolder();
            }
        } else if (chosen == index + 2) {
            System.exit(0);
        }
        System.out.println("Chosen path: " + searchFolder + "\n");
        repository = new SongRepository(searchFolder);
    }

    public static SongRepository getRepository() {
        return repository;
    }
}