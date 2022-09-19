package uk.ac.ucl.shell.application.util;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import uk.ac.ucl.shell.exception.InvalidFormatException;

/**
 * Utility class for the Cut app
 * Parses the supplied argument and returns the range in list form
 */
public class RangeUtil
{
    private final String rangeStr;
    private List<Integer> rangeList;
    private Integer toEndNumber = null;

    /**
     * Class constructor
     * @param rangeStr the argument of the "-b" option in cut
     *                 specifies the bytes to extract from each lien
     */
    public RangeUtil(String rangeStr)
    {
        this.rangeStr = rangeStr;
        this.rangeList = new ArrayList<>();
    }

    /**
     * Returns analysed range list
     *
     * @return list object of analysed range (index of characters to be included in output)
     */
    public List<Integer> getRangeList() throws RuntimeException
    {
        try
        {
            analyse();
        }
        catch (NumberFormatException e)
        {
            throw new InvalidFormatException("Range");
        }
        return rangeList;
    }

    private void analyse() throws NumberFormatException
    {
        String[] rangeListStr = rangeStr.split(",");

        for (String range : rangeListStr)
        {
            if (range.contains("-"))
            {
                if (handleDash(range))
                    break;
            }
            else
                addToRangeList(Integer.parseInt(range));
        }
        sort();
    }

    private boolean handleDash(String range)
    {
        boolean performBreak = false;
        String[] curr = range.split("-");
        if (curr.length == 0)            // covers entire range
        {
            rangeList = new ArrayList<>();
            rangeList.add(-1);
            performBreak = true;         // ignore other ranges
        }
        else
            analyseCurrentRange(curr);

        return performBreak;
    }

    private void analyseCurrentRange(String[] rangeNow) throws NumberFormatException
    {
        if (rangeNow.length > 2)
            throw new NumberFormatException();

        Integer[] rangePair = getRangePair(rangeNow);
        Integer start = rangePair[0];
        Integer end = rangePair[1];

        if (start == -1 && end == -1)
            throw new NumberFormatException();

        if (end != -1)
        {
            for (int index = start; index <= end; index++)
                addToRangeList(index);
        }
        else
        {
            if (toEndNumber == null || start < toEndNumber)
                toEndNumber = start;
            addToRangeList(start);
            addToRangeList(end);
        }
    }

    private Integer[] getRangePair(String[] range) throws NumberFormatException
    {
        Integer[] pair;
        if (range.length == 1)                   // range is open-ended (eg, 3-, 6-)
            pair = new Integer[]{Integer.parseInt(range[0]), -1};
        else if (range[0].compareTo("") == 0)    // range is open ended (eg, -3, -6)
            pair = new Integer[]{1, Integer.parseInt(range[1])};
        else
        {
            int start = Integer.parseInt(range[0]);
            int end = Integer.parseInt(range[1]);
            if (end < start)
                pair = new Integer[]{-1, -1};
            else
                pair = new Integer[]{start, end};
        }

        return pair;
    }

    private void addToRangeList(Integer cutIndex)
    {
        if (!rangeList.contains(cutIndex))
        {
            if (rangeList.contains(-1))
                rangeList.add(rangeList.size() - 1, cutIndex);
            else
                rangeList.add(cutIndex);
        }
    }

    private void sort()
    {
        if (rangeList.size() == 1)
            return;

        if (!rangeList.contains(-1))
            Collections.sort(rangeList);
        else
        {
            rangeList.remove(rangeList.size() - 1);
            Collections.sort(rangeList);
            rangeList.add(-1);
        }

        if (toEndNumber != null)
        {
            while (rangeList.size() - 2 >= 0 && rangeList.get(rangeList.size() - 2) > toEndNumber)
                rangeList.remove(rangeList.size() - 2);
        }
    }
}
