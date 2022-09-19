package uk.ac.ucl.shell.command.commands;

import org.junit.*;
import static org.junit.Assert.*;

import uk.ac.ucl.shell.command.ICommand;
import uk.ac.ucl.shell.parser.*;

public class PipeTest
{
    @Test
    public void constructor_noInput_returnsDefault()
    {
        Pipe pipe = new Pipe();
        assertNull(pipe.getLeft());
        assertNull(pipe.getRight());
    }

    @Test
    public void addLeft_validCommand_returnsCommand()
    {
        Pipe pipe = new Pipe();
        String commandString = "echo leftTest";
        ICommand leftCmd = getICommandFromCmdString(commandString);
        
        pipe.addLeft(leftCmd);

        assertEquals(leftCmd, pipe.getLeft());
    }

    @Test
    public void addLeft_SecondInvalidCmd_returnsFirstCmd()
    {
        Pipe pipe = new Pipe();
        String cmdString1 = "echo leftTest";
        ICommand leftCmd1 = getICommandFromCmdString(cmdString1);
        String cmdString2 = "echo invalidCmd";
        ICommand leftCmd2 = getICommandFromCmdString(cmdString2);

        pipe.addLeft(leftCmd1);
        pipe.addLeft(leftCmd2);

        assertEquals(leftCmd1, pipe.getLeft());
    }

    @Test
    public void addRight_validCommand_returnsCommand()
    {
        Pipe pipe = new Pipe();
        String commandString = "echo rightTest";
        Call rightCall = new Call(commandString);
        
        pipe.addRight(rightCall);

        assertEquals(rightCall, pipe.getRight());
    }

    @Test
    public void addRight_SecondInvalidCall_returnsFirstCall()
    {
        Pipe pipe = new Pipe();
        String cmdString1 = "echo rightTest";
        Call rightCall1 = new Call(cmdString1);
        String cmdString2 = "echo invalidCmd";
        Call rightCall2 =  new Call(cmdString2);

        pipe.addRight(rightCall1);
        pipe.addRight(rightCall2);

        assertEquals(rightCall1, pipe.getRight());
    }

    @Test
    public void addLeftaddRight_validCommands_returnsCommands()
    {
        Pipe pipe = new Pipe();
        String leftCmdString = "echo leftTest";
        ICommand leftCommand = getICommandFromCmdString(leftCmdString);
        String rightCmdString = "echo";
        Call rightCall =  new Call(rightCmdString);

        pipe.addLeft(leftCommand);
        pipe.addRight(rightCall);

        assertEquals(leftCommand, pipe.getLeft());
        assertEquals(rightCall, pipe.getRight());
    }

    private ICommand getICommandFromCmdString(String cmdString)
    {
        return new Parser().parseCommand(cmdString);
    }
}
