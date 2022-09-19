package uk.ac.ucl.shell.util;

import java.io.File;
import java.nio.file.Paths;

/**
 * Contains relevant ASCII information and filepaths
 */
public class Constants
{
    private Constants(){}

    public static final int RETURN_KEY = 10;
    public static final int ESC_KEY = 27;
    public static final int SPACEBAR = 32;
    public static final int BACKSPACE = 127;
    // start of the unicode private use area
    public static final int UP = 57344;        
    public static final int DOWN = 57345;
    public static final int RIGHT = 57346;
    public static final int LEFT = 57347;

    public static final String IN_REDIRECT = "<";
    public static final String OUT_REDIRECT = ">";

    public static final String SHELL_NAME = "COMP0010 shell";

    public static final String EXTENSIONS_FILE = Paths.get(File.separator, "comp0010", "src","main", "res", "common-extensions.txt").toString();
    public static final String OUTPUT_FILE = Paths.get(File.separator, "comp0010","src","main", "res", "output.txt").toString();
    public static final String HISTORY_FILE = Paths.get(File.separator, "comp0010","src","main", "res", "history.txt").toString();
    public static final int HISTORY_LENGTH = 500;

    public static final String APPS_DIR = Paths.get(File.separator, "comp0010", "src", "main", "java", "uk", "ac", "ucl", "shell", "application", "apps").toString();
    protected static final String[] SPECIAL_FILES = new String[]{HISTORY_FILE, OUTPUT_FILE, EXTENSIONS_FILE, APPS_DIR};
}
