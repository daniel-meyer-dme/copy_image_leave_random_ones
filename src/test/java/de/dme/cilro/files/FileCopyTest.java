package de.dme.cilro.files;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * Created by d.meyer on 12.06.2017.
 */
public class FileCopyTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    File targetFolder;


    private File test1;
    private File test2;


    @Before
    public void setUp() throws Exception {
        File sourceFolder = temporaryFolder.newFolder("source");

        test1 = File.createTempFile("Test", ".png", sourceFolder);
        test2 = File.createTempFile("Test2", ".png", sourceFolder);

        targetFolder = temporaryFolder.newFolder("target");

    }

    @Test
    public void files_should_be_copied() {
        new FileCopy(test1, targetFolder).doCopy();
        new FileCopy(test2, targetFolder).doCopy();

        assertEquals("File should have been copied", 1, targetFolder.listFiles((dir, name) -> name.equals(test1.getName())).length);
        assertEquals("File should have been copied", 1, targetFolder.listFiles((dir, name) -> name.equals(test2.getName())).length);
    }

    @Test
    public void filenames_should_be_changed_by_param() {
        new FileCopy(test1, targetFolder, "_test").doCopy();
        new FileCopy(test2, targetFolder).doCopy();

        String newNameTest1 = test1.getName().replace(".", "_test.");

        assertEquals("File should have been renamed", 0, targetFolder.listFiles((dir, name) -> name.equals(test1.getName())).length);
        assertEquals("File should have been copied and renamed", 1, targetFolder.listFiles((dir, name) -> name.equals(newNameTest1)).length);
        assertEquals("File should have been copied and not renamed", 1, targetFolder.listFiles((dir, name) -> name.equals(test2.getName())).length);
    }


}