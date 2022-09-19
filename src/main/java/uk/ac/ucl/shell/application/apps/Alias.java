package uk.ac.ucl.shell.application.apps;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.ac.ucl.shell.application.Application;
import uk.ac.ucl.shell.command.util.AliasesUtil;
import uk.ac.ucl.shell.exception.InvalidFormatException;
import uk.ac.ucl.shell.exception.InvalidNumOfArgsException;

/**
 * Enables replacing a command with another string
 */
public class Alias extends Application
{
    private final AliasesUtil aliasesUtil;

    public Alias(List<String> args, String inStream, String outStream)
    {
        super(args, inStream, outStream);
        aliasesUtil = AliasesUtil.getAliasesClass();
    }

    /**
     * @inheritdoc
     * Can be called with "alias" or "alias [name=[string]]"
     */
    @Override
    protected void validateArgs()
    {
        if (!(checkNumArgs(args, 2) || checkNumArgs(args, 0)))
            throw new InvalidNumOfArgsException("alias");

        if (checkNumArgs(args, 2))
        {
            String alias = getAliasFromArgs();
            String command = getCommandStringFromArgs();
            checkArgFormat(alias, command);
        }
    }

    private String getAliasFromArgs()
    {
        return args.get(0);
    }

    private String getCommandStringFromArgs()
    {
        return args.get(1);
    }

    // Format should be 'alias='valid command''
    private void checkArgFormat(String alias, String command)
    {
        String commandFormatRegex = "^[a-zA-Z0-9]+[=].+$";    // Matches name=string (single quotes stripped already)

        Pattern commandFormatPattern = Pattern.compile(commandFormatRegex);
        Matcher m = commandFormatPattern.matcher(alias + command);

        if (!(m.matches()))
            throw new InvalidFormatException("Alias", "alias [name=[string]]");
    }

    @Override
    protected void evaluate()
    {
        if (args.isEmpty())
        {
            Set<String> setAliases = aliasesUtil.getAliases();
            output.addAll(setAliases);
        }
        else
        {
            String alias = getAliasFromArgs();
            String command = getCommandStringFromArgs();
            alias = formatAlias(alias);
            command = formatCommand(command);
            aliasesUtil.addAlias(alias, command);
        }
    }

    private String formatAlias(String originalAlias)
    {
        return originalAlias.replace("=","");
    }

    private String formatCommand(String originalCommand)
    {
        originalCommand = originalCommand.trim();
        return originalCommand;
    }
}
