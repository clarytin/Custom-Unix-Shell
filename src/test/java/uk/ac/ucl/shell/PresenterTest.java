package uk.ac.ucl.shell;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static uk.ac.ucl.shell.util.Constants.*;

public class PresenterTest extends ITest
{
    private final int B_ASCII = 66;
    private TerminalModel terminal;
    private Presenter rawPresenter;
    private Presenter cookedPresenter;

    @Before
    public void init()
    {
        var rawModeView = new TestView(false);
        rawModeView.setRawMode(true);
        rawPresenter = new Presenter(rawModeView);

        var cookedModeView = new TestView(false);
        cookedModeView.setRawMode(false);
        cookedPresenter = new Presenter(cookedModeView);

        terminal = TerminalModel.getTerminal();
        terminal.resetCursor();
        terminal.resetHistoryIndex();
        terminal.setHistory(new ArrayList<>(Arrays.asList("echo newest command", "echo middle command", "echo oldest command")));
        terminal.setCmdLine("echo newest command");
    }

    @Test
    public void evaluateInput_Normal_PrintsCorrectOutput()
    {
        rawPresenter.evaluateCmdLine();
        assertEquals("newest command", out.toString().strip());
    }

    @Test
    public void evaluateInput_Empty_DoesNothing()
    {
        terminal.setCmdLine("");
        rawPresenter.evaluateCmdLine();
        assertEquals("", out.toString().strip());
    }

    @Test
    public void acceptRawInput_NormalChar_PrintsNewCmdLine()
    {
        rawPresenter.acceptRawInput(B_ASCII);
        assertEquals("\u001B[1K\u001B[1G" +
                "/comp0010> echo newest commandB" +
                "\u001B[1D\u001B[1D\u001B[1C", out.toString().strip());
    }

    @Test
    public void acceptRawInput_NormalChar_AddsCharToCmdLine()
    {
        rawPresenter.acceptRawInput(B_ASCII);
        assertEquals("echo newest commandB", terminal.getCmdLine());
    }

    @Test
    public void acceptRawInput_SpecialChar_PerformsAction()
    {
        terminal.moveCursor(-2);

        rawPresenter.acceptRawInput(RIGHT);

        assertEquals(-1, terminal.getCursor());
    }

    @Test(expected = IllegalStateException.class)
    public void acceptRawInput_CookedMode_ThrowsIllegalStateException()
    {
        cookedPresenter.acceptRawInput(5);
    }

    @Test(expected = AssertionError.class)
    public void acceptInput_Raw_Fails()
    {
        rawPresenter.acceptInput("echo mango");
    }

    @Test
    public void acceptInput_PrintsCmdLine()
    {
        cookedPresenter.acceptInput("echo mango");
        assertEquals("/comp0010> " +
                "\u001B[1K\u001B[F\u001B[1G" +
                "echo mango", out.toString().strip());
    }

    @Test
    public void acceptInput_SetsCmdLine()
    {
        cookedPresenter.acceptInput("echo mango");
        assertEquals("echo mango", terminal.getCmdLine());
    }

    @Test
    public void acceptInput_Empty_SetsCmdLinePrintsPrompt()
    {
        cookedPresenter.acceptInput("");
        assertEquals("", terminal.getCmdLine());
        assertEquals("/comp0010> \u001B[1K\u001B[F\u001B[1G", out.toString().strip());
    }

    @Test
    public void beginCommand_PrintsPrompt()
    {
        rawPresenter.beginCommand();
        assertEquals("/comp0010>", out.toString().strip());
    }

    @Test
    public void beginCommand_InitializesTerminal()
    {
        // put some random stuff
        terminal.setHistory(new ArrayList<>(Collections.singletonList("echo all too well")));
        terminal.moveCursor(-2);
        terminal.setCmdLine("echo hello");

        rawPresenter.beginCommand();

        assertEquals("", terminal.getCmdLine());
        assertEquals(0, terminal.getHistoryIndex());
        assertEquals(2, terminal.getHistory().size());
    }

    @Test
    public void endCommand_CmdLine_UpdatesCurrHistoryEntry()
    {
        terminal.setCmdLine("echo yo");
        rawPresenter.endCommand();
        assertEquals("echo yo", terminal.getHistory().get(0));
    }

    @Test
    public void endCommand_Empty_UpdatesCurrHistoryEntry()
    {
        terminal.setCmdLine("");

        rawPresenter.endCommand();
        assertEquals(2, terminal.getHistory().size());
    }

    @Test
    public void handleArrows_Right_PrintsCursorRight()
    {
        terminal.setCmdLine("echo hi");
        terminal.resetCursor();
        terminal.moveCursor(-2);
        rawPresenter.acceptRawInput(RIGHT);
        assertEquals("\u001B[1C", out.toString());
    }

    @Test
    public void handleArrows_Right_MovesCursorRight()
    {
        terminal.resetCursor();
        terminal.moveCursor(-2);

        rawPresenter.acceptRawInput(RIGHT);

        assertEquals(-1, terminal.getCursor());
    }

    @Test
    public void handleArrows_Left_PrintsCursorLeft()
    {
        rawPresenter.acceptRawInput(LEFT);
        assertEquals("\u001B[1D", out.toString());
    }

    @Test
    public void handleArrows_Left_MovesCursorLeft()
    {
        rawPresenter.acceptRawInput(LEFT);
        assertEquals(-1, terminal.getCursor());
    }

