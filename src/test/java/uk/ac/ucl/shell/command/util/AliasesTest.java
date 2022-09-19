package uk.ac.ucl.shell.command.util;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ucl.shell.ITest;
import uk.ac.ucl.shell.exception.AliasDoesNotExistException;

import static org.junit.Assert.*;

public class AliasesTest extends ITest
{
    private AliasesUtil aliasesUtil;

    @Before
    public void initAliases() 
    {
        aliasesUtil = AliasesUtil.getAliasesClass();
        if (aliasesUtil.isAlias("j"))
        {
            aliasesUtil.removeAlias("j");
        }
    }

    @Test
    public void addAlias_addsAliases() 
    {
        aliasesUtil.addAlias("j", "echo j");
        assertTrue(aliasesUtil.hasAlias("echo j"));
    }

    @Test
    public void getCommandFromAlias_ReturnsCorrectCommand() 
    {
        aliasesUtil.addAlias("j", "echo j");
        assertEquals("echo j", aliasesUtil.getCmdStringFromAlias("j"));
    }

    @Test
    public void getCmdArgsFromAlias_ReturnsCorrectArgs() 
    {
        aliasesUtil.addAlias("j", "echo j");
        assertEquals("echo,j", String.join(",", aliasesUtil.getCmdArgsFromAlias("j")));
    }

    @Test
    public void getKeySet_ReturnsKeys()
    {
        aliasesUtil.addAlias("j", "echo j");
        var aliasesSet = aliasesUtil.getAliases();
        assertFalse(aliasesSet.isEmpty());
    }

    @Test(expected = AliasDoesNotExistException.class)
    public void removeAlias_NullAlias_ShouldThrowException()
    {
        aliasesUtil.removeAlias(null);
    }

    @Test(expected = AliasDoesNotExistException.class)
    public void removeAlias_InvalidAlias_ShouldThrowException()
    {
        aliasesUtil.removeAlias("aadfasdlfjaskdfhw");
    }
}
