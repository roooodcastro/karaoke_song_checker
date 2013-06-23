package com.rodcastro.karaokesonglist;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 *
 * @author Rodrigo
 */
public class Settings {

    private static Preferences preferences = Preferences.userNodeForPackage(com.rodcastro.karaokesonglist.Settings.class);
    public static final String PREF_WORKING_PATH = "working_path";

    public static String getWorkingPath() {
        return preferences.get(PREF_WORKING_PATH, ".");
    }

    public static boolean setWorkingPath(String path) {
        try {
            preferences.put(PREF_WORKING_PATH, path);
            preferences.flush();
            return true;
        } catch (BackingStoreException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}