package com.sec.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtService {

    String SECRET_KEY = "f7a8d7b5b7d2fcf1d3e5b896f647c0a2e9fa32e1f3aa5c5f1f81b8c5b7b9c1a0";

    public String extractUserName(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        Key signingKey = getSigningKey();
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY); //actual decoding of SECRET_KEY
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //generating a token
    public String generateToken(
            Map<String, Object> extraClaims, UserDetails userDetails
    ) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))// creation date of the token
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 2)) // two hour exp date
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)// secret key + algorithm
                .compact(); // close the building process(.build();)
    }

    //generating token without claims
    public String generateTokenNoClaims(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    //return ture if the token is expired
    public boolean isTokenExpired(String token) {
        return extractExpirationDate(token).before(new Date());
    }

    //return the Expiration date from token
    private Date extractExpirationDate(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    //validating the token and returns true if it's not expired
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return userDetails.getUsername().equals(username) && !isTokenExpired(token);
    }

}
