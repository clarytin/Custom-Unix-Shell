package uk.ac.ucl.shell.exception;

/**
 * Thrown when the shell attempts to create a file and an IO Exception occurs
 */
public class FileCreationException extends RuntimeException
{
    /**
     * Constructs a {@code FileCreationException} with the specified filename
     *
     * @param filename the filename that triggered the exception
     */
    public FileCreationException(String filename)
    {
        super("File already exists or could not be created at " + filename);
    }
}