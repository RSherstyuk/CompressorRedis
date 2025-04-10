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
