package de.dme.cilro.files;

import java.io.File;

/**
 * Created by d.meyer on 12.06.2017.
 */
public class FileDeletion {

    public FileDeletion(File toDelete) {

        if (toDelete != null && toDelete.isFile()) {
            toDelete.delete();
        }
    }


}
