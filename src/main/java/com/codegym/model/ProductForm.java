package com.codegym.model;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;

@Component
public class ProductForm implements Validator {

    private Long id;
    private String createDate;
    private MultipartFile image;

    @NotEmpty
    @Size(min = 2, max = 30)
    private String name;

    private Double price;
    private Double quantity;
    private String description;
    private Integer active;


    public ProductForm() {}

    public ProductForm(String createDate, MultipartFile image, String name, Double price, Double quantity, String description, Integer active) {
        this.createDate = createDate;
        this.image = image;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.active = active;

    }

    public ProductForm(Long id, String createDate, MultipartFile image, String name, Double price, Double quantity, String description, Integer active) {
        this.id = id;
        this.createDate = createDate;
        this.image = image;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return ProductForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ProductForm productForm = (ProductForm) target;
        Double quantity = productForm.getQuantity();
        ValidationUtils.rejectIfEmpty(errors, "quantity", "quantity.empty");
        if (quantity <= 0 || quantity >= 10){
            errors.rejectValue("quantity", "quantity.rank");
        }
    }
}

