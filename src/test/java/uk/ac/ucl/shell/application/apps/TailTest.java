package uk.ac.ucl.shell.application.apps;

import org.junit.Test;
import uk.ac.ucl.shell.ITest;
import uk.ac.ucl.shell.exception.IllegalOptionException;
import uk.ac.ucl.shell.exception.InvalidNumOfArgsException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static uk.ac.ucl.shell.Constants.LIST_FILE;

public class TailTest extends ITest
{

    @Test
    public void tail_NoOption_ShouldOutputListContents()
    {
        Tail tail = new Tail(new ArrayList<>(Collections.singletonList(LIST_FILE)), "", "");
        tail.exec();
        assertEquals("3. parser,1. shell,4. result,2. lexer",
                String.join(",", tail.getOutput()));
    }

    @Test
    public void tail_NoOptionStdIn_ShouldReadFromStdIn()
    {
        Tail tail = new Tail(new ArrayList<>(), LIST_FILE, "");
        tail.exec();
        assertEquals("3. parser,1. shell,4. result,2. lexer",
                String.join(",", tail.getOutput()));
    }

    @Test(expected = InvalidNumOfArgsException.class)
    public void tail_NoOptionNullStdIn_ShouldThrowException()
    {
        Tail tail = new Tail(new ArrayList<>(), null, "");
        tail.exec();
    }


    @Test
    public void tail_WithOption_ShouldOutputListContents()
    {
        Tail tail = new Tail(new ArrayList<>(Arrays.asList("-n", "2", LIST_FILE)), "", "");
        tail.exec();
        assertEquals("4. result,2. lexer", String.join(",", tail.getOutput()));
    }


    @Test(expected = NumberFormatException.class)
    public void tail_NoOptionArg_ThrowsException()
    {
        Tail tail = new Tail(new ArrayList<>(Arrays.asList("-n", LIST_FILE)), "", "");
        tail.exec();
    }

    @Test(expected = IllegalOptionException.class)
    public void tail_InvalidOption_ThrowsIllegalOptionException()
    {
        Tail tail = new Tail(new ArrayList<>(Arrays.asList("-d", "3", LIST_FILE)), "", "");
        tail.exec();
    }

    @Test(expected = InvalidNumOfArgsException.class)
    public void tail_InvalidOption_ThrowsInvalidNumOfArgsException()
    {
        Tail tail = new Tail(new ArrayList<>(Arrays.asList("d", "3", LIST_FILE)), "", "");
        tail.exec();
    }

    @Test(expected = InvalidNumOfArgsException.class)
    public void tail_ExtraArgs_ThrowsInvalidNumOfArgsException()
    {
        Tail tail = new Tail(new ArrayList<>(Arrays.asList("-n", "3","hello", LIST_FILE)), "", "");
        tail.exec();
    }

    @Test(expected = NumberFormatException.class)
    public void tail_AlphaOptionArg_ThrowsException()
    {
        Tail tail = new Tail(new ArrayList<>(Arrays.asList("-n", "j", LIST_FILE)), "", "");
        tail.exec();
    }
}
