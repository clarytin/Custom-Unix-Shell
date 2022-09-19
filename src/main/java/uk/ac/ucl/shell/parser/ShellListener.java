package uk.ac.ucl.shell.parser;

import uk.ac.ucl.shell.ShellGrammarBaseListener;
import uk.ac.ucl.shell.ShellGrammarParser;
import uk.ac.ucl.shell.command.ICommand;
import uk.ac.ucl.shell.command.commands.Call;
import uk.ac.ucl.shell.command.commands.Pipe;
import uk.ac.ucl.shell.command.commands.Seq;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Instance of <a href="https://www.antlr.org/api/Java/org/antlr/v4/runtime/tree/ParseTreeListener.html"> ParseTreeListener </a>
 * that builds ICommand objects
 *
 * <p>
 * Note that in this class, we differentiate between a real seq command and a dummy
 * seq with one component. This is because the Shell grammar had to be free of left-recursion,
 * resulting in the seq rule being written with an optional empty field.
 * </p>
 */
public class ShellListener extends ShellGrammarBaseListener
{
    private static final String SEQ_CMD_1 = "seq1"; // dummy seq with one component
    private static final String SEQ_CMD_2 = "seq2"; // true seq command
    private static final String PIPE_CMD = "pipe";
    private static final String CALL_CMD = "call";

    ShellGrammarParser parser;
    Deque<Cmd> stack;
    Boolean enteredRedirect;

    /**
     * Constructs a {@link ShellListener} with a Parser that has parsed the command line
     *
     * @param parser the instance of {@link org.antlr.v4.runtime.Parser} associated with the command line
     */
    public ShellListener(ShellGrammarParser parser)
    {
        this.parser = parser;
        this.stack = new ArrayDeque<>();
        this.enteredRedirect = false;
    }

    /**
     * Returns a top-level command generated from the parse tree walk
     *
     * @return command object
     */
    public ICommand getCommand()
    {
        assert this.stack.peek() != null;
        return this.stack.peek().command;
    }

    /**
     * Adds a parsed seq command to the stack, differentiating between real and dummy seq instances
     *
     * @param ctx the {@link org.antlr.v4.runtime.RuleContext} for Seq commands
     */
    @Override
    public void enterSeq(ShellGrammarParser.SeqContext ctx)
    {
        if (ctx.getChildCount() == 1)
            this.stack.add(new Cmd(SEQ_CMD_1, new Seq()));
        else
            this.stack.add(new Cmd(SEQ_CMD_2, new Seq()));
    }

    /**
     * Adds a parsed Pipe command to the stack
     *
     * @param ctx the {@link org.antlr.v4.runtime.RuleContext} for Pipe commands
     */
    @Override
    public void enterPiped(ShellGrammarParser.PipedContext ctx)
    {
        this.stack.add(new Cmd(PIPE_CMD, new Pipe()));
    }

    /**
     * Adds a parsed Call command to the stack
     *
     * @param ctx the {@link org.antlr.v4.runtime.RuleContext} for Call commands
     */
    @Override
    public void enterCall(ShellGrammarParser.CallContext ctx)
    {
        this.stack.push(new Cmd(CALL_CMD, new Call(ctx.getText())));
    }

    /**
     * Calls a function to recursively attach the Call to its parent
     *
     * @param ctx the {@link org.antlr.v4.runtime.RuleContext} for Call commands
     */
    @Override
    public void exitCall(ShellGrammarParser.CallContext ctx)
    {
        completeCommand();
    }

    // Recursive function to attach a finished command to its parent command
    // Equivalent to walking up the parse tree
    private void completeCommand()
    {
        if (stack.size() == 1)
            return;

        var curr = this.stack.pop();
        var parent = this.stack.peek();

        switch (parent.type)
        {
            case PIPE_CMD:
                completePipe(parent, curr);
                break;
            case SEQ_CMD_1:
                ((Seq) parent.command).addLeft(curr.command);
                completeCommand();
                break;
            case SEQ_CMD_2:
                completeSeq(parent, curr);
                break;
            default:
                throw new IllegalStateException("undefined command");
        }
    }

    // Build Pipe command and if complete, add to parent
    private void completePipe(Cmd parent, Cmd curr)
    {
        if (!parent.contains)
        {
            ((Pipe) parent.command).addLeft(curr.command);
            parent.contains = true;
        }
        else
        {
            ((Pipe) parent.command).addRight((Call) curr.command);
            completeCommand();
        }
    }

    // Build Seq command and if complete, add to parent
    private void completeSeq(Cmd parent, Cmd curr)
    {
        if (!parent.contains)
        {
            ((Seq) parent.command).addLeft(curr.command);
            parent.contains = true;
        } else
        {
            ((Seq) parent.command).addRight(curr.command);
            completeCommand();
        }
    }

    /**
     * Auxiliary data structure to hold command information
     *
     * type: seq, pipe, or call
     * cmd: command object
     * contains: for pipe or seq, true if command has been placed inside it
     */
    protected static class Cmd
    {
        private final String type;
        private final ICommand command;
        private boolean contains;

        public Cmd(String type, ICommand command)
        {
            this.type = type;
            this.command = command;
            this.contains = false;
        }
    }
}
