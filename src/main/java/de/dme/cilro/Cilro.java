package de.dme.cilro;

import de.dme.cilro.files.CopyRandomImages;
import de.dme.cilro.files.FileCopy;
import de.dme.cilro.files.FileDeletion;

import javax.swing.*;
import java.io.File;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by d.meyer on 12.06.2017.
 */
public class Cilro {
    static Logger logger = Logger.getLogger(Cilro.class.getSimpleName());


    public Cilro(File sourceDir, File targetDir, int numberOfImagesToShuffleBack) {
        checkDirs(sourceDir, targetDir);
        logger.log(Level.INFO, "SourceDir:" + sourceDir.getAbsolutePath() + " TargetDir:" + targetDir.getAbsolutePath());
        File finalTargetDir = targetDir;
        Arrays.stream(sourceDir.listFiles()).parallel().forEach(file -> {
            if (!file.getName().contains("cilro")) {
                logger.log(Level.INFO, "Copy file:" + file.getAbsolutePath());
                new FileCopy(file, finalTargetDir);
            }
            logger.log(Level.INFO, "Deleting file:" + file.getAbsolutePath());
            new FileDeletion(file);
        });

        new CopyRandomImages(targetDir, sourceDir, 100);
    }

    private void checkDirs(File sourceDir, File targetDir) {
        if (sourceDir == null || !sourceDir.exists()) {
            throw new IllegalArgumentException("A existing Directory has to be specified as source-dir");
        }
        if (targetDir == null || !targetDir.exists()) {
            throw new IllegalArgumentException("A existing Directory has to be specified as target-dir");
        }
        if (!sourceDir.isDirectory()) {
            throw new IllegalArgumentException("A Directory has to be specified as source-dir");
        }
        if (!targetDir.isDirectory()) {
            throw new IllegalArgumentException("A Directory has to be specified as target-dir");
        }
    }

    public static void main(String[] args) {
        File sourceDir;
        File targetDir;
        if (args != null && args.length >= 2) {
            sourceDir = new File(args[0]);
            targetDir = new File(args[1]);
        } else {
            JFileChooser fileChooser = new JFileChooser();

            fileChooser.setDialogTitle("Choose your source image-directory");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.showDialog(null, "Accept");
            sourceDir = fileChooser.getSelectedFile();


            fileChooser.setDialogTitle("Choose your target image-directory");
            fileChooser.showDialog(null, "Accept");
            targetDir = fileChooser.getSelectedFile();
        }

        int numberOfImagesToShuffleBack = 100;
        if (args.length == 3) {
            numberOfImagesToShuffleBack = Integer.valueOf(args[2]);
        }

        new Cilro(sourceDir, targetDir, numberOfImagesToShuffleBack);

        logger.log(Level.INFO, "Done");
    }
}
