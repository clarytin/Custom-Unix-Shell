package uk.ac.ucl.shell.parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ucl.shell.ITest;
import uk.ac.ucl.shell.ShellGrammarLexer;
import uk.ac.ucl.shell.ShellGrammarParser;
import uk.ac.ucl.shell.command.ICommand;
import uk.ac.ucl.shell.command.commands.Call;
import uk.ac.ucl.shell.command.commands.Seq;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

public class ParserTest extends ITest
{
    private Parser parser;

    @Before
    public void init()
    {
        parser = new Parser();
    }

    @Test
    public void parseCmd_MultipleSeq_ShouldWork()
    {
        try
        {
            var cmd = parser.parseCommand("echo hi ; cat bye.txt ; find -name p.txt ; echo lol ; echo yo");
            assertThat(cmd, instanceOf(Seq.class));
        }
        catch(Exception e)
        {
            fail();
        }
    }

    @Test
    public void parseCmd_MultiplePipe_ShouldWork()
    {
        try
        {
            parser.parseCommand("echo hi | cat bye.txt | find -name p.txt | echo lol");
        }
        catch (Exception e)
        {
            fail();
        }
    }

    @Test(expected = AssertionError.class)
    public void parseCmd_EmptyCmd_ShouldThrowError()
    {
        CharStream parserInput = CharStreams.fromString("w");
        ShellGrammarLexer lexer = new ShellGrammarLexer(parserInput);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        ShellGrammarParser parser = new ShellGrammarParser(tokenStream);

        new ShellListener(parser).getCommand();
    }

    @Test
    public void parseCall_ParsesArgs()
    {
        var call = new Call("echo j");
        parser.parseCall(call);
        assertEquals(2, call.getArgs().size());
    }

    @Test
    public void parseCall_TrailingSpaces_Parses()
    {
        var call = new Call("  echo j   ");
        parser.parseCall(call);
        assertEquals(2, call.getArgs().size());
    }

    @Test
    public void parsingCall_InRedirect_ShouldAddIn()
    {
        var call = new Call("cat < loop.txt");
        parser.parseCall(call);
        assertEquals("loop.txt", call.getInRedirect());
    }

    @Test
    public void parsingCall_OutRedirect_ShouldAddOut()
    {
        var call = new Call("cat > lollipop.txt");
        parser.parseCall(call);
        assertEquals("lollipop.txt", call.getOutRedirect());
    }

    @Test
    public void parsingCall_RedirectFirst_ShouldAddOut()
    {
        var call = new Call("< loop.txt > lollipop.txt cat");
        parser.parseCall(call);
        assertEquals("loop.txt", call.getInRedirect());
        assertEquals("lollipop.txt", call.getOutRedirect());
    }

    @Test
    public void parsingCall_RedirectNoArg_ShouldFail()
    {
        var call = new Call("call > \n\r\n");
        parser.parseCall(call);
        assertEquals("", call.getOutRedirect());
    }

    @Test
    public void parsingCall_MultipleArgs_ShouldAddAll()
    {
        var call = new Call("echo afdsaf مرحبا \"asfd\" > lollipop.txt");
        parser.parseCall(call);
        var args = new ArrayList<>(Arrays.asList("echo", "afdsaf", "مرحبا", "\"asfd\""));
        assertEquals(args, call.getArgs());
    }

    @Test
    public void parsingCall_ArgAndRedirect_ShouldAddAll()
    {
        var call = new Call("echo afdsaf  > lollipop.txt");
        parser.parseCall(call);
        var args = new ArrayList<>(Arrays.asList("echo", "afdsaf"));
        assertEquals(args, call.getArgs());
        assertEquals("lollipop.txt", call.getOutRedirect());
    }

    @Test
    public void parsingCall_Esc_ShouldWork()
    {
        try
        {
            var call = new Call("\n");
            parser.parseCall(call);
        }
        catch(Exception e)
        {
            fail();
        }
    }

    @Test
    public void parseCommand_ResultsInCommand()
    {
        assertThat(new Parser().parseCommand("echo j"), instanceOf(ICommand.class));
    }

    @Test
    public void parsingCommand_Esc_ShouldWork()
    {
        try
        {
            new Parser().parseCommand("\n");
        }
        catch(Exception e)
        {
            fail();
        }
    }

    @Test
    public void parsingArgs_Esc_ShouldWork()
    {
        try
        {
            new Parser().parseArg("\n");
        }
        catch(Exception e)
        {
            fail();
        }
    }
}
