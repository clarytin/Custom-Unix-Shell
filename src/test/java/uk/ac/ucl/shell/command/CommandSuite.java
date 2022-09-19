package uk.ac.ucl.shell.command;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import uk.ac.ucl.shell.ITest;
import uk.ac.ucl.shell.command.commands.CallTest;
import uk.ac.ucl.shell.command.commands.PipeTest;
import uk.ac.ucl.shell.command.commands.SeqTest;
import uk.ac.ucl.shell.command.util.AliasesTest;
import uk.ac.ucl.shell.command.util.GlobbingTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CallTest.class,
        PipeTest.class,
        SeqTest.class,
        CommandVisitorTest.class,
        AliasesTest.class,
        GlobbingTest.class
})

public class CommandSuite extends ITest
{
}
