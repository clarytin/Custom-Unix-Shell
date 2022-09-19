package uk.ac.ucl.shell.application.apps;

import uk.ac.ucl.shell.application.Option;
import uk.ac.ucl.shell.exception.FileDoesNotExistException;
import uk.ac.ucl.shell.util.Directory;
import uk.ac.ucl.shell.exception.InvalidNumOfArgsException;
import uk.ac.ucl.shell.application.Application;

import java.util.List;

/**
 * Prints the first N lines of a given file or stdin
 * If there are less than N lines, prints only the existing lines without raising an exception
 * If N is not specified, prints the first 10 lines
 */
public class Head extends Application
{
    private final Directory dir;
    private int headLines;
    private String headArg;

    public Head(List<String> args, String in, String out)
    {
        super(args, in, out);
        validOptions.put("n", new Option("n", false, 1));
        dir = Directory.getDirectory();
    }

    @Override
    protected void validateArgs()
    {
        if ((checkNumArgs(args, 0) || checkNumArgs(args, 2)) && this.inStream != null)
            this.args.add(this.inStream);

        if (!checkNumArgs(args, 1) && !checkNumArgs(args, 3))
            throw new InvalidNumOfArgsException("head");
    }

    @Override
    protected void evaluate()
    {
        initParams();
        if (!dir.fileExists(headArg))
            throw new FileDoesNotExistException("head - " + headArg);

        output = dir.getAllLines(headArg);

        int fileLength = output.size();

        if (headLines < fileLength)
            output = output.subList(0, headLines);
    }

    private void initParams()
    {
        loadOptions();
        headArg = args.get(0);
        if (!optionInvoked("n"))
            headLines = 10;
        else
        {
            String numLines = "";
            try
            {
                numLines = getOptionArgs("n").get(0);
                headLines = Integer.parseInt(numLines);
            }
            catch (NumberFormatException e)
            {
                throw new NumberFormatException("argument for option -n (" + numLines + ") could not be parsed as a number");
            }
        }
    }
}
