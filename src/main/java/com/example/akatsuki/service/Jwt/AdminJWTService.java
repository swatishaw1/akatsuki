package com.example.akatsuki.service.Jwt;


import com.example.akatsuki.model.Admin;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class AdminJWTService {

//    @Value("${jwt.secret}")
    private String secretKey1;

    public AdminJWTService(){
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk1 = keyGen.generateKey();
            secretKey1= Base64.getEncoder().encodeToString((sk1).getEncoded());
            System.out.println("secretKey1: " + secretKey1);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

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

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey1);
        System.out.println("secretKey1: " + Keys.hmacShaKeyFor(keyBytes));
        return Keys.hmacShaKeyFor(keyBytes);// Decode the base64 encoded secret key and create a Key object
    }
}
