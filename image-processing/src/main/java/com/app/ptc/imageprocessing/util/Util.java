package com.app.ptc.imageprocessing.util;

import org.springframework.http.MediaType;

import java.util.Locale;

public class Util {
    /**
     * Gets the extension from a filename/path.
     *
     * @param filename
     * @return
     */
    public static String getExtension(String filename) {

        String extension  = "";

        filename = filename.toLowerCase(Locale.ROOT);

        if (filename.indexOf(".") != -1)
            extension = filename.substring(filename.lastIndexOf("."));

        return extension;
    }
}
