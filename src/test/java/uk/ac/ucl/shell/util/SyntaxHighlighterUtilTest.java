package uk.ac.ucl.shell.util;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ucl.shell.ITest;
import uk.ac.ucl.shell.command.util.AliasesUtil;
import uk.ac.ucl.shell.exception.TestErrorException;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static uk.ac.ucl.shell.util.Constants.APPS_DIR;

public class SyntaxHighlighterUtilTest extends ITest
{
    private SyntaxHighlighterUtil highlighter;

    @Before
    public void init()
    {
        this.highlighter = new SyntaxHighlighterUtil();
    }

    @Test
    public void printResult_ShouldPrintBlue()
    {
        highlighter.printResult("hello");
        assertEquals("\u001b[34mhello", out.toString().strip());
    }

    @Test
    public void printError_ShouldPrintRed()
    {
        highlighter.printError("error");
        assertEquals("\u001b[31merror", err.toString().strip());
    }

    @Test
    public void printDefault_ShouldPrintDefaultLine()
    {
        highlighter.printDefault("hello");
        assertEquals("\u001b[39mhello", out.toString().strip());
    }

    @Test
    public void highlightLine_InvalidCommand_ShouldPrintError()
    {
        highlighter.highlightLine("h");
        assertEquals("\u001b[31mh", out.toString().strip());
    }

    @Test
    public void highlightLine_WithFlags_ShouldHighlightFlags()
    {
        highlighter.highlightLine("find -name hi");
        assertEquals(
                "\u001B[32mfind " +
                        "\u001B[35m-name " +
                        "\u001B[39mhi", out.toString().strip());
    }

    @Test
    public void highlightLine_WithInvalidFilename_ShouldHighlightFile()
    {
        highlighter.highlightLine("cat randomfile.txt");
        assertEquals("\u001B[32mcat \u001B[31;4mrandomfile.txt\u001B[24m", out.toString().strip());
    }

    @Test
    public void highlightLine_WithInValidExtension_ShouldHighlightRed()
    {
        highlighter.highlightLine("cat.randomextension");
        assertEquals("\u001B[31mcat.randomextension", out.toString().strip());
    }

    @Test
    public void highlightLine_FileNameSingleQuote_ShouldHighlightFiles()
    {
        highlighter.highlightLine("cat 'lol.txt'");
        assertEquals("\u001B[32mcat \u001B[31;4m'lol.txt'\u001B[24m", out.toString().strip());
    }

    @Test
    public void highlightLine_Alias_ShouldHighlightAsApp()
    {
        AliasesUtil.getAliasesClass().addAlias("newalias", "echo hi");
        highlighter.highlightLine("newalias");
        assertEquals("\u001B[32mnewalias", out.toString().strip());
    }

    @Test
    public void highlightLine_WithSingleQuotes_ShouldHighlightQuotes()
    {
        highlighter.highlightLine("'*.txt'");
        assertEquals("\u001B[31m'*.txt'", out.toString().strip());
    }

    @Test
    public void highlightLine_WithDoubleQuotes_ShouldHighlightQuotes()
    {
        highlighter.highlightLine("\"*.txt\"");
        assertEquals("\u001B[31m\"*.txt\"", out.toString().strip());
    }

    @Test
    public void highlightLine_WithBackQuotes_ShouldHighlightQuotes()
    {
        highlighter.highlightLine("echo `hello`");
        assertEquals("\u001B[32mecho \u001B[39m`hello`", out.toString().strip());
    }

    @Test
    public void highlightLine_EmptyToken_ShouldHighlightQuotes()
    {
        highlighter.highlightLine("echo ");
        assertEquals("\u001B[32mecho", out.toString().strip());
    }


    @Test
    public void highlightLine_InvalidQuotes_ShouldHighlightInvalid()
    {
        highlighter.highlightLine("echo `echo hello");
        assertEquals("\u001B[32mecho \u001B[31m`echo \u001B[39mhello", out.toString().strip());
    }

