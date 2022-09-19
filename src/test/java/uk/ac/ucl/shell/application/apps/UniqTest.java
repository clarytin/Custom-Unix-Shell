package uk.ac.ucl.shell.application.apps;

import org.junit.Test;
import uk.ac.ucl.shell.ITest;
import uk.ac.ucl.shell.exception.IllegalOptionException;
import uk.ac.ucl.shell.exception.InvalidNumOfArgsException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static uk.ac.ucl.shell.Constants.*;

public class UniqTest extends ITest
{
    @Test
    public void uniq_NoOption_ShouldOutputListContents()
    {
        Uniq uniq = new Uniq(new ArrayList<>(Collections.singletonList(ALPHA_FILE)), "", "");
        uniq.exec();
        assertEquals("aBc,acc,abc,aBc,abb,Abc,abc",
                String.join(",", uniq.getOutput()));
    }

    @Test
    public void uniq_WithOption_ShouldOutputListContents()
    {
        Uniq uniq = new Uniq(new ArrayList<>(Arrays.asList("-i", ALPHA_FILE)), "", "");
        uniq.exec();
        assertEquals("aBc,acc,abc,abb,Abc", String.join(",", uniq.getOutput()));
    }

    @Test
    public void uniq_NoArgs_ShouldReadFromStdin()
    {
        Uniq uniq = new Uniq(new ArrayList<>(), ALPHA_FILE, "");
        uniq.exec();
        assertEquals("aBc,acc,abc,aBc,abb,Abc,abc",
                String.join(",", uniq.getOutput()));
    }

    @Test(expected = InvalidNumOfArgsException.class)
    public void uniq_NoArgsNoStdIn_ShouldThrowException()
    {
        Uniq uniq = new Uniq(new ArrayList<>(), null, "");
        uniq.exec();
    }

    @Test
    public void uniq_EmptyFile_ShouldOutputEmptyContents()
    {
        Uniq uniq = new Uniq(new ArrayList<>(Arrays.asList("-i", EMPTY_FILE)), "", "");
        uniq.exec();
        assertEquals("", String.join(",", uniq.getOutput()));
    }

    @Test(expected = InvalidNumOfArgsException.class)
    public void uniq_ExtraArgs_ThrowsInvalidNumOfArgsException()
    {
        Uniq uniq = new Uniq(new ArrayList<>(Arrays.asList("-i", "1", LIST_FILE)), "", "");
        uniq.exec();
    }

    @Test(expected = InvalidNumOfArgsException.class)
    public void uniq_NullFile_ThrowsInvalidNumOfArgsException()
    {
        Uniq uniq = new Uniq(new ArrayList<>(Arrays.asList("-i", "1", null)), "", "");
        uniq.exec();
    }

    @Test(expected = IllegalOptionException.class)
    public void uniq_InvalidOption_ThrowsIllegalOptionException()
    {
        Uniq uniq = new Uniq(new ArrayList<>(Arrays.asList("-k", LIST_FILE)), "", "");
        uniq.exec();
    }
}
