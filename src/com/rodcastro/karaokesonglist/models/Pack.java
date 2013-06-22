package com.rodcastro.karaokesonglist.models;

import java.util.TreeSet;

/**
 *
 * @author rodcastro
 */
public class Pack implements Comparable<Pack> {

    private String prefix;
    private int number;
    private String packInfo;
    private TreeSet<Song> songs;
    private Series series;

    public Pack(String packInfo) {
        this.songs = new TreeSet<Song>();
        this.number = Integer.parseInt(packInfo.substring(2).trim());
        this.prefix = Series.getPrefixFromPackInfo(packInfo);
        this.packInfo = packInfo.toUpperCase();
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public TreeSet<Song> getSongs() {
        return songs;
    }

    public void setSongs(TreeSet<Song> songs) {
        this.songs = songs;
    }

    public String getPackInfo() {
        return packInfo;
    }

    public void setPackInfo(String packInfo) {
        this.packInfo = packInfo;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public boolean addSong(Song song) {
        if (song.isValid()) {
            String songPrefix = Series.getPrefixFromPackInfo(song.getPackInfo());
            if (songPrefix.equals(this.series.getPrefix())) {
                boolean added = songs.add(song);
                if (added) {
                    song.setPack(this);
                }
                return added;
            }
        }
        return false;
    }

    public boolean containsSong(Song song) {
        return songs.contains(song);
    }

    public boolean removeSong(Song song) {
        boolean removed = songs.remove(song);
        if (removed) {
            song.setPack(null);
        }
        return removed;
    }

    public Series getSeries() {
        return series;
    }

    public boolean setSeries() {
        return Series.getSeries(packInfo).addPack(this);
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public String getFullName() {
        return Series.getFullName(packInfo) + " " + this.number;
    }

    @Override
    public int compareTo(Pack another) {
        return this.packInfo.compareTo(another.packInfo);
    }
}
