package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hashing {

    // md5 and sha-256 hashing methods
    public static String md5(Path path) throws IOException {
        return hash(path, "MD5");
    }
    public static String sha256(Path path) throws IOException {
        return hash(path, "SHA-256");
    }

    private static String bytesToHex(byte[] bytes) { // convert to hex for readability
        StringBuilder result = new StringBuilder();
        for (byte b: bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

    // actual hashing function
    private static String hash(Path path, String algorithm) throws IOException {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);

            try (InputStream in = Files.newInputStream(path)) { // reading file in 8kb chunks for efficiency
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) { // until EOF
                    digest.update(buffer, 0, bytesRead);
                }
            }

            return bytesToHex(digest.digest());

        } catch (NoSuchAlgorithmException e) { // include anyway
            throw new RuntimeException("Invalid algorithm: " + algorithm);
        }
    }
}
