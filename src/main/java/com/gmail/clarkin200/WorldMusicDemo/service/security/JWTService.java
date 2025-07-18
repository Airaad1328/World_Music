package com.gmail.clarkin200.WorldMusicDemo.service.security;

import com.gmail.clarkin200.WorldMusicDemo.model.user.UserCredential;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service("JWTService")
public class JWTService {

    @Value("${token.secret.key}")
    private String key;

    public String extractUserEmail (String token) {
        return extractClaim(token,Claims::getSubject);
    }

    public String generateToken(UserDetails userDetails) {
        Map<String,Object> claims = new HashMap<>();
        if(userDetails instanceof UserCredential user) {
            claims.put("id",user.getUserId());
            claims.put("email",user.getUsername());
            claims.put("role",user.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList());
            claims.put("username",user.getUsername());
        }
        return generateToken(claims,userDetails);
    }

    public boolean isTokenValid (String token,UserDetails userDetails) {
        final String userEmail = extractUserEmail(token);
        return (userEmail.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private <T> T extractClaim (String token, Function<Claims,T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private String generateToken(Map<String,Object> extraClaims,UserDetails userDetails) {
        return Jwts.builder().setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 100000 * 60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    public boolean isTokenExpired (String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
