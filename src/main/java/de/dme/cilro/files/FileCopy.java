package de.dme.cilro.files;


import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by d.meyer on 12.06.2017.
 */
public class FileCopy {
    Logger logger = Logger.getLogger(FileCopy.class.getName());
    private final File file;
    private final File targetDir;
    private final String addToName;

    public FileCopy(File toCopy, File targetDir) {
        this(toCopy, targetDir, null);
    }

    public FileCopy(File toCopy, File targetDir, String addToName) {
        this.file = toCopy;
        this.targetDir = targetDir;
        this.addToName = addToName;
    }

    public void doCopy() {
        FileImageInputStream fileImageInputStream = null;
        FileImageOutputStream fileImageOutputStream = null;
        try {
            fileImageInputStream = new FileImageInputStream(file);


            File newFile = new File(targetDir.getAbsolutePath().concat(File.separator).concat(createTargetFileName()));
            if (newFile.createNewFile()) {
                fileImageOutputStream = new FileImageOutputStream(newFile);
                byte[] buffer = new byte[4096];
                while (fileImageInputStream.read(buffer) > 0) {
                    fileImageOutputStream.write(buffer);
                }
            }
        } catch (IOException e) {
            logger.log(Level.INFO, e.getMessage());
        } finally {
            try {
                if (fileImageInputStream != null) {
                    fileImageInputStream.close();
                }
                if (fileImageOutputStream != null) {
                    fileImageOutputStream.close();
                }
            } catch (IOException e) {
                logger.log(Level.INFO, e.getMessage());
            }
        }
    }

    private String createTargetFileName() {
        StringBuffer result = new StringBuffer(file.getName());
        if (addToName != null) {
            result.insert(result.lastIndexOf("."), addToName);
        }
        return result.toString();
    }
}
