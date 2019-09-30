package com.codegym.service.impl;

import com.codegym.model.Product;
import com.codegym.model.ProductForm;
import com.codegym.repository.ProductRepository;
import com.codegym.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@PropertySource("classpath:global_config_app.properties")
public class ProductServiceImpl implements ProductService {

    @Autowired
    Environment env;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public void save(Product product) {

        productRepository.save(product);
    }

    @Override
    public void add(Product product) {
        productRepository.add(product);
    }

    public void saveUseStoreProcedure(ProductForm productform){
        // lay ten file
        MultipartFile multipartFile = productform.getImage();
        String fileName = multipartFile.getOriginalFilename();
        String fileUpload = env.getProperty("file_upload").toString();

        // luu file len server
        try {
            //multipartFile.transferTo(imageFile);
            FileCopyUtils.copy(productform.getImage().getBytes(), new File(fileUpload + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        // tham khảo: https://github.com/codegym-vn/spring-static-resources

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date lDate = formatter.parse(productform.getCreateDate());
            // tao doi tuong de luu vao db
            Product productObject = new Product(lDate, fileName, productform.getName(), productform.getPrice(), productform.getQuantity(), productform.getDescription(), productform.getActive());
            // luu vao db
            //productService.save(productObject);
            //productService.save(productObject);
            productRepository.add(productObject);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
