package com.codegym.controller;

import com.codegym.model.Product;
import com.codegym.model.ProductForm;
import com.codegym.service.ProductService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.io.*;
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

        //demo write json object

        JSONObject productDetail = new JSONObject();
        productDetail.put("image", "samsunggalaxy.jpg");
        productDetail.put("name", "Samsung Galaxy");
        productDetail.put("price", "300");

        JSONObject productObject = new JSONObject();
        productObject.put("product", productDetail);

        //Add product to list
        JSONArray productList = new JSONArray();
        productList.add(productObject);

        //Write JSON file
        try (FileWriter file = new FileWriter("/Users/nguyenkhanhtung/Documents/products.json")) {

            file.write(productList.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // demo read Json Object

        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("/Users/nguyenkhanhtung/Documents/products.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray productList1 = (JSONArray) obj;
            System.out.println(productList1);

            //Iterate over employee array
            productList1.forEach( p -> parseProductObject( (JSONObject) p ) );

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }



        Page<Product> products = productService.findAllPaging(pageable);

        //Iterable<Product> productList = productService.findAll();

        //List<Product> productsByName = productService.findTop2ByNameContaining("Iphone");

        ModelAndView modelAndView = new ModelAndView("/product/list");
        modelAndView.addObject("products", products);

        return modelAndView;
    }

    private static void parseProductObject(JSONObject product)
    {
        //Get product object within list
        JSONObject productObject = (JSONObject) product.get("product");

        //Get image
        String image = (String) productObject.get("image");
        System.out.println(image);

        //Get name
        String name = (String) productObject.get("name");
        System.out.println(name);

        //Get price
        String price = (String) productObject.get("price");
        System.out.println(price);
    }

    @GetMapping("/create-product")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/product/create");
        modelAndView.addObject("productform", new ProductForm());

        return modelAndView;
    }

    @RequestMapping(value = "/save-product", method = RequestMethod.POST)
    public ModelAndView saveProduct(@Valid @ModelAttribute("productform") ProductForm productform, BindingResult result) {

        new ProductForm().validate(productform, result);

        // thong bao neu xay ra loi
        if (result.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("/product/create");
            return modelAndView;
        }

        productService.saveUseStoreProcedure(productform);



        ModelAndView modelAndView = new ModelAndView("/product/create");
        modelAndView.addObject("productform", new ProductForm());
        modelAndView.addObject("message", "New product created successfully");
        return modelAndView;
    }
}
