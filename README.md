# Redis File Compressor

A distributed file compression system using Redis as a message queue, implemented in Java with Maven.

## Features

- **Distributed processing**: Multiple consumers can work simultaneously
- **Multi-threaded**: Utilizes all available CPU cores
- **Redis-backed queue**: Reliable message delivery between producers and consumers
- **GZIP compression**: Efficient file compression using Apache Commons Compress
- **Easy configuration**: Environment variables for Redis connection

## Prerequisites

- Java 11+
- Maven 3.6+
- Redis server 5.0+
- Apache Commons Compress
- Jedis (Redis Java client)

## Installation

1. Clone the repository:
```bash
git clone https://github.com/yourusername/redis-file-compressor.git
cd redis-file-compressor

Build the project:
mvn clean package

java -jar target/redis-file-compressor-1.0-SNAPSHOT.jar /path/to/output/directory

``` java
RedisQueue queue = new RedisQueue("localhost", 6379);
queue.push("OUTPUT_DIR:/path/to/output");  // Set output directory first
queue.push("/path/to/file1.txt");          // Add files to process
queue.push("/path/to/file2.txt");
queue.close();
```
