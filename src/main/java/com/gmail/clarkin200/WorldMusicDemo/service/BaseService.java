package com.gmail.clarkin200.WorldMusicDemo.service;

import java.util.List;
import java.util.Optional;

public interface BaseService <T,ID> {
    Optional<T> findById (ID id);
    Optional<List<T>> fetchAll();
    boolean deleteById (ID id);
    Optional<T> create (T entity);
    Optional<T> update (T entity);

}
