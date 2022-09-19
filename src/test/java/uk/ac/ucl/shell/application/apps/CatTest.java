package uk.ac.ucl.shell.application.apps;

import org.junit.Test;
import uk.ac.ucl.shell.ITest;
import uk.ac.ucl.shell.application.ApplicationFactory;
import uk.ac.ucl.shell.application.IApplication;
import uk.ac.ucl.shell.exception.FileReadException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static uk.ac.ucl.shell.Constants.NUMBERS_FILE;

public class CatTest extends ITest
{
    @Test
    public void cat_ShouldOutputListContents()
    {
        List<String> testInput = new ArrayList<>(Arrays.asList("cat", NUMBERS_FILE));
        IApplication app = new ApplicationFactory().getApplication(testInput, "", "");
        app.exec();
        assertEquals("123,456", String.join(",", app.getOutput()));
    }

    @Test (expected= FileReadException.class)
    public void cat_ThrowsFileErrorException()
    {
        List<String> testInput = new ArrayList<>(Collections.singletonList("cat"));
        IApplication app = new ApplicationFactory().getApplication(testInput, "", "");
        app.exec();
    }

    @Test
    public void cat_ReadsFromStdIn()
    {
        var app = new Cat(new ArrayList<>(), NUMBERS_FILE, "");
        app.exec();
        assertEquals("123", app.getOutput().get(0));
    }

    @Test
    public void cat_NoArgsNullInputStream_ShouldDoNothing()
    {
        var app = new Cat(new ArrayList<>(), null, "");
        app.exec();
        assertEquals(0, app.getOutput().size());

    }
}
