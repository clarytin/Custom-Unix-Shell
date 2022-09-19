package uk.ac.ucl.shell.command.commands;

import uk.ac.ucl.shell.command.ICommand;
import uk.ac.ucl.shell.command.ICommandVisitor;

import java.util.List;

/**
 * Command that consists of two commands to be evaluated in succession
 *
 * <p>
 * The left command is evaluated before the right command
 * </p>
 */
public class Seq implements ICommand
{
    ICommand left;
    ICommand right;

    /**
     *  Class constructor
     */
    public Seq()
    {
        this.left = null;
        this.right = null;
    }

    /**
     * Returns the command executed before the right command
     *
     * @return the command executed first
     */
    public ICommand getLeft()
    {
        return this.left;
    }

    /**
     * Returns the command executed after the left command
     *
     * @return the command execute last
     */
    public ICommand getRight()
    {
        return this.right;
    }

    /**
     * Allows the {@link uk.ac.ucl.shell.parser.ShellListener} to add components to this command
     *
     * @param cmd the command on the left side of this sequence
     */
    public void addLeft(ICommand cmd)
    {
        if (this.left == null)
            this.left = cmd;
    }

    /**
     * Allows the {@link uk.ac.ucl.shell.parser.ShellListener} to add components to this command
     *
     * @param cmd the command on the right side of this sequence
     */
    public void addRight(ICommand cmd)
    {
        if (this.right == null)
            this.right = cmd;
    }

    public List<String> evaluate(ICommandVisitor visitor)
    {
        return visitor.visit(this);
    }
}