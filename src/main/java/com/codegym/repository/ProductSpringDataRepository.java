package com.codegym.repository;

import com.codegym.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProductSpringDataRepository extends PagingAndSortingRepository<Product, Long> {

    List<Product> findTop2ByNameContaining(String name);
}
