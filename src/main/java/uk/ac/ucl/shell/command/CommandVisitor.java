package uk.ac.ucl.shell.command;

import uk.ac.ucl.shell.Presenter;
import uk.ac.ucl.shell.command.commands.Call;
import uk.ac.ucl.shell.command.commands.Pipe;
import uk.ac.ucl.shell.command.commands.Seq;
import uk.ac.ucl.shell.command.util.AliasesUtil;
import uk.ac.ucl.shell.command.util.GlobbingUtil;
import uk.ac.ucl.shell.exception.FileWriteException;
import uk.ac.ucl.shell.util.Directory;
import uk.ac.ucl.shell.parser.Parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static uk.ac.ucl.shell.util.Constants.IN_REDIRECT;
import static uk.ac.ucl.shell.util.Constants.OUTPUT_FILE;

/**
 * Implements the evaluations of Seq, Pipe, and Call commands
 */
public class CommandVisitor implements ICommandVisitor
{
    private final Presenter presenter;
    private final Directory dir;

    /**
     * Class constructor
     *
     * @param presenter the presenter that invoked this class
     *                  needed to evaluate command substitutions
     */
    public CommandVisitor(Presenter presenter)
    {
        this.presenter = presenter;
        this.dir = Directory.getDirectory();
    }

    /**
     * Executes a command, performing command substitution, globbing, IO redirection
     *
     * @param call the call command to be executed
     * @return     the output of the call
     */
    public List<String> visit(Call call)
    {
        commandSub(call);
        new Parser().parseCall(call);          // performs argument splitting
        globbing(call);
        removeQuotes(call);
        aliasing(call);
        return call.executeApp();
    }

    /**
     * Executes left command and redirects output to the input of the right command
     *
     * @param pipe the pipe command to be executed
     * @return     the output of the entire pipe command
     */
    public List<String> visit(Pipe pipe)
    {
        List<String> output = pipe.getLeft().evaluate(this);
        try
        {
            dir.writeToAbsoluteFilePath(OUTPUT_FILE, output);
        }
        catch(IOException e)
        {
            throw new FileWriteException("Output.txt", "Output could not be piped");
        }
        pipe.getRight().addRedirect(IN_REDIRECT, OUTPUT_FILE);
        return pipe.getRight().evaluate(this);
    }

    /**
     * Executes constituent commands in succession
     *
     * @param seq the seq command to be executed
     * @return    the output of the entire seq command
     */
    public List<String> visit(Seq seq)
    {
        var output = new ArrayList<>(seq.getLeft().evaluate(this));
        if (seq.getRight() != null)
            output.addAll(seq.getRight().evaluate(this));
        return output;
    }

    // Executes any backquoted commands and replaces them with their outputs
    private void commandSub(Call call)
    {
        var string = call.getCommandString();
        List<String> sections = splitSingleQuotes(string);
        for (int c = 0; c < sections.size(); c++)
        {
            if (sections.get(c).charAt(0) != '\'')
            {
                String output = checkArg(sections.get(c), true);
                sections.set(c, output);
            }
        }
        call.setCommandString(String.join(" ", sections));
    }

    // Splits a command line into sections of single-quoted and unquoted strings
    private List<String> splitSingleQuotes(String cmd)
    {
        var sections = new ArrayList<String>();
        String singleQuotedRegex = "('[^']*')";  // captures single-quoted strings
        Pattern regex = Pattern.compile(singleQuotedRegex);
        Matcher matcher = regex.matcher(cmd);

        int lastEnd = 0;
        while (matcher.find())
        {
            sections.add(cmd.substring(lastEnd, matcher.start()));
            sections.add(cmd.substring(matcher.start(), matcher.end()));
            lastEnd = matcher.end();
        }

        if (!(cmd.substring(lastEnd).isEmpty()))
            sections.add(cmd.substring(lastEnd));

        return sections;
    }

    // During command substitution: removes quotes and replaces commands with their output
    // After command substitution: removes quotes
    private String checkArg(String arg, boolean execute)
    {
        StringBuilder newArg = new StringBuilder();
        Matcher matcher = getBackquotedMatcher(arg);

        int lastEnd = 0;
        while (matcher.find())
        {
            var command = arg.substring(matcher.start(), matcher.end());
            command = command.substring(1, command.length() - 1);

            if (execute)
                command = execSubstitute(command);

            newArg.append(arg, lastEnd, matcher.start());
            newArg.append(command);
            lastEnd = matcher.end();
        }
        newArg.append(arg.substring(lastEnd));
        return newArg.toString();
    }

    // Executes a backquoted command and returns its output as a single argument
    private String execSubstitute(String command)
    {
        var out = presenter.getOutput(command);

        StringBuilder output = new StringBuilder();
        for (int c = 0; c < out.size(); c++)
        {
            var line = out.get(c);
            if (c != 0)
                output.append(" ");
            output.append(line);
        }
        return "`" + output + "`";
    }

    private Matcher getBackquotedMatcher(String command)
    {
        String backquotedRegex = "(`[^\n`]*`)";
        Pattern regex = Pattern.compile(backquotedRegex);
        return regex.matcher(command);
    }

    // helper function to remove quotation marks from quoted arguments
    private void removeQuotes(Call call)
    {
        var args = call.getArgs();
        for (int c = 0; c < args.size(); c++)
        {
            var splits = new Parser().parseArg(args.get(c));
            StringBuilder newArg = new StringBuilder();
            for (var split : splits)
            {
                if (split.startsWith("\""))
                    split = checkArg(split, false);         // remove quotes surrounding backquoted command output

                if (split.startsWith("\"") || split.startsWith("'") || split.startsWith("`"))
                {
                    split = split.substring(1, split.length() - 1); // remove quotes
                    split = escape(split);                          // escape special characters
                }
                newArg.append(split);
            }
            call.setArg(c, newArg.toString());
        }
    }

    // Escapes special chars
    private String escape(String s)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);
            if (c != '\\')
                sb.append(c);
        }
        return sb.toString();
    }

    // Implements pattern matching
    private void globbing(Call call)
    {
        if (requiresGlobbingBeforeExec(call)) {
             List<String> argsToCheck = call.getArgs();
             GlobbingUtil globbing = new GlobbingUtil();
             List<String> newArgs = globbing.execGlobbing(argsToCheck);
             call.setArgs(newArgs);
         }
    }

    private Boolean requiresGlobbingBeforeExec(Call call) {
         String app = call.getArgs().get(0);
         if (app.equals("find")) {
             return false;
         }
         return true;
     }


    // Converts alias into original command
    private void aliasing(Call call)
    {
        String alias = call.getCommandString();
        if (AliasesUtil.getAliasesClass().isAlias(alias))
        {
            String newCommandString = AliasesUtil.getAliasesClass().getCmdStringFromAlias(alias);
            List<String> newArgs = AliasesUtil.getAliasesClass().getCmdArgsFromAlias(alias);
            call.setCommandString(newCommandString);
            call.setArgs(newArgs);
        }
    }
}
