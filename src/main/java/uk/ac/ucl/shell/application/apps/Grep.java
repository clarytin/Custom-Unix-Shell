package uk.ac.ucl.shell.application.apps;

import uk.ac.ucl.shell.util.Directory;
import uk.ac.ucl.shell.exception.FileReadException;
import uk.ac.ucl.shell.exception.InvalidNumOfArgsException;
import uk.ac.ucl.shell.application.Application;

import java.io.BufferedReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Searches for lines containing a match to the specified pattern
 * The output of the command is the list of lines
 * Each line is printed followed by a newline
 */
public class Grep extends Application
{
    private final Directory dir;

    public Grep(List<String> args, String in, String out)
    {
        super(args, in, out);
        dir = Directory.getDirectory();
    }

    @Override
    protected void validateArgs()
    {
        if (checkNumArgs(args, 1) && this.inStream != null)
            this.args.add(this.inStream);
            
        if (!checkNumArgs(args, 2, -1))
            throw new InvalidNumOfArgsException("grep");
    }

    @Override
    protected void evaluate()
    {
        int numOfFiles = args.size() - 1;
        String[] fileName = getFilename(numOfFiles);

        grep(fileName, numOfFiles);
    }

    private void grep(String[] fileName, int numOfFiles)
    {
        Pattern grepPattern = Pattern.compile(args.get(0));
        for (int j = 0; j < numOfFiles; j++)
        {
            BufferedReader reader = dir.getBufferedReader(fileName[j]);
            String line;
            while ((line = dir.readBufferedLine(reader,fileName[j])) != null)
            {
                Matcher matcher = grepPattern.matcher(line);
                if (matcher.find())
                {
                    if (numOfFiles > 1)
                    {
                        String grepLine = args.get(j + 1) + ":" + line;
                        output.add(grepLine);
                    }
                    else
                        output.add(line);
                }
            }
        }
    }

    private String[] getFilename(int numOfFiles)
    {
        String[] fileName = new String[numOfFiles];
        for (int i = 0; i < numOfFiles; i++)
        {
            String name = args.get(i + 1);

            if (dir.fileExists(name) && !dir.isDirectory(name))
                fileName[i] = name;
            else
                throw new FileReadException(name);
        }
        return fileName;
    }
}
