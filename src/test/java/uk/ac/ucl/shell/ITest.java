package uk.ac.ucl.shell;

import org.junit.Before;
import org.junit.After;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import uk.ac.ucl.shell.application.*;

public abstract class ITest
{
    private final PrintStream originalOut = System.out;
    protected final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final PrintStream originalErr = System.err;
    protected final ByteArrayOutputStream err = new ByteArrayOutputStream();
    private final String originalPath = System.getProperty("user.dir"); // keep a reference of the home directory for restoration before each test

    @Before
    public void initialise()
    {
        // reset output and error streams
        System.setOut(new PrintStream(out));
        System.setErr(new PrintStream(err));
    }

    @After
    public void restore()
    {
        // reset to home directory
        IApplication app = new ApplicationFactory().getApplication(new ArrayList<>(Collections.singletonList("pwd")), "", "");
        app.exec();
        while (String.join(",", app.getOutput()).compareTo(originalPath) != 0)
        {
            app = new ApplicationFactory().getApplication(new ArrayList<>(Arrays.asList("cd", "..")), "", "");
            app.exec();
            app = new ApplicationFactory().getApplication(new ArrayList<>(Collections.singletonList("pwd")), "", "");
            app.exec();
        }

        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    public String getOriginalPath()
    {
        return originalPath;
    }
}
