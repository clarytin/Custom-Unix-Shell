package uk.ac.ucl.shell.command.util;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import uk.ac.ucl.shell.ITest;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GlobbingTest extends ITest
{
    private List<String> getTestMdFiles() {
        String expected1 = Paths.get("src", "test", "res", "testexamples", "animals.md").toString();
        String expected2 = Paths.get("src", "test", "res", "testexamples", "flowers.md").toString();
        List<String> expectedFiles = Arrays.asList(expected1, expected2);
        return expectedFiles;
    }

    private List<String> getTestTextFiles() {
        String expectedFile1 = Paths.get("src", "test", "res", "testexamples", "alpha.txt").toString();
        String expectedFile2 = Paths.get("src", "test", "res", "testexamples", "different_length.txt").toString();
        String expectedFile3 = Paths.get("src", "test", "res", "testexamples", "empty.txt").toString();
        String expectedFile4 = Paths.get("src", "test", "res", "testexamples", "list.txt").toString();
        String expectedFile5 = Paths.get("src", "test", "res", "testexamples", "numbers.txt").toString();
        String expectedFile6 = Paths.get("src", "test", "res", "testexamples", "testOutput.txt").toString();
        String expectedFile7 = Paths.get("src", "test", "res", "testexamples", "testOutput2.txt").toString();
        List<String> expectedFiles = Arrays.asList(expectedFile1,
                                                    expectedFile2,
                                                    expectedFile3,
                                                    expectedFile4,
                                                    expectedFile5,
                                                    expectedFile6,
                                                    expectedFile7);
        return expectedFiles;
    }

    private List<String> getRecursiveTxtFiles() {
        String expectedFile1 = Paths.get("src", "test", "res", "testexamples", "alpha.txt").toString();
        String expectedFile2 = Paths.get("src", "test", "res", "testexamples", "different_length.txt").toString();
        String expectedFile3 = Paths.get("src", "test", "res", "testexamples", "empty.txt").toString();
        String expectedFile4 = Paths.get("src", "test", "res", "testexamples", "list.txt").toString();
        String expectedFile5 = Paths.get("src", "test", "res", "testexamples", "numbers.txt").toString();
        String expectedFile6 = Paths.get("src", "test", "res", "testexamples", "testOutput.txt").toString();
        String expectedFile7 = Paths.get("src", "test", "res", "testexamples", "testOutput2.txt").toString();
        String expectedFile8 = Paths.get("src", "test", "res", "example.txt").toString();
        List<String> expectedFiles = Arrays.asList(expectedFile1,
                                                    expectedFile2,
                                                    expectedFile3,
                                                    expectedFile4,
                                                    expectedFile5,
                                                    expectedFile6,
                                                    expectedFile7,
                                                    expectedFile8);
        return expectedFiles;
    }

    @Test
    public void initGlobbing_noValues_returnsGlobbingObj() {
        GlobbingUtil gUtil = new GlobbingUtil();
        assertEquals(GlobbingUtil.class, gUtil.getClass());
    }

    @Test
    public void execGlobbing_testResources_returnsMdFiles()
    {
        List<String> testDirPath = Arrays.asList(Paths.get("src", "test", "res", "testexamples", "*.md").toString());
        List<String> testInput = new ArrayList<>(testDirPath);
        GlobbingUtil gUtil = new GlobbingUtil();
        List<String> expectedFiles = getTestMdFiles();

        List<String> result = gUtil.execGlobbing(testInput);

        assertTrue(CollectionUtils.isEqualCollection(expectedFiles, result));
    }

    @Test
    public void execGlobbing_testResources_returnsTxtFiles() {
        List<String> testDirPath = Arrays.asList(Paths.get("src", "test", "res", "testexamples", "*.txt").toString());
        List<String> testInput = new ArrayList<>(testDirPath);
        GlobbingUtil gUtil = new GlobbingUtil();
        List<String> expectedFiles = getTestTextFiles();

        List<String> result = gUtil.execGlobbing(testInput);

        assertTrue(CollectionUtils.isEqualCollection(expectedFiles, result));
    }

    @Test(expected=RuntimeException.class)
    public void execGlobbing_invalidSearch_returnsError() {
        List<String> testDirPath = Arrays.asList(Paths.get("src", "test", "res", "testexamples", "invalidSearch*.txt").toString());
        List<String> testInput = new ArrayList<>(testDirPath);
        GlobbingUtil gUtil = new GlobbingUtil();

        gUtil.execGlobbing(testInput);
    }

    @Test
    public void execGlobbing_multipleArgs_returnsFiles() {
        Path arg1 = Paths.get("src", "test", "res", "testexamples", "*.txt");
        Path arg2 = Paths.get("src", "test", "res", "testexamples", "*.md");
        List<String> testArgs = Arrays.asList(arg1.toString(), arg2.toString());
        GlobbingUtil gUtil = new GlobbingUtil();
        List<String> expectedTextFiles = getTestTextFiles();
        List<String> expectedMdFiles = getTestMdFiles();
        List<String> expectedFiles = new ArrayList<>();
        expectedFiles.addAll(expectedTextFiles);
        expectedFiles.addAll(expectedMdFiles);

        List<String> result = gUtil.execGlobbing(testArgs);

        assertTrue(CollectionUtils.isEqualCollection(expectedFiles, result));
    }

    @Test
    public void getGlobMatchesRecursively_validArg_returnsMdFiles() {
        Path arg = Paths.get("src", "test", "res", "*.md");
        List<String> testArg = Arrays.asList(arg.toString());
        GlobbingUtil gUtil = new GlobbingUtil();
        List<String> expectedFiles = getTestMdFiles();

        List<String> result = gUtil.getGlobMatchesRecursively(testArg);

        //assertEquals(expectedFiles, result);
        assertTrue(CollectionUtils.isEqualCollection(expectedFiles, result));
    }

    @Test
    public void getGlobMatchesRecursively_multipleArgs_returnsMdTxtFiles() {
        Path arg1 = Paths.get("src", "test", "res", "*.md");
        Path arg2 = Paths.get("src", "test", "res", "*.txt");
        List<String> testArgs = Arrays.asList(arg1.toString(), arg2.toString());
        GlobbingUtil gUtil = new GlobbingUtil();
        List<String> expectedTextFiles = getRecursiveTxtFiles();
        List<String> expectedMdFiles = getTestMdFiles();
        List<String> expectedFiles = new ArrayList<>();
        expectedFiles.addAll(expectedTextFiles);
        expectedFiles.addAll(expectedMdFiles);

        List<String> result = gUtil.getGlobMatchesRecursively(testArgs);

        //assertEquals(expectedFiles, result);
        assertTrue(CollectionUtils.isEqualCollection(expectedFiles, result));
    } 
}
