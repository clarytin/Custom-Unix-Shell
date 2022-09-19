package uk.ac.ucl.shell.command.commands;

import org.junit.*;
import static org.junit.Assert.*;

import uk.ac.ucl.shell.command.ICommand;
import uk.ac.ucl.shell.parser.Parser;

public class SeqTest
{
    @Test
    public void constructor_noInput_returnsDefault()
    {
        Seq seq = new Seq();
        assertNull(seq.getLeft());
        assertNull(seq.getRight());
    }

    @Test
    public void addLeft_validCommand_returnsCommand() {
        Seq seq = new Seq();
        String leftCmdString = "echo left";
        ICommand leftCommand = getICommandFromCmdString(leftCmdString);

        seq.addLeft(leftCommand);

        assertEquals(leftCommand, seq.getLeft());
    }

    @Test
    public void addLeft_SecondInvalidCmd_returnsFirstCmd() {
        Seq seq = new Seq();
        String cmdStringFirst = "echo leftTest";
        ICommand leftCmdFirst = getICommandFromCmdString(cmdStringFirst);
        String cmdStringSecond = "echo invalidCmd";
        ICommand leftCmdSecond = getICommandFromCmdString(cmdStringSecond);

        seq.addLeft(leftCmdFirst);
        seq.addLeft(leftCmdSecond);

        assertEquals(leftCmdFirst, seq.getLeft());
    }

    @Test
    public void addRight_validCommand_returnsCommand() {
        Seq seq = new Seq();
        String commandString = "echo rightTest";
        ICommand rightCommand = getICommandFromCmdString(commandString);
        
        seq.addRight(rightCommand);

        assertEquals(rightCommand, seq.getRight());
    }

    @Test
    public void addRight_SecondInvalidCmd_returnsFirstCmd() {
        Seq seq = new Seq();
        String cmdStringFirst = "echo rightTest";
        ICommand commandFirst = getICommandFromCmdString(cmdStringFirst);
        String cmdStringSecond = "echo invalidCmd";
        ICommand commandSecond = getICommandFromCmdString(cmdStringSecond);

        seq.addRight(commandFirst);
        seq.addRight(commandSecond);

        assertEquals(commandFirst, seq.getRight());
    }

    @Test
    public void addLeftaddRight_validCommands_returnsCommands() {
        Seq seq = new Seq();
        String leftCmdString = "echo leftTest";
        ICommand leftCmd = getICommandFromCmdString(leftCmdString);
        String rightCmdString = "echo rightTest";
        ICommand rightCmd = getICommandFromCmdString(rightCmdString);

        seq.addLeft(leftCmd);
        seq.addRight(rightCmd);

        assertEquals(leftCmd, seq.getLeft());
        assertEquals(rightCmd, seq.getRight());
    }

    private ICommand getICommandFromCmdString(String cmdString)
    {
        return new Parser().parseCommand(cmdString);
    }
}
