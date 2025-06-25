package com.tu.ecommerce.util;

import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

public class UserUtil {

    public static String getUsername(Jwt jwt) {
        return jwt.getSubject();
    }

    public static List<String> getUserAuthorities(Jwt jwt) {
        return jwt.getClaimAsStringList("groups");
    }

    public static String getUserFullname(Jwt jwt) {
        return jwt.getClaimAsString("fullname");
    }
}
