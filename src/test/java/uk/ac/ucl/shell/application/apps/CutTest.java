package uk.ac.ucl.shell.application.apps;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import uk.ac.ucl.shell.ITest;
import uk.ac.ucl.shell.application.ApplicationFactory;
import uk.ac.ucl.shell.application.IApplication;
import uk.ac.ucl.shell.exception.*;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static uk.ac.ucl.shell.Constants.NUMBERS_FILE;
import static uk.ac.ucl.shell.Constants.DIFF_LEN_FILE;

@RunWith(Enclosed.class)
public class CutTest extends ITest
{
    @RunWith(Parameterized.class)
    public static class CutParamTest extends ITest
    {
        private final List<String> testInput;
        private final String expectedResult;

        public CutParamTest(List<String> testInput, String expectedResult)
        {
            this.testInput = testInput;
            this.expectedResult = expectedResult;
        }

        @Parameterized.Parameters
        public static Collection testInputs()
        {
            var testBasic = new ArrayList<>(Arrays.asList("cut", "-b", "2-", NUMBERS_FILE));
            var testSingleIndex = new ArrayList<>(Arrays.asList("cut", "-b", "2", NUMBERS_FILE));
            var testOpenEndIndex = new ArrayList<>(Arrays.asList("cut", "-b", "-2", NUMBERS_FILE));
            var testFullIndex = new ArrayList<>(Arrays.asList("cut", "-b", "-", NUMBERS_FILE));
            var testRange = new ArrayList<>(Arrays.asList("cut", "-b", "1-2", NUMBERS_FILE));
            var testMixedIndex = new ArrayList<>(Arrays.asList("cut", "-b", "1-2,3", NUMBERS_FILE));
            var testDuplicateIndex = new ArrayList<>(Arrays.asList("cut", "-b", "3,1-", NUMBERS_FILE));
            var testOpenTailIndex = new ArrayList<>(Arrays.asList("cut", "-b", "3-", DIFF_LEN_FILE));

            return Arrays.asList(new Object[][]{
                    {testBasic, "23,56"},
                    {testSingleIndex, "2,5"},
                    {testOpenEndIndex, "12,45"},
                    {testFullIndex, "123,456"},
                    {testRange, "12,45"},
                    {testMixedIndex, "123,456"},
                    {testDuplicateIndex, "123,456"},
                    {testOpenTailIndex, "34,,7"}
            });
        }

        @Test
        public void cut_ShouldOutputListContents()
        {
            IApplication app = new ApplicationFactory().getApplication(this.testInput, "", "");
            app.exec();
            assertEquals(this.expectedResult, String.join(",", app.getOutput()));
        }
    }

    public static class CutSingleTests extends ITest
    {
        @Test
        public void cut_ShouldBeAbleToReadFromStdIn()
        {
            List<String> testInput = new ArrayList<>(Arrays.asList("cut", "-b", "2-"));
            IApplication app = new ApplicationFactory().getApplication(testInput, NUMBERS_FILE, "");
            app.exec();
            assertEquals("23,56", String.join(",", app.getOutput()));
        }

        @Test(expected = FileReadException.class)
        public void cut_TwoArgs_NoStdin_ShouldThrowException()
        {
            List<String> testInput = new ArrayList<>(Arrays.asList("cut", "-b", "2-"));
            IApplication app = new ApplicationFactory().getApplication(testInput, "", "");
            app.exec();
        }

        @Test(expected = InvalidNumOfArgsException.class)
        public void cut_TwoArgs_NullStdin_ShouldThrowException()
        {
            List<String> testInput = new ArrayList<>(Arrays.asList("cut", "-b", "2-"));
            IApplication app = new ApplicationFactory().getApplication(testInput, null, "");
            app.exec();
        }

        @Test(expected = InvalidNumOfArgsException.class)
        public void cut_NoArg_ThrowsInvalidNumOfArgsException()
        {
            Cut cut = new Cut(new ArrayList<>(Collections.emptyList()), "", "");
            cut.exec();
        }

        @Test(expected = InvalidFormatException.class)
        public void cut_FlippedRange_ThrowsException()
        {
            List<String> testInput = new ArrayList<>(Arrays.asList("cut", "-b", "2-1", NUMBERS_FILE));
            IApplication app = new ApplicationFactory().getApplication(testInput, "", "");
            app.exec();
        }

        @Test(expected = IllegalOptionException.class)
        public void cut_InvalidOption_ThrowsIllegalOptionException()
        {
            Cut cut = new Cut(new ArrayList<>(Arrays.asList("-d", "2-", NUMBERS_FILE)), "", "");
            cut.exec();
        }

        @Test(expected = InvalidFormatException.class)
        public void cut_CommaRange_ThrowsCustomException()
        {
            Cut cut = new Cut(new ArrayList<>(Arrays.asList("-b", ",", NUMBERS_FILE)), "", "");
            cut.exec();
        }

        @Test(expected = InvalidFormatException.class)
        public void cut_EmptyRange_ThrowsException()
        {
            Cut cut = new Cut(new ArrayList<>(Arrays.asList("-b", "", NUMBERS_FILE)), "", "");
            cut.exec();
        }

        @Test(expected = InvalidFormatException.class)
        public void cut_InvalidRange_ThrowsException()
        {
            Cut cut = new Cut(new ArrayList<>(Arrays.asList("-b", "1-4-5", NUMBERS_FILE)), "", "");
            cut.exec();
        }

        @Test(expected = InvalidFormatException.class)
        public void cut_AlphaRange_ThrowsException()
        {
            Cut cut = new Cut(new ArrayList<>(Arrays.asList("-b", "a-b", NUMBERS_FILE)), "", "");
            cut.exec();
        }
    }
}