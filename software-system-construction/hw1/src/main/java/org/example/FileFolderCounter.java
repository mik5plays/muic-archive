package org.example;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.NumberFormat;
import java.util.*;

/*
For valid input, -f is required.
Your program will print the total number of files, folders, and file size in bytes, regardless of whether options 1-3 are used or not.
Numbers printed should be formatted with commas every third digit from the right.
 */

public class FileFolderCounter {
    private long totalFiles = 0;
    private long totalFolders = 0;
    private long totalBytes = 0;
    private final List<Path> files = new ArrayList<>(); // every single file traversed

    // catching IOException too in case problems with file and folder traversal
    public void traverse(Path path) throws IOException {
        Files.walkFileTree(path, new SimpleFileVisitor<>() {
            // count total files, folders, and bytes as well
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                totalFolders++;
                return FileVisitResult.CONTINUE;
            }
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                totalFiles++;
                totalBytes += attrs.size();
                files.add(file);
                return FileVisitResult.CONTINUE;
            }
            @Override
            public FileVisitResult visitFileFailed(Path file, IOException e) {
                System.err.println("Failed to access " + file + " | " + e.getMessage());
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public List<Path> getFiles() { return files; }

    public void print() {
        NumberFormat nf = NumberFormat.getInstance();
        System.out.println("===== Files, Folders, Sizes =====");
        System.out.printf("%-30s %15s\n", "Files:", nf.format(totalFiles));
        System.out.printf("%-30s %15s\n", "Folders [including root]:", nf.format(totalFolders));
        System.out.printf("%-30s %15s\n", "Size [in bytes]:", nf.format(totalBytes));
        System.out.println("=================================");
    }

}
