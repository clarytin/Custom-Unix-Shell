package uk.ac.ucl.shell.exception;

/**
 * Thrown when an application is called with too many arguments
 */
public class TooManyArgsException extends RuntimeException 
{
    /**
     * Construct a {@code TooManyArgsException} with the application specified
     *
     * @param app   application being executed when exception was thrown
     */
    public TooManyArgsException(String app)
    {
        super(app + ": too many arguments");
    }
}
