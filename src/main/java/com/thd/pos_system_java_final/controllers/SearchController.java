package com.thd.pos_system_java_final.controllers;

import com.thd.pos_system_java_final.models.Product.Product;
import com.thd.pos_system_java_final.models.Product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SearchController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/search")
    @ResponseBody
    public List<Product> searchProducts(@RequestParam String keyword) {
        return productRepository.findAllByProductNameContaining(keyword);
    }

    @GetMapping("/searchBarcode")
    public Product searchBarcode(@RequestParam String barcode) {
        return productRepository.findByBarcode(barcode);
    }
}

