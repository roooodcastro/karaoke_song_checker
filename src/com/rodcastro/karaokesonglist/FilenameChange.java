package com.rodcastro.karaokesonglist;

import com.rodcastro.karaokesonglist.utils.StringUtils;
import java.io.File;

/**
 *
 * @author rodcastro
 */
public class FilenameChange {

    private String directory;
    private String order;
    private String tag;
    public static final char ORDER_TAG = 'T';
    public static final char ORDER_SONG_NUMBER = 'N';
    public static final char ORDER_ARTIST = 'A';
    public static final char ORDER_SONG_NAME = 'S';
    public static final char ORDER_MIX_NUMBER_AND_NAME = 'M';
    public static final char ORDER_MIX_NUMBER_ARTIST = 'R';
    public static final char ORDER_MIX_NUM_ART_SONG = 'E';
    private String delimiter = " - ";

    public FilenameChange(String directory, String order) {
        this.directory = directory;
        String[] folders = directory.split("/");
        tag = folders[folders.length - 1].replace(" ", "").replace("OK", "");
        System.out.println(tag);
        this.order = order;
    }

    public void apply() {
        File dir = new File(directory);
        String[] files = dir.list();
        for (String file : files) {
            String[] partsDot = file.split("\\.");
            if (partsDot.length > 0) {
                String extension = partsDot[partsDot.length - 1];
                String fileNameNoExt = file.substring(0, file.length() - 4);
                String[] partsDash = fileNameNoExt.split("-");
                String songNumber = "0";
                String artist = null;
                String songName = null;
                String[] parted;
                if (partsDash.length == order.length()) {
                    int pos = 0;
                    for (char c : order.toCharArray()) {
                        switch (c) {
                            case ORDER_TAG:
                                break;
                            case ORDER_SONG_NUMBER:
                                songNumber = partsDash[pos].trim().replaceAll("[^0-9]", "");
                                break;
                            case ORDER_ARTIST:
                                artist = StringUtils.formatName(partsDash[pos]);
                                break;
                            case ORDER_SONG_NAME:
                                songName = StringUtils.formatName(partsDash[pos]);
                                break;
                            case ORDER_MIX_NUMBER_AND_NAME:
                                parted = partsDash[pos].trim().split(" ");
                                if (parted[0].trim().equals("Track")) {
                                    songNumber = parted[1].trim().replaceAll("[^0-9]", "");
                                    songName = StringUtils.formatName(StringUtils.join(parted, " ", 2, parted.length));
                                } else {
                                    songNumber = parted[0].trim().replaceAll("[^0-9]", "");
                                    songName = StringUtils.formatName(StringUtils.join(parted, " ", 1, parted.length));
                                }
                                break;
                            case ORDER_MIX_NUMBER_ARTIST:
                                parted = partsDash[pos].trim().split(" ");
                                if (parted[0].trim().equals("Track")) {
                                    songNumber = parted[1].trim().replaceAll("[^0-9]", "");
                                    artist = StringUtils.formatName(StringUtils.join(parted, " ", 2, parted.length));
                                } else {
                                    songNumber = parted[0].trim().replaceAll("[^0-9]", "");
                                    artist = StringUtils.formatName(StringUtils.join(parted, " ", 1, parted.length));
                                }
                                break;
                            case ORDER_MIX_NUM_ART_SONG:
                                String corrected = partsDash[pos].replace("  ", "-");
                                String[] parts = corrected.split("-");
                                if (parts.length == 2) {
                                    parted = parts[0].trim().split(" ");
                                    songNumber = parted[0].trim().replaceAll("[^0-9]", "");
                                    artist = StringUtils.formatName(StringUtils.join(parted, " ", 1, parted.length));
                                    songName = StringUtils.formatName(parts[1]);
                                }
                                break;
                        }
                        pos++;
                    }
                    String newFileName = tag + "-" + songNumber + delimiter + artist + delimiter + songName + "." + extension;
                    File oldFile = new File(directory + "/" + file);
                    File renamedFile = new File(directory + "/" + newFileName);
                    oldFile.renameTo(renamedFile);
                    System.out.println("Renamed file '" + newFileName + "'");
                } else {
                }
            }
        }
        File oldDir = new File(directory);
        File newDir = new File(directory + " OK");
        oldDir.renameTo(newDir);
        System.out.println("Marked directory '" + oldDir + "' as corrected");
    }
}
