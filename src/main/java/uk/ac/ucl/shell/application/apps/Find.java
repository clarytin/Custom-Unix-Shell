package uk.ac.ucl.shell.application.apps;

import uk.ac.ucl.shell.application.Application;
import uk.ac.ucl.shell.application.Option;
import uk.ac.ucl.shell.command.util.GlobbingUtil;
import uk.ac.ucl.shell.exception.InvalidNumOfArgsException;
import uk.ac.ucl.shell.util.Directory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Recursively searches for files with matching names
 * Outputs the list of relative paths, each followed by a newline
 */
public class Find extends Application
{
    private final Directory dir;
    private String fileName;
    private String diffDirPath;

    public Find(List<String> args, String in, String out)
    {
        super(args, in, out);
        this.dir = Directory.getDirectory();
        validOptions.put("name", new Option("name", true, 1));
    }

    @Override
    protected void validateArgs()
    {
        if (!checkNumArgs(args, 2, 3))
            throw new InvalidNumOfArgsException("find");
    }

    @Override
    protected void evaluate()
    {
        loadOptions();
        initParams();

        GlobbingUtil globbing = new GlobbingUtil();
        if (diffDirPath != null) {
            globbing.setPath(diffDirPath);
        }
        List<String> matchingFiles = globbing.getGlobMatchesRecursively(fileName);
        matchingFiles = formatFilePaths(matchingFiles);
        output.addAll(matchingFiles);
    }

    private void initParams()
    {
        List<String> nameFlag = getOptionArgs("name");
        if (nameFlag.size() > 1) {
            throw new RuntimeException("More than 1 filename given under name flag " + nameFlag.toString());
        }
        fileName = getOptionArgs("name").get(0);
        if (args.isEmpty()) {
            diffDirPath = null;
        }
        else {
            diffDirPath = dir.getCurrentDir() + File.separator + args.get(0);
        }
    }

    private List<String> formatFilePaths(List<String> matchingFiles) {
        List<String> outputFilePaths = new ArrayList<>();
        for (String file : matchingFiles) {
            file = pathToOutput() + File.separator + file;
            outputFilePaths.add(file);
        }
        return outputFilePaths;
    }

    private String pathToOutput() {
        String pathToOutput = "";
        if (diffDirPath != null) {
            pathToOutput = diffDirPath + pathToOutput;
        }
        else
        {
            pathToOutput = ".";
        }
        return pathToOutput;
    }
}
