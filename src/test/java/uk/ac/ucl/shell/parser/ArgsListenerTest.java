package uk.ac.ucl.shell.parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ucl.shell.ArgsGrammarLexer;
import uk.ac.ucl.shell.ArgsGrammarParser;
import uk.ac.ucl.shell.ITest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

public class ArgsListenerTest extends ITest
{
    private ParseTreeWalker walker;
    private List<String> splits;

    @Before
    public void initWalker()
    {
        walker = new ParseTreeWalker();
        splits = new ArrayList<>();
    }

    @Test(expected = RuntimeException.class)
    public void parsingArgs_Invalid_ShouldThrowError()
    {
        var parser = init("'\n;`");
        ParseTree tree = parser.arg_contents();
        ArgsListener listener = new ArgsListener(parser, splits);
        walker.walk(listener, tree);
    }

    @Test
    public void parsingArgs_Empty_ShouldNotThrowError()
    {
        try
        {
            var parser = init("");
            ParseTree tree = parser.arg_contents();
            ArgsListener listener = new ArgsListener(parser, splits);
            walker.walk(listener, tree);
        }
        catch(Exception e)
        {
            fail();
        }

    }

    @Test
    public void parsingArgs_EOF_ShouldNotThrowError()
    {
        try
        {
            var parser = init("a");
            ParseTree tree = parser.arg_contents();
            ArgsListener listener = new ArgsListener(parser, splits);
            walker.walk(listener, tree);
        }
        catch(Exception e)
        {
            fail();
        }
    }

    @Test
    public void parsingArgs_Single_ShouldNotThrowError()
    {
        try
        {
            var parser = init("'sdfaf'");
            ParseTree tree = parser.argument();
            ArgsListener listener = new ArgsListener(parser, splits);
            walker.walk(listener, tree);
        }
        catch(Exception e)
        {
            fail();
        }
    }

    @Test
    public void parsingArgs_Double_ShouldNotThrowError()
    {
        try
        {
            var parser = init("\"sdfaf\"");
            ParseTree tree = parser.argument();
            ArgsListener listener = new ArgsListener(parser, splits);
            walker.walk(listener, tree);
        }
        catch(Exception e)
        {
            fail();
        }
    }

    @Test
    public void parsingArgs_Back_ShouldNotThrowError()
    {
        try
        {
            var parser = init("`sdfaf`");
            ParseTree tree = parser.argument();
            ArgsListener listener = new ArgsListener(parser, splits);
            walker.walk(listener, tree);
        }
        catch(Exception e)
        {
            fail();
        }
    }

    @Test
    public void parsingArgs_Mismatched_ShouldNotThrowError()
    {
        try
        {
            var parser = init("'sfsdf");
            ParseTree tree = parser.argument();
            ArgsListener listener = new ArgsListener(parser, splits);
            walker.walk(listener, tree);
        }
        catch(Exception e)
        {
            fail();
        }
    }

    private ArgsGrammarParser init(String arg)
    {
        CharStream parserInput = CharStreams.fromString(arg);
        ArgsGrammarLexer lexer = new ArgsGrammarLexer(parserInput);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        return new ArgsGrammarParser(tokenStream);
    }
}
