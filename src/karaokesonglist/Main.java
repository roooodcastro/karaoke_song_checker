package karaokesonglist;

import java.util.List;
import karaokesonglist.models.SongRepository;
import karaokesonglist.visitors.SongCorrectVisitor;
import java.util.Scanner;
import java.util.TreeSet;
import javax.swing.UIManager;
import karaokesonglist.models.Pack;
import karaokesonglist.models.Series;
import karaokesonglist.ui.MainWindow;
import karaokesonglist.visitors.CorrectCheckVisitor;
import karaokesonglist.visitors.SongListVisitor;
import karaokesonglist.visitors.SongLoaderVisitor;

/**
 *
 * @author rodcastro
 */
public class Main {

    private static SongRepository repository = new SongRepository("/media/rodcastro/SAMSUNG/Karaoke songs");

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            System.out.println("Unable to load native look and feel");
        }

        String param = "/media/rodcastro/SAMSUNG/Karaoke songs/Sunfly";
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("What do you want to do?");
            System.out.println("C - correct folders, R - check corrected folders for errors, L - list songs, M - check for missing packs, I - open user interface");
            String response = scanner.next();
            if (response.equals("C") || response.equals("c")) {
                SongCorrectVisitor songVisitor = new SongCorrectVisitor(param);
                songVisitor.findSongs();
                songVisitor.applyPendingChanges();
            } else if (response.equals("R") || response.equals("r")) {
                CorrectCheckVisitor checkVisitor = new CorrectCheckVisitor(param);
                checkVisitor.findSongs();
                checkVisitor.applyPendingChanges();
            } else if (response.equals("L") || response.equals("l")) {
                SongListVisitor listVisitor = new SongListVisitor(param);
                listVisitor.listSongs();
                listVisitor.showInvalidDirectories();
                listVisitor.showSemiInvalidDirectories();
                listVisitor.showZipDirectories();
                listVisitor.showStatistics();
            } else if (response.equals("M") || response.equals("m")) {
                SongLoaderVisitor loader = new SongLoaderVisitor(param);
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
            }
            System.out.println("\n");
        }
    }

    public static SongRepository getRepository() {
        return repository;
    }
}
