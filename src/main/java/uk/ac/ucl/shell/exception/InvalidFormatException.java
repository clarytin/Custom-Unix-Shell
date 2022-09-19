package uk.ac.ucl.shell.exception;

/**
 * Thrown to indicate an input with wrong formatting has been supplied to an {@link uk.ac.ucl.shell.application.Application}
 */
public class InvalidFormatException extends RuntimeException
{
    /**
     * Constructs an {@code InvalidFormatException} with the invoker and
     * information on the correct format
     *
     * @param invoker        the context where the exception was thrown
     * @param correctFormat  a description of the correct format
     */
    public InvalidFormatException(String invoker, String correctFormat)
    {
        super(invoker + ": Invalid format, correct format is " + correctFormat);
    }

    /**
     * Constructs an {@code InvalidFormatException} with the invoker and
     *
     * @param invoker        the context where the exception was thrown
     */
    public InvalidFormatException(String invoker)
    {
        super(invoker + ": Invalid format");
    }
}
