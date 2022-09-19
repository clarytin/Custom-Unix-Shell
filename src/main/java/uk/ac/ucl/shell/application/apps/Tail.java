package uk.ac.ucl.shell.application.apps;

import uk.ac.ucl.shell.application.Application;
import uk.ac.ucl.shell.application.Option;
import uk.ac.ucl.shell.exception.IllegalOptionException;
import uk.ac.ucl.shell.exception.InvalidNumOfArgsException;
import uk.ac.ucl.shell.util.Directory;

import java.util.List;

/**
 * Prints the last N lines of a given file or stdin
 * If there are less than N lines, prints only the existing lines without raising an exception
 */
public class Tail extends Application
{
    private final Directory dir;
    private int tailLines;
    private String tailArg;

    public Tail(List<String> args, String in, String out)
    {
        super(args, in, out);
        validOptions.put("n", new Option("n", false, 1));
        dir = Directory.getDirectory();
    }

    @Override
    protected void validateArgs()
    {
        if ((checkNumArgs(args, 0)  || checkNumArgs(args, 2)) && inStream != null)
            this.args.add(inStream);

        if (!checkNumArgs(args, 1) && !checkNumArgs(args, 3))
            throw new InvalidNumOfArgsException("tail");

        if (args.size() == 3 && !(args.contains("-n")))
        {
            for (var arg : args)
            {
                if (arg.startsWith("-"))
                    throw new IllegalOptionException(arg);
            }
            throw new InvalidNumOfArgsException("tail");
        }
    }

    @Override
    protected void evaluate()
    {
        initParams();

        output = dir.getAllLines(tailArg);
        int fileLength = output.size();
        if (tailLines < fileLength)
        {
            output = output.subList(fileLength - tailLines, fileLength);
        }
    }

    private void initParams()
    {
        if (args.size() == 3)
        {
            tailArg = args.get(2);
            try
            {
                tailLines = Integer.parseInt(args.get(1));
            }
            catch (NumberFormatException e)
            {
                throw new NumberFormatException("argument for option -n (" + args.get(1) + ") could not be parsed as a number");
            }
        }
        else
        {
            tailLines = 10;
            tailArg = args.get(0);
        }
    }
}
