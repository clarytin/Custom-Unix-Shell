package uk.ac.ucl.shell.application.apps;

import uk.ac.ucl.shell.exception.TooManyArgsException;
import uk.ac.ucl.shell.util.Directory;
import uk.ac.ucl.shell.application.Application;

import java.util.List;

/**
 * Outputs the current working directory followed by a newline
 */
public class Pwd extends Application
{
    private final Directory dir;

    public Pwd(List<String> args, String in, String out)
    {
        super(args, in, out);
        dir = Directory.getDirectory();
    }

    @Override
    protected void validateArgs() throws RuntimeException
    {
        if (!checkNumArgs(args, 0))
            throw new TooManyArgsException("pwd");
    }

    @Override
    protected void evaluate()
    {
        output.add(dir.getCurrentDir());
    }
}
