package com.example.akatsuki.service.Jwt;


import com.example.akatsuki.model.Admin;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;

@Service
public class AdminJWTService {

    //Generating SecretKey
    @Value("${jwt.secret}")
    private String secretKey1;
    @Qualifier("customAdminDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;

//    public AdminJWTService(){
//        try {
//            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
//            SecretKey sk1 = keyGen.generateKey();
//            secretKey1= Base64.getEncoder().encodeToString((sk1).getEncoded());
//            System.out.println("secretKey1: " + secretKey1);
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey1);
        System.out.println("secretKey1: " + Keys.hmacShaKeyFor(keyBytes));
        return Keys.hmacShaKeyFor(keyBytes);// Decode the base64 encoded secret key and create a Key object
    }

    //Generating SecretKey
    //Generating JWT Token via secretKey
    public String generateToken(Admin admin) {
        Map<String, Objects> claims1=new HashMap<>();
        String strNumber1 = String.valueOf(admin.getAdminId());
        return Jwts.builder()
                .claims().add(claims1)
                .subject(strNumber1)
                .issuer("DCB")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+60*10*1000))
                .and()
                .signWith(getKey())
                .compact();
    }

    public String extractUsername(String token) {
        // extract the username from jwt token
        return extractClaims(token, Claims::getSubject);
    }

    private <T>T extractClaims(String token, Function<Claims,T> claimsResolver) {
        Claims claims = extractClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenValid(String token, UserDetails userDetails1) {
        final String username = extractUsername(token);
        return (userDetails1.getUsername().equals(username) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }
}
