package uk.ac.ucl.shell.application.apps;

import org.junit.Test;
import uk.ac.ucl.shell.ITest;
import uk.ac.ucl.shell.application.ApplicationFactory;
import uk.ac.ucl.shell.application.IApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UnsafeTest extends ITest
{
    @Test
    public void unsafe_NormalArgs_ShouldPrintErrorMessageNormally()
    {
        List<String> testInput = new ArrayList<>(Arrays.asList("_ls", "hello"));
        IApplication app = new ApplicationFactory().getApplication(testInput, "", "");
        app.exec();
        assertEquals("hello: file does not exist",
                String.join(",", app.getOutput()));
    }
}