    @Test
    public void handleArrows_Up_PrintsPreviousCommand()
    {
        rawPresenter.acceptRawInput(UP);
        assertEquals("\u001B[1K\u001B[1G" +
                "/comp0010> echo middle command" +
                "\u001B[1D\u001B[1D\u001B[1C", out.toString().strip());
    }

    @Test
    public void handleArrows_Up_MovesTerminalHistoryIndex()
    {
        rawPresenter.acceptRawInput(UP);
        assertEquals(-1, this.terminal.getHistoryIndex());
    }

    @Test
    public void handleArrows_Down_PrintsNextCommand()
    {
        terminal.getPrevHistoryEntry();
        terminal.getPrevHistoryEntry();

        rawPresenter.acceptRawInput(DOWN);

        assertEquals("\u001B[1K\u001B[1G" +
                "/comp0010> echo middle command" +
                "\u001B[1D\u001B[1D\u001B[1C", out.toString());
    }

    @Test
    public void handleArrows_Downs_MovesTerminalHistoryIndex()
    {
        terminal.getPrevHistoryEntry();
        terminal.getPrevHistoryEntry();

        rawPresenter.acceptRawInput(DOWN);

        assertEquals(-1, this.terminal.getHistoryIndex());
    }

    @Test
    public void handleBackspace_PrintsBackspace()
    {
        rawPresenter.acceptRawInput(BACKSPACE);
        assertEquals("\u001B[1K\u001B[1G" +
                "/comp0010> echo newest comman" +
                "\u001B[1D", out.toString());
    }

    @Test
    public void handleBackspace_ChangesTerminal()
    {
        rawPresenter.acceptRawInput(BACKSPACE);
        assertEquals("echo newest comman", terminal.getCmdLine());
    }

    @Test
    public void startInteractive_MissingFile_MakesEmptyHistory() throws IOException
    {
        var historyFile = new File(HISTORY_FILE);
        historyFile.delete();
        rawPresenter.startInteractiveMode();
        assertEquals(0, terminal.getHistory().size());
        historyFile.createNewFile();
    }

    @Test
    public void startInteractive_NewView_ShouldWork()
    {
        try
        {
            var timer = new Timer();
            var task = new TimerTask()
            {
                @Override
                public void run() { new Presenter(new View()).startInteractiveMode(); }
            };
            timer.schedule(task, 0);

            var start = System.currentTimeMillis();
            while (System.currentTimeMillis() < start + 0.75 * 1000){}

            timer.cancel();
            timer.purge();
        }
        catch(Exception e)
        {
            fail();
        }
    }

    @Test
    public void acceptRaw_Esc_ShouldWork()
    {
        try
        {
            rawPresenter.acceptRawInput(ESC_KEY);
        }
        catch(Exception e)
        {
            fail();
        }
    }

    @Test
    public void acceptRaw_Space_ShouldAddToCmdLine()
    {
        rawPresenter.acceptRawInput(SPACEBAR);
        assertEquals("echo newest command ", terminal.getCmdLine());
    }

    @Test
    public void acceptRaw_BackspaceEmptyCmdLine_ShouldWork()
    {
        terminal.setCmdLine("");
        rawPresenter.acceptRawInput(BACKSPACE);
        assertEquals("", terminal.getCmdLine());
    }

    @Test
    public void acceptRaw_BackspaceOneChar_ShouldWork()
    {
        terminal.setCmdLine("a");
        rawPresenter.acceptRawInput(BACKSPACE);
        assertEquals("", terminal.getCmdLine());
    }

    @Test
    public void acceptRaw_BackspaceEndsSpace_ShouldMoveCursor()
    {
        terminal.setCmdLine("echo newest command   ");
        rawPresenter.acceptRawInput(BACKSPACE);
        assertEquals("[1K\u001B[1G/comp0010> echo newest command  \u001B[1D\u001B[1C", out.toString().trim());
    }

    @Test
    public void acceptRaw_ArrowRightCursorAtLastChar_ShouldNotMoveCursor()
    {
        rawPresenter.acceptRawInput(RIGHT);
        assertEquals(0, terminal.getCursor());
    }

    @Test
    public void acceptRaw_ArrowLeftCursorAtFirstChar_ShouldNotMoveCursor()
    {
        terminal.setCmdLine("hello");
        terminal.moveCursor(-5);
        rawPresenter.acceptRawInput(LEFT);
        assertEquals(-5, terminal.getCursor());
    }

    @Test
    public void acceptRaw_ArrowDownNullCmdLine_ShouldNotReplaceCmdLine()
    {
        terminal.getNextHistoryEntry();
        rawPresenter.acceptRawInput(DOWN);
        assertEquals("", out.toString().trim());
    }

    @Test
    public void replaceCmdLine_Empty_ShouldNotReplaceCmdLine()
    {
        terminal.setCmdLine("");
        terminal.getPrevHistoryEntry();
        rawPresenter.acceptRawInput(DOWN);
        assertEquals("[1K\u001B[1G/comp0010> \u001B[1C", out.toString().trim());
    }

    // allow us to run the view as if not in a terminal
    private static class TestView extends View
    {
        TestView(boolean inTerminal)
        {
            super();
            this.setInTerminal(inTerminal);
        }
    }
}