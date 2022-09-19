package uk.ac.ucl.shell.application;

import org.junit.Test;
import uk.ac.ucl.shell.ITest;
import uk.ac.ucl.shell.application.apps.Exit;
import uk.ac.ucl.shell.application.apps.Find;
import uk.ac.ucl.shell.application.apps.Grep;
import uk.ac.ucl.shell.exception.*;

import java.util.*;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;
import static uk.ac.ucl.shell.Constants.ALPHA_FILE;

public class ApplicationTest extends ITest
{
    @Test(expected = InvalidApplicationException.class)
    public void getApplication_InvalidApp_ShouldThrowException()
    {
        List<String> testInput = new ArrayList<>(Arrays.asList("hello", ALPHA_FILE));
        IApplication app = new ApplicationFactory().getApplication(testInput, "", "");
        app.exec();
    }

    @Test
    public void grep_ShouldReturnGrep()
    {
        List<String> testInput = new ArrayList<>(Arrays.asList("grep", ALPHA_FILE));
        IApplication app = new ApplicationFactory().getApplication(testInput, "", "");
        assertThat(app, instanceOf(Grep.class));
    }

    @Test
    public void find_ShouldReturnFind()
    {
        List<String> testInput = new ArrayList<>(Arrays.asList("find", "afdafd", "asdff"));
        IApplication app = new ApplicationFactory().getApplication(testInput, "", "");
        assertThat(app, instanceOf(Find.class));
    }

    @Test
    public void exit_ShouldReturnExit()
    {
        List<String> testInput = new ArrayList<>(Collections.singletonList("exit"));
        IApplication app = new ApplicationFactory().getApplication(testInput, "", "");
        assertThat(app, instanceOf(Exit.class));
    }

    @Test(expected = MissingFlagsException.class)
    public void missingRequiredFlag_ShouldThrowError()
    {
        List<String> testInput = new ArrayList<>(Arrays.asList("find", "arg1", "arg2"));
        IApplication app = new ApplicationFactory().getApplication(testInput, "", "");
        app.exec();
    }

    @Test(expected = FileDoesNotExistException.class)
    public void inputStream_NonExistentFile_ShouldThrowError()
    {
        IApplication app = new TestApp(new ArrayList<>(), "fakeapp.txt", "");
        app.exec();
    }

    @Test
    public void checkNumArgs_SameNum_ReturnsTrue()
    {
        List<String> testArgs = new ArrayList<>(Arrays.asList("a", "j"));
        var app = new TestApp(testArgs, "", "");
        assertTrue(app.checkNumArgs(app.args, 2));
    }

    @Test
    public void checkNumArgs_InRange_ReturnsTrue()
    {
        List<String> testArgs = new ArrayList<>(Arrays.asList("s", "j"));

        var app = new TestApp(testArgs, "", "");
        assertTrue(app.checkNumArgs(app.args, 0, 2));
    }

    @Test
    public void checkNumArgs_OutsideRange_ReturnFalse()
    {
        List<String> testArgs = new ArrayList<>(Arrays.asList("a", "j"));
        var app = new TestApp(testArgs, "", "");
        assertFalse(app.checkNumArgs(app.args, 0, 1));
    }

    @Test
    public void checkNumArgs_InRangeNoMax_ReturnsTrue()
    {
        List<String> testArgs = new ArrayList<>(Arrays.asList("a", "j"));
        var app = new TestApp(testArgs, "", "");
        assertTrue(app.checkNumArgs(app.args, 0, -1));
    }

    @Test
    public void checkNumArgs_OutOfRangeNoMax_ReturnFalse()
    {
        List<String> testArgs = new ArrayList<>(Arrays.asList("a", "j"));
        var app = new TestApp(testArgs, "", "");
        assertFalse(app.checkNumArgs(app.args, 3, -1));
    }

    @Test
    public void checkFlag_FlagExists_ReturnTrue()
    {
        List<String> testArgs = new ArrayList<>(Arrays.asList("hi.txt", "-n", "5"));
        var app = new TestApp(testArgs, "", "");
        assertTrue(app.checkFlag(testArgs, 1, "-n"));
    }

    @Test
    public void checkFlag_FlagNotThere_ReturnFalse()
    {
        List<String> testArgs = new ArrayList<>(Arrays.asList("hi.txt", "-n", "5"));
        var app = new TestApp(testArgs, "", "");
        assertFalse(app.checkFlag(testArgs, 1, "-k"));
    }

    @Test
    public void validateOptions_ArgsStartingWithDash_ShouldWork()
    {
        try
        {
            List<String> testArgs = new ArrayList<>(Arrays.asList("-", "-0"));
            new TestApp(testArgs, "", "");
        }
        catch(Exception e)
        {
            fail();
        }
    }

    @Test
    public void loadOptions_RemovesArgsFromCmdLine_AndAddsToOptions()
    {
        List<String> testArgs = new ArrayList<>(Arrays.asList("-n", "5", "hi.txt"));
        var app = new TestApp(testArgs, "", "", true);
        assertFalse(app.checkFlag(testArgs, 0, "n"));
    }

    @Test
    public void loadOptions_NoRequired_ShouldWork()
    {
        try
        {
            var app = new TestApp(new ArrayList<>(), "", "");
            app.loadOptions();
        }
        catch (Exception e)
        {
            fail();
        }
    }

    @Test(expected = AssertionError.class)
    public void loadOptions_InvalidOptionsPresent_ThrowError()
    {
        var app = new TestApp(new ArrayList<>(Collections.singletonList("-z")), "", "", true);
        app.loadOptions();
    }

    @Test
    public void optionInvoked_Valid_ShouldWork()
    {
        var app = new TestApp(new ArrayList<>(Arrays.asList("-n", "hello")), "", "", true);
        app.exec();
        assertTrue(app.optionInvoked("n"));
    }

    @Test(expected = AssertionError.class)
    public void optionInvoked_Invalid_ShouldThrowError()
    {
        new TestApp(new ArrayList<>(Arrays.asList("-s", "-o")), "", "").optionInvoked("p");
    }

    @Test(expected = InvalidNumOfArgsException.class)
    public void noArgAfterOptionWithArg_ShouldThrowError()
    {
        List<String> testInput = new ArrayList<>(Arrays.asList("-n", "-v", "arg"));
        IApplication app = new TestApp(testInput, "", "", true);
        app.exec();
    }

    private static class TestApp extends Application
    {
        // negative test
        protected TestApp(List<String> args, String inStream, String outStream)
        {
            super(args, inStream, outStream);
        }

        // positive test
        protected TestApp(List<String> args, String inStream, String outStream, Boolean testOptions)
        {
            super(args, inStream, outStream);
            validOptions.put("n", new Option("n", true, 1));
            validOptions.put("v", new Option("v", false, 0));
        }
        protected void validateArgs() { }
        protected void evaluate() { }
    }
}