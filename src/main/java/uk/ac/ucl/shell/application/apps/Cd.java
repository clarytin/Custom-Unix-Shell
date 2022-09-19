package uk.ac.ucl.shell.application.apps;

import uk.ac.ucl.shell.util.Directory;
import uk.ac.ucl.shell.exception.InvalidNumOfArgsException;
import uk.ac.ucl.shell.exception.InvalidDirectoryException;
import uk.ac.ucl.shell.application.Application;

import java.io.IOException;
import java.util.List;

/**
 * Changes the current working directory
 */
public class Cd extends Application
{
    private final Directory dir;

    public Cd(List<String> args, String in, String out)
    {
        super(args, in, out);
        dir = Directory.getDirectory();
    }

    @Override
    protected void validateArgs()
    {
        if (!checkNumArgs(args, 1))
            throw new InvalidNumOfArgsException("cd");
    }

    @Override
    protected void evaluate()
    {
        String directory = args.get(0);
        if (!dir.fileExists(directory) || !dir.isDirectory(directory))
            throw new InvalidDirectoryException(directory);

        try
        {
            dir.setCurrentDir(dir.getFile(directory).getCanonicalPath());
        }
        catch (IOException e)
        {
            throw new InvalidDirectoryException(directory);
        }
    }
}
