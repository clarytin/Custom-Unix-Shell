package uk.ac.ucl.shell;

import org.junit.Test;
import org.junit.Before;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TerminalModelTest extends ITest
{
    private TerminalModel termModel;

    @Before
    public void init()
    {
        termModel = TerminalModel.getTerminal();
        termModel.resetCursor();
        termModel.resetHistoryIndex();
        termModel.setCmdLine("");
        termModel.setHistory(new ArrayList<>(Arrays.asList("a", "b")));
    }

    @Test
    public void getTerminal_ReturnsTerminal()
    {
        assertThat(termModel, instanceOf(TerminalModel.class));
    }

    @Test
    public void getPrompt_ReturnsCurrDirPrompt()
    {
        String prompt = termModel.getPrompt();
        assertEquals(prompt.strip(), getOriginalPath() + ">");
    }

    @Test
    public void resetCursor_SetsCursorToZero()
    {
        assertEquals(0, termModel.getCursor());
    }

    @Test
    public void setCmdLine_ChangesCmdLine()
    {
        termModel.setCmdLine("hello");
        assertEquals("hello", termModel.getCmdLine().strip());
    }

    @Test
    public void addToCommandLine_AppendsToCommandLine()
    {
        termModel.addToCommandLine('h');
        assertEquals("h", termModel.getCmdLine().strip());
    }

    @Test
    public void addToCommandLine_Cursor_AddsAtPosition()
    {
        termModel.setCursor(-5);
        termModel.setCmdLine("echo rainbow");
        termModel.addToCommandLine('h');
        assertEquals("echo rahinbow", termModel.getCmdLine().strip());
    }

    @Test
    public void getHistory_ReturnsCommandHistory()
    {
        List<String> historyList = termModel.getHistory();
        assertEquals("a,b", String.join(",", historyList));
    }

    @Test
    public void addNewHistoryEntry_AppendsHistoryEntry()
    {
        List<String> historyList = termModel.getHistory();
        termModel.addNewHistoryEntry("c");
        assertEquals("c,a,b", String.join(",", historyList));
    }

    @Test
    public void getPrevHistory_ReturnsNextOldestHistory()
    {
        assertEquals("b", termModel.getPrevHistoryEntry());
    }

    @Test
    public void getNextHistory_InLastestEntry_ReturnsNull()
    {
        assertEquals(null, termModel.getNextHistoryEntry());
    }

    @Test
    public void updateHistory_ChangesCurrentHistoryEntry()
    {
        termModel.updateCurrHistoryEntry("c");
        List<String> historyList = termModel.getHistory();
        assertEquals("c,b", String.join(",", historyList));
    }

    @Test
    public void deleteCurrHistory_RemovesCurrentEntry()
    {
        termModel.deleteCurrHistoryEntry();
        List<String> historyList = termModel.getHistory();
        assertEquals("b", String.join(",", historyList));
    }

    @Test
    public void getPrevEntry_LastEntry_ReturnsNull()
    {
        termModel.getPrevHistoryEntry();
        termModel.getPrevHistoryEntry();
        termModel.getPrevHistoryEntry();

        assertEquals(null, termModel.getPrevHistoryEntry());
    }

    @Test
    public void moveCursor_CursorZero_MovesByValue()
    {
        termModel.moveCursor(-1);
        assertEquals(-1, termModel.getCursor());
    }

    @Test
    public void moveCursor_CursorValid_MovesByValue()
    {
        termModel.setCmdLine("echo hello");
        termModel.setCursor(-5);
        termModel.moveCursor(2);
        assertEquals(-3, termModel.getCursor());
    }

    @Test
    public void moveCursor_Zero_ChangesNothing()
    {
        termModel.setCmdLine("echo hello");
        termModel.setCursor(-5);
        termModel.moveCursor(0);
        assertEquals(-5, termModel.getCursor());
    }

    @Test(expected = AssertionError.class)
    public void moveCursor_PositiveInt_Exception()
    {
        termModel.setCmdLine("echo hello");
        termModel.setCursor(1);
        termModel.moveCursor(1);
    }

    @Test(expected = AssertionError.class)
    public void moveCursor_NegativeInt_Exception()
    {
        termModel.setCmdLine("echo hello");
        termModel.setCursor(-123412);
        termModel.moveCursor(1);
    }
}
