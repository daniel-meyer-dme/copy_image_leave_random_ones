package de.dme.cilro;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by d.meyer on 12.06.2017.
 */
public class CilroTest {
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    File targetFolder;


    private List<File> fileList;
    private File sourceFolder;
    private File test1;
    private File test2;


    @Before
    public void setUp() throws Exception {
        sourceFolder = temporaryFolder.newFolder("source");

        test1 = File.createTempFile("Test", ".png", sourceFolder);
        test2 = File.createTempFile("Test2", ".png", sourceFolder);
        File test3 = File.createTempFile("Test3", ".png", sourceFolder);
        File test4 = File.createTempFile("Test4", ".png", sourceFolder);

        fileList = Arrays.asList(test1, test2, test3, test4);

        targetFolder = temporaryFolder.newFolder("target");

    }

    @Test(expected = IllegalArgumentException.class)
    public void only_dirs_and_no_files_should_be_accepted_1() {
        new Cilro(test1, test2, 100);
        fail("Only dirs should be specified");
    }


    @Test(expected = IllegalArgumentException.class)
    public void only_dirs_and_no_files_should_be_accepted_2() {
        new Cilro(targetFolder, test2, 100);
        fail("Only dirs should be specified");
    }


    @Test(expected = IllegalArgumentException.class)
    public void only_dirs_and_no_files_should_be_accepted_3() {
        new Cilro(test1, targetFolder, 100);
        fail("Only dirs should be specified");
    }


    @Test(expected = IllegalArgumentException.class)
    public void no_null_values_should_be_accepted_1() {
        new Cilro(targetFolder, null, 100);
        fail("No null values allowed");
    }

    @Test(expected = IllegalArgumentException.class)
    public void no_null_values_should_be_accepted_2() {
        new Cilro(null, targetFolder, 100);
        fail("No null values allowed");
    }

    @Test(expected = IllegalArgumentException.class)
    public void only_existing_dirs_should_be_accepted_1() {
        new Cilro(sourceFolder, new File("test"), 100);
        fail("Only exiting Dirs allowed");
    }

    @Test(expected = IllegalArgumentException.class)
    public void only_existing_dirs_should_be_accepted_2() {
        new Cilro(new File("test"), sourceFolder, 100);
        fail("Only exiting Dirs allowed");
    }

    @Test
    public void random_files_should_be_ignored_on_copy() throws IOException {
        File test3 = File.createTempFile("Test5", "_cilro.png", sourceFolder);

        new Cilro(sourceFolder, targetFolder, 100);

        assertEquals("No random file should be copied", 0, sourceFolder.listFiles((dir, name) -> name.contains("_cilro")).length);
    }

    @Test
    public void source_files_should_be_copied() throws IOException {
        new Cilro(sourceFolder, targetFolder, 100);

        assertEquals("All source-files should be copied", fileList.size(), targetFolder.listFiles().length);
    }

    @Test
    public void target_files_should_be_copied() throws IOException {
        new Cilro(sourceFolder, targetFolder, 3);

        assertEquals("3 random images should be copied to source", 3, targetFolder.listFiles().length);
    }

}