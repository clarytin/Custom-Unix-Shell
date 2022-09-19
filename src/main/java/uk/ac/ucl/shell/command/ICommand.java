package uk.ac.ucl.shell.command;

import java.util.List;

/**
 * Interface for commands that accept the {@link CommandVisitor}
 */
public interface ICommand
{
    /**
     * Calls the visitor on the invoking ICommand to evaluate its stored command
     *
     * @param visitor  Visitor class for evaluating ICommand classes
     * @return         output from evaluating the command
     */
    List<String> evaluate(ICommandVisitor visitor);
}