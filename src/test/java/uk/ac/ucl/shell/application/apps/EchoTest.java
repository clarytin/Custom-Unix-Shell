package uk.ac.ucl.shell.application.apps;

import org.junit.Test;
import uk.ac.ucl.shell.ITest;
import uk.ac.ucl.shell.application.ApplicationFactory;
import uk.ac.ucl.shell.application.IApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class EchoTest extends ITest
{

    @Test
    public void echo_ShouldOutputArgContents()
    {
        List<String> testInput = new ArrayList<>(Arrays.asList("echo", "hello", "world"));
        IApplication app = new ApplicationFactory().getApplication(testInput, "", "");
        app.exec();
        assertEquals("hello world", String.join(",", app.getOutput()));
    }
}
