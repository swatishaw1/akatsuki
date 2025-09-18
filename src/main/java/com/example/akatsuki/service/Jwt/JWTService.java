package com.example.akatsuki.service.Jwt;


import com.example.akatsuki.model.User;
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
public class JWTService {

//    @Value("${jwt.secret}")
    private String secretKey="";

    //Via this we are generating a secret key
    public JWTService() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGen.generateKey();
            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
            System.out.println("secretKey: " + secretKey);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public String generateToken(User user) {
        Map<String, Objects> claims = new HashMap<>();
        String strNumber = String.valueOf(user.getUserId());
        return Jwts.builder()
                .claims().add(claims).subject(strNumber).issuer("DCB")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+ 60 * 10*1000))
                .and()
                .signWith(generateKey())
                .compact();
    }

    private SecretKey generateKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        System.out.println("secretKey: " + Keys.hmacShaKeyFor(keyBytes));
        return Keys.hmacShaKeyFor(keyBytes);// Decode the base64 encoded secret key and create a Key object
    }
}
