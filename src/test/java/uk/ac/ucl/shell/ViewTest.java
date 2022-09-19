package uk.ac.ucl.shell;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import uk.ac.ucl.shell.ITest;
import uk.ac.ucl.shell.exception.*;

import static org.junit.Assert.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RunWith(Enclosed.class)
public class ViewTest extends ITest
{
    @RunWith(Parameterized.class)
    public static class ViewErrParamTest extends ITest
    {
        private final List<String> testInput;
        private final String expectedResult;
        private View view;

        public ViewErrParamTest(List<String> testInput, String expectedResult)
        {
            this.testInput = testInput;
            this.expectedResult = expectedResult;
        }

        @Parameterized.Parameters
        public static Collection testInputs()
        {
            var testNonInteractiveInvalid = new ArrayList<>(Arrays.asList("-c", "ls world"));
            var testInvalidFlag = new ArrayList<>(Arrays.asList("-n", "ls world"));
            var testWrongArgNum = new ArrayList<>(Collections.singletonList("-c"));

            return Arrays.asList(new Object[][]{
                    {testNonInteractiveInvalid, "\u001b[31mCOMP0010 shell: world: file does not exist"},
                    {testInvalidFlag, "\u001b[31mCOMP0010 shell: -n: illegal option"},
                    {testWrongArgNum, "\u001b[31mCOMP0010 shell: Wrong number of arguments"}
            });
        }

        @Before
        public void viewErrParam_init()
        {
            this.view = new View();
        }

        @Test
        public void view_start_ShouldOutputListContentsToErr()
        {
            view.start(this.testInput);
            assertEquals(this.expectedResult, err.toString().strip());
        }
    }

    public static class ViewSingleTests extends ITest
    {
        private View view;
        private View viewTerminal;

        @Before
        public void init()
        {
            this.view = new View();
            this.viewTerminal = new View();
            viewTerminal.setInTerminal(true);
        }

        @Test
        public void startNonInteractive_Valid_PrintsOutput()
        {
            view.start(new ArrayList<>(Arrays.asList("-c", "echo world")));
            assertEquals("world", out.toString().strip());
        }

        @Test
        public void print_NotInTerminal_PrintsPlain()
        {
            view.print("hello");
            assertEquals("hello", out.toString().strip());
        }

        @Test
        public void printResult_NotInTerminal_PrintsPlain()
        {
            view.printResult(new ArrayList<>(Collections.singletonList("hello")));
            assertEquals("hello", out.toString().strip());
        }

        @Test
        public void printCommandLine_Empty_ShouldNotPrint()
        {
            view.printCmdLine("");
            assertEquals("", out.toString().strip());
        }

        @Test(expected = AssertionError.class)
        public void printCommandLineCursor_NotRaw_ShouldHighlightWithCursor()
        {
            view.setRawMode(false);
            view.printCmdLine("echo hello ; cat hi.txt > bye.txt", 5);
        }

        @Test
        public void printCommandLine_ShouldHighlightCmdLineArg()
        {
            view.printCmdLine("echo hello ; cat hi.txt > bye.txt");
            assertEquals("echo hello ; cat hi.txt > bye.txt", out.toString().strip());
        }

        @Test
        public void printCommandLineCursor_ShouldHighlightWithCursor()
        {
            view.setRawMode(true);
            view.printCmdLine("echo hello ; cat hi.txt > bye.txt", -5);
            // prints ansi character to move cursor back to original position
            assertEquals("echo hello ; cat hi.txt > bye.txt" +
                    "\u001B[1D\u001B[1D\u001B[1D\u001B[1D\u001B[1D\u001B[1D", out.toString().strip());
        }

        @Test
        public void print_InTerminal_PrintsDefaultWithAnsi()
        {
            viewTerminal.print("hello");
            assertEquals("\u001B[39mhello", out.toString().strip());
        }

        @Test
        public void print_InTerminal_PrintsResultInBlue()
        {
            viewTerminal.printResult(new ArrayList<>(Collections.singletonList("hello")));
            assertEquals("\u001B[34mhello", out.toString().strip());
        }

        @Test
        public void printCmdLine_InTerminal_ShouldHighlightCmdLineArg()
        {
            viewTerminal.printCmdLine("echo hello ; cat hi.txt > bye.txt");
            assertEquals("\u001B[32mecho \u001B[39mhello \u001B[39m\u001B[33m; " +
                    "\u001B[32mcat \u001B[31;4mhi.txt\u001B[24m " +
                    "\u001B[39m> \u001B[31;4mbye.txt\u001B[24m", out.toString().strip());
        }

