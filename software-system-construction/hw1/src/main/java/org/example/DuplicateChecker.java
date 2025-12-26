package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class DuplicateChecker {
    private final String algorithm;

    public DuplicateChecker(String algorithm) { // constructor to hold algorithm type (whatever we fed)
        this.algorithm = algorithm.toLowerCase();
    }

    public Map<String, List<Path>> findDuplicates(List<Path> paths) throws IOException {
        Map<String, List<Path>> groups = new HashMap<>();

        // traverse each file, continue if not a file (but a directory)
        for (Path path: paths) {
            if (!Files.isRegularFile(path)) continue;

            String key;
            switch (algorithm) { // creating keys for each group, group by hash for md5, sha256 but group by size for bbb
                case "md5" -> key = Hashing.md5(path);
                case "sha256" -> key = Hashing.sha256(path);
                case "bbb" -> key = Files.size(path) + ""; // size as a string
                default -> throw new RuntimeException("Invalid or unsupported algorithm " + algorithm);
            }

            groups.computeIfAbsent(key, k -> new ArrayList<>()).add(path); // add file to group
        }

        if (algorithm.equals("bbb")) groups = refineBBB(groups); // for bbb, refine groups with actual bbb comparisons not just size

        return groups;

    }
    private Map<String, List<Path>> refineBBB(Map<String, List<Path>> groups) throws IOException {
        Map<String, List<Path>> finalGroup = new HashMap<>();
        int groupID = 0;

        for (List<Path> files : groups.values()) {
            while (!files.isEmpty()) {
                Path base = files.remove(0); // use the very first file in group as comparison
                List<Path> duplicates = new ArrayList<>();
                duplicates.add(base);

                Iterator<Path> iter = files.iterator();
                while (iter.hasNext()) {
                    Path compare = iter.next();
                    if (BBB.isSame(base, compare)) {
                        duplicates.add(compare);
                        iter.remove();
                    }
                }

                if (duplicates.size() > 1) { // meaning there in fact duplicates
                    finalGroup.put("duplicate-" + (groupID++), duplicates);
                }
            }
        }

        return finalGroup;
    }
}
