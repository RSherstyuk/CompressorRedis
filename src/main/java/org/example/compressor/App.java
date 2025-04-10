package org.example.compressor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    private static final int TIMEOUT_SEC = 1;
    private static final String OUTPUT_PREFIX = "OUTPUT_DIR:";

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java -jar compressor.jar <output_directory>");
            System.exit(1);
        };

        Config config = new Config();
        RedisQueue queue = new RedisQueue(config.getRedisHost(), config.getRedisPort());
        Path outputDir = Paths.get(args[0]).toAbsolutePath();
        AtomicBoolean stopFlag = new AtomicBoolean(false);

        // Создаем директорию, если не существует
        try {
            Files.createDirectories(outputDir);
        } catch (IOException e) {
            logger.error("Cannot create output directory: " + outputDir, e);
            System.exit(1);
        }

        int threadCount = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        logger.info("Starting consumer with {} threads", threadCount);
        logger.info("Output directory: {}", outputDir);

        // Запускаем worker'ы
        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                while (!stopFlag.get()) {
                    try {
                        String message = queue.pop(TIMEOUT_SEC);
                        if (message == null) continue;

                        if (message.startsWith(OUTPUT_PREFIX)) {
                            // Обработка директории (если нужно)
                            continue;
                        }

                        Path inputPath = Paths.get(message);
                        FileCompressor.compressFile(inputPath, outputDir);
                    } catch (Exception e) {
                        logger.error("Error processing message", e);
                    }
                }
            });
        }

        // Ожидаем завершения
        System.out.println("Press Enter to stop...");
        new Scanner(System.in).nextLine();
        stopFlag.set(true);

        try {
            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.warn("Shutdown interrupted", e);
        } finally {
            queue.close();
        }
    }
}