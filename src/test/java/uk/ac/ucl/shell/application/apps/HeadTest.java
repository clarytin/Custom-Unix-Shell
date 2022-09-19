package uk.ac.ucl.shell.application.apps;

import org.junit.Test;
import uk.ac.ucl.shell.ITest;
import uk.ac.ucl.shell.application.ApplicationFactory;
import uk.ac.ucl.shell.application.IApplication;
import uk.ac.ucl.shell.exception.FileDoesNotExistException;
import uk.ac.ucl.shell.exception.IllegalOptionException;
import uk.ac.ucl.shell.exception.InvalidNumOfArgsException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static uk.ac.ucl.shell.Constants.LIST_FILE;

public class HeadTest extends ITest
{
    @Test
    public void head_NoOption_ShouldOutputListContents()
    {
        List<String> testInput = new ArrayList<>(Arrays.asList("head", LIST_FILE));
        IApplication app = new ApplicationFactory().getApplication(testInput, "", "");
        app.exec();
        assertEquals("3. parser,1. shell,4. result,2. lexer",
                String.join(",", app.getOutput()));
    }

    @Test
    public void head_WithOption_ShouldOutputListContents()
    {
        Head head = new Head(new ArrayList<>(Arrays.asList("-n", "2", LIST_FILE)), "", "");
        head.exec();
        assertEquals("3. parser,1. shell", String.join(",", head.getOutput()));
    }

    @Test
    public void head_NoArgs_ShouldReadFromStdIn()
    {
        var head = new Head(new ArrayList<>(), LIST_FILE, "");
        head.exec();
        assertEquals("3. parser,1. shell,4. result,2. lexer",
                String.join(",", head.getOutput()));
    }

    @Test(expected = InvalidNumOfArgsException.class)
    public void head_NoArgsNoStdIn_ShouldThrowException()
    {
        var head = new Head(new ArrayList<>(), null, "");
        head.exec();
    }

    @Test(expected = NumberFormatException.class)
    public void head_NoOptionArg_ThrowsException()
    {
        Head head = new Head(new ArrayList<>(Arrays.asList("-n", LIST_FILE)), "", "");
        head.exec();
    }

    @Test(expected = InvalidNumOfArgsException.class)
    public void head_ExtraArgs_ThrowsInvalidNumOfArgsException()
    {
        Head head = new Head(new ArrayList<>(Arrays.asList("-n", "2", "hello", LIST_FILE)), "", "");
        head.exec();
    }

    @Test(expected = FileDoesNotExistException.class)
    public void head_InvalidFile_ThrowsException()
    {
        Head head = new Head(new ArrayList<>(Arrays.asList("-n", "2", "hello.txt")), "", "");
        head.exec();
    }

    @Test(expected = IllegalOptionException.class)
    public void head_InvalidOption_ThrowsIllegalOptionException()
    {
        Head head = new Head(new ArrayList<>(Arrays.asList("-d", "3", LIST_FILE)), "", "");
        head.exec();
    }

    @Test(expected = NumberFormatException.class)
    public void head_AlphaOptionArg_ThrowsException()
    {
        Head head = new Head(new ArrayList<>(Arrays.asList("-n", "j", LIST_FILE)), "", "");
        head.exec();
    }
}
