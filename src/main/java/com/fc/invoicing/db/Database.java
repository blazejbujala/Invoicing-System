package com.fc.invoicing.db;

import java.util.List;
import java.util.UUID;

public interface Database<T> {

    T add(T object);

    T getById(UUID id);

    List<T> getAll();

    T update(UUID id, T updatedInvoice);

    boolean delete(UUID id);

    void clear();
}