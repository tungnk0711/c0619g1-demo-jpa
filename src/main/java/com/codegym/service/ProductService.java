package com.codegym.service;

import com.codegym.model.Product;
import com.codegym.model.ProductForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService extends GeneralService<Product>{

    void saveUseStoreProcedure(ProductForm productform);

    List<Product> findTop2ByNameContaining(String name);

    Page<Product> findAllPaging(Pageable pageable);
}
