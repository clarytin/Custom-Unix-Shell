package uk.ac.ucl.shell.application.apps;

import uk.ac.ucl.shell.TerminalModel;
import uk.ac.ucl.shell.application.Application;
import uk.ac.ucl.shell.exception.FileWriteException;
import uk.ac.ucl.shell.exception.RawTerminalException;
import uk.ac.ucl.shell.util.Directory;
import uk.ac.ucl.shell.exception.TooManyArgsException;

import java.io.IOException;
import java.util.List;

import static uk.ac.ucl.shell.util.Constants.HISTORY_FILE;
import static uk.ac.ucl.shell.util.Constants.HISTORY_LENGTH;
import static uk.ac.ucl.shell.util.RawModeUtil.resetConsoleMode;


public class Exit extends Application
{
    private final Directory dir;

    public Exit(List<String> args, String in, String out)
    {
        super(args, in, out);
        this.dir = Directory.getDirectory();
    }

    @Override
    public void validateArgs()
    {
        if (!checkNumArgs(args, 0))
            throw new TooManyArgsException("exit");
    }

    @Override
    protected void evaluate()
    {
        try
        {
            resetConsoleMode();
        }
        catch (IOException e)
        {
            throw new RawTerminalException();
        }
        saveHistory();
        System.exit(0);

    }

    /**
     * Saves history to the history file
     * Access modifier is Protected to enable testing without calling System.exit(0);
     */
    protected void saveHistory()
    {
        try
        {
            var history = TerminalModel.getTerminal().getHistory();
            if (history.size() > HISTORY_LENGTH)
                history = history.subList(0, HISTORY_LENGTH);
            dir.writeToAbsoluteFilePath(HISTORY_FILE, history);
        }
        catch (IOException e)
        {
            throw new FileWriteException("History.txt", "History could not be saved");
        }
    }
}
