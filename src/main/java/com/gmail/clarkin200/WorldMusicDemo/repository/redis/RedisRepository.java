package com.gmail.clarkin200.WorldMusicDemo.repository.redis;

import java.util.List;
import java.util.Optional;

public interface RedisRepository <T,V> {
    Optional<List<T>> fetchAll ();
    Optional<T> findById (V id);
    boolean deleteById (V id);
    Optional<T> create (T entity);
}
