package uk.ac.ucl.shell;

import java.util.Arrays;

/**
 * Main class for COMP0010 Shell
 */
public class Shell
{
    /**
     * Main method for running COMP0010 Shell
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        View view = new View();
        view.start(Arrays.asList(args));
    }
}