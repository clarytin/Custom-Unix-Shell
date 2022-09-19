package uk.ac.ucl.shell;

import java.nio.file.Paths;

public class Constants
{
    public static final String EXAMPLES_DIR = Paths.get("src", "test", "res", "testexamples").toString();
    public static final String NUMBERS_FILE = Paths.get("src", "test", "res", "testexamples", "numbers.txt").toString();
    public static final String LIST_FILE = Paths.get("src", "test", "res", "testexamples", "list.txt").toString();
    public static final String ALPHA_FILE = Paths.get("src", "test", "res", "testexamples", "alpha.txt").toString();
    public static final String EMPTY_FILE = Paths.get("src", "test", "res", "testexamples", "empty.txt").toString();
    public static final String TEST_OUTPUT_FILE = Paths.get("src", "test", "res", "testexamples", "testOutput.txt").toString();
    public static final String TEST_OUTPUT2_FILE = Paths.get("src", "test", "res", "testexamples", "testOutput2.txt").toString();
    public static final String DIFF_LEN_FILE = Paths.get("src", "test", "res", "testexamples", "different_length.txt").toString();
}
