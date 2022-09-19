package uk.ac.ucl.shell.application;

import java.util.List;

/**
 * Interface for defining Applications, the programs and utilities that COMP0010 shell can run
 */
public interface IApplication
{
    /**
     * Executes the program with its internally supplied input, output, and argument
     *
     * @throws RuntimeException if an exception occurs while running the application
     */
    void exec() throws RuntimeException;

    /**
     * Returns the application output after handling redirection
     *
     * @return empty list if output has been redirected or does not exist
     *         if output exists, return it in list form
     */
    List<String> getOutput();
}