    @Test
    public void highlightLine_Semicolons_ShouldHighlightSemicolons()
    {
        highlighter.highlightLine("echo hello ; echo hi ; echo bye");
        assertEquals("\u001B[32mecho \u001B[39mhello \u001B[39m\u001B[33m; " +
                "\u001B[32mecho \u001B[39mhi \u001B[39m\u001B[33m; " +
                "\u001B[32mecho \u001B[39mbye", out.toString().strip());
    }

    @Test
    public void highlightLine_SemicolonBefore_ShouldHighlightSemicolons()
    {
        highlighter.highlightLine("echo hello ;echo hi");
        assertEquals("\u001B[32mecho \u001B[39mhello \u001B[33m;\u001B[32mecho \u001B[39mhi", out.toString().strip());
    }

    @Test
    public void highlightLine_Pipes_ShouldHighlightPipes()
    {
        highlighter.highlightLine("cat hi.txt | grep -n 10 | uniq");
        assertEquals("\u001B[32mcat \u001B[31;4mhi.txt" +
                "\u001B[24m \u001B[39m\u001B[33m| " +
                "\u001B[32mgrep \u001B[35m-n \u001B[39m10 " +
                "\u001B[39m\u001B[33m| \u001B[32muniq", out.toString().strip());
    }

    @Test
    public void highlightLine_PipeBefore_ShouldHighlightPipe()
    {
        highlighter.highlightLine("cat hi.txt |grep -n 10");
        assertEquals("\u001B[32mcat \u001B[31;4mhi.txt\u001B[24m \u001B[33m|\u001B[32mgrep \u001B[35m-n \u001B[39m10", out.toString().strip());
    }

    @Test
    public void highlightLine_Unsafe_ShouldHighlightUnsafes()
    {
        highlighter.highlightLine("_find");
        assertEquals("\u001B[32m_find", out.toString().strip());
    }

    @Test
    public void highlightLine_WrongApp_ShouldPrintInRed()
    {
        highlighter.highlightLine("ech hi");
        assertEquals("\u001B[31mech \u001B[31mhi", out.toString().strip());
    }

    /*
    @Test
    public void highlightLine_FileExists_ShoulHighlightCyan()
    {
        try
        {
            var dir = Directory.getDirectory();
            dir.setCurrentDir(dir.getFile("comp0010").getCanonicalPath());

            highlighter.highlightLine("cat pom.xml");
            assertEquals("fill", out.toString().strip());

            dir.setCurrentDir(dir.getFile("..").getCanonicalPath());
        }
        catch (Exception e)
        {
            throw new TestErrorException();
        }
    }

     */




    @Test
    public void highlightLine_PeriodNotFile_ShouldNotBeFile()
    {
        highlighter.highlightLine("'Hi. How are you'");
        assertEquals("\u001B[31m'Hi. \u001B[31mHow \u001B[31mare \u001B[31myou'", out.toString().strip());
    }

    @Test
    public void highlightLine_NotCommonFileExtension_ShouldNotBeFile()
    {
        highlighter.highlightLine("coolfile.qwewqas");
        assertEquals("\u001B[31mcoolfile.qwewqas", out.toString().strip());
    }

    @Test
    public void highlightLine_BQNotCommonFileExtension_ShouldNotBeFile()
    {
        highlighter.highlightLine("'coolfile.qwewqas'");
        assertEquals("\u001B[31m'coolfile.qwewqas'", out.toString().strip());
    }

    @Test
    public void highlightLine_NotAFileExtension_ShouldNotBeFile()
    {
        highlighter.highlightLine("cat coolfile.qwewqas");
        assertEquals("\u001B[32mcat \u001B[39mcoolfile.qwewqas", out.toString().strip());
    }

    @Test
    public void getApps_FileError_ShouldStillWork()
    {
        new File(APPS_DIR).delete();
        assertEquals("alias", highlighter.getApps()[0]);
    }
}
