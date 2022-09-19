package uk.ac.ucl.shell.exception;

/**
 * Thrown to indicate the shell is attempting to enter a directory that does not exist
 */
public class InvalidDirectoryException extends RuntimeException
{
    /**
     * Constructs an {@code InvalidDirectoryException} with the invalid name
     *
     * @param dirName directory that cannot be found
     */
    public InvalidDirectoryException(String dirName)
    {
        super(dirName + ": directory does not exist");
    }
}
