package de.dme.cilro.files;

import java.io.File;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by d.meyer on 12.06.2017.
 */
public class CopyRandomImages {

    Set<File> toCopy;
    SecureRandom random = new SecureRandom();

    public CopyRandomImages(File sourceDir, File targetDir, int numberOfImages) {
        final File[] listFiles = sourceDir.listFiles(file -> file.isFile());

        if (listFiles.length < numberOfImages) {
            numberOfImages = listFiles.length;
        }

        toCopy = new HashSet<>(numberOfImages);


        while (toCopy.size() < numberOfImages) {
            File fileToCopy = listFiles[random.nextInt(listFiles.length)];

            toCopy.add(fileToCopy);
        }

        toCopy.parallelStream().forEach(file -> new FileCopy(file, targetDir, "_cilro").doCopy());

    }
}
