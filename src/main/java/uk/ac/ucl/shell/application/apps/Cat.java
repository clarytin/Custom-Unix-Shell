package uk.ac.ucl.shell.application.apps;

import uk.ac.ucl.shell.util.Directory;
import uk.ac.ucl.shell.application.Application;

import java.util.List;

/**
 * Concatenates the content of given files and prints it to stdout
 */
public class Cat extends Application
{
    private final Directory dir;

    public Cat(List<String> args, String in, String out)
    {
        super(args, in, out);
        dir = Directory.getDirectory();
    }

    @Override
    protected void validateArgs()
    {
        // Accept input from stdin when no arguments are given
        if (checkNumArgs(args,0) && inStream != null)
            args.add(inStream);
    }

    @Override
    protected void evaluate()
    {
        for (String arg : args)
            output.addAll(dir.getAllLines(arg));
    }
}
