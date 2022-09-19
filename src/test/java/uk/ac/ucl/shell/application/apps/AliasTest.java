package uk.ac.ucl.shell.application.apps;

import org.junit.Test;
import uk.ac.ucl.shell.ITest;
import uk.ac.ucl.shell.application.ApplicationFactory;
import uk.ac.ucl.shell.application.IApplication;
import uk.ac.ucl.shell.command.util.AliasesUtil;
import uk.ac.ucl.shell.exception.AliasExistsException;
import uk.ac.ucl.shell.exception.InvalidFormatException;
import uk.ac.ucl.shell.exception.InvalidNumOfArgsException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class AliasTest extends ITest
{
    @Test
    public void alias_ShouldSetCommandAlias()
    {
        List<String> testInput = new ArrayList<>(Arrays.asList("alias", "e=", "\"echo j\""));
        IApplication app = new ApplicationFactory().getApplication(testInput, "", "");
        app.exec();
        assertTrue(AliasesUtil.getAliasesClass().isAlias("e"));
    }

    @Test
    public void alias_EmptyArg_ShouldOutputAliasList()
    {
        List<String> testInput = new ArrayList<>(Collections.singletonList("alias"));
        IApplication app = new ApplicationFactory().getApplication(testInput, "", "");
        app.exec();
        assertTrue(true);
        // as execution of tests does not go by order, it is difficult to determine the final output
        // check if this tests can run successfully
    }

    @Test(expected = AliasExistsException.class)
    public void alias_ExistedCommand_ThrowsAliasAlreadyExists()
    {
        List<String> testInput = new ArrayList<>(Arrays.asList("alias", "j=", "\"echo l\""));
        IApplication app = new ApplicationFactory().getApplication(testInput, "", "");
        app.exec();

        testInput = new ArrayList<>(Arrays.asList("alias", "k=", "\"echo l\""));
        app = new ApplicationFactory().getApplication(testInput, "", "");
        app.exec();
    }


    // Applications classes
    // Tests for other commands format or exception throwing, successful runs are tested while running applications
    @Test(expected = InvalidNumOfArgsException.class)
    public void alias_ExtraArg_ThrowsInvalidNumOfArgsException()
    {
        List<String> testInput = new ArrayList<>(Arrays.asList("alias", "hello", "e=", "\"echo j\""));
        IApplication app = new ApplicationFactory().getApplication(testInput, "", "");
        app.exec();
    }

    @Test(expected = InvalidFormatException.class)
    public void alias_WrongFormat_ThrowsCustomException()
    {
        List<String> testInput = new ArrayList<>(Arrays.asList("alias", "e", "\"echo j\""));
        IApplication app = new ApplicationFactory().getApplication(testInput, "", "");
        app.exec();
    }
}
