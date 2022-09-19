package uk.ac.ucl.shell.application.apps;

import org.junit.Test;
import uk.ac.ucl.shell.ITest;
import uk.ac.ucl.shell.application.ApplicationFactory;
import uk.ac.ucl.shell.application.IApplication;
import uk.ac.ucl.shell.command.util.AliasesUtil;
import uk.ac.ucl.shell.exception.AliasDoesNotExistException;
import uk.ac.ucl.shell.exception.InvalidNumOfArgsException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;

public class UnaliasTest extends ITest
{
    @Test
    public void unalias_NormalInput_ShouldRemoveAliases()
    {
        List<String> testInput = new ArrayList<>(Arrays.asList("alias", "ek=", "\"echo k\""));
        IApplication app = new ApplicationFactory().getApplication(testInput, "", "");
        app.exec();

        // test unalias
        testInput = new ArrayList<>(Arrays.asList("unalias", "ek"));
        app = new ApplicationFactory().getApplication(testInput, "", "");
        app.exec();
        assertFalse(AliasesUtil.getAliasesClass().isAlias("ek"));
    }

    @Test(expected = AliasDoesNotExistException.class)
    public void unalias_NonExistingAlias_ThrowsException()
    {
        List<String> testInput = new ArrayList<>(Arrays.asList("unalias", "hello"));
        IApplication app = new ApplicationFactory().getApplication(testInput, "", "");
        app.exec();
    }

    @Test(expected = InvalidNumOfArgsException.class)
    public void unalias_ExtraArgs_ThrowsInvalidNumOfArgsException()
    {
        List<String> testInput = new ArrayList<>(Arrays.asList("unalias", "e", "\"echo j\""));
        IApplication app = new ApplicationFactory().getApplication(testInput, "", "");
        app.exec();
    }
}
