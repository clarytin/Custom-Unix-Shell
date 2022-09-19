package uk.ac.ucl.shell;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.internal.CLibrary;
import uk.ac.ucl.shell.exception.IllegalOptionException;
import uk.ac.ucl.shell.exception.InvalidNumOfArgsException;
import uk.ac.ucl.shell.util.Constants;
import uk.ac.ucl.shell.util.RawModeUtil;
import uk.ac.ucl.shell.util.SyntaxHighlighterUtil;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;
import static uk.ac.ucl.shell.util.Constants.SHELL_NAME;

/**
 * Passes console input to the Presenter and handles all text shown to the User
 *
 * In the implemented MVP design pattern, this would likely be
 * classified as a Passive View.
 */
public class View
{
    private final SyntaxHighlighterUtil highlighter;
    private Presenter presenter;
    private boolean inTerminal;
    private Boolean rawMode;

    /**
     * Constructs a {@code View} instance
     */
    public View()
    {
        this.presenter = new Presenter(this);
        this.highlighter = new SyntaxHighlighterUtil();
        this.inTerminal = CLibrary.isatty(CLibrary.STDOUT_FILENO) == 1;
    }

    /**
     * Runs the shell in either interactive or non-interactive mode
     *
     * @param args command-line arguments passed to the Shell upon start-up
     */
    public void start(List<String> args)
    {
        if (args.isEmpty())
            interactive();
        else
        {
            try
            {
                nonInteractive(args);
            }
            catch (Exception e)
            {
                highlighter.printError(SHELL_NAME + ": " + e.getMessage());
            }
        }
    }

    /**
     * The default printing method. Can be used to print the shell prompt and
     * other general information.
     *
     * @param in the string to be printed
     */
    public void print(String in)
    {
        if (inTerminal)
            highlighter.printDefault(in);
        else
            System.out.print(in);
    }

    /**
     * The default printing method for command line input. This
     * may optionally perform syntax highlighting on the input
     *
     * @param cmdLine the command line to be highlighted
     *                may contain flags (-f, --name) and filenames
     */
    public void printCmdLine(String cmdLine)
    {
        if (cmdLine.length() != 0)
        {
            if (inTerminal)
                highlighter.highlightLine(cmdLine);
            else
                System.out.print(cmdLine);
        }
    }

    /**
     * The printing method for command line input from a console
     * in raw mode. This uses cursor position to only highlight the
     * tokens that may have changed since the previous printing
     *
     * @param cmdLine the command line to be highlighted
     *                may contain flags (-f, --name) and filenames
     * @param cursor  the current position of the cursor
     */
    public void printCmdLine(String cmdLine, int cursor)
    {
        assert rawMode;
        printCmdLine(cmdLine);
        for(int c = 0; c <= -cursor; c++)
            moveCursorLeft();
    }

    /**
     * The default printing method for the output of a command. This
     * prints each output string in a new line. If the output stream
     * is not connected to a terminal, text formatting is not applied
     *
     * @param results the command outputs to be printed
     */
    public void printResult(List<String> results)
    {

        if (inTerminal)
        {
            for (String result : results)
                highlighter.printResult(result);
        }
        else
        {
            for (String result : results)
                System.out.println(result);
        }
    }

    /**
     * Prints an ANSI character to move the cursor one space to the left
     */
    public void moveCursorLeft()
    {
        System.out.print(ansi().cursorLeft(1));
    }

    /**
     * Prints an ANSI character to move the cursor one space to the right
     */
    public void moveCursorRight()
    {
        System.out.print(ansi().cursorRight(1));
    }

    /**
     * Erases the line above the cursor
     * Can be used after the return key is pressed to erase an input line
     */
    public void clearPrevLine()
    {
        System.out.print(ansi()
                .eraseLine(Ansi.Erase.BACKWARD)
                .cursorUpLine()
                .cursorToColumn(0));
    }

    /**
     * Erases the current line as marked by the cursor
     */
    public void clearCurrLine()
    {
        System.out.print(ansi()
                .eraseLine(Ansi.Erase.BACKWARD)
                .cursorToColumn(0));
    }

    /**
     * Erases character to the left of the cursor, moving the cursor left by one space
     */
    public void backspace()
    {
        System.out.print(ansi()
        .cursorLeft(1)
        .eraseLine(Ansi.Erase.FORWARD));
    }

    /**
     * Returns the variable that keeps track of whether the terminal is in raw mode
     * Can be used for assertions in methods that require a specific console mode
     *
     * @return true if View is set to run for a console in raw mode
     *         false if not
     *         null if uninitialised, i.e. shell is running in non-interactive mode
     */
    public Boolean getRawMode()
    {
        return this.rawMode;
    }

    /**
     * Stores the results of setting the console to raw mode
     *
     * @param rawMode true if console was successfully set to raw mode, false if not
     */
    public void setRawMode(Boolean rawMode)
    {
        this.rawMode = rawMode;
    }

    /**
     * For testing, manually sets whether to run shell as if within a terminal
     * @param inTerminal true to run as if within a terminal, false otherwise
     */
    protected void setInTerminal(boolean inTerminal)
    {
        this.inTerminal = inTerminal;
    }

    /**
     * For testing View with a custom presenter
     * @param p  the presenter to be used in this test
     */
    protected void setPresenter(Presenter p)
    {
        this.presenter = p;
    }

    private void nonInteractive(List<String> args)
    {
        if (args.size() != 2)
            throw new InvalidNumOfArgsException();

        if (!args.get(0).equals("-c"))
            throw new IllegalOptionException(args.get(0));

        presenter.evaluate(args.get(1));
    }

    protected void interactive()
    {
        presenter.startInteractiveMode();
        assert rawMode != null;

        while (true)
        {
            presenter.beginCommand();
            processCommand();
            presenter.endCommand();
        }
    }

    private void processCommand()
    {
        if (rawMode)
            runRawMode();
        else
        {
            Scanner scanner = new Scanner(System.in);
            runCookedMode(scanner);
        }
    }

    private void runRawMode()
    {
        try
        {
            int i;
            while ((i = RawModeUtil.read(true)) != Constants.RETURN_KEY)
                presenter.acceptRawInput(i);

            System.out.print('\n');
            presenter.evaluateCmdLine();
        }
        catch (RuntimeException e)
        {
            highlighter.printError(SHELL_NAME + ": " + e.getMessage());
        }
        catch (IOException e)
        {
            highlighter.printError(SHELL_NAME + ": Input could not be read, please try again.");
        }
    }

    private void runCookedMode(Scanner scanner)
    {
        try
        {
            String cmdLine = scanner.nextLine();
            presenter.acceptInput(cmdLine);
            presenter.evaluateCmdLine();
        }
        catch (Exception e)
        {
            highlighter.printError(SHELL_NAME + ": " + e.getMessage());
        }
    }
}
