package com.example.expensetracker.security;


import com.example.expensetracker.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
public class JwtUtils {

    private final String jwtSecret = "mySecretKey";
    private final long jwtExpirationMs = 86400000;

    public String generateToken(String email, Set<Role> roleSet) {
        List<String> rolesNames = roleSet.stream()
                .map(role -> "ROLE_" + role.getName().name())
                .toList();

        return Jwts.builder()
                .setSubject(email)
                .claim("roles", rolesNames)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
