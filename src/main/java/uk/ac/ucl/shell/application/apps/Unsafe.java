package uk.ac.ucl.shell.application.apps;

import uk.ac.ucl.shell.application.Application;
import uk.ac.ucl.shell.application.IApplication;

import java.util.ArrayList;

/**
 * Runs a given application but instead of raising exceptions,
 * prints the error message to stdout
 */
public class Unsafe extends Application
{
    IApplication app;
    public Unsafe(IApplication app)
    {
        super(new ArrayList<>(), null, null);
        this.app = app;
    }

    @Override
    protected void validateArgs()
    {
        // not needed
    }

    @Override
    protected void evaluate()
    {
        try
        {
            this.app.exec();
            output = this.app.getOutput();
        }
        catch (Exception e)
        {
            output.add(e.getMessage());
        }
    }
}
