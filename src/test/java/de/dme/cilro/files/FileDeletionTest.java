package de.dme.cilro.files;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Created by d.meyer on 12.06.2017.
 */
public class FileDeletionTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    File sourceFolder;


    private File test1;
    private File test2;

    @org.junit.Before
    public void setUp() throws Exception {
        sourceFolder = temporaryFolder.newFolder("source");
        test1 = File.createTempFile("Test", ".png", sourceFolder);
        test2 = File.createTempFile("Test2", ".png", sourceFolder);
    }

    @Test
    public void files_should_be_deleted() {
        new FileDeletion(test1);
        new FileDeletion(test2);
        new FileDeletion(sourceFolder);


        assertFalse("The File has to be deleted", test1.exists());
        assertFalse("The File has to be deleted", test2.exists());
        assertTrue("Folders shouldnt be deleted", sourceFolder.exists());

    }

}