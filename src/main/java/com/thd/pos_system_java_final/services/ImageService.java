package com.thd.pos_system_java_final.services;

import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class ImageService {

    public static String encodeImage(byte[] image) {
        return Base64.getEncoder().encodeToString(image);
    }
}

