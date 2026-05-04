package kz.narxoz.onlinelibrary.service;

import java.util.List;

public interface CrudService<T> {
    List<T> getAll();
    T getById(Long id);
    T create(T item);
    T update(Long id, T item);
    void delete(Long id);
}
