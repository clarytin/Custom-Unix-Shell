package uk.ac.ucl.shell.application;

import java.util.ArrayList;
import java.util.List;

/**
 * Command line arguments that configure the way an application will run
 * 
 * See <a href="https://en.wikipedia.org/wiki/Command-line_interface#Command-line_option">Options</a> for more information
 */
public class Option
{
    private final String flag;
    private final boolean required;
    private boolean invoked;

    private final int numArgs;
    private final List<String> optionArgs;

    /**
     * Class constructor
     * @param flag      the string identifier for this flag
     * @param required  whether the flag is required for the application to run properly
     * @param numArgs   the number of arguments this flag accepts
     */
    public Option (String flag, boolean required, int numArgs)
    {
        this.flag = flag;
        this.required = required;
        this.numArgs = numArgs;
        this.optionArgs = new ArrayList<>();
        this.invoked = false;
    }


    /**
     * Gets the String used to identify this option
     * @return a string associated with this option that's unique in that app
     */
    public String getFlag()
    {
        return flag;
    }

    /**
     * Finds out whether this option was part of the command line input
     * @return {@code true} if this option was used in calling the command
     *         {@code true}  otherwise
     */
    public boolean getInvoked()
    {
        return this.invoked;
    }

    /**
     * Sets the invocation state to true
     */
    public void setInvokedTrue()
    {
        this.invoked = true;
    }

    /**
     * Returns information on whether the application is allowed to run without this flag
     *
     * @return {@code true} if option is required for the application
     *         {@code false} otherwise
     */
    public boolean isRequired()
    {
        return this.required;
    }

    /**
     * Returns the number of arguments this option takes
     * Option arguments are those positioned directly after the option
     *
     * @return the exact number of arguments this option allows
     */
    public int getNumArgs()
    {
        return this.numArgs;
    }

    /**
     * Stores the arguments associated with this option
     *
     * @param arg option-specific argument
     */
    public void addOptionArg(String arg)
    {
        optionArgs.add(arg);
    }

    /**
     * Gets the arguments used with this option
     *
     * @return a list of arguments from the command line associated with this option
     *         empty list if this option was not used to invoke the command
     */
    public List<String> getOptionArgs()
    {
        return optionArgs;
    }
}
