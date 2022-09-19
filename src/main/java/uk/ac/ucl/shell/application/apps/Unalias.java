package uk.ac.ucl.shell.application.apps;

import uk.ac.ucl.shell.application.Application;
import uk.ac.ucl.shell.command.util.AliasesUtil;
import uk.ac.ucl.shell.exception.InvalidNumOfArgsException;

import java.util.List;

/**
 * Removes a saved alias
 */
public class Unalias extends Application
{
    private final AliasesUtil aliasesUtil;

    public Unalias(List<String> args, String inStream, String outStream)
    {
        super(args, inStream, outStream);
        aliasesUtil = AliasesUtil.getAliasesClass();
    }

    @Override
    protected void validateArgs()
    {
        if (!checkNumArgs(args, 1))
            throw new InvalidNumOfArgsException("unalias");
    }

    @Override
    protected void evaluate()
    {
        assert !args.isEmpty();
        String alias = args.get(0);
        aliasesUtil.removeAlias(alias);
    }
}
