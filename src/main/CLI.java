package main;

import embedding.HOPE;
import embedding.Word2Vec;
import feature.Feature;
import function.Function;
import org.apache.commons.cli.*;
import representation.AST;

import java.io.File;
import java.util.List;

/**
 * @author cary.shi on 2019/11/29
 */
public class CLI {
    private static Options options = new Options();

    private void printHelp() {
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("Show how to use Functional Clone Detection.", options);
    }

    void detectClone(List<File> sourceFiles) {
        int len = sourceFiles.size();
        for (int i = 0; i < len; i++) {
            File sourceFile1 = sourceFiles.get(i);
            List<File> word2vecFeatureFileList1 = Word2Vec.getWord2VecBySourceFile(sourceFile1);
            List<File> hopeFeatureFileList1 = HOPE.getHOPEVecBySourceFile(sourceFile1);

            for (int j = i + 1; j < len; j++) {
                File sourceFile2 = sourceFiles.get(j);
                List<File> word2vecFeatureFileList2 = Word2Vec.getWord2VecBySourceFile(sourceFile2);
                List<File> hopeFeatureFileList2 = HOPE.getHOPEVecBySourceFile(sourceFile2);


            }
        }
    }

    public static void main(String[] args) {
        CLI cli = new CLI();

        options.addOption("H", "Help", false, "Print help message");
        options.addOption("F", "File", true, "The path of the file to scan");

        CommandLineParser commandLineParser = new DefaultParser();
        CommandLine commandLine = null;
        try {
            commandLine = commandLineParser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert commandLine != null;
        if (commandLine.hasOption("H")) {
            cli.printHelp();
        }
        String path = commandLine.getOptionValue("F");
        File scanFile = new File(path);


    }
}
