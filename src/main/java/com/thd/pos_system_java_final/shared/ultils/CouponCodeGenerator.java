package com.thd.pos_system_java_final.shared.ultils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class CouponCodeGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int CODE_LENGTH = 10;
    private static final String PREFIX = "GTV";

    public static String generateRandomCode() {
        StringBuilder code = new StringBuilder(PREFIX);
        while (code.length() < CODE_LENGTH) {
            code.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }

        return code.toString();
    }

}
