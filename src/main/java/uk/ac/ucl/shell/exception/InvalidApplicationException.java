package uk.ac.ucl.shell.exception;

/**
 * Thrown to indicate the shell cannot resolve the application name supplied
 */
public class InvalidApplicationException extends RuntimeException
{
    /**
     * Constructs an {@code InvalidApplicationException} with the invalid name
     *
     * @param name application name that cannot be resolved
     */
    public InvalidApplicationException(String name)
    {
        super(name + ": invalid application");
    }
}