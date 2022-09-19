package uk.ac.ucl.shell.util;

import uk.ac.ucl.shell.exception.*;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Handles directory-related functions and tracks the current working directory
 */
public class Directory
{
    private static Directory instance;
    private final Charset encoding;
    private String currentDirectory;

    private Directory()
    {
        encoding = StandardCharsets.UTF_8;
        currentDirectory = System.getProperty("user.dir");
    }

    /**
     * Lazily initializes a singleton instance of this class
     *
     * @return singleton instance of Directory class
     */
    public static Directory getDirectory()
    {
        if (instance == null)
        {
            instance = new Directory();
        }
        return instance;
    }

    /**
     * Returns the path of the current directory in string
     *
     * @return current directory's path in string
     */
    public String getCurrentDir()
    {
        return currentDirectory;
    }

    /**
     * Updates the current directory instance
     *
     * @param newDir    new current directory in string
     */
    public void setCurrentDir(String newDir)
    {
        currentDirectory = newDir;
    }

    /**
     * Creates an empty file with the contents specified
     *
     * @param filename file to be created
     */
    public void createFile(String filename)
    {
        try
        {
            File file = new File(getCurrentDir() + File.separator + filename);
            if (!file.createNewFile())
                throw new IOException();
        }
        catch (IOException e)
        {
            throw new FileCreationException(filename);
        }
    }

    /**
     * Returns the file object of the stated filename
     *
     * @param arg     filename in string
     * @return        file object of the stated filename
     */
    public File getFile(String arg)
    {
        File file = new File(getPath(arg).toString());
        if (file.exists())
        {
            return file;
        }

        throw new FileDoesNotExistException(arg);
    }

    /**
     * Creates a file with the contents specified
     *
     * @param filename file to be created
     * @param contents contents to be placed in file
     */
    public void writeToFile(String filename, List<String> contents)
    {
        try
        {
            File file = new File(getCurrentDir() + File.separator + filename);
            FileWriter filewriter = new FileWriter(file, false);
            filewriter.write(String.join("\n", contents));
            filewriter.close();
        }
        catch (IOException e)
        {
            throw new FileWriteException(filename);
        }
    }

    /**
     * Stores argument in a file specified by an absolute path
     *
     * @param filepath     location where contents will be stored
     *                     must be an absolute file path
     * @param contents     contents to be placed in dedicated output file
     * @throws IOException if file cannot be found
     */
    public void writeToAbsoluteFilePath(String filepath, List<String> contents) throws IOException
    {
        try (var filewriter = new FileWriter(filepath, false))
        {
            filewriter.write(String.join("\n", contents));
        }
        catch (IOException e)
        {
            throw new IOException();
        }
    }

    /**
     * Returns all lines of a file in the form of list
     *
     * @param arg     filename in string
     * @return        a complete list of file lines
     */
    public List<String> getAllLines(String arg) throws RuntimeException
    {
        try
        {
            return Files.readAllLines(getPath(arg), encoding);
        }
        catch (IOException e)
        {
            throw new FileReadException(arg);
        }
    }

    /**
     * Returns the buffered reader of the stated file
     *
     * @param arg     filename in string
     * @return        buffered reader object of the stated file
     */
    public BufferedReader getBufferedReader(String arg) throws RuntimeException
    {
        try
        {
            return Files.newBufferedReader(getPath(arg), encoding);
        }
        catch (IOException e)
        {
            throw new FileReadException(arg);
        }
    }

    /**
     * Reads the next line from a buffered reader
     *
     * @param reader    buffered reader object of the stated file
     * @param filename  filename in string
     * @return          string of the next line
     */
    public String readBufferedLine(BufferedReader reader, String filename)
    {
        try
        {
            return reader.readLine();
        }
        catch (IOException e)
        {
            throw new FileReadException(filename);
        }
    }

    /**
     * Checks if the stated path is a valid directory
     *
     * @param arg     path in string
     * @return        <code>true</code>if it is a directory, <code>false</code> if not
     */
    public boolean isDirectory(String arg)
    {
        File file = getFile(arg);
        return file.isDirectory();
    }

    /**
     * Checks if the file exists
     *
     * @param arg     filename in string
     * @return        <code>true</code> if file exists, else <code>false</code>
     */
    public boolean fileExists(String arg)
    {
        File file = new File(getPath(arg).toString());
        return file.exists();
    }

    private Path getPath(String arg)
    {
        for (var file : Constants.SPECIAL_FILES)
        {
            if (arg.equals(file))
                return Paths.get(file);
        }

        var separator = File.separator;
        if (getCurrentDir().equals(File.separator))
            separator = "";

        return Paths.get(getCurrentDir() + separator + arg);
    }
}
