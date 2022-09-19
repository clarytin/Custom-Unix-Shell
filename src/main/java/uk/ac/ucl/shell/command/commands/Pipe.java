package uk.ac.ucl.shell.command.commands;

import uk.ac.ucl.shell.command.ICommand;
import uk.ac.ucl.shell.command.ICommandVisitor;

import java.util.List;

/**
 * Command that connects two commands via a <a href="https://www.gnu.org/software/bash/manual/html_node/Pipelines.html">Pipeline</a>
 *
 * <p>
 * In a Pipe command, the output of the left command is connected to the input of the right command
 * </p>
 */
public class Pipe implements ICommand
{
    ICommand left;
    Call right;

    /**
     * Class constructor
     */
    public Pipe()
    {
        this.left = null;
        this.right = null;
    }

    /**
     * Gets command whose output will be fed into the input of another command
     *
     * @return the left command of this Pipe
     */
    public ICommand getLeft()
    {
        return this.left;
    }

    /**
     * Gets command whose input appended with the output of another command
     *
     * @return the Call command to be executed with the modified input
     */
    public Call getRight()
    {
        return this.right;
    }

    /**
     * Allows the {@link uk.ac.ucl.shell.parser.ShellListener} to add components to this command
     *
     * @param cmd the left side of this pipeline
     */
    public void addLeft(ICommand cmd)
    {
        if (this.left == null)
            this.left = cmd;
    }

    /**
     * Allows the {@link uk.ac.ucl.shell.parser.ShellListener} to add components to this command
     *
     * @param call the call on the right side of this pipeline
     */
    public void addRight(Call call)
    {
        if (this.right == null)
            this.right = call;
    }

    public List<String> evaluate(ICommandVisitor visitor)
    {
        return visitor.visit(this);
    }
}