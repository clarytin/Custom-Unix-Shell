package uk.ac.ucl.shell.exception;

/**
 * Thrown when the terminal could not be reset to normal mode
 */
public class RawTerminalException extends RuntimeException
{
    /**
     * Construct a generic {@code RawTerminalException} with instructions
     * on how to resolve this
     */
    public RawTerminalException()
    {
        super("Failed to exit raw mode. Once the shell has exited, you may run " +
                "\"stty cooked\" or \"stty sane\" to go back to normal settings.");
    }
}