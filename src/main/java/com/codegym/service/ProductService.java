package com.codegym.service;

import com.codegym.model.Product;
import com.codegym.model.ProductForm;

public interface ProductService extends GeneralService<Product>{

    void saveUseStoreProcedure(ProductForm productform);
}
