package uk.ac.ucl.shell.command.util;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import uk.ac.ucl.shell.command.commands.Call;
import uk.ac.ucl.shell.exception.AliasExistsException;
import uk.ac.ucl.shell.exception.AliasDoesNotExistException;
import uk.ac.ucl.shell.parser.Parser;

/**
 * Utility class for handling <a href="https://en.wikipedia.org/wiki/Alias_(command)">Aliases</a>
 */
public class AliasesUtil
{
    private static AliasesUtil instance;
    private final HashMap<String, Call> aliases;

    private AliasesUtil()
    {
        // HashMap: [key: alias, value: arrayList of command string and args]
        aliases = new HashMap<>();
    }

    public static AliasesUtil getAliasesClass()
    {
        if (instance == null)
            instance = new AliasesUtil();
        
        return instance;
    }

    /**
     * Checks if string is saved as a key (alias)
     *
     * @param alias string to check
     * @return      true if it is an alias, false otherwise
     */
    public boolean isAlias(String alias) 
    {
        return aliases.containsKey(alias);
    }

    /**
     * Checks if application has an alias associated with the series of arguments
     *
     * @param command arraylist of command string + subsequent arguments
     * @return        true if there is a corresponding alias, false otherwise
     */
    public boolean hasAlias(Call command)
    {
        return containsVal(command);
    }

    /**
     * Checks if application has an alias associated with the series of arguments
     *
     * @param command string of the command line application
     * @return        true if there is a corresponding alias, false otherwise
     */
    public boolean hasAlias(String command)
    {
        Call commandParsed = parseCommand(command);
        return hasAlias(commandParsed);
    }

    /**
     * Saves new alias - command pair to a hashmap
     *
     * @param alias   string to represent an application
     *                has no spaces/special characters
     * @param command application to be given an alias
     */
    public void addAlias(String alias, Call command) 
    {
        if (hasAlias(command))
            throw new AliasExistsException(command.toString(), alias);
        
        aliases.put(alias, command);
    }

    /**
     * Saves new alias - command pair
     *
     * @param alias   string to represent an application
     *                has no spaces/special characters
     * @param command application to be given an alias
     *                string of the command line application
     */
    public void addAlias(String alias, String command) 
    {
        Call commandParsed = parseCommand(command);
        addAlias(alias, commandParsed);

    }

    /**
     * Removes alias - command pair
     *
     * @param alias alias of the alias-command pair to remove
     */
    public void removeAlias(String alias) 
    {
        Call command = getCall(alias);
        aliases.remove(alias, command);
    }

    /**
     * Gets the command string (no args) associated with the alias
     *
     * @param alias alias to get the associated command string
     * @return command string (with no args)
     */
    public String getCmdStringFromAlias(String alias)
    {
        return getCall(alias).getCommandString();   // First arg is the command string
    }

    /**
     * Gets the command args (without the command string) associated with the alias
     *
     * @param alias alias to get associated command args
     * @return command args (without the command string)
     */
    public List<String> getCmdArgsFromAlias(String alias)
    {
        return getCall(alias).getArgs();
    }

    /**
     * Gets all aliases saved
     *
     * @return set of aliases
     */
    public Set<String> getAliases() 
    {
        return aliases.keySet();
    }

    // Utility functions
    private Call getCall(String alias)
    {
        return findCommandInHashMap(alias);
    }

    // Gets ArrayList of command string and args from alias
    // Should not be accessed directly - go through getCommand for error handling
    private Call findCommandInHashMap(String alias) 
    {
        Call call = aliases.get(alias);
        if (call == null)
            throw new AliasDoesNotExistException(alias);
        else
            return call;
    }

    private Call parseCommand(String commandString) 
    {
        Call call = new Call(commandString);
        new Parser().parseCall(call);
        return call;
    }

    private boolean containsVal(Call command)
    {
        String commandString = command.getCommandString();
        boolean result = false;
        for (Call cmd : aliases.values()) 
        {
            if (cmd.getCommandString().compareTo(commandString) == 0) 
            {
                result = true;
                break;
            }
        }
        return result;
    }
}
