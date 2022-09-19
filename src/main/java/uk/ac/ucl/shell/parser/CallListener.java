package uk.ac.ucl.shell.parser;

import uk.ac.ucl.shell.CallGrammarBaseListener;
import uk.ac.ucl.shell.CallGrammarParser;
import uk.ac.ucl.shell.command.commands.Call;

/**
 * Instance of <a href="https://www.antlr.org/api/Java/org/antlr/v4/runtime/tree/ParseTreeListener.html"> ParseTreeListener </a>
 * that builds Call objects from a string
 */
public class CallListener extends CallGrammarBaseListener
{
    CallGrammarParser parser;
    private boolean insideRedirect;
    private final Call call;

    /**
     * Constructs a {@link CallListener} with the call object whose fields will be populated
     *
     * @param parser the instance of {@link org.antlr.v4.runtime.Parser} associated with this Call string
     * @param call   the Call object to be populated with information from the parse tree walk
     */
    public CallListener(CallGrammarParser parser, Call call)
    {
        this.parser = parser;
        this.insideRedirect = false;
        this.call = call;
    }

    /**
     * Sets inRedirect flag to <code>true</code> to parse next argument as redirection
     *
     * @param ctx the {@link org.antlr.v4.runtime.RuleContext} for redirection
     */
    @Override
    public void enterRedirection(CallGrammarParser.RedirectionContext ctx)
    {
        this.insideRedirect = true;
    }

    /**
     * Adds redirect information to the Call object and resets inRedirect flag
     *
     * @param ctx the {@link org.antlr.v4.runtime.RuleContext} for redirection
     */
    @Override
    public void exitRedirection(CallGrammarParser.RedirectionContext ctx)
    {
        String direction = ctx.getText().charAt(0) + "";
        String location = ctx.getText().substring(1).trim();
        call.addRedirect(direction, location);
        this.insideRedirect = false;
    }

    /**
     * Appends argument information to the Call object unless this will be used in a redirect
     *
     * @param ctx the {@link org.antlr.v4.runtime.RuleContext} for white-space separated arguments
     */
    @Override
    public void exitArguments(CallGrammarParser.ArgumentsContext ctx)
    {
        // if inside a redirect rule, argument will be added in exitRedirection()
        if (!this.insideRedirect)
            call.addArg(ctx.getText());
    }
}
