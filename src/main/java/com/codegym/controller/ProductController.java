package com.codegym.controller;

import com.codegym.model.Product;
import com.codegym.model.ProductForm;
import com.codegym.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Controller
public class ProductController {



    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ModelAndView showProducts(@PageableDefault(value = 2) Pageable pageable) {

        Page<Product> products = productService.findAllPaging(pageable);

        //Iterable<Product> productList = productService.findAll();

        //List<Product> productsByName = productService.findTop2ByNameContaining("Iphone");

        ModelAndView modelAndView = new ModelAndView("/product/list");
        modelAndView.addObject("products", products);

        return modelAndView;
    }

    @GetMapping("/create-product")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/product/create");
        modelAndView.addObject("productform", new ProductForm());

        return modelAndView;
    }

    @RequestMapping(value = "/save-product", method = RequestMethod.POST)
    public ModelAndView saveProduct(@ModelAttribute ProductForm productform, BindingResult result) {

        // thong bao neu xay ra loi
        if (result.hasErrors()) {
            System.out.println("Result Error Occured" + result.getAllErrors());
        }

        productService.saveUseStoreProcedure(productform);



        ModelAndView modelAndView = new ModelAndView("/product/create");
        modelAndView.addObject("productform", new ProductForm());
        modelAndView.addObject("message", "New product created successfully");
        return modelAndView;
    }
}
