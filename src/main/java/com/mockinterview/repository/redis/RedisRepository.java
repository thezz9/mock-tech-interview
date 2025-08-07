package com.mockinterview.repository.redis;

import java.time.Duration;
import java.util.Optional;

public interface RedisRepository {
    void save(String key, String value, Duration ttl);
    Optional<String> findByKey(String key);
    boolean existsByKey(String key);
    void deleteByKey(String key);
}
