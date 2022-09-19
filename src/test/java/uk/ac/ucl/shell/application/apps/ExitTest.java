package uk.ac.ucl.shell.application.apps;

import org.junit.Test;
import uk.ac.ucl.shell.ITest;
import uk.ac.ucl.shell.TerminalModel;
import uk.ac.ucl.shell.exception.TooManyArgsException;
import uk.ac.ucl.shell.util.Directory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static uk.ac.ucl.shell.util.Constants.HISTORY_FILE;
import static uk.ac.ucl.shell.util.Constants.HISTORY_LENGTH;

public class ExitTest extends ITest
{
    @Test(expected = TooManyArgsException.class)
    public void exit_Args_ShouldThrowException()
    {
        var app = new Exit(new ArrayList<>(Collections.singletonList("dfjdfs")), "", "");
        app.exec();
    }

    @Test
    public void exit_NoArgs_ShouldBeValidated()
    {
        try
        {
            var app = new Exit(new ArrayList<>(), "", "");
            app.validateArgs();
        }
        catch(Exception e)
        {
            fail();
        }
    }

    @Test
    public void exit_SaveHistory_ShouldWork()
    {
        var history = new ArrayList<>(Arrays.asList("1", "2", "3"));
        TerminalModel.getTerminal().setHistory(history);

        var app = new Exit(new ArrayList<>(), "", "");
        app.saveHistory();

        var dir = Directory.getDirectory();
        assertEquals(3, dir.getAllLines(HISTORY_FILE).size());
    }

    @Test
    public void exit_SaveHistoryTooLong_ShouldTruncate()
    {
        var newHistory = new ArrayList<String>();
        for(int c = 0; c < HISTORY_LENGTH + 20; c++)
            newHistory.add("echo hi there");
        TerminalModel.getTerminal().setHistory(newHistory);


        var app = new Exit(new ArrayList<>(), "", "");
        app.saveHistory();

        var dir = Directory.getDirectory();
        assertEquals(HISTORY_LENGTH, dir.getAllLines(HISTORY_FILE).size());
    }
}
