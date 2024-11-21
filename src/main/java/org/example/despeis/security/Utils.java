package org.example.despeis.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;


public class Utils {

    public static String getUserId(JwtAuthenticationToken jwtToken) {
        return jwtToken.getToken().getSubject();
    }

    public static String getEmail(JwtAuthenticationToken jwtToken) {
        return jwtToken.getToken().getClaimAsString("email");
    }

    public static String getFirstName(JwtAuthenticationToken jwtToken) {
        return jwtToken.getToken().getClaimAsString("given_name");
    }

    public static String getLastName(JwtAuthenticationToken jwtToken) {
        return jwtToken.getToken().getClaimAsString("family_name");
    }



}
