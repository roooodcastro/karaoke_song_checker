package com.rodcastro.karaokesonglist.utils;

import java.io.File;

/**
 *
 * @author rodcastro
 */
public class FileUtils {

    public static boolean isFileValid(String filename) {
        if (filename != null) {
            File file = new File(filename);
            return file.exists();
        }
        return false;
    }
    
}
