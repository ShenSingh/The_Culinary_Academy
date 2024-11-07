package com.zenveus.the_culinary_academy.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptHasher {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String hashPassword(String password) {
        return encoder.encode(password);
    }

    public static boolean verifyPassword(String password, String hash) {
        return encoder.matches(password, hash);
    }
}
