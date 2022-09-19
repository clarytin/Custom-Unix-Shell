package uk.ac.ucl.shell.application.apps;

import org.junit.Test;
import uk.ac.ucl.shell.ITest;
import uk.ac.ucl.shell.application.ApplicationFactory;
import uk.ac.ucl.shell.application.IApplication;
import uk.ac.ucl.shell.exception.FileDoesNotExistException;
import uk.ac.ucl.shell.exception.InvalidNumOfArgsException;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class LsTest extends ITest
{
    @Test
    public void ls_NoDir_ShouldOutputListContents()
    {
        List<String> testInput = new ArrayList<>(Collections.singletonList("ls"));
        IApplication app = new ApplicationFactory().getApplication(testInput, "", "");
        app.exec();
        assertEquals(String.join(",", app.getOutput()),
                String.join("\t", new File(getOriginalPath()).list()));
    }

    @Test
    public void ls_WithDir_ShouldOutputListContents()
    {
        List<String> testInput = new ArrayList<>(Arrays.asList("ls", "src"));
        IApplication app = new ApplicationFactory().getApplication(testInput, "", "");
        app.exec();
        assertEquals(String.join(",", app.getOutput()),
                String.join("\t", new File(getOriginalPath() + "/src").list()));
    }

    @Test(expected = InvalidNumOfArgsException.class)
    public void ls_ExtraArgs_ThrowsInvalidNumOfArgsException()
    {
        List<String> testInput = new ArrayList<>(Arrays.asList("ls", "hello", "yo"));
        IApplication app = new ApplicationFactory().getApplication(testInput, "", "");
        app.exec();
    }

    @Test(expected = FileDoesNotExistException.class)
    public void ls_InvalidDir_ThrowsInvalidFileException()
    {
        List<String> testInput = new ArrayList<>(Arrays.asList("ls", "hello"));
        IApplication app = new ApplicationFactory().getApplication(testInput, "", "");
        app.exec();
    }
}
