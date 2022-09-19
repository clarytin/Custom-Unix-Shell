package uk.ac.ucl.shell.exception;

/**
 * Thrown when the shell attempts to read a file and an IO Exception occurs
 */
public class FileReadException extends RuntimeException
{
    /**
     * Constructs a {@code FileReadException} with the specified filename
     *
     * @param filename the filename that triggered the exception
     */
    public FileReadException(String filename)
    {
        super(filename + ": file error - invalid or cannot read");
    }
}
