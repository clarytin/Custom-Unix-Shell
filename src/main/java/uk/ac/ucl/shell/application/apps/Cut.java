package uk.ac.ucl.shell.application.apps;

import uk.ac.ucl.shell.application.Option;
import uk.ac.ucl.shell.exception.InvalidFormatException;
import uk.ac.ucl.shell.util.Directory;
import uk.ac.ucl.shell.exception.InvalidNumOfArgsException;
import uk.ac.ucl.shell.application.util.RangeUtil;
import uk.ac.ucl.shell.application.Application;

import java.io.BufferedReader;
import java.util.List;

/**
 * Cuts out sections from each line of a given file or stdin and prints the result to stdout
 */
public class Cut extends Application
{
    private final Directory dir;
    private String cutRange;
    private String cutArg;

    public Cut(List<String> args, String in, String out)
    {
        super(args, in, out);
        dir = Directory.getDirectory();
        validOptions.put("b", new Option("b", true, 1));
    }

    @Override
    protected void validateArgs()
    {
        if (checkNumArgs(args, 2) && this.inStream != null)
            this.args.add(this.inStream);

        if (!checkNumArgs(args, 3))
            throw new InvalidNumOfArgsException("cut");

        cutRange = this.args.get(1);
        this.args.remove(1);
        cutArg = this.args.get(1);
    }

    @Override
    protected void evaluate()
    {
        if (cutRange.compareTo("") == 0 || cutRange.compareTo(",") == 0)
            throw new InvalidFormatException("Range");

        List<Integer> rangeList= new RangeUtil(cutRange).getRangeList();

        if (rangeList.get(0) == -1)
        {
            output = dir.getAllLines(cutArg);
        }
        else
        {
            BufferedReader reader = dir.getBufferedReader(cutArg);
            String line;
            while ((line = dir.readBufferedLine(reader, cutArg)) != null) // method throws error if fail to read file
                output.add(cut(rangeList, line));
        }
    }

    private String cut(List<Integer> rangeList, String line)
    {
        StringBuilder cutLine = new StringBuilder();
        int indexNow = 0;
        for (Integer index : rangeList)
        {
            if (index > line.length())
                break;

            if (index == -1 && indexNow <= line.length())
                cutLine.append(line.substring(indexNow));
            else
            {
                cutLine.append(line.charAt(index - 1));
                indexNow = index;
            }
        }
        return cutLine.toString();
    }
}
