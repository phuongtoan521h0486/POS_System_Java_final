package com.thd.pos_system_java_final.models.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

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
