package com.thd.pos_system_java_final.models.Product;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    List<Product> findAll();

    Product findByProductId(int productId);

    List<Product> findAllByProductNameContaining(String keyword);

    Product findByBarcode(String barcode);
}
