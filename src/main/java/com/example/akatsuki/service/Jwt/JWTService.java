package com.example.akatsuki.service.Jwt;


import com.example.akatsuki.CustomUserDetails;
import com.example.akatsuki.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.micrometer.observation.Observation;
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
public class JWTService {

    //Generating SecretKey
    @Value("${jwt.secret}")
    private String secretKey="";
    @Qualifier("customUserDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;

    //Via this we are generating a secret key
//    public JWTService() {
//        try {
//            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
//            SecretKey sk = keyGen.generateKey();
//            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
//            System.out.println("secretKey: " + secretKey);
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//    }

    private SecretKey generateKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        System.out.println("secretKey: " + Keys.hmacShaKeyFor(keyBytes));
        return Keys.hmacShaKeyFor(keyBytes);// Decode the base64 encoded secret key and create a Key object
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);// Decode the base64 encoded secret key
        return Keys.hmacShaKeyFor(keyBytes);// Decode the base64 encoded secret key and create a Key object
    }

    public String generateToken(User user) {
        Map<String, List> claims=new HashMap<>();
        claims.put("role", Arrays.asList("USER"));
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(user.getUsername())
                .issuer("DCB")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+ 60 * 10*1000))
                .and()
                .signWith(generateKey())
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T>T extractClaims(String token, Function<Claims,T> claimsResolver) {
        Claims claims = extractClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUsername(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }
}
