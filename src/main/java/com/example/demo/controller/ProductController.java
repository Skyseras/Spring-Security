package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import com.example.demo.service.dto.ProductNotExistException;
import com.example.demo.service.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/")
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> listProducts = productService.listProducts();
        return new ResponseEntity<>(listProducts, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> addProduct(@RequestBody Product product) {
        productService.addProduct(product);
        return new ResponseEntity<>(new ResponseDTO("true", "Product has been added"), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{productID}")
    public ResponseEntity<ResponseDTO> deleteProduct(@PathVariable("productID") Integer productID) throws ProductNotExistException {
        productService.deleteProduct(productID);
        return new ResponseEntity<>(new ResponseDTO("true", "Product has been removed"), HttpStatus.OK);
    }
}