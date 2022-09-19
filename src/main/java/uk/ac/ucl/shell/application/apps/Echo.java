package uk.ac.ucl.shell.application.apps;

import uk.ac.ucl.shell.application.Application;

import java.util.List;

/**
 * Prints its arguments separated by spaces and followed by a newline to stdout
 */
public class Echo extends Application
{
    public Echo(List<String> args, String in, String out)
    {
        super(args, in, out);
    }

    @Override
    protected void validateArgs()
    {
        // any number of args is accepted
    }

    @Override
    protected void evaluate()
    {
        StringBuilder out = new StringBuilder();
        for (int c = 0; c < args.size(); c++)
        {
            out.append(args.get(c));
            if (c != args.size() - 1)
                out.append(" ");
        }
            
        output.add(out.toString());
    }
}
