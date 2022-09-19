package uk.ac.ucl.shell.exception;

/**
 * Thrown to indicate the shell is attempting to create an app with an
 * option that cannot be resolved for that application
 */
public class IllegalOptionException extends RuntimeException
{
    /**
     * Constructs an {@code IllegalOptionException} with the invalid flag
     *
     * @param flag command line option that cannot be used with the app
     */
    public IllegalOptionException(String flag)
    {
        super(flag + ": illegal option");
    }
}
