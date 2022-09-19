package uk.ac.ucl.shell.application;

import uk.ac.ucl.shell.exception.*;
import uk.ac.ucl.shell.util.Directory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Default implementation of the IApplication interface
 *
 * This class defines a template method for the execution of applications
 * and provides an interface for verifying options and obtaining their arguments
 */
public abstract class Application implements IApplication
{
    private final Directory dir;

    protected List<String> args;
    protected List<String> output;

    protected String inStream;
    protected String outStream;
    protected final HashMap<String, Option> validOptions;

    protected boolean optionsLoaded;

    /**
     * Constructs an {@link Application} with the specified parameters
     *
     * @param args      the arguments for this application
     *                  all command line input after the name, including options and option arguments
     * @param inStream  the input stream for this application
     *                  empty string if no input is specified
     * @param outStream the output stream for this application
     *                  empty string if output is not specified
     */
    protected Application(List<String> args, String inStream, String outStream)
    {
        this.dir = Directory.getDirectory();

        this.args = args;
        this.inStream = inStream;
        this.outStream = outStream;

        this.output = new ArrayList<>();
        this.validOptions = new HashMap<>();
        this.optionsLoaded = false;
    }

    public List<String> getOutput()
    {
        if (this.outStream == null || this.outStream.equals(""))
            return this.output;
        else
        {
            dir.writeToFile(this.outStream, this.output);
            return new ArrayList<>();
        }
    }

    /**
     * Template method for executing an application
     *
     * {@code validateArgs()} and {@code evaluate()} must be overridden by the child class
     */
    public void exec()
    {
        validateArgs();
        checkOptions();
        initRedirection();
        evaluate();
    }

    /**
     * Checks that the raw {@code args} are valid
     *
     * Example checks include:
     * <ul>
     *     <li>Validating the number of arguments</li>
     *     <li>Validating the type of arguments supplied</li>
     * </ul>
     */
    protected abstract void validateArgs();

    /**
     * Sanity checks the command-line options
     */
    protected void checkOptions()
    {
        List<String> optionsFound = validateOptions();
        checkForRequiredOptions(optionsFound);
        checkForRequiredArgs(optionsFound);
        setOptionsAsInvoked(optionsFound);
    }

    /**
     * Implements the actual application logic
     * Prints output to stdout by appending to {@code this.output}
     */
    protected abstract void evaluate();

    /**
     * Extracts options and their associated arguments from the argument list
     * These become accessible with {@link Application#getOptionArgs(String)},
     * freeing the argument list and making options position-independent
     */
    protected void loadOptions()
    {
        for(int c = 0; c < args.size(); c++)
        {
            var arg = args.get(c);
            if (arg.startsWith("-"))
            {
                // remove options and option arguments from args list
                Option opt = validOptions.get(arg.substring(1));
                assert opt != null;
                if (opt.getNumArgs() != 0)
                {
                    for (int d = 1; d < opt.getNumArgs() + 1; d++)
                        args.remove(c + 1);
                }
                args.remove(c--);
            }
        }
        optionsLoaded = true;
    }

    /**
     * Returns the arguments that are associated with this option
     *
     * @param flag the string used to invoke the option
     * @return     the command line argument associated with this option
     *             null if the option does not accept arguments
     */
    protected List<String> getOptionArgs(String flag)
    {
        assert optionsLoaded;
        return validOptions.get(flag).getOptionArgs();
    }

    /**
     * Finds out if the given option was invoked during the command
     *
     * @param flag the string used to invoke the option
     * @return  {@code true} if this option was used to call this command
     *          {@code false} otherwise
     */
    protected boolean optionInvoked(String flag)
    {
        assert validOptions.get(flag) != null;
        return validOptions.get(flag).getInvoked();
    }

    /**
     * Checks that the number of args given by the user matches the number needed for the command
     *
     * @param args    arguments provided
     * @param numArgs exact number of arguments required
     * @return        {@code true} if number of args matches, {@code false} if not
     */
    protected boolean checkNumArgs(List<String> args, Integer numArgs)
    {
        return args.size() == numArgs;
    }

    /**
     * Checks that the number of args given by the user is in acceptable args range
     *
     * @param args    arguments provided
     * @param minArgs minimum number of arguments (inclusive)
     * @param maxArgs maximum number of arguments (-1 for no max) (inclusive)
     * @return        {@code true} if number of args is within range, {@code false} if not
     */
    protected boolean checkNumArgs(List<String> args, Integer minArgs, Integer maxArgs)
    {
        return args.size() >= minArgs && (maxArgs == -1 || args.size() <= maxArgs);
    }

    /**
     * Checks that the flag is valid and at required position
     *
     * @param args     arguments provided
     * @param position expected index of flag
     * @param flag     name of flag
     * @return         {@code true} if the flag is on the expected position,{@code false} if not
     */
    protected boolean checkFlag(List<String> args, Integer position, String flag) 
    {
        return args.get(position).compareTo(flag) == 0;
    }

    private void initRedirection()
    {
        if (this.inStream != null && !dir.fileExists(this.inStream))
            throw new FileDoesNotExistException("Input Redirection");

        if (this.outStream != null && !dir.fileExists(this.outStream))
            dir.createFile(this.outStream);
    }

    private List<String> validateOptions()
    {
        var foundFlags = new ArrayList<String>();
        for (var arg : args)
        {
            if (!arg.startsWith("-"))
                continue;

            var option = arg.substring(1);
            validateOption(option, foundFlags);
        }
        return foundFlags;
    }

    private void checkForRequiredOptions(List<String> foundFlags)
    {
        ArrayList<String> required = new ArrayList<>();

        for (var entry : validOptions.entrySet())
        {
            if (entry.getValue().isRequired())
                required.add(entry.getKey());
        }

        for (String flag : foundFlags)
            required.remove(flag);

        if (!required.isEmpty())
        {
            String missing = required.toString();
            missing = missing.substring(1, missing.length() - 1);
            throw new MissingFlagsException(missing);
        }
    }

    private void validateOption(String flag, List<String> foundFlags)
    {
        if (flag.length() == 0 || !Character.isAlphabetic(flag.charAt(0)))
            return;

        var option = validOptions.get(flag);
        if (option != null)
            foundFlags.add(option.getFlag());
        else
            throw new IllegalOptionException("-" + flag);
    }


    private void setOptionsAsInvoked(List<String> optionsFound)
    {
        for (String option : optionsFound)
            validOptions.get(option).setInvokedTrue();
    }

    private void checkForRequiredArgs(List<String> optionsFound)
    {
        for (String curr : optionsFound)
        {
            var option = validOptions.get(curr);

            if (option.getNumArgs() != 0)
                checkArgsAfterOption(option);
        }
    }

    private void checkArgsAfterOption(Option option)
    {
        for(int c = 0; c < args.size(); c++)
        {
            var arg = args.get(c);
            var flag = "-" + option.getFlag();

            if (arg.equals(flag))
            {
                for(int d = 1; d < option.getNumArgs() + 1; d++)
                {
                    var optionArg = args.get(c + d);
                    if(optionArg.startsWith("-"))
                        throw new InvalidNumOfArgsException("Flag " + flag);
                    else
                        option.addOptionArg(optionArg);
                }
            }
        }
    }
}
