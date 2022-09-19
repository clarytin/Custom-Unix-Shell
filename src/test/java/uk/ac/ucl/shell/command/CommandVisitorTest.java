package uk.ac.ucl.shell.command;

import org.junit.Test;
import uk.ac.ucl.shell.ITest;
import uk.ac.ucl.shell.Presenter;
import uk.ac.ucl.shell.View;
import uk.ac.ucl.shell.command.commands.Call;
import uk.ac.ucl.shell.command.commands.Pipe;
import uk.ac.ucl.shell.command.commands.Seq;
import uk.ac.ucl.shell.exception.InvalidApplicationException;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static uk.ac.ucl.shell.Constants.NUMBERS_FILE;

public class CommandVisitorTest extends ITest
{
    @Test
    public void visit_Pipe_ShouldOutputListContents() 
    {
        Call leftCommand = new Call("echo j");
        Call rightCommand = new Call("echo");
        Pipe pipe = new Pipe();
        pipe.addLeft(leftCommand);
        pipe.addRight(rightCommand);
        List<String> result = new CommandVisitor(new Presenter(new View())).visit(pipe);
        assertEquals("",String.join(",",result));
    }

    @Test
    public void visit_Seq_ShouldOutputListContents() 
    {
        Call leftCommand = new Call("echo j");
        Call rightCommand = new Call("echo k");
        Seq seq = new Seq();
        seq.addLeft(leftCommand);
        seq.addRight(rightCommand);
        List<String> result = new CommandVisitor(new Presenter(new View())).visit(seq);
        assertEquals("j,k",String.join(",",result));
    }

    @Test
    public void visit_CallSplitSingleQuotes_ShouldOutputListContents() 
    {
        Call command = new Call("echo \"j\"");
        List<String> result = new CommandVisitor(new Presenter(new View())).visit(command);
        assertEquals("j",String.join(",",result));
    }

    @Test
    public void visit_CallCommandSubstitution_ShouldOutputListContents() 
    {
        Call command = new Call("echo `echo j`");
        List<String> result = new CommandVisitor(new Presenter(new View())).visit(command);
        assertEquals("j",String.join(",",result));
    }

    @Test
    public void visit_CallSplitSingleQuotes_ShouldArgContents() 
    {
        Call command = new Call("echo 'j' 'k'");
        List<String> result = new CommandVisitor(new Presenter(new View())).visit(command);
        assertEquals("j k",String.join(",",result));
    }

    @Test
    public void visit_CallAlias_ShouldAliasContents() 
    {
        Call command = new Call("alias k= \"echo k\"");
        new CommandVisitor(new Presenter(new View())).visit(command);

        command = new Call("k");
        var result = new CommandVisitor(new Presenter(new View())).visit(command);

        assertEquals("k",String.join(",",result));
    }

    @Test (expected= InvalidApplicationException.class)
    public void visit_CallInvalidAlias_ThrowsCustomException() 
    {
        Call command = new Call("alias l= \"echo l\"");
        new CommandVisitor(new Presenter(new View())).visit(command);

        command = new Call("m");
        new CommandVisitor(new Presenter(new View())).visit(command);
    }

    @Test
    public void visit_CallOutputRedirection_ShouldOutputEmptyString() 
    {
        Call command = new Call("cat " + NUMBERS_FILE + " > testing.txt");
        List<String> result = new CommandVisitor(new Presenter(new View())).visit(command);
        assertEquals("",String.join(",",result));
    }

    @Test
    public void visit_WithSubstitution_ShouldWork() 
    {
        Call command = new Call("echo `echo hi hello goodbye`");
        List<String> result = new CommandVisitor(new Presenter(new View())).visit(command);
        assertEquals("hi hello goodbye", String.join(" ",result));
    }

    @Test
    public void commandSub_InsideSingleQuotes_ShouldWork()
    {
        Call command = new Call("echo 'hi' `echo hello goodbye yo` pie");
        List<String> result = new CommandVisitor(new Presenter(new View())).visit(command);
        assertEquals("hi hello goodbye yo pie", String.join(" ",result));
    }

    @Test
    public void escaping_SpecialChars_ShouldWork()
    {
        Call command = new Call("echo '\\ helloo'");
        List<String> result = new CommandVisitor(new Presenter(new View())).visit(command);
        assertEquals(" helloo", String.join(" ",result));
    }
}
