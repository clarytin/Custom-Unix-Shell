package uk.ac.ucl.shell.application;

import uk.ac.ucl.shell.ITest;
import uk.ac.ucl.shell.Shell;

import org.junit.Test;

import static org.junit.Assert.*;

public class NonInteractiveAppTest extends ITest
{
    @Test
    public void nonInteractive_NormalInput_ShouldPrintOutput()
    {
        String[] args = new String[]{"-c", "echo hello"};
        Shell.main(args);
        assertEquals("hello", out.toString().strip());
    }
}
