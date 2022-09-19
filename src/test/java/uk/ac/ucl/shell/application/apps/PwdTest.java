package uk.ac.ucl.shell.application.apps;

import org.junit.Test;
import uk.ac.ucl.shell.ITest;
import uk.ac.ucl.shell.application.ApplicationFactory;
import uk.ac.ucl.shell.application.IApplication;
import uk.ac.ucl.shell.exception.TooManyArgsException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PwdTest extends ITest
{
    @Test
    public void pwd_ShouldOutputCurrentDirectoryPath()
    {
        List<String> testInput = new ArrayList<>(Collections.singletonList("pwd"));
        IApplication app = new ApplicationFactory().getApplication(testInput, "", "");
        app.exec();
        assertEquals(String.join(",", app.getOutput()), getOriginalPath());
    }

    @Test(expected = TooManyArgsException.class)
    public void pwd_ExtraArgs_ThrowsTooManyArgsException()
    {
        Pwd pwd = new Pwd(new ArrayList<>(Collections.singletonList("1")), "", "");
        pwd.exec();
    }
}
