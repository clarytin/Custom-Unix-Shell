package uk.ac.ucl.shell;

import uk.ac.ucl.shell.command.ICommand;
import uk.ac.ucl.shell.command.CommandVisitor;
import uk.ac.ucl.shell.exception.FileReadException;
import uk.ac.ucl.shell.parser.Parser;
import uk.ac.ucl.shell.util.Directory;
import uk.ac.ucl.shell.util.RawModeUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static uk.ac.ucl.shell.util.Constants.*;

/**
 * Processes command-line input received from the View and updates
 * the TerminalModel and View accordingly.
 *
 * <p>
 * If in raw mode, the Presenter also updates the View to display
 * the key pressed, since the shell has disabled automatic echoing of
 * characters as they're typed.
 *
 * When the return key is pressed, this class calls the TerminalModel
 * to run applications and updates the View with the output.
 * </p>
 */
public class Presenter
{
    private final View view;
    private final TerminalModel terminal;

    /**
     * Constructs a Presenter with the View that created this class
     *
     * @param view the View that invoked this Presenter
     */
    public Presenter(View view)
    {
        this.view = view;
        this.terminal = TerminalModel.getTerminal();
    }

    /**
     * Performs the initial steps to run COMP0010 shell in interactive mode
     */
    public void startInteractiveMode()
    {
        try
        {
            terminal.setHistory(Directory.getDirectory().getAllLines(HISTORY_FILE));
        }
        catch (FileReadException e)
        {
            terminal.setHistory(new ArrayList<>());
        }

        if (view.getRawMode() != null)
            return;

        try
        {
            RawModeUtil.setConsoleToRaw();
            view.setRawMode(true);
        }
        catch (IOException e)
        {
            view.setRawMode(false);
            view.print("Console could not be set to raw mode. Normal mode will be used.");
        }
    }

    /**
     * Configures the TerminalModel and View to accept a new command
     */
    public void beginCommand()
    {
        terminal.setCmdLine("");
        terminal.resetHistoryIndex();
        terminal.resetCursor();
        terminal.addNewHistoryEntry(" ");
        view.print(terminal.getPrompt());
    }

    /**
     * Finishes processing a command by saving the command line in history
     */
    public void endCommand()
    {
        String cmdLine = terminal.getCmdLine();
        terminal.updateCurrHistoryEntry(cmdLine);

        if (cmdLine.trim().equals(""))
            terminal.deleteCurrHistoryEntry();
    }

    /**
     * Stores and displays a command line input
     *
     * @param cmdLine the supplied command line in its entirety
     */
    public void acceptInput(String cmdLine)
    {
        assert !view.getRawMode();
        terminal.setCmdLine(cmdLine);
        view.print(terminal.getPrompt());
        view.clearPrevLine();
        if (!cmdLine.equals(""))
        {
            view.printCmdLine(cmdLine);
            view.print("\n");
        }
    }

    /**
     * Stores and displays a character input, such as input received from raw mode consoles
     *
     * @param character the latest character entered by the user
     */
    public void acceptRawInput(int character)
    {
        if (!view.getRawMode())
            throw new IllegalStateException();
        if (handleSpecial(character))
            return;

        terminal.addToCommandLine((char) character);
        replaceCmdLine(terminal.getCmdLine());
        view.moveCursorRight();
    }

    /**
     * Performs the command supplied by the user
     * The final output, if any, is printed to STDOUT
     */
    public void evaluateCmdLine()
    {
        if (!terminal.getCmdLine().equals(""))
            evaluate(terminal.getCmdLine());
    }

    /**
     * Performs the command represented by the String argument
     * and prints its final output to STDOUT
     *
     * @param command the command to be executed
     */
    public void evaluate(String command)
    {
        var output = getOutput(command);
        view.printResult(output);
    }

    /**
     * Parses and executes the supplied command.
     * See {@link Parser} and {@link CommandVisitor} for the relevant logic
     *
     * @param command the command to be executed
     * @return        the output after executing the command
     */
    public List<String> getOutput(String command)
    {
        ICommand cmd = new Parser().parseCommand(command);
        CommandVisitor visitor = new CommandVisitor(this);
        return cmd.evaluate(visitor);
    }

    private boolean handleSpecial(int character)
    {
        if (character == ESC_KEY)
            return true;

        else if (character == BACKSPACE)
        {
            handleBackspace();
            return true;
        }
        else if (character == SPACEBAR)
        {
            view.print(" ");
            terminal.addToCommandLine(' ');
            return true;
        }
        else if (character >= UP)    // UP is defined with the lowest value among the arrow keys
        {
            handleArrows(character);
            return true;
        }
        return false;
    }

    private void handleBackspace()
    {
        String cmdLine = terminal.getCmdLine();
        if (cmdLine.length() == 0)
            return;

        var index = cmdLine.length() + terminal.getCursor();
        terminal.setCmdLine(cmdLine.substring(0, index - 1) + cmdLine.substring(index));

        view.clearCurrLine();
        view.print(terminal.getPrompt());
        view.printCmdLine(terminal.getCmdLine(), terminal.getCursor());
        if (terminal.getCmdLine().endsWith(" ") || terminal.getCmdLine().equals(""))
            view.moveCursorRight();
    }

    private void handleArrows(int character)
    {
        if (character == RIGHT)
        {
            if (terminal.getCursor() < 0)
            {
                view.moveCursorRight();
                terminal.moveCursor(1);
            }
        }
        else if (character == LEFT)
        {
            if (-terminal.getCursor() < terminal.getCmdLine().length())
            {
                view.moveCursorLeft();
                terminal.moveCursor(-1);
            }
        }
        else
            handleUpAndDown(character);
    }

    private void handleUpAndDown(int character)
    {
        String newCmdLine;
        if (character == UP)
            newCmdLine = terminal.getPrevHistoryEntry();
        else
            newCmdLine = terminal.getNextHistoryEntry();

        if (newCmdLine != null)
        {
            terminal.resetCursor();
            replaceCmdLine(newCmdLine);
            view.moveCursorRight();
        }
    }

    private void replaceCmdLine(String newCmdLine)
    {
        terminal.setCmdLine(newCmdLine);
        view.clearCurrLine();
        view.print(terminal.getPrompt());

        if (!newCmdLine.trim().equals(""))
        {
            view.printCmdLine(newCmdLine, terminal.getCursor());
            view.moveCursorLeft();
        }
    }
}