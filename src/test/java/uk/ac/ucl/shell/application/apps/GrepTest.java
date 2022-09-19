package uk.ac.ucl.shell.application.apps;

import org.junit.Test;
import uk.ac.ucl.shell.ITest;
import uk.ac.ucl.shell.exception.FileReadException;
import uk.ac.ucl.shell.exception.InvalidNumOfArgsException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static uk.ac.ucl.shell.Constants.*;

public class GrepTest extends ITest
{
    @Test(expected = InvalidNumOfArgsException.class)
    public void grep_NoArgs_ShouldThrowError()
    {
        var grep = new Grep(new ArrayList<>(), "", "");
        grep.exec();
    }

    @Test
    public void grep_NormalInput_ShouldWork()
    {
        var grep = new Grep(new ArrayList<>(Arrays.asList("abc", ALPHA_FILE)), "", "");
        grep.exec();
        assertEquals(Arrays.asList("abc", "abc", "abc"), grep.getOutput());

    }

    @Test(expected = InvalidNumOfArgsException.class)
    public void grep_OneArgNullStdIn_ShouldThrowError()
    {
        var grep = new Grep(new ArrayList<>(Collections.singletonList("abc")), null, "");
        grep.exec();
    }

    @Test
    public void grep_OneArg_ShouldReadFromStdIn()
    {
        var grep = new Grep(new ArrayList<>(Collections.singletonList("abc")), ALPHA_FILE, "");
        grep.exec();
        assertEquals(Arrays.asList("abc", "abc", "abc"), grep.getOutput());
    }

    @Test
    public void grep_MultipleFiles_ShouldReadAll()
    {
        var grep = new Grep(new ArrayList<>(Arrays.asList("123", NUMBERS_FILE, DIFF_LEN_FILE)), "", "");
        grep.exec();
        assertEquals(Arrays.asList(NUMBERS_FILE + ":123", DIFF_LEN_FILE + ":1234"), grep.getOutput());
    }

    @Test(expected = FileReadException.class)
    public void grep_Dir_ShouldThrowError()
    {
        var grep = new Grep(new ArrayList<>(Arrays.asList("123", EXAMPLES_DIR)), "", "");
        grep.exec();
    }

    @Test(expected = FileReadException.class)
    public void grep_InvalidFile_ShouldThrowError()
    {
        var grep = new Grep(new ArrayList<>(Arrays.asList("123", "qwerqdqs.txt")), "", "");
        grep.exec();
    }
}
