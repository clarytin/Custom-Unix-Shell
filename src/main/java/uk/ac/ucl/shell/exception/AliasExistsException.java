package uk.ac.ucl.shell.exception;

/**
 * Thrown to indicate that the application is attempting to create an alias that already exists
 */
public class AliasExistsException extends RuntimeException
{
    /**
     * Constructs an {@code AliasAlreadyExistException} with the
     * command to be aliased and the attempted alias
     *
     * @param command application to be given an alias
     * @param alias   string alias
     */
    public AliasExistsException(String command, String alias)
    {
        super(command + " : Alias " + alias + " already exists, use 'unalias " + alias + "' to unset previous alias");
    }

    /**
     * Constructs an {@code AliasAlreadyExistException} with the command to be aliased
     *
     * @param alias string alias
     */
    public AliasExistsException(String alias)
    {
        super(alias + " : Alias already exists, use 'unalias alias' to unset previous alias");
    }
}
