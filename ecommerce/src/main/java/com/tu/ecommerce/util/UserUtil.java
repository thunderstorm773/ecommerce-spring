package com.tu.ecommerce.util;

import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.List;

public class UserUtil {

    public static String getUsername(Jwt jwt) {
        return jwt.getSubject();
    }

    public static List<String> getUserAuthorities(Jwt jwt) {
        if (jwt == null) {
            return new ArrayList<>();
        }

        return jwt.getClaimAsStringList("groups");
    }

    public static boolean isUserAdmin(Jwt jwt) {
        List<String> loggedUserAuthorities = getUserAuthorities(jwt);
        boolean isAdmin = false;
        if (loggedUserAuthorities.contains("Admin")) {
            isAdmin = true;
        }

        return isAdmin;
    }

    public static String getUserFullname(Jwt jwt) {
        return jwt.getClaimAsString("fullname");
    }
}
