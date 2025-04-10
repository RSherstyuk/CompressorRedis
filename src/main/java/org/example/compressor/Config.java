package org.example.compressor;

public class Config {
    public String getRedisHost() {
        return System.getenv().getOrDefault("REDIS_HOST", "localhost");
    }

    public int getRedisPort() {
        return Integer.parseInt(System.getenv().getOrDefault("REDIS_PORT", "6379"));
    }
}
