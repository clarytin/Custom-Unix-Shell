package uk.ac.ucl.shell.util;

import org.junit.BeforeClass;
import org.junit.Test;
import uk.ac.ucl.shell.ITest;
import uk.ac.ucl.shell.exception.*;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import static org.junit.Assert.*;
import static uk.ac.ucl.shell.Constants.LIST_FILE;

public class DirectoryTest extends ITest
{
    private static Directory dir;
    private final String exampleFile = Paths.get("src", "test", "res", "example.txt").toString();

    @BeforeClass
    public static void init()
    {
        dir = Directory.getDirectory();
    }

    @Test
    public void writeToFile_ShouldWriteContents()
    {
        var filename = "testWriteToFile.txt";
        var contents = new ArrayList<>(Collections.singletonList("だってばよ"));

        dir.writeToFile(filename, contents);

        try
        {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            assertEquals("だってばよ", scanner.nextLine());
            assert(!scanner.hasNext());
            file.delete();
        }
        catch (FileNotFoundException e)
        {
            throw new TestErrorException();
        }
    }

    @Test(expected = FileWriteException.class)
    public void writeToFile_InvalidName_ShouldThrowException()
    {
        dir.writeToFile("writeToFile_InvalidName/1.txt",
                new ArrayList<>(Collections.singletonList("だってばよ")));
    }

    @Test
    public void writeToFile_EmptyContents_ShouldWriteNothing()
    {
        try
        {
            dir.writeToFile(exampleFile, new ArrayList<>());
            Scanner scanner = new Scanner(new File(exampleFile));
            assertFalse(scanner.hasNext());
        }
        catch (FileNotFoundException e)
        {
            throw new TestErrorException();
        }
    }

    @Test
    public void writeToAbsoluteFilePath_ShouldWork() throws IOException
    {
        var contents = new ArrayList<String>();
        contents.add("testing 123");
        dir.writeToAbsoluteFilePath(exampleFile, contents);

        try
        {
            Scanner scanner = new Scanner(new File(exampleFile));
            assertEquals("testing 123", scanner.nextLine());
            assert(!scanner.hasNext());
        }
        catch (FileNotFoundException e)
        {
            throw new TestErrorException();
        }
    }

    @Test
    public void writeToAbsoluteFilePath_EmptyContents_ShouldWriteNothing() throws IOException
    {
        var contents = new ArrayList<String>();
        dir.writeToAbsoluteFilePath(exampleFile, contents);
        try
        {
            Scanner scanner = new Scanner(new File(exampleFile));
            assertFalse(scanner.hasNext());
        }
        catch (FileNotFoundException e)
        {
            throw new TestErrorException();
        }
    }

    @Test(expected = IOException.class)
    public void writeToAbsoluteFilePath_InvalidName_ShouldThrowException() throws IOException
    {
        var filename = "";
        var contents = new ArrayList<String>();
        contents.add("123");
        dir.writeToAbsoluteFilePath(filename, contents);
    }


    @Test
    public void createFile_ShouldMakeAFile()
    {
        var filepath = Paths.get("src", "test", "res", "testing.txt").toString();
        dir.createFile(filepath);
        File file = new File(filepath);
        assertTrue(file.exists());
        file.delete();
    }

    @Test(expected = FileCreationException.class)
    public void createFile_ExistingFile_ShouldThrowException()
    {
        dir.createFile(LIST_FILE);
    }


    @Test(expected = FileReadException.class)
    public void getAllLines_InvalidFile_ShouldThrowException()
    {
        dir.getAllLines("getAllLines_InvalidFile.txt");
    }


    @Test(expected = FileDoesNotExistException.class)
    public void getFile_IMOVnvalidFile_ShouldThrowException()
    {
        dir.getFile("getFile_InvalidFile.txt");
    }



    @Test(expected = FileNotFoundException.class)
    public void readBufferedLine_InvalidFile_ShouldThrowException() throws IOException
    {
        dir.readBufferedLine(new BufferedReader(new FileReader("hi.txt")), "qwekjhxui.txt");
    }


    @Test(expected = FileReadException.class)
    public void getBufferedReader_InvalidFile_ShouldThrowException()
    {
        dir.getBufferedReader("getBufferedReader_InvalidFile.txt");
    }

    @Test
    public void fileExists_InvalidPath_ShouldBeFalse()
    {
        assertFalse(dir.fileExists("asdasfa.txt"));
    }
}
