package uk.ac.ucl.shell.parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ucl.shell.CallGrammarLexer;
import uk.ac.ucl.shell.CallGrammarParser;
import uk.ac.ucl.shell.ITest;
import uk.ac.ucl.shell.command.commands.Call;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CallListenerTest extends ITest
{
    private ParseTreeWalker walker;

    @Before
    public void initWalker()
    {
        walker = new ParseTreeWalker();
    }

    @Test(expected = RuntimeException.class)
    public void parsingRedirect_NoRedirect_ShouldThrowError()
    {
        var parser = init("jsd");
        ParseTree tree = parser.redirection();
        CallListener listener = new CallListener(parser, new Call("asda"));
        walker.walk(listener, tree);
    }

    @Test(expected = RuntimeException.class)
    public void parsingArg_Contents_Invalid_ShouldThrowError()
    {
        var parser = init("'\n;`");
        ParseTree tree = parser.arg_contents();
        CallListener listener = new CallListener(parser, new Call(""));
        walker.walk(listener, tree);
    }

    @Test
    public void parsingArg_Contents_EOF_ShouldNotThrowError()
    {
        try
        {
            var parser = init("a");
            ParseTree tree = parser.arg_contents();
            CallListener listener = new CallListener(parser, new Call(""));
            walker.walk(listener, tree);
        }
        catch(Exception e)
        {
            fail();
        }
    }

    @Test(expected = StringIndexOutOfBoundsException.class)
    public void parsingAtom_Invalid_ShouldThrowError()
    {
        var parser = init("'\n;`");
        ParseTree tree = parser.atom();
        CallListener listener = new CallListener(parser, new Call(""));
        walker.walk(listener, tree);
    }

    @Test
    public void parsingCall_TrailingWhitespace_ShouldNotThrowError()
    {
        try
        {
            var parser = init("    echo hi    ");
            ParseTree tree = parser.call();
            CallListener listener = new CallListener(parser, new Call(""));
            walker.walk(listener, tree);
        }
        catch(Exception e)
        {
            fail();
        }
    }

    @Test
    public void parsingOptionalAtom_Invalid_ShouldNotThrowError()
    {
        try
        {
            var parser = init("'\n;`");
            ParseTree tree = parser.optional_atom();
            CallListener listener = new CallListener(parser, new Call(""));
            walker.walk(listener, tree);
        }
        catch (Exception e)
        {
            fail();
        }
    }

    @Test
    public void parsingOptionalAtom_Multiple_ShouldNotThrowError()
    {
        try
        {
            var parser = init("afasdf \n  <   asdfa   >  \r asdfadf qweiqoeao sdfa 'ADS' wq");
            ParseTree tree = parser.optional_atom();
            CallListener listener = new CallListener(parser, new Call(""));
            walker.walk(listener, tree);
        }
        catch(Exception e)
        {
            fail();
        }
    }

    @Test(expected = StringIndexOutOfBoundsException.class)
    public void parsingArguments_Invalid_ShouldNotAddArgs()
    {
        var parser = init("'\n;`");
        var call = new Call("");
        ParseTree tree = parser.arguments();
        CallListener listener = new CallListener(parser, call);
        walker.walk(listener, tree);
    }


    private CallGrammarParser init(String arg)
    {
        CharStream parserInput = CharStreams.fromString(arg);
        CallGrammarLexer lexer = new CallGrammarLexer(parserInput);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        return new CallGrammarParser(tokenStream);
    }
}