package uk.ac.ucl.shell.exception;

/**
 * Thrown to indicate that the wrong number of arguments has been supplied to an {@link uk.ac.ucl.shell.application.Application}
 * or an {@link uk.ac.ucl.shell.application.Option}
 */
public class InvalidNumOfArgsException extends RuntimeException 
{
    /**
     * Constructs a generic {@code InvalidNumOfArgsException}
     */
    public InvalidNumOfArgsException()
    {
        super("Wrong number of arguments");
    }

    /**
     * Constructs an {@code InvalidNumOfArgsException} with the invoking method
     */
    public InvalidNumOfArgsException(String context)
    {
        super(context + ": Invalid number of arguments");
    }
}