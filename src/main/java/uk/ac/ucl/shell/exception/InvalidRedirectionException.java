package uk.ac.ucl.shell.exception;

/**
 * Thrown when the parser attempts to add input or output redirection
 * where these have already been specified
 */
public class InvalidRedirectionException extends RuntimeException
{
    /**
     * Constructs an {@code InvalidRedirectionException} with the specified redirection
     * @param arg string specifying whether the error occurred with
     *            Input or Output redirection
     */
    public InvalidRedirectionException(String arg) 
    {
        super(arg + ": more than one redirection");
    }
}
