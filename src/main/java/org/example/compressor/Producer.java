package org.example.compressor;

public class Producer {
    public static void main(String[] args) {
        Config config = new Config();
        RedisQueue queue = new RedisQueue(config.getRedisHost(), config.getRedisPort());

        // Сначала отправляем выходную директорию
        queue.push("OUTPUT_DIR:/path/to/output");

        // Затем файлы
        queue.push("/path/to/file1.txt");
        queue.push("/path/to/file2.txt");

        queue.close();
    }
}