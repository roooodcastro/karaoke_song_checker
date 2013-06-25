package com.rodcastro.karaokesonglist.models;

import java.io.File;
import com.rodcastro.karaokesonglist.utils.StringUtils;

/**
 *
 * @author rodcastro
 */
public class Song implements Comparable<Song> {

    private String packInfo = null;
    private Pack pack = null;
    private int number = -1;
    private String artist = null;
    private String songName = null;
    private String fileName = null;
    private String directory = null;
    private boolean valid = false;
    private int uniqueId = 0;

    public Song(String directory, String filename) {
        try {
            this.directory = directory;
            this.fileName = filename;
            String[] partsDot = filename.split("\\.");
            String[] partsDash = StringUtils.join(partsDot, ".", 0, partsDot.length - 1).split("-");
            if (partsDash.length == 4) {
                this.packInfo = partsDash[0].trim();
                this.number = Integer.valueOf(partsDash[1].trim());
                this.artist = partsDash[2].trim();
                this.songName = partsDash[3].trim();
                this.uniqueId = StringUtils.generateHash(packInfo) * (StringUtils.generateHash(songName) + StringUtils.generateHash(artist));
                valid = true;
            }
        } catch (Exception ex) {
        }
    }

    public Song(String directory, String pack, int number, String artist, String songName) {
        this.directory = directory;
        this.packInfo = pack;
        this.number = number;
        this.artist = artist;
        this.songName = songName;
        this.fileName = toFilename(null);
        this.valid = true;
    }

    public Song(int uniqueId) {
        this.uniqueId = uniqueId;
        this.valid = false;
    }

    public static Song decodeFileName(String directory, String filename) {
        Song song = new Song(directory, filename);
        if (song.valid) {
            return song;
        }
        return null;
    }

    /**
     * Recompiles the filename based on the current information about the song
     * and renames the two files (.cdg & .mp3) accordingly.
     */
    public void updateFile() {
        File oldCdgFile = new File(directory + "/" + fileName.substring(0, fileName.length() - 4) + ".cdg");
        File oldMp3File = new File(directory + "/" + fileName.substring(0, fileName.length() - 4) + ".mp3");
        File newCdgFile = new File(directory + "/" + toFilename("cdg"));
        File newMp3File = new File(directory + "/" + toFilename("mp3"));
        oldCdgFile.renameTo(newCdgFile);
        oldMp3File.renameTo(newMp3File);
    }

    public String toFilename(String extension) {
        if (valid) {
            String defaultName = packInfo + "-" + number + " - " + artist + " - " + songName;
            if (extension != null) {
                return defaultName + "." + extension;
            } else {
                return defaultName;
            }
        } else {
            return fileName;
        }
    }

    public boolean isValid() {
        return valid;
    }

    @Override
    public String toString() {
        if (valid) {
            return "Pack: \"" + packInfo + "\", Number: " + StringUtils.addTrailingWhitespace(number + ", ", 5) + "Artist: \""
                    + StringUtils.addTrailingWhitespace(artist + "\", ", 50)
                    + "Song: \"" + songName + "\"";
        } else {
            return "ERROR: File name invalid: " + fileName;
        }
    }

    @Override
    public int compareTo(Song another) {
        return this.uniqueId - another.uniqueId;
    }

    public String getArtist() {
        return artist;
    }

    public String getFormattedArtist() {
        return artist.replace("*", "-");
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getPackInfo() {
        return packInfo;
    }

    public void setPackInfo(String packInfo) {
        this.packInfo = packInfo;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getFormattedSongName() {
        return songName.replace("*", "-");
    }

    public Pack getPack() {
        return pack;
    }

    public void setPack(Pack pack) {
        this.pack = pack;
    }

    public int getUniqueId() {
        return uniqueId;
    }
    
    public String getAbsolutePath() {
        return directory + "/" + fileName;
    }
}