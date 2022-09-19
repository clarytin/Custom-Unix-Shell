package uk.ac.ucl.shell.application.apps;

import org.junit.Test;
import uk.ac.ucl.shell.ITest;
import uk.ac.ucl.shell.application.ApplicationFactory;
import uk.ac.ucl.shell.application.IApplication;
import uk.ac.ucl.shell.exception.InvalidDirectoryException;
import uk.ac.ucl.shell.exception.InvalidNumOfArgsException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static uk.ac.ucl.shell.Constants.LIST_FILE;

public class CdTest extends ITest
{
    @Test
    public void cd_ShouldOutputListContents()
    {
        List<String> testInput = new ArrayList<>(Arrays.asList("cd", "src"));
        IApplication app = new ApplicationFactory().getApplication(testInput, "", "");
        app.exec();

        testInput = new ArrayList<>(Collections.singletonList("pwd"));
        app = new ApplicationFactory().getApplication(testInput, "", "");
        app.exec();
        assertEquals(String.join(",", app.getOutput()), getOriginalPath() + "/src");
    }

    @Test(expected = InvalidNumOfArgsException.class)
    public void cd_ExtraArg_ThrowsInvalidNumOfArgsException()
    {
        List<String> testInput = new ArrayList<>(Arrays.asList("cd", "src", "hello"));
        IApplication app = new ApplicationFactory().getApplication(testInput, "", "");
        app.exec();
    }

    @Test(expected = InvalidDirectoryException.class)
    public void cd_InvalidDir_ThrowsInvalidDirectoryException()
    {
        List<String> testInput = new ArrayList<>(Arrays.asList("cd", "hello"));
        IApplication app = new ApplicationFactory().getApplication(testInput, "", "");
        app.exec();
    }

    @Test(expected = InvalidDirectoryException.class)
    public void cd_File_ThrowsInvalidDirectoryException()
    {
        Cd cd = new Cd(new ArrayList<>(Collections.singletonList(LIST_FILE)), "", "");
        cd.exec();
    }
}
