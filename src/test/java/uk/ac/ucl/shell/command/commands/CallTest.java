package uk.ac.ucl.shell.command.commands;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ucl.shell.exception.InvalidRedirectionException;
import uk.ac.ucl.shell.exception.NullApplicationException;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static uk.ac.ucl.shell.Constants.*;

public class CallTest 
{
    private Call call;
    private final String commandString = "echo j";

    @Before
    public void init()
    {
        call = new Call("echo j");
    }

    @Test
    public void call_Constructor_ShouldSetCorrectValues()
    {
        assertEquals(commandString, call.getCommandString());
        assertEquals(new ArrayList<>(), call.getArgs());
        assertNull(call.getInRedirect());
        assertNull(call.getOutRedirect());
    }

    @Test
    public void setCommandString_Valid_ChangesCommandString()
    {
        var newCmdline = "echo Test";
        call.setCommandString(newCmdline);
        assertEquals(newCmdline, call.getCommandString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setCommandString_Null_ThrowsException()
    {
        call.setCommandString(null);
    }

    @Test
    public void addArg_ValidArgs_ShouldAddArgumentsToCall()
    {
        var newArg = "testArg";
        var expected = new ArrayList<>(Arrays.asList(newArg));

        call.addArg(newArg);
        assertEquals(expected, call.getArgs());
    }

    @Test
    public void addArg_ManyArgs_ShouldWork()
    {
        int numArgs = 99;
        var expected = new ArrayList<String>();

        for (int i = 0; i < numArgs; i++)
            expected.add(Integer.toString(i));

        for (int i = 0; i < numArgs; i++)
            call.addArg(Integer.toString(i));

        assertEquals(expected, call.getArgs());
    }

    @Test(expected = RuntimeException.class)
    public void addArg_Null_ThrowsException()
    {
        call.addArg(null);
    }

    @Test
    public void setArg_ValidArg_ChangesArgAtIndex()
    {
        call.addArg("Test");

        var expected = "ChangedArg";
        call.setArg(0, expected);

        assertEquals(expected, call.getArgs().get(0));
    }

    @Test(expected = RuntimeException.class)
    public void setArg_NullArg_ThrowsException()
    {
        call.addArg("test");
        call.setArg(0, null);
    }

    @Test
    public void setInputRedirect_ValidInput_ShouldSetRedirect()
    {
        String redirectDirection = "<";
        call.addRedirect(redirectDirection, ALPHA_FILE);
        assertEquals(ALPHA_FILE, call.getInRedirect());
    }

    @Test
    public void setOutputRedirect_ValidInput_ShouldSetRedirect()
    {
        String redirectDirection = ">";
        call.addRedirect(redirectDirection, TEST_OUTPUT_FILE);
        assertEquals(TEST_OUTPUT_FILE, call.getOutRedirect());
    }

    @Test
    public void addRedirect_inputAndOutputRedirect_returnsRedirects()
    {
        call.addRedirect("<", ALPHA_FILE);
        call.addRedirect(">", TEST_OUTPUT_FILE);
        assertEquals(ALPHA_FILE, call.getInRedirect());
        assertEquals(TEST_OUTPUT_FILE, call.getOutRedirect());
    }

    @Test(expected = InvalidRedirectionException.class)
    public void setInputRedirect_MoreThanOnce_ShouldThrowException()
    {
        String redirectDirection = "<";
        call.addRedirect(redirectDirection, ALPHA_FILE);
        call.addRedirect(redirectDirection, NUMBERS_FILE);
    }

    @Test(expected = InvalidRedirectionException.class)
    public void setOutputRedirect_MoreThanOnce_ShouldThrowException()
    {
        String redirectDirection = ">";
        call.addRedirect(redirectDirection, TEST_OUTPUT_FILE);
        call.addRedirect(redirectDirection, TEST_OUTPUT2_FILE);
    }

    @Test(expected = NullApplicationException.class)
    public void executeApp_basicCall_returnsNoCommandError()
    {
        // unitialized arguments = no name to resolve, null application
        call.executeApp();
    }

    @Test(expected = NullApplicationException.class)
    public void executeApp_callWithInRedirect_returnsNoCommandError()
    {
        call.addRedirect("<", ALPHA_FILE);
        call.executeApp();
    }

    @Test(expected = NullApplicationException.class)
    public void executeApp_callWithOutRedirect_returnsNoCommandError()
    {
        call.addRedirect(">", TEST_OUTPUT_FILE);
        call.executeApp();
    }

    @Test(expected = NullApplicationException.class)
    public void executeApp_inAndOutRedirect_returnsNoCommandError()
    {
        call.addRedirect("<", ALPHA_FILE);
        call.addRedirect(">", TEST_OUTPUT_FILE);
        call.executeApp();
    }

    @Test(expected = IllegalArgumentException.class)
    public void addRedirect_InvalidDirection_ThrowsIllegalArgumentException()
    {
        call.addRedirect("???", ALPHA_FILE);
    }
}
