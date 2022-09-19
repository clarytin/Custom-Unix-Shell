package uk.ac.ucl.shell.parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import uk.ac.ucl.shell.CallGrammarLexer;
import uk.ac.ucl.shell.CallGrammarParser;
import uk.ac.ucl.shell.ShellGrammarLexer;
import uk.ac.ucl.shell.ShellGrammarParser;
import uk.ac.ucl.shell.ArgsGrammarLexer;
import uk.ac.ucl.shell.ArgsGrammarParser;
import uk.ac.ucl.shell.command.ICommand;
import uk.ac.ucl.shell.command.commands.Call;

import java.util.ArrayList;
import java.util.List;

/**
 * Uses <a href="https://www.antlr.org/">Antlr4</a> to create parse trees
 * and build shell-specific data structures by walking these trees
 *
 * See <a href="https://github.com/antlr/antlr4/blob/master/doc/listeners.md">Parse Tree Listener</a> for how this process works
 */
public class Parser
{
    /**
     * Builds a single ICommand instance comprised of Call, Seq, and Pipe commands
     *
     * @param cmdLine the command line input to be parsed
     * @return        the ICommand object built from the string
     */
    public ICommand parseCommand(String cmdLine)
    {
        CharStream parserInput = CharStreams.fromString(cmdLine);
        ShellGrammarLexer lexer = new ShellGrammarLexer(parserInput);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        ShellGrammarParser parser = new ShellGrammarParser(tokenStream);

        ParseTree tree = parser.parse();
        ParseTreeWalker walker = new ParseTreeWalker();
        ShellListener listener = new ShellListener(parser);
        walker.walk(listener, tree);

        return listener.getCommand();
    }

    /**
     * Parses command string for argument and redirection data and assigns this information in the Call object
     *
     * @param call  the Call object containing the command line to parsed
     */
    public void parseCall(Call call)
    {
        CharStream parserInput = CharStreams.fromString(call.getCommandString().trim());
        CallGrammarLexer lexer = new CallGrammarLexer(parserInput);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        CallGrammarParser parser = new CallGrammarParser(tokenStream);

        ParseTree tree = parser.call();
        ParseTreeWalker walker = new ParseTreeWalker();
        CallListener listener = new CallListener(parser, call);
        walker.walk(listener, tree);
    }

    /**
     * Splits an argument into quoted and unquoted sections
     *
     * @param arg  the argument to be parsed
     * @return     the generated argument sections
     */
    public List<String> parseArg(String arg)
    {
        CharStream parserInput = CharStreams.fromString(arg);
        ArgsGrammarLexer lexer = new ArgsGrammarLexer(parserInput);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        ArgsGrammarParser parser = new ArgsGrammarParser(tokenStream);

        ParseTree tree = parser.argument();
        ParseTreeWalker walker = new ParseTreeWalker();
        var splits = new ArrayList<String>();
        ArgsListener listener = new ArgsListener(parser, splits);
        walker.walk(listener, tree);
        return splits;
    }
}
