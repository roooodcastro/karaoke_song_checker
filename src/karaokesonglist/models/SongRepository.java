package karaokesonglist.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import karaokesonglist.visitors.SongLoaderVisitor;

/**
 *
 * @author rodcastro
 */
public class SongRepository {

    private SongLoaderVisitor visitor;
    private TreeSet<Song> songs;
    private List<Song> invalidSongs;
    private TreeSet<Pack> packs;

    public SongRepository(String path) {
        visitor = new SongLoaderVisitor(path);
        songs = new TreeSet<Song>();
        packs = new TreeSet<Pack>();
        invalidSongs = new ArrayList<Song>();
    }

    public void loadSongs() {
        if (songs.isEmpty()) {
            visitor.loadSongs();
            this.songs = visitor.getSongs();
            this.packs = visitor.getPacks();
            this.invalidSongs = visitor.getInvalidSongs();
        }
    }

    public int getSongCount() {
        return songs.size();
    }

    public Song findSong(int uniqueId) {
        Song mock = new Song(uniqueId);
        return songs.floor(mock);
    }

    public String[][] findSongs(String search) {
        Iterator<Song> iterator = songs.iterator();
        List<Song> results = new ArrayList<Song>();
        Song current;
        while (iterator.hasNext()) {
            current = iterator.next();
            if (current.getArtist().toUpperCase().contains(search.toUpperCase())
                    || current.getSongName().toUpperCase().contains(search.toUpperCase())) {
                results.add(current);
            }
        }
        String[][] array = new String[results.size()][];
        for (int i = 0; i < results.size(); i++) {
            Song song = results.get(i);
            String[] row = new String[4];
            if (song.getPack() != null) {
                row[0] = song.getPack().getFullName();
            } else {
                row[0] = "---";
            }
            row[1] = song.getFormattedArtist();
            row[2] = song.getFormattedSongName();
            row[3] = song.getUniqueId() + "";
            array[i] = row;
        }
        return array;
    }

    public String[][] findInvalidSongs(String search) {
        List<Song> results = new ArrayList<Song>();
        for (Song song : invalidSongs) {
            if (song.getFileName().toUpperCase().contains(search.toUpperCase())) {
                results.add(song);
            }
        }
        String[][] array = new String[results.size()][];
        for (int i = 0; i < results.size(); i++) {
            Song song = results.get(i);
            String[] row = new String[1];
            row[0] = song.getFileName();
            array[i] = row;
        }
        return array;
    }
}