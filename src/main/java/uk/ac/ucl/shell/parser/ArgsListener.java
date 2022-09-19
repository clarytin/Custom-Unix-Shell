package uk.ac.ucl.shell.parser;

import uk.ac.ucl.shell.ArgsGrammarBaseListener;
import uk.ac.ucl.shell.ArgsGrammarParser;

import java.util.List;

/**
 * Instance of <a href="https://www.antlr.org/api/Java/org/antlr/v4/runtime/tree/ParseTreeListener.html"> ParseTreeListener </a>
 * that splits arguments into quoted and unquoted sections
 */
public class ArgsListener extends ArgsGrammarBaseListener
{
    ArgsGrammarParser parser;
    private final List<String> sections;

    /**
     * Constructs an {@link ArgsListener} with an empty list and the parser for this string
     *
     * @param parser   the instance of {@link org.antlr.v4.runtime.Parser} associated with this argument string
     * @param sections the split sections of the argument, in the order that they appear within the string
     */
    public ArgsListener(ArgsGrammarParser parser, List<String> sections)
    {
        this.parser = parser;
        this.sections = sections;
    }

    /**
     * Appends section of the argument to our list
     *
     * @param ctx the {@link org.antlr.v4.runtime.RuleContext} for argument contents
     */
    @Override
    public void enterArg_contents(ArgsGrammarParser.Arg_contentsContext ctx)
    {
        sections.add(ctx.getText());
    }
}
