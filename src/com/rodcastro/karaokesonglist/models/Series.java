package com.rodcastro.karaokesonglist.models;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 *
 * @author rodcastro
 */
public class Series {

    private String prefix;
    private TreeSet<Pack> packs;
    private static List<Series> seriesInstances = new ArrayList<Series>();

    private Series(String prefix) {
        this.prefix = prefix;
        this.packs = new TreeSet<Pack>();
    }

    public static Series getSeries(String packInfo) {
        for (Series series : seriesInstances) {
            if (series.prefix.equals(Series.getPrefixFromPackInfo(packInfo))) {
                return series;
            }
        }
        Series series = new Series(Series.getPrefixFromPackInfo(packInfo));
        seriesInstances.add(series);
        return series;
    }

    public static String getFullName(String pack) {
        String prefix = Series.getPrefixFromPackInfo(pack);
        if (prefix.equals("SF")) {
            return "Sunfly Hits";
        } else if (prefix.equals("MW")) {
            return "Sunfly Most Wanted";
        } else if (prefix.equals("KK")) {
            return "Sunfly Karaoke Kool";
        } else if (prefix.equals("PS")) {
            return "Sunfly Platinum Series";
        } else if (prefix.equals("GS")) {
            return "Sunfly Gold Series";
        }
        return "Error: Unknown prefix!";
    }

    public static String getPrefixFromPackInfo(String pack) {
        if (pack != null && pack.length() >= 2) {
            return pack.substring(0, 2).toUpperCase();
        }
        return "--";
    }

    public boolean addPack(Pack pack) {

        if (pack.getPrefix().equals(this.prefix)) {
            boolean added = packs.add(pack);
            if (added) {
                pack.setSeries(this);
            }
            return added;
        }
        return false;
    }

    public boolean containsPack(Pack pack) {
        return packs.contains(pack);
    }

    public TreeSet<Pack> getPacks() {
        return packs;
    }

    public void setPacks(TreeSet<Pack> packs) {
        this.packs = packs;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix.toUpperCase();
    }

    /**
     * Returns the number of the first pack in this series.
     */
    public int getFirstPack() {
        return packs.first().getNumber();
    }

    /**
     * Returns the number of the last pack in this series.
     */
    public int getLastPack() {
        return packs.last().getNumber();
    }

    /**
     * Return an array of all the pack numbers that are missing between the first
     * and last pack of this series.
     * 
     * @return An array indicating the missing packs in this series.
     */
    public List<Integer> getMissingPacks() {
        Pack current = packs.first();
        List<Integer> missing = new ArrayList<Integer>();
        for (int i = getFirstPack(); i < getLastPack(); i++) {
            Pack next = packs.higher(current);
            if (next == null) {
                return missing;
            }
            for (int j = current.getNumber() + 1; j < next.getNumber(); j++) {
                missing.add(j);
            }
            current = next;
        }
        return missing;
    }
}