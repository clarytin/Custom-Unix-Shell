package uk.ac.ucl.shell.parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ucl.shell.ITest;
import uk.ac.ucl.shell.ShellGrammarLexer;
import uk.ac.ucl.shell.ShellGrammarParser;
import uk.ac.ucl.shell.command.commands.Pipe;

import static org.junit.Assert.assertNotNull;


public class ShellListenerTest extends ITest
{
    private ShellGrammarParser parser;

    @Before
    public void init()
    {
        CharStream parserInput = CharStreams.fromString("echo hi ; echo bye | cat poop.txt");
        ShellGrammarLexer lexer = new ShellGrammarLexer(parserInput);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);

        this.parser = new ShellGrammarParser(tokenStream);
    }

    @Test(expected = IllegalStateException.class)
    public void invalidCommandType_ShouldThrowException()
    {
        var listener = new TestShellListener(parser);
        listener.addToStack("FAKE_CMD");
        listener.addToStack("FAKE_CMD");
        listener.exitCall(new ShellGrammarParser.CallContext(new ParserRuleContext(), 6));
    }

    @Test(expected = AssertionError.class)
    public void getCommand_Null_ShouldThrowError()
    {
        var listener = new TestShellListener(parser);
        listener.getCommand();
    }

    @Test
    public void getCommand_Valid_ShouldReturnCommand()
    {
        var tree = parser.parse();
        var walker = new ParseTreeWalker();
        var listener = new ShellListener(parser);
        walker.walk(listener, tree);

        var command = listener.getCommand();
        assertNotNull(command);
    }

    private static class TestShellListener extends ShellListener
    {
        private TestShellListener(ShellGrammarParser parser) { super(parser); }
        protected void addToStack(String type) { this.stack.add(new Cmd(type, new Pipe())); }
    }
}
