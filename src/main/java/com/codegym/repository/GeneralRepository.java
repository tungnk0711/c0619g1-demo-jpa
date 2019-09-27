package com.codegym.repository;

import com.codegym.model.Product;

import java.util.List;

public interface GeneralRepository<E> {
    List<E> findAll();
    void save(E e);

    void add(E e);
}
