package uk.ac.ucl.shell.application;

import uk.ac.ucl.shell.exception.NullApplicationException;
import uk.ac.ucl.shell.exception.InvalidApplicationException;
import uk.ac.ucl.shell.application.apps.*;

import java.util.List;

/**
 * Utility class for creating Application objects
 * Note that the command name is passed as the first argument
 */
public class ApplicationFactory
{
    /**
     * Returns the appropriate application based on the command name stored in the first argument
     *
     * @param args the arguments of the application
     *             the first argument is the name of the application
     * @param in   the input stream of the application
     * @param out  the output stream of the application
     * @return     the application formed from the supplied information
     */
    public IApplication getApplication(List<String> args, String in, String out)
    {
        if (args.isEmpty())
            throw new NullApplicationException();

        String name = args.get(0).toLowerCase();
        List<String> argsToPass = args.subList(1, args.size());

        var unsafe = false;
        if (name.startsWith("_"))
        {
            name = name.substring(1);
            unsafe = true;
        }

        var app = getApp(name, argsToPass, in, out);
        return unsafe ? new Unsafe(app) : app;
    }

    private IApplication getApp(String name, List<String> args, String in, String out)
    {
        switch (name)
        {
            case "cd":
                return new Cd(args, in, out);
            case "ls":
                return new Ls(args, in, out);
            case "echo":
                return new Echo(args, in, out);
            case "pwd":
                return new Pwd(args, in, out);
            case "cat":
                return new Cat(args, in, out);
            case "head":
                return new Head(args, in, out);
            case "tail":
                return new Tail(args, in, out);
            case "grep":
                return new Grep(args, in, out);
            case "find":
                return new Find(args, in, out);
            case "sort":
                return new Sort(args, in, out);
            case "cut":
                return new Cut(args, in, out);
            case "uniq":
                return new Uniq(args, in, out);
            case "exit":
                return new Exit(args, in, out);
            case "alias":
                return new Alias(args, in, out);
            case "unalias":
                return new Unalias(args, in, out);
            default:
                throw new InvalidApplicationException(name);
        }
    }
}
