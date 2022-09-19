package uk.ac.ucl.shell.exception;

/**
 * Thrown to indicate that the shell is attempting to access an alias that does not exist
 */
public class AliasDoesNotExistException extends RuntimeException
{
    /**
     * Constructs an {@code AliasDoesNotExistException} with the alias to be accessed
     *
     * @param alias string alias that does not exist
     */
    public AliasDoesNotExistException(String alias)
    {
        super(alias + " : Alias does not exist");
    }
}
