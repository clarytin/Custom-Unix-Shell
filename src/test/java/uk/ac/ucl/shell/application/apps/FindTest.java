package uk.ac.ucl.shell.application.apps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import uk.ac.ucl.shell.ITest;
import uk.ac.ucl.shell.application.ApplicationFactory;
import uk.ac.ucl.shell.application.IApplication;
import uk.ac.ucl.shell.exception.IllegalOptionException;
import uk.ac.ucl.shell.exception.InvalidNumOfArgsException;
import uk.ac.ucl.shell.exception.MissingFlagsException;

import static uk.ac.ucl.shell.Constants.*;

public class FindTest extends ITest
{
    @Test(expected = InvalidNumOfArgsException.class)
    public void constructor_noArgs_returnsException() {
        List<String> inputArg = new ArrayList<>();
        inputArg.add("find");
        IApplication app = new ApplicationFactory().getApplication(inputArg, "", "");
        app.exec();
    }

/*     @Test(expected = RuntimeException.class) 
    public void constructor_invalidNameArg_returnsException() {
        List<String> inputArg = new ArrayList<>();
        inputArg.add("find");
        inputArg.add("-name");
        inputArg.add("invalid*");
        inputArg.add("invalid2*");
        IApplication app = new ApplicationFactory().getApplication(inputArg, "", "");
        app.exec();
    } */

    private List<String> getTestMdFiles() {
        String expected1 = Paths.get(".", "src", "test", "res", "testexamples", "animals.md").toString();
        String expected2 = Paths.get(".", "src", "test", "res", "testexamples", "flowers.md").toString();
        List<String> expectedFiles = Arrays.asList(expected1, expected2);
        return expectedFiles;
    }
    
    @Test
    public void evaluate_validArgs_returnsFiles() {
        String pathInput = Paths.get("src", "test", "res", "*.md").toString();
        List<String> inputArg = new ArrayList<>(Arrays.asList("find", "-name", pathInput));
        List<String> expectedFiles = getTestMdFiles();
        IApplication app = new ApplicationFactory().getApplication(inputArg, "", "");

        app.exec();

        //assertEquals(expectedFiles, app.getOutput());

        assertTrue(CollectionUtils.isEqualCollection(expectedFiles, app.getOutput()));
    }

    /* @RunWith(Parameterized.class)
    public static class FindParamTest extends ITest
    {
        private final List<String> testInput;
        private final String expectedResult;

        public FindParamTest(List<String> testInput, String expectedResult)
        {
            this.testInput = testInput;
            this.expectedResult = expectedResult;
        }

        @Parameterized.Parameters
        public static Collection testInputs()
        {
            var testName= new ArrayList<>(Arrays.asList("find", "-name", "pom.xml"));
            var testDirectory = new ArrayList<>(Arrays.asList("find", "-name", EXAMPLES_DIR, "numbers.txt"));
            var testDirectoryWithGlobbing = new ArrayList<>(Arrays.asList("find", "-name", EXAMPLES_DIR, "*.txt"));
            var testEmptyOutput = new ArrayList<>(Arrays.asList("find", "-name", "randomname.pdf"));

            return Arrays.asList(new Object[][]{
                    {testName, "fill"},
                    {testDirectory, "fill"},
                    {testDirectoryWithGlobbing, "fill"},
                    {testEmptyOutput, "fill"},
            });
        } 
        
        @Test
        public void find_ShouldOutputCorrectFiles()
        {
            IApplication app = new ApplicationFactory().getApplication(this.testInput, "", "");
            app.exec();
            assertEquals(this.expectedResult, String.join(",", app.getOutput()));
        } 
    } */
    
    @Test(expected = MissingFlagsException.class)
    public void find_NoFlag_ThrowsException()
    {
        List<String> testInput = new ArrayList<>(Arrays.asList("find", NUMBERS_FILE, LIST_FILE));
        IApplication app = new ApplicationFactory().getApplication(testInput, "", "");
        app.exec();
    }

    @Test(expected = InvalidNumOfArgsException.class)
    public void find_NoArgs_ThrowsException()
    {
        List<String> testInput = new ArrayList<>(Arrays.asList("find"));
        IApplication app = new ApplicationFactory().getApplication(testInput, "", "");
        app.exec();
    }

    @Test(expected = IllegalOptionException.class)
    public void find_InvalidOption_ThrowsIllegalOptionException()
    {
        var find = new Find(new ArrayList<>(Arrays.asList("-p", NUMBERS_FILE)), "", "");
        find.exec();
    }

}
