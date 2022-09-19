package uk.ac.ucl.shell.command.util;

import uk.ac.ucl.shell.exception.InvalidFormatException;
import uk.ac.ucl.shell.util.Directory;
import uk.ac.ucl.shell.exception.FileReadException;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;

import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;

import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

/**
 * Utility class for handling <a href="https://en.wikipedia.org/wiki/Glob_(programming)">Globbing</a>
 */
public class GlobbingUtil
{
    private final Directory directory;
    private PathMatcher matcher;
    private String path;

    private String noMatchError = "Error: No files match globbing pattern";

    /**
     * Class constructor
     */
    public GlobbingUtil()
    {
        this.directory = Directory.getDirectory();
        this.path = directory.getCurrentDir();
    }

    public void setPath(String newPath) {
        path = newPath;
    }

    /**
     * Expands the wildcard characters in arguments by returning
     * appending the args list with all possible matches
     *
     * @param args  the args to check for globbing patterns
     * @return      updated list of args after Globbing has been implemented
     */
    public List<String> execGlobbing(List<String> args)
    {
        List<String> newArgs = new ArrayList<>();
        for (String arg : args)
        {
            if (requiresGlobbing(arg))
            {
                List<String> matchingPaths = getGlobbingMatches(arg);
                newArgs.addAll(matchingPaths);
            }
            else
                newArgs.add(arg);
        }
        if (newArgs.isEmpty()) {
            throw new RuntimeException(noMatchError);
        }
        return newArgs;
    }

    public List<String> execGlobbing(String argToCheck) {
        List<String> argForGlobbing = new ArrayList<>();
        argForGlobbing.add(argToCheck);
        List<String> matchingFiles = execGlobbing(argForGlobbing);
        return matchingFiles;
    }

    private Boolean requiresGlobbing(String arg) {
        if (arg.contains("*")) {
            return true;
        }
        return false;
    }

    public List<String> getGlobMatchesRecursively(List<String> args) {
        List<String> newArgs = new ArrayList<>();
        List<String> childPaths = getPathsToCheckRecursively(args);
        try {
            newArgs.addAll(execGlobbing(childPaths));
        }
        catch (RuntimeException e) {
            // Catches 'no matches' error - may not immediately be a match
            if (!(e.getMessage().equals(noMatchError))) {
                throw e;
            }
        }
        return newArgs;
    }

    public List<String> getGlobMatchesRecursively(String arg) {
        List<String> argForGlobbing = new ArrayList<>();
        argForGlobbing.add(arg);
        List<String> matchingFiles = getGlobMatchesRecursively(argForGlobbing);
        return matchingFiles;
    }

    public List<String> getPathsToCheckRecursively(List<String> args) {
        List<String> pathsToCheck = new ArrayList<>();
        for (String arg: args) {
            pathsToCheck.add(arg);
            List<String> childPaths = getChildPathsToCheck(arg);
            if (childPaths.size() != 0) { // if further paths found
                List<String> childResults = new ArrayList<>();
                if (getDirectoryOfFilePath(arg).compareTo("") != 0)
                {
                    for (String child : childPaths)
                    {
                        childResults.add(getDirectoryOfFilePath(arg) + File.separator + child);
                    }
                }
                else
                    childResults = childPaths;
                childResults = getPathsToCheckRecursively(childResults);
                pathsToCheck.addAll(childResults);
            }
        }
        return pathsToCheck;
    }

    private List<String> getChildPathsToCheck(String originalPath) {
        List<String> pathsToCheck = new ArrayList<>();
        String currentDir = directory.getCurrentDir() + File.separator + getDirectoryOfFilePath(originalPath);
        String fileName = getFilenameOfFilePath(originalPath);
        pathsToCheck = generatePathsForChildDirs(currentDir, fileName);
        return pathsToCheck;
    }

    private List<String> generatePathsForChildDirs(String currentDir, String fileName) {
        List<String> newPaths = new ArrayList<>();
        File currFile = new File(currentDir);
        String[] childDirs = currFile.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });
        if (childDirs != null)
        {
            for (String childDir : childDirs) {
                newPaths.add(childDir + File.separator + fileName);
            }
        }
        return newPaths;
    }

    private List<String> getGlobbingMatches(String glob)
    {
        try
        {
            matcher = FileSystems.getDefault().getPathMatcher(formatFilePathForMatching(glob));
        }
        catch (PatternSyntaxException e)
        {
            throw new InvalidFormatException("Globbing pattern " + formatFilePathForError(glob));
        }
        List<String> matchingPaths = walkFilesAndMatch(glob);
        return matchingPaths;
    }

    // Based off code found at https://javapapers.com/java/glob-with-java-nio/
    private List<String> walkFilesAndMatch(String filePath)
    {
        List<String> matchingFiles = new ArrayList<>();
        try
        {
            walkFileTree(matchingFiles);
        }
        catch (IOException e)
        {
            throw new FileReadException(filePath);
        }
        return matchingFiles;
    }

    private void walkFileTree(List<String> matchingFiles) throws IOException {
        Files.walkFileTree(Paths.get(path), new SimpleFileVisitor<>()
            {
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes attributes)
                {
                    if (matches(path))
                        matchingFiles.add(reformatFilePath(path.toString()));

                    return FileVisitResult.CONTINUE;
                }
                @Override
                public FileVisitResult visitFileFailed(Path p, IOException e)
                {
                    return FileVisitResult.CONTINUE;
                }
            });
    }

    //  Utility functions
    private String formatFilePathForMatching(String filePath)
    {
        // "glob:" : to indicate that globbing should be done by PathMatcher
        // directory.getCurrentDir() + File.separator :  prepend current dir so files are properly located
        return "glob:" + path + File.separator + filePath;
    }

    private String formatFilePathForError(String filePath)
    {
        return path + File.separator + filePath;
    }

    private String reformatFilePath(String pathWithDir)
    {
        // File paths (for purpose of walkFileTree) have the current dir added to the front
        // Need to remove this before using filename as an arg
        //return pathWithDir;
        return pathWithDir.replaceFirst(path + File.separator, "");
    }

    private boolean matches(Path pathToTest)
    {
        return matcher.matches(pathToTest);
    }

    private String getDirectoryOfFilePath(String filePath) {
        String dir;
        Path path = Paths.get(filePath);
        Path pathDir = path.getParent();
        if (pathDir == null) {
            dir = "";
        }
        else {
            dir = pathDir.toString();
        }
        return dir;
    }

    private String getFilenameOfFilePath(String filePath) {
        Path path = Paths.get(filePath);
        String fileName = path.getFileName().toString();
        if (fileName == null) {
            throw new RuntimeException("Error: file path with no filename provided to getFilename function");
        }
        return fileName;
    }
}
