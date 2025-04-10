package org.example.compressor;

import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileCompressor {
    private static final Logger logger = LoggerFactory.getLogger(FileCompressor.class);

    public static void compressFile(Path inputPath, Path outputDir) throws IOException {
        Path outputPath = outputDir.resolve(inputPath.getFileName() + ".gz");

        try (InputStream input = Files.newInputStream(inputPath);
             OutputStream output = Files.newOutputStream(outputPath);
             GzipCompressorOutputStream gzipOutput = new GzipCompressorOutputStream(output)) {

            byte[] buffer = new byte[8192];
            int len;
            while ((len = input.read(buffer)) > 0) {
                gzipOutput.write(buffer, 0, len);
            }

            logger.info("Compressed: {} -> {}", inputPath, outputPath);
        } catch (IOException e) {
            logger.error("Error compressing file: " + inputPath, e);
            throw e;
        }
    }
}