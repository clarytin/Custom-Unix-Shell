package uk.ac.ucl.shell.exception;

/**
 * Thrown to indicate the shell is attempting to access a file or directhat does not exist
 *
 * All Shell exceptions are caught in {@link uk.ac.ucl.shell.View}, so having an unchecked version of
 * FileNotFoundException helps keep our code clean
 */
public class FileDoesNotExistException extends RuntimeException
{
    /**
     * Constructs an {@code FileDoesNotExistException} with the invalid name
     *
     * @param filename file that doesn't exist
     */
    public FileDoesNotExistException(String filename)
    {
        super(filename + ": file does not exist");
    }
}
