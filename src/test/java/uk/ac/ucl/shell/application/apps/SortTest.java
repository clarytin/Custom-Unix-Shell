package uk.ac.ucl.shell.application.apps;

import org.junit.Test;
import uk.ac.ucl.shell.ITest;
import uk.ac.ucl.shell.exception.IllegalOptionException;
import uk.ac.ucl.shell.exception.InvalidNumOfArgsException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static uk.ac.ucl.shell.Constants.EMPTY_FILE;
import static uk.ac.ucl.shell.Constants.LIST_FILE;

public class SortTest extends ITest
{

    @Test
    public void sort_NoArgs_ShouldReadFromStdin()
    {
        var sort = new Sort(new ArrayList<>(), LIST_FILE, "");
        sort.exec();
        assertEquals("1. shell,2. lexer,3. parser,4. result",
                String.join(",", sort.getOutput()));
    }

    @Test
    public void sort_NoOption_ShouldOutputAscendingListContents()
    {
        var sort = new Sort(new ArrayList<>(Collections.singletonList(LIST_FILE)), "", "");
        sort.exec();
        assertEquals("1. shell,2. lexer,3. parser,4. result",
                String.join(",", sort.getOutput()));
    }

    @Test
    public void sort_WithOption_ShouldOutputDescendingListContents()
    {
        var sort = new Sort(new ArrayList<>(Arrays.asList("-r", LIST_FILE)), "", "");
        sort.exec();
        assertEquals("4. result,3. parser,2. lexer,1. shell", String.join(",", sort.getOutput()));
    }

    @Test
    public void sort_EmptyFile_ShouldOutputEmptyContents()
    {
        var sort = new Sort(new ArrayList<>(Arrays.asList("-r", EMPTY_FILE)), "", "");
        sort.exec();
        assertEquals("", String.join(",", sort.getOutput()));
    }

    @Test(expected = InvalidNumOfArgsException.class)
    public void sort_NoArgsNoStdIn_ShouldThrowError()
    {
        var app = new Sort(new ArrayList<>(), null, "");
        app.exec();
    }

    @Test(expected = InvalidNumOfArgsException.class)
    public void sort_ExtraArgs_ThrowsInvalidNumOfArgsException()
    {
        Sort sort = new Sort(new ArrayList<>(Arrays.asList("-r", "1", LIST_FILE)), "", "");
        sort.exec();
    }

    @Test(expected = InvalidNumOfArgsException.class)
    public void sort_NullFile_ThrowsInvalidNumOfArgsException()
    {
        Sort sort = new Sort(new ArrayList<>(Arrays.asList("-r", "1", null)), "", "");
        sort.exec();
    }

    @Test(expected = IllegalOptionException.class)
    public void sort_InvalidOption_ThrowsIllegalOptionException()
    {
        Sort sort = new Sort(new ArrayList<>(Arrays.asList("-k", LIST_FILE)), "", "");
        sort.exec();
    }
}
