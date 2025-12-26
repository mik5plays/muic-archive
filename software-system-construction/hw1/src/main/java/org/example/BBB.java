package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class BBB {
    public static boolean isSame(Path a, Path b) throws IOException {
        if (Files.size(a) != Files.size(b)) return false; // different file sizes means they cannot be the same

        // compare bytes and buffer
        try (InputStream inputA = Files.newInputStream(a); InputStream inputB = Files.newInputStream(b)) {
            byte[] bufferA = new byte[8192];
            byte[] bufferB = new byte[8192];

            int numReadA, numReadB;
            while ((numReadA = inputA.read(bufferA)) != -1) {
                numReadB = inputB.read(bufferB);
                if (numReadA != numReadB) return false; // should read the same amount of bytes, if not then not the same
                for (int i = 0; i < numReadA; i++) {
                    if (bufferA[i] != bufferB[i]) return false; // byte by byte comparison
                }
            }
        }
        return true; // then they are the same
    }
}
