package uk.ac.ucl.shell;

import uk.ac.ucl.shell.util.Directory;

import java.util.List;

import static uk.ac.ucl.shell.util.Directory.getDirectory;

/**
 * Represents the internal data of the Shell
 * Provides an interface for storing and changing the command line, cursor position, and history.
 *
 * <p>
 * The history interface is made of two parts:
 * <ul>
 *   <li>History, a list of previously written commands in reverse-chronological order</li>
 *   <li>History index, a number tracking the current history item being accessed,
 *   forming the basis for methods that retrieve the next or previous entry</li>
 * </ul>
 *
 * Accessor and setter methods are supplied to manipulate the history
 * </p>
 */
public class TerminalModel
{
    private static TerminalModel instance;
    private final Directory dir;

    private String cmdLine;
    private int cursor;

    private List<String> history;
    private int historyIndex;

    protected TerminalModel()
    {
        this.dir = getDirectory();
        this.cmdLine = "";
        this.cursor = 0;
        this.historyIndex = 0;
    }

    /**
     * Singleton accessor for TerminalModel objects
     *
     * @return the TerminalModel object associated with this shell
     */
    public static TerminalModel getTerminal()
    {
        if (instance == null)
            instance = new TerminalModel();

        return instance;
    }

    /**
     * The terminal prompt is the text that appears before every command line input
     *
     * @return the terminal prompt for the current state of the shell
     */
    public String getPrompt()
    {
        return dir.getCurrentDir() + "> ";
    }

    /**
     * Gets the position of the cursor relative to the last character of input
     *
     * @return the position of the cursor relative to last character of input
     *         0 if cursor is to the right of the last character
     *         -N if cursor is N positions to the left from its position at 0
     */
    public int getCursor()
    {
        return cursor;
    }

    /**
     * Reset the cursor to sit at the end of the command line
     */
    public void resetCursor()
    {
        this.cursor = 0;
    }

    /**
     * Changes the value of the internal cursor by the specified value
     *
     * @param value the number of positions for the cursor to move
     *              a value +N represents moving N positions to the right
     *              a value -N represents moving N positions to the left
     *              a value 0 would not move the cursor
     */
    public void moveCursor(int value)
    {
        assert cursor <= 0;
        assert cursor >= -getCmdLine().length();
        this.cursor += value;
    }

    /**
     * The command line is the String input provided by the user.
     * It may contain flags such as "-f" and "--name" as well as
     * string arguments, including filenames
     *
     * @return the command line stored in the internal model
     */
    public String getCmdLine()
    {
        return cmdLine;
    }

    /**
     * Resets the value of the command line to the argument provided
     *
     * @param cmdLine the value to be set as the current command line
     */
    public void setCmdLine(String cmdLine)
    {
        this.cmdLine = cmdLine;
    }

    /**
     * Handles additional character input to the command line, such
     * as when raw mode is enabled in a terminal
     *
     * @param character the character to be added to the command line
     */
    public void addToCommandLine(char character)
    {
        if (this.cursor == 0)
            this.cmdLine += character;
        else
        {
            int position = this.cmdLine.length() + this.cursor;
            this.cmdLine = this.cmdLine.substring(0, position)
                    + character
                    + this.cmdLine.substring(position);
        }
    }

    /**
     * Retrieves the command line history
     *
     * History is stored in reverse chronological order, with the latest item
     * at position 0 and the oldest item at the last position
     *
     * @return the chronological list of command line inputs
     *         null if hist
     */
    public List<String> getHistory()
    {
        return this.history;
    }

    /**
     * Overwrites the current history variable with the argument provided
     *
     * @param history the list of command line inputs to be set as the history
     */
    public void setHistory(List<String> history)
    {
        this.history = history;
    }

    /**
     * Resets the history index used to keep track of last history entry fetched
     */
    public void resetHistoryIndex()
    {
        this.historyIndex = 0;
    }

    /**
     * Appends argument to the command line history, storing it as the newest entry
     *
     * @param cmdLine command line input to become latest entry in the history
     */
    public void addNewHistoryEntry(String cmdLine)
    {
        this.history.add(0, cmdLine);
    }

    /**
     * Retrieves the next oldest history entry relative to the last one fetched
     *
     * @return next oldest history entry relative to the item pointed to by the history index
     *         null if the last history entry fetched was the oldest one
     */
    public String getPrevHistoryEntry()
    {
        if (historyIndex == 0)
            updateCurrHistoryEntry(this.cmdLine);

        if (-historyIndex < history.size() - 1)
        {
            this.historyIndex -= 1;
            return this.history.get(-historyIndex);
        }
        return null;
    }

    /**
     * Retrieves the next-newest history entry after the last one fetched
     *
     * @return next newest history entry relative to the item pointed to by the history index
     *         null if the last history entry fetched was the latest one
     */
    public String getNextHistoryEntry()
    {
        if (historyIndex < 0)
        {
            this.historyIndex += 1;
            return this.history.get(-historyIndex);
        }
        return null;
    }

    /**
     * Replaces the current history entry with the supplied command line
     *
     * @param cmdLine the current contents of the command line
     */
    public void updateCurrHistoryEntry(String cmdLine)
    {
        if (cmdLine.equals(""))
            cmdLine = " ";
        this.history.set(0, cmdLine);
    }

    /**
     * Removes the latest entry from the history list so as not to store empty entries
     */
    public void deleteCurrHistoryEntry()
    {
        this.history.remove(0);
    }

    /**
     * Returns history index for testing classes
     *
     * @return the current history index
     */
    public int getHistoryIndex()
    {
        return this.historyIndex;
    }

    /**
     * Testing method to set cursor
     *
     * @param cursor value to set cursor
     */
    protected void setCursor(int cursor)
    {
        this.cursor = cursor;
    }
}