package uk.ac.ucl.shell.exception;

/**
 * Thrown to indicate the shell is trying to process an application input with missing required flags
 */
public class MissingFlagsException extends RuntimeException
{
    /**
     * Constructs an {@code MissingFlagsException} with the missing flags
     *
     * @param missing the required flags that could not be found
     */
    public MissingFlagsException(String missing)
    {
        super("Missing required flag/s: " + missing);
    }
}
