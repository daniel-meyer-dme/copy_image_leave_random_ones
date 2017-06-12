package de.dme.cilro.files;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by d.meyer on 12.06.2017.
 */
public class CopyRandomImagesTest {
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    File targetFolder;


    private List<File> fileList;
    private File sourceFolder;


    @Before
    public void setUp() throws Exception {
        sourceFolder = temporaryFolder.newFolder("source");

        File test1 = File.createTempFile("Test", ".png", sourceFolder);
        File test2 = File.createTempFile("Test2", ".png", sourceFolder);
        File test3 = File.createTempFile("Test3", ".png", sourceFolder);
        File test4 = File.createTempFile("Test4", ".png", sourceFolder);

        fileList = Arrays.asList(test1, test2, test3, test4);

        targetFolder = temporaryFolder.newFolder("target");

    }

    @Test
    public void given_number_of_files_should_be_copied() {
        final int numberOfImages = new SecureRandom().nextInt(fileList.size()) + 1;
        new CopyRandomImages(sourceFolder, targetFolder, numberOfImages);

        assertEquals(numberOfImages + " files should have been copied", numberOfImages, targetFolder.listFiles().length);


    }

    @Test
    public void max_source_file_count_should_be_copied() {
        final int numberOfImages = 100;
        new CopyRandomImages(sourceFolder, targetFolder, numberOfImages);

        assertEquals(fileList.size() + " files should have been copied", fileList.size(), targetFolder.listFiles().length);
    }

    @Test
    public void filenames_should_be_changed() {
        new CopyRandomImages(sourceFolder, targetFolder, 2);

        assertEquals("Files should have been copied and renamed", 2, targetFolder.listFiles((dir, name) -> name.contains("_cilro")).length);
    }
}