package com.thd.pos_system_java_final.controllers;

import com.thd.pos_system_java_final.models.Cart.Item;
import com.thd.pos_system_java_final.models.Product.Product;
import com.thd.pos_system_java_final.models.Product.ProductRepository;
import com.thd.pos_system_java_final.services.CartService;
import com.thd.pos_system_java_final.services.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private HttpSession session;

    @Autowired
    private CartService cartService;

    @GetMapping("")
    public String listProduct(Model model) {
        List<Product> productList = new ArrayList<>();
        productRepository.findAll().forEach(productList::add);
        model.addAttribute("imageUtils", imageService);
        model.addAttribute("products", productList);
        return "Product/index";
    }

    @PostMapping("/add")
    public String addProduct(String productName, String barcode, double retailPrice, double importPrice, String category, MultipartFile picture)
            throws IOException {
        Product p = new Product();

        p.setProductName(productName);
        p.setBarcode(barcode);
        p.setRetailPrice(retailPrice);
        p.setImportPrice(importPrice);
        p.setCategory(category);
        if (!picture.isEmpty()) {
                p.setPicture(picture.getBytes());
            } else {
                p.setPicture(getDefaultImageBytes());
            }
        p.setCreationDate(new Date());

        productRepository.save(p);

        return "redirect:/product";
    }

    @GetMapping("/add")
    public String addProduct() {
        return "/Product/add";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        Product p = productRepository.findById(id).get();
        model.addAttribute("product", p);
        model.addAttribute("imageUtils", imageService);
        return "/Product/edit";
    }

    @PostMapping("/edit")
    public String edit(int productId, String productName, String barcode, double retailPrice, double importPrice, String category, MultipartFile picture) throws IOException {
        Product p = productRepository.findById(productId).get();

        p.setProductName(productName);
        p.setBarcode(barcode);
        p.setRetailPrice(retailPrice);
        p.setImportPrice(importPrice);
        p.setCategory(category);

        if (!picture.isEmpty()) {
            p.setPicture(picture.getBytes());
        }

        productRepository.save(p);

        return "redirect:/product";
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    public String delete() {
        return "Not support this method";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        List<Item> items = (List<Item>) session.getAttribute("cart");
        if (!cartService.isInOrder(id)) {
            productRepository.deleteById(id);
        }

        return "redirect:/product";
    }

    private byte[] getDefaultImageBytes() {
        try {
            Resource resource = new ClassPathResource("static/images/default-product-image.png");
            return Files.readAllBytes(resource.getFile().toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}