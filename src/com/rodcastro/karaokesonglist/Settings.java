package com.rodcastro.karaokesonglist;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.prefs.Preferences;

/**
 *
 * @author Rodrigo
 */
public class Settings {

    public static final String PREF_WORKING_PATH = "working_path";
    public static final String PREF_KARAOKE_PATH = "karaoke_path";
    private final static Properties properties = new Properties();
    private final static File propertiesFile = new File("karaoke_browser.properties");

    static {
        try {
            propertiesFile.createNewFile();
            properties.load(new FileInputStream(propertiesFile));
        } catch (IOException ex) {
        }

    }
    
    private static boolean setPreference(String pref, String path) {
        try {
            properties.setProperty(pref, path);
            properties.store(new FileOutputStream(propertiesFile), null);
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public static String getWorkingPath() {
        return properties.getProperty(PREF_WORKING_PATH, ".");
    }

    public static boolean setWorkingPath(String path) {
        return setPreference(PREF_WORKING_PATH, path);
    }
    
    public static String getKaraokePath() {
        return properties.getProperty(PREF_KARAOKE_PATH, null);
    }
    
    public static boolean setKaraokePath(String path) {
        return setPreference(PREF_KARAOKE_PATH, path);
    }
}