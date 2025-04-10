package org.example.compressor;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisQueue {
    private static final String QUEUE_NAME = "file_queue";
    private final JedisPool jedisPool;

    public RedisQueue(String host, int port) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(128);
        this.jedisPool = new JedisPool(poolConfig, host, port);
    }

    public void push(String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.lpush(QUEUE_NAME, value);
        }
    }

    public String pop(int timeoutSec) {
        try (Jedis jedis = jedisPool.getResource()) {
            var result = jedis.brpop(timeoutSec, QUEUE_NAME);
            return result != null && result.size() > 1 ? result.get(1) : null;
        }
    }

    public void close() {
        jedisPool.close();
    }
}