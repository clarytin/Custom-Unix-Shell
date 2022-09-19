package uk.ac.ucl.shell.application.apps;

import uk.ac.ucl.shell.exception.InvalidNumOfArgsException;
import uk.ac.ucl.shell.util.Directory;
import uk.ac.ucl.shell.application.Application;

import java.io.File;
import java.util.List;
import java.util.Objects;

/**
 * Lists the content of a directory
 * Prints a list of files and directories separated by tabs and followed by a newline
 * Ignores files and directories whose names start with .
 */
public class Ls extends Application
{
    private final Directory dir;

    public Ls(List<String> args, String in, String out)
    {
        super(args, in, out);
        dir = Directory.getDirectory();
    }

    @Override
    protected void validateArgs() throws RuntimeException
    {
        if (!checkNumArgs(args, 0, 1))
            throw new InvalidNumOfArgsException("ls");
    }

    @Override
    protected void evaluate()
    {
        // get file object of current directory
        File currDir = dir.getFile("");

        if (args.size() == 1)
            currDir = dir.getFile(args.get(0));

        String fileList = String.join("\t", Objects.requireNonNull(currDir.list()));
        output.add(fileList);
    }
}
