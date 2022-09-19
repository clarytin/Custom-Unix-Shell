package uk.ac.ucl.shell.exception;

/**
 * Thrown when the shell is trying to resolve an application
 * without being supplied with an application name
 */
public class NullApplicationException extends RuntimeException
{
    /**
     * Constructs a generic {@code NullApplicationException}
     */
    public NullApplicationException()
    {
        super("no application");
    }
}
