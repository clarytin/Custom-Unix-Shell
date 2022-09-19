package uk.ac.ucl.shell.application.apps;

import uk.ac.ucl.shell.application.Application;
import uk.ac.ucl.shell.application.Option;
import uk.ac.ucl.shell.util.Directory;
import uk.ac.ucl.shell.exception.InvalidNumOfArgsException;

import java.util.ArrayList;
import java.util.List;

/**
 * Detects and deletes adjacent duplicate lines from an input file/stdin
 * and prints the result to stdout
 */
public class Uniq extends Application
{
    // TODO : fix "echo aaa > dir1/file2.txt; cat dir1/file1.txt dir1/file2.txt | uniq -i"
    // TODO: fix "cat dir1/file1.txt dir1/file2.txt | sort | uniq"
    // TODO: fix ""sort dir1/file1.txt | uniq"
    private final Directory dir;
    private boolean ignoreCase;

    public Uniq(List<String> args, String in, String out) {
        super(args, in, out);
        validOptions.put("i", new Option("i", false, 0));
        dir = Directory.getDirectory();
    }

    @Override
    protected void validateArgs() throws RuntimeException
    {
        if (checkNumArgs(args, 0, 1) && this.inStream != null)
            this.args.add(this.inStream);

        if (!checkNumArgs(args, 1, 2))
            throw new InvalidNumOfArgsException("uniq");
    }

    @Override
    protected void evaluate()
    {
        loadOptions();
        if (optionInvoked("i"))
            ignoreCase = true;

        List<String> contents = dir.getAllLines(getFilePath());

        if (contents.isEmpty())
            output.add("");
        else
            output = getNewFileContents(contents);
    }

    private List<String> getNewFileContents(List<String> contents)
    {
        var newFileContents = new ArrayList<String>();
        String previousLine = contents.get(0);
        for (String line : contents.subList(1,contents.size()))
        {
            if (ignoreCase)
            {
                if (!(previousLine.equalsIgnoreCase(line)))
                {
                    newFileContents.add(previousLine);
                    previousLine = line;
                }
            }
            else
            {
                if (!(previousLine.equals(line)))
                {
                    newFileContents.add(previousLine);
                    previousLine = line;
                }
            }
        }
        newFileContents.add(previousLine);
        return newFileContents;
    }

    // Utility functions
    private String getFilePath()
    {
        return args.get(0);
    }
}