        @Test
        public void printCmdLine_CursorInTerminal_ShouldHighlightWithCursor()
        {
            viewTerminal.setRawMode(true);
            viewTerminal.printCmdLine("echo hello ; cat hi.txt > bye.txt", -5);
            assertEquals("\u001B[32mecho \u001B[39mhello \u001B[39m\u001B[33m; " +
                    "\u001B[32mcat \u001B[31;4mhi.txt\u001B[24m \u001B[39m> \u001B[31;4mbye.txt\u001B[24m" +
                    // ansi characters to move cursor back to original position
                    " \u001B[1D\u001B[1D\u001B[1D\u001B[1D\u001B[1D\u001B[1D", out.toString().strip());
        }

        @Test
        public void moveCursorLeft_PrintsAnsiForLeft()
        {
            view.moveCursorLeft();
            assertEquals("\u001b[1D", out.toString().strip());
        }

        @Test
        public void moveCursorRight_PrintsAnsiForRight()
        {
            view.moveCursorRight();
            assertEquals("\u001b[1C", out.toString().strip());
        }

        @Test
        public void clearPrevLine_PrintsAnsiToClearPrev()
        {
            view.clearPrevLine();
            assertEquals("\u001b[1K\u001b[F\u001b[1G", out.toString().strip());
        }

        @Test
        public void clearCurrLine_PrintsAnsiToClearCurr()
        {
            view.clearCurrLine();
            assertEquals("\u001b[1K\u001b[1G", out.toString().strip());
        }

        @Test
        public void view_Backspace_PrintsAnsiForBackspace()
        {
            view.backspace();
            assertEquals("\u001b[1D\u001b[0K", out.toString().strip());
        }

        @Test
        public void start_RawModeNoInput_ShouldWork()
        {
            view.setRawMode(true);
            try
            {
                runTimedView("");
            }
            catch (Exception e)
            {
                fail();
            }
            assertTrue(true);
        }

        @Test
        public void start_EchoMode_ShouldWork()
        {

            view.setRawMode(false);
            try
            {
                runTimedView("echo hi -n | csdfs `asd`\\r\\n");
            }
            catch (Exception e)
            {
                fail();
            }
            assertTrue(true);
        }

        @Test
        public void start_EchoModeEmpty_ShouldWork()
        {

            view.setRawMode(false);
            try
            {
                runTimedView("");
            }
            catch (Exception e)
            {
                fail();
            }
            assertTrue(true);
        }

        @Test
        public void start_RawMode_ShouldWork()
        {
            view.setRawMode(true);
            try
            {
                runTimedView("echo hi -n | csdfs `asd`\\r\\n");
            }
            catch (Exception e)
            {
                fail();
            }
            assertTrue(true);
        }

        @Test(expected = AssertionError.class)
        public void start_Uninitialized_ShouldFail()
        {
            try
            {
                new TestViewNull().interactive();
            }
            catch (Exception e)
            {
                fail();
            }
        }
        private static class TestViewNull extends View
        {
            private TestViewNull()
            {
                super();
                setPresenter(new TestPresenter(this));
                interactive();
            }
        }
        private static class TestPresenter extends Presenter
        {
            private TestPresenter(View view)
            {
                super(view);
            }
            public void startInteractiveMode() { /* nothing */ }
        }

        @Test
        public void testShellNonInteractive()
        {
            try
            {
                runTimedTask(new NonInteractiveTask());
            }
            catch (Exception e)
            {
                fail();
            }
        }

        private void runTimedView(String input)
        {
            var originalIn = System.in;
            var originalOut = System.out;

            initDummySystem(input);
            runTimedTask(new ViewTask());

            System.setIn(originalIn);
            System.setOut(originalOut);
        }

        private void initDummySystem(String input)
        {
            var fakeIn = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
            System.setIn(fakeIn);

            var dummyStream = new PrintStream(new OutputStream(){
                public void write(int b) { /* nothing */ }
            });

            System.setOut(dummyStream);
        }

        private void runTimedTask(TimerTask task)
        {
            var timer = new Timer();
            timer.schedule(task, 0);

            var start = System.currentTimeMillis();
            while (System.currentTimeMillis() < start + 0.75 * 1000){}

            timer.cancel();
            timer.purge();
        }

        private class ViewTask extends TimerTask
        {
            @Override
            public void run()
            {
                view.start(new ArrayList<>());
            }
        }

        private static class NonInteractiveTask extends TimerTask
        {
            @Override
            public void run()
            {
                String[] args = new String[]{"-c", "echo hello"};
                Shell.main(args);
            }
        }
    }
}
