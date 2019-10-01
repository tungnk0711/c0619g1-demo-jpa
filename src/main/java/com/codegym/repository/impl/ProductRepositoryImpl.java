package com.codegym.repository.impl;

import com.codegym.model.Product;
import com.codegym.repository.ProductRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Transactional
public class ProductRepositoryImpl implements ProductRepository {
    @PersistenceContext
    private EntityManager em;


    @Override
    public void save(Product model) {
        if(model.getId() != null){
            em.merge(model);
        } else {
            em.persist(model);
        }
    }

    @Override
    public void add(Product product) {
        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String createDate = sdf.format(product.getCreateDate());


        StoredProcedureQuery spAddProduct = em.createNamedStoredProcedureQuery("addProduct");
        spAddProduct.setParameter("active", product.getActive());
        spAddProduct.setParameter("createDate", Timestamp.valueOf(createDate));
        spAddProduct.setParameter("description", product.getDescription());
        spAddProduct.setParameter("image", product.getImage());
        spAddProduct.setParameter("name", product.getName());
        spAddProduct.setParameter("price", product.getPrice());
        spAddProduct.setParameter("quantity", product.getQuantity());
        spAddProduct.execute();
    }


    @Override
    public List<Product> findAll() {
        //TypedQuery<Product> query = em.createQuery("select p from Product p", Product.class);
        //return query.getResultList();

        /*List<Product> productList = em.createNamedQuery("findAllProducts")
                .setParameter(1,Long.valueOf(2))
                .getResultList();
        return productList;*/

        /*List<Product> productList = em.createNamedQuery("findProductsById")
                .setParameter("id",Long.valueOf(2))
                .getResultList();
        return productList;*/

        StoredProcedureQuery getAllProductsQuery = em.createNamedStoredProcedureQuery("getAllProducts");
        getAllProductsQuery.execute();

        List<Product> productList = getAllProductsQuery.getResultList();

        return  productList;

    }
}
