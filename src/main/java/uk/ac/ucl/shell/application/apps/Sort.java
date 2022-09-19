package uk.ac.ucl.shell.application.apps;

import uk.ac.ucl.shell.application.Application;
import uk.ac.ucl.shell.application.Option;
import uk.ac.ucl.shell.util.Directory;
import uk.ac.ucl.shell.exception.InvalidNumOfArgsException;

import java.util.Collections;
import java.util.List;

/**
 * Sorts the contents of a file/stdin line by line and prints the result to stdout
 */
public class Sort extends Application
{
    private final Directory dir;
    boolean reverse;
    String sortArg;

    public Sort(List<String> args, String in, String out)
    {
        super(args, in, out);
        validOptions.put("r", new Option("r", false, 0));
        dir = Directory.getDirectory();
    }

    @Override
    protected void validateArgs()
    {
        if (checkNumArgs(args, 0, 1) && this.inStream != null)
            this.args.add(this.inStream);


        if (!checkNumArgs(args, 1, 2))
            throw new InvalidNumOfArgsException("sort");
    }

    @Override
    protected void evaluate()
    {
        loadOptions();
        if (optionInvoked("r"))
            reverse = true;

        sortArg = args.get(0);

        output = dir.getAllLines(sortArg);
        if (output.isEmpty()) // empty file
        {
            output.add("");
        }

        Collections.sort(output);
        if (reverse)
        {
            output.sort(Collections.reverseOrder());
        }
    }
}
