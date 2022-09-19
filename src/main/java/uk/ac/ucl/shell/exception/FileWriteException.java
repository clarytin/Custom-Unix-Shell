package uk.ac.ucl.shell.exception;

/**
 * Thrown when the shell attempts to write a file and an IO Exception occurs
 */
public class FileWriteException extends RuntimeException
{
    /**
     * Constructs a {@code FileWriteException} with the specified filename
     *
     * @param filename the filename that triggered the exception
     */
    public FileWriteException(String filename)
    {
        super(filename + ": file error - invalid or cannot be written to");
    }

    /**
     * Constructs a {@code FileWriteException} with the specified filename and information
     *
     * @param filename  the filename that triggered the exception
     * @param extraInfo extra information to be showed with the exception
     */
    public FileWriteException(String filename, String extraInfo)
    {
        super(filename + ": file error - invalid or cannot be written to. " + extraInfo);
    }
}
