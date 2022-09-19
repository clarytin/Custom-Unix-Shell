package uk.ac.ucl.shell.command.commands;

import uk.ac.ucl.shell.application.IApplication;
import uk.ac.ucl.shell.exception.InvalidRedirectionException;
import uk.ac.ucl.shell.application.ApplicationFactory;
import uk.ac.ucl.shell.command.ICommand;
import uk.ac.ucl.shell.command.ICommandVisitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static uk.ac.ucl.shell.util.Constants.IN_REDIRECT;
import static uk.ac.ucl.shell.util.Constants.OUT_REDIRECT;

/**
 * Command that executes an application with specified inputs
 */
public class Call implements ICommand
{
    private String commandString;
    private IApplication app;

    private List<String> args;
    private String inRedirect;
    private String outRedirect;

    /**
     * Class constructor
     *
     * @param commandString the string representation of this Call command
     */
    public Call(String commandString)
    {
        this.commandString = Objects.requireNonNull(commandString);
        this.args = new ArrayList<>();
        this.inRedirect = null;
        this.outRedirect = null;
    }

    /**
     * Creates an instance of {@link IApplication} and executes it
     *
     * @return the output from the application execution
     */
    public List<String> executeApp()
    {
        createApp();
        this.app.exec();
        return this.app.getOutput();
    }

    /**
     * Obtains the full list of arguments
     *
     * @return the arguments associated with this Call
     */
    public List<String> getArgs()
    {
        return this.args;
    }

    /**
     * Replaces the arguments with another set of arguments
     *
     * @param args the new arguments to be associated with this Call
     */
    public void setArgs(List<String> args)
    {
        this.args = Objects.requireNonNull(args);
    }

    /**
     * Replaces a specific argument with a different value
     *
     * @param index index of the argument to be replaced
     * @param arg   new value of the argument
     */
    public void setArg(int index, String arg)
    {
        this.args.set(index, Objects.requireNonNull(arg));
    }

    /**
     * Returns the original string used to create this Call
     *
     * @return the command line associated with this Call
     */
    public String getCommandString()
    {
        return this.commandString;
    }

    /**
     * Allows the editing of the command string, such as for command substitution
     *
     * @param newString the command string to be associated with this Call
     */
    public void setCommandString(String newString)
    {
        if (newString == null)
            throw new IllegalArgumentException("Call cannot have a null command string");
        this.commandString = newString;
    }

    /**
     * Allows the {@link uk.ac.ucl.shell.parser.CallListener} to add arguments to this Call
     *
     * @param arg the argument to be appended
     */
    public void addArg(String arg)
    {
        this.args.add(Objects.requireNonNull(arg));
    }

    /**
     * Allows the {@link uk.ac.ucl.shell.parser.CallListener} to add redirects to this Call
     *
     * @param direction "<" for input redirection
     *                  ">" for output redirection
     * @param location  for input redirection, the file to be read for input
     *                  for output redirection, the file to be overwritten with the output
     */
    public void addRedirect(String direction, String location)
    {
        if (direction.equals(IN_REDIRECT))
        {
            if (this.inRedirect != null)
                throw new InvalidRedirectionException("Input");
            else
                this.inRedirect = location;
        }
        else if (direction.equals(OUT_REDIRECT))
        {
            if (this.outRedirect != null)
                throw new InvalidRedirectionException("Output");
            else
                this.outRedirect = location;
        }
        else
            throw new IllegalArgumentException();
    }

    /**
     * Gets the file this call's input has been redirected to
     *
     * @return string of the location of the redirected path
     */
    public String getInRedirect()
    {
        return inRedirect;
    }

    /**
     * Gets the file this call's output has been redirected to
     *
     * @return string of the location of the redirected path
     */
    public String getOutRedirect()
    {
        return outRedirect;
    }

    public List<String> evaluate(ICommandVisitor visitor)
    {
        return visitor.visit(this);
    }

    private void createApp()
    {
        this.app = new ApplicationFactory().getApplication(args, inRedirect, outRedirect);
    }
}
