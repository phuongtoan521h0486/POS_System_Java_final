package com.thd.pos_system_java_final.models.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int productId;
    @Column(unique = true)
    private String barcode;
    private String productName;
    private double importPrice;
    private double retailPrice;
    private String category;
    private Date creationDate;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] picture;
}
