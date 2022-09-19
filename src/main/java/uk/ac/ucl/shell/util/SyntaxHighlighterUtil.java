package uk.ac.ucl.shell.util;

import uk.ac.ucl.shell.command.util.AliasesUtil;
import uk.ac.ucl.shell.exception.FileDoesNotExistException;
import uk.ac.ucl.shell.exception.FileReadException;

import java.util.*;

import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Attribute.*;
import static org.fusesource.jansi.Ansi.Color.*;

/**
 * Utility class that uses <a href="https://github.com/fusesource/jansi">JANSI</a>
 * to format console text and manipulate the cursor.
 */
public class SyntaxHighlighterUtil
{
    private final Directory dir = Directory.getDirectory();

    private boolean isApp = true;
    private List<String> extensions;
    private final String[] apps = initApps();


    /**
     * Highlights the argument as a command line input, applying
     * text formatting to each token depending on its type
     *
     * @param cmdLine  the command line to be formatted
     */
    public void highlightLine(String cmdLine)
    {
        String[] tokens = cmdLine.split(" ");

        isApp = true;
        for (String token : tokens)
            highlight(token);
    }

    /**
     * Prints text in the default color
     *
     * @param line the text to be rendered with default formatting
     */
    public void printDefault(String line)
    {
        System.out.print(ansi()
                .fgDefault()
                .render(line));
    }

    /**
     * Prints text in red to signify that an error has occurred
     *
     * @param message the message to be printed with a red font color
     */
    public void printError(String message)
    {
        System.err.println(ansi()
                .fgRed()
                .render(message));
    }

    /**
     * Prints text in blue for application output
     *
     * @param result result string
     */
    public void printResult(String result)
    {
        System.out.println(ansi()
                .fgBlue()
                .render(result));
    }

    /**
     * Gets apps for testing {@link SyntaxHighlighterUtil#initApps()}
     *
     * @return list of applications that can be highlighted
     */
    protected String[] getApps()
    {
        return this.apps;
    }

    private void highlight(String token)
    {
        token = checkBefore(token);
        if (isApp)
            color(token, analyseApp(token));
        else
        {
            if (token.startsWith("-"))                       // flags
                color(token, MAGENTA);
            else if (token.contains(".") && isFile(token))   // files
                underline(token, analyseFile(token));
            else if (token.startsWith("\"") ||               // quoted
                    token.startsWith("'") || token.startsWith("`"))
                color(token, analyseQuotes(token));
            else if (token.endsWith(";") || token.endsWith("|"))
            {
                colorNoSpace(token.substring(0, token.length() - 1), DEFAULT);
                color(token.substring(token.length() - 1), YELLOW);
                isApp = true;
            }
            else
                color(token, DEFAULT);                        // arguments and others
        }
    }

    private boolean isFile(String token)
    {
        if (token.length() <= 2)
            return false;

        if (extensions == null)
            initExtensions();

        if (token.endsWith("'") || token.endsWith("`") || token.endsWith("\""))
            token = token.substring(0, token.length() - 1);

        var split = token.split("\\.");
        var key = split[split.length - 1];
        return Collections.binarySearch(extensions, key) >= 0;
    }

    private String checkBefore(String token)
    {
        if (token.length() != 1 && (token.startsWith(";") || token.startsWith("|")))
        {
            isApp = true;
            colorNoSpace(token.substring(0,1), YELLOW);
            token = token.substring(1);
        }
        return "".concat(token);
    }

    private Color analyseApp(String token)
    {
        if (token.charAt(0) == '_')
            token = token.substring(1);

        String tok = "".concat(token);
        var aliases = AliasesUtil.getAliasesClass();
        if (Arrays.binarySearch(apps, tok.toLowerCase()) >= 0 || aliases.isAlias(tok))
        {
            isApp = false;
            return GREEN;
        }

        return RED;
    }

    private Color analyseFile(String token)
    {
        if (dir.fileExists(token))
            return CYAN;

        return RED;
    }

    private Color analyseQuotes(String token)
    {
        if (token.charAt(token.length() - 1) == token.charAt(0))
            return DEFAULT;

        return RED;
    }

    private void color(String text, Color color)
    {
        System.out.print(ansi()
                .fg(color)
                .render(text + " "));
    }

    private void colorNoSpace(String text, Color color)
    {
        System.out.print(ansi()
                .fg(color)
                .render(text));
    }

    private void underline(String text, Color color)
    {
        System.out.print(ansi()
                .fg(color)
                .a(UNDERLINE)
                .render(text));

        System.out.print(ansi()
                .a(UNDERLINE_OFF)
                .render(" "));
    }

    private String[] initApps()
    {
        try
        {
            var appDirectory = dir.getFile(Constants.APPS_DIR);

            var list = appDirectory.list();
            for (int i = 0; i < Objects.requireNonNull(list).length; i++)
            {
                list[i] = list[i].substring(0, list[i].length() - 5); // remove .java
                list[i] = list[i].toLowerCase();
            }

            Arrays.sort(list);
            return list;
        }
        catch (FileDoesNotExistException e)
        {
            return new String[]{"alias", "cat", "cd", "cut", "echo", "exit",
                    "find", "grep", "head", "ls", "pwd", "sort", "tail",
                    "unalias", "uniq", "unsafe"};
        }
    }

    private void initExtensions()
    {
        try
        {
            extensions = dir.getAllLines(Constants.EXTENSIONS_FILE); // list of common file extensions
        }
        catch(FileReadException e)
        {
            extensions = new ArrayList<>(Arrays.asList("txt", "tex",
                    "xml", "html", "java", "c", "pdf", "doc", "ppt", "md"));
        }
        Collections.sort(extensions);
    }
}
