package uk.ac.ucl.shell.application.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

import org.junit.Test;
import static org.junit.Assert.*;
import static uk.ac.ucl.shell.Constants.NUMBERS_FILE;

import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import uk.ac.ucl.shell.ITest;
import uk.ac.ucl.shell.application.apps.Cut;
import uk.ac.ucl.shell.exception.InvalidFormatException;

@RunWith(Enclosed.class)
public class RangeTest extends ITest
{
    @RunWith(Parameterized.class)
    public static class RangeParamTest extends ITest
    {
        private final String testInput;
        private final String expectedResult;

        public RangeParamTest(String testInput, String expectedResult)
        {
            this.testInput = testInput;
            this.expectedResult = expectedResult;
        }

        @Parameterized.Parameters
        public static Collection testInputs()
        {
            var nonNegativeStartAndEnd = "-2,1-2,3";
            var doubleOpenHeadRange = "-2,-3";
            var normalRange = "1-2";
            var repeatedIndex = "1-2,1";
            var openEndRangeAndIndex = "4-,2";
            var indexAndOpenEndRange = "2,4-";
            var openHeadRange = "-2";
            var openHeadAndOpenEndRanges = "-2,3-";
            var openEndRangeAndNormalRange = "4-,2-3";
            var openHeadRangeAndNormalRange = "-4,2-3";
            var smallRangeListSize = "-1";

            return Arrays.asList(new Object[][]{
                    { nonNegativeStartAndEnd, "1,2,3" },
                    { doubleOpenHeadRange, "1,2,3" },
                    { normalRange, "1,2" },
                    { repeatedIndex, "1,2" },
                    { openEndRangeAndIndex, "2,4,-1" },
                    { indexAndOpenEndRange, "2,4,-1" },
                    { openHeadRange, "1,2" },
                    { openHeadAndOpenEndRanges, "1,2,3,-1" },
                    { openEndRangeAndNormalRange, "2,3,4,-1" },
                    { openHeadRangeAndNormalRange, "1,2,3,4" },
                    { smallRangeListSize, "1" }
            });
        }

        @Test
        public void analyseCurrentRange_TestInput_ReturnsRangeList()
        {
            var rangeUtil = new RangeUtil(testInput);
            List<Integer> rangeList = rangeUtil.getRangeList();
            List<String> result = new ArrayList<>();
            for (Integer rangeNum : rangeList)
            {
                result.add(String.valueOf(rangeNum));
            }
            assertEquals(expectedResult, String.join(",",result));
        }
    }

    public static class RangeSingleTests extends ITest
    {
        @Test(expected = NumberFormatException.class)
        public void range_NegOneDashNegOne_ThrowsException()
        {
            var rangeUtil = new RangeUtil("-1,-1");
            rangeUtil.getRangeList();
        }
    }
}