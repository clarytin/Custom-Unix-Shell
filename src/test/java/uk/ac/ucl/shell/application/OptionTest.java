package uk.ac.ucl.shell.application;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class OptionTest
{
    @Test
    public void option_getFlag_ReturnsStringIdentifier()
    {
        Option opt = new Option("b", false, 3);
        assertEquals("b", opt.getFlag());
    }

    @Test
    public void option_setInvoked_ChangesInvokingState()
    {
        Option opt = new Option("b", true, 3);
        opt.setInvokedTrue();
        assertTrue(opt.getInvoked());
    }

    @Test
    public void option_isRequired_ReturnsWhetherRequired()
    {
        Option opt = new Option("b", false, 3);
        assertFalse(opt.isRequired());
    }

    @Test
    public void option_getNumArgs_ReturnsNumArgsAccepted()
    {
        Option opt = new Option("b", true, 2);
        assertEquals(2, opt.getNumArgs());
    }


    @Test
    public void option_addOptionArgs_AddsArgumentsToOption()
    {
        Option opt = new Option("b", false, 1);
        opt.addOptionArg("testOpt");
        assertEquals("testOpt", String.join(",", opt.getOptionArgs()));
    }
}
