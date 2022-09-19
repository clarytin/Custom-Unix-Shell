package uk.ac.ucl.shell.command;

import uk.ac.ucl.shell.command.commands.Call;
import uk.ac.ucl.shell.command.commands.Pipe;
import uk.ac.ucl.shell.command.commands.Seq;

import java.util.List;

/**
 * Visitor interface for evaluating Call, Pipe, and Seq commands
 */
public interface ICommandVisitor
{
    /**
     * Evaluates a Call command
     *
     * @param call the Call command to be evaluated
     * @return     the output from evaluating the Call command
     */
    List<String> visit(Call call);

    /**
     * Evaluates a Pipe command
     *
     * @param pipe the Pipe command to be evaluated
     * @return     the output from evaluating the Pipe command
     */
    List<String> visit(Pipe pipe);

    /**
     * Evaluates a Seq command
     *
     * @param seq  the Seq command to be evaluated
     * @return     the output from evaluating the Seq command
     */
    List<String> visit(Seq seq);
}