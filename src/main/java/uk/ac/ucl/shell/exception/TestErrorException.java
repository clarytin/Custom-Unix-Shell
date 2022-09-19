package uk.ac.ucl.shell.exception;

/**
 * Thrown to indicate a test has not executed properly
 */
public class TestErrorException extends RuntimeException
{
    /**
     * Constructs a generic {@code TestError Exception}
     */
    public TestErrorException()
    {
        super("Unit test could not be properly executed");
    }
}