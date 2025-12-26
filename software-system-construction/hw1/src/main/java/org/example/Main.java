package org.example;

import io.muzoo.ssc.assignment.tracker.SscAssignment;

import org.apache.commons.cli.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/*
-c, --count-duplicates: Print the total count of duplicate files (e.g., N files with the same content will count as one).
-a ALGO, --algorithm ALGO: Specifies the algorithm for finding duplicates, with options like bbb, sha256, md5 (where bbb is byte-by-byte comparison, sha256 and md5 are by comparing their sha256 and md5 checksums respectively).
-p, --print: Print relative pathes of all duplicates grouped together.
-f /path/to/folder: Specifies the path to the target folder (required).
 */

public class Main extends SscAssignment{

    public static void main(String[] args) {
        Options options = new Options();

        // -c --count-duplicates
        Option countDuplicates = Option.builder("c")
                .longOpt("count-duplicates")
                .desc("Prints the total count of duplicate files")
                .get();
        // -a --algorithm
        Option algorithmOption = Option.builder("a")
                .longOpt("algorithm")
                .hasArg()
                .argName("ALGO")
                .desc("Specifies the algorithm for finding duplicates e.g bbb, sha256, md5")
                .get();
        // -p --print
        Option printOption = Option.builder("p")
                .longOpt("print")
                .desc("Print relative paths of all duplicates grouped together")
                .get();
        // -f
        Option fileOption = Option.builder("f")
                .hasArg()
                .argName("path")
                .desc("Specifies the path to the target folder (required).")
                .get();

        options.addOption(countDuplicates);
        options.addOption(algorithmOption);
        options.addOption(printOption);
        options.addOption(fileOption);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        String folder = null;
        String algorithm = null;
        boolean willCountDuplicates = false;
        boolean willPrintDuplicates = false;

        try {
            CommandLine cmd = parser.parse(options, args);

            willCountDuplicates = cmd.hasOption("c");
            willPrintDuplicates = cmd.hasOption("p");
            algorithm = cmd.getOptionValue("a", "bbb");
            folder = cmd.getOptionValue("f");

            // test if valid algorithm
            if (!algorithm.equalsIgnoreCase("bbb") && !algorithm.equalsIgnoreCase("sha256") && !algorithm.equalsIgnoreCase("md5")) {
                System.out.println("Invalid algorithm: " + algorithm);
                formatter.printHelp("java -jar hw1.jar", options, true);
                System.exit(1);
            }

            // test if parsed correctly -- only used for testing won't be in the actual finished product
//            System.out.println("Count duplicates: " + willCountDuplicates);
//            System.out.println("Print duplicates: " + willPrintDuplicates);
//            System.out.println("Algorithm: " + algorithm);
//            System.out.println("Folder: " + folder);

        } catch (ParseException e) {
            System.out.println("Error: " + e.getMessage());
            formatter.printHelp("java -jar hw1.jar", options, true);
            System.exit(1);
        }

        Path path = Paths.get(folder);
        FileFolderCounter counter = new FileFolderCounter();
        try { // count total file folder sizes
            counter.traverse(path);
        } catch (IOException e) {
            System.err.println("Error traversing folder " + e.getMessage());
            System.exit(1);
        }

        counter.print(); // print total file folder sizes
        DuplicateChecker checker = new DuplicateChecker(algorithm);
        Map<String, List<Path>> duplicates = null;
        try {
            duplicates = checker.findDuplicates(counter.getFiles());
        } catch (IOException e) {
            System.err.println("Error finding duplicates: " + e.getMessage());
            System.exit(1);
        }

        // handle count and print
        if (willCountDuplicates) {
            // filter any groups with more than 1 entry
            long count = duplicates.values()
                    .stream()
                    .filter(list -> list.size() > 1).count();
            System.out.printf("%-30s %15s\n", "Total duplicates:", count);
        }

        if (willPrintDuplicates) {
            Path folderPath = Path.of(folder);
            // relative paths of all dupes grouped together
            duplicates.values()
                    .stream()
                    .filter(list -> list.size() > 1)
                    .forEach(group -> {
                        group.forEach(absolute -> System.out.println(folderPath.relativize(absolute)));
                        System.out.println(); // divide by each group
                    });
        }
    }
}