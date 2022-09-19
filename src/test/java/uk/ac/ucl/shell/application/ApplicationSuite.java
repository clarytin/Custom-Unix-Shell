package uk.ac.ucl.shell.application;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import uk.ac.ucl.shell.application.apps.*;
import uk.ac.ucl.shell.application.util.RangeTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ApplicationTest.class,
        NonInteractiveAppTest.class,
        RangeTest.class,
        OptionTest.class,
        AliasTest.class,
        CatTest.class,
        CdTest.class,
        CutTest.class,
        EchoTest.class,
        ExitTest.class,
        FindTest.class,
        GrepTest.class,
        HeadTest.class,
        LsTest.class,
        PwdTest.class,
        SortTest.class,
        TailTest.class,
        UnaliasTest.class,
        UniqTest.class,
        UnsafeTest.class,

})

public class ApplicationSuite { }