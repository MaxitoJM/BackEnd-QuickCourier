package com.quickcourier.quickcourier_api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Utilidad para generar y validar tokens JWT con HS256.
 * Compatible con JJWT 0.11.x y Spring Boot 3.3.x
 */
@Component
public class JwtTokenUtil {
    
    private final SecretKey key;
    private final long expiration;

    public JwtTokenUtil(JwtProperties props) {
        // ⚠️ Asegúrate de que jwt.secret tenga al menos 32 bytes de longitud
        this.key = Keys.hmacShaKeyFor(props.getSecret().getBytes(StandardCharsets.UTF_8));
        this.expiration = props.getExpiration();
    }

    /** Genera un JWT firmado con HS256 */
    public String generateToken(String email) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expiration);
        
        return Jwts.builder()
                .setSubject(email)           // ✅ setSubject en lugar de subject
                .setIssuedAt(now)            // ✅ setIssuedAt en lugar de issuedAt
                .setExpiration(exp)          // ✅ setExpiration en lugar de expiration
                .signWith(key, SignatureAlgorithm.HS256)  // ✅ SignatureAlgorithm.HS256
                .compact();
    }

    /** Extrae el email (subject) del token */
    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parserBuilder()    // ✅ parserBuilder en lugar de parser
                .setSigningKey(key)             // ✅ setSigningKey en lugar de verifyWith
                .build()
                .parseClaimsJws(token)          // ✅ parseClaimsJws en lugar de parseSignedClaims
                .getBody();                     // ✅ getBody en lugar de getPayload
        
        return claims.getSubject();
    }

    /** Valida la firma y expiración del token */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()               // ✅ parserBuilder en lugar de parser
                .setSigningKey(key)            // ✅ setSigningKey en lugar de verifyWith
                .build()
                .parseClaimsJws(token);        // ✅ parseClaimsJws en lugar de parseSignedClaims
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("❌ Token inválido: " + e.getMessage());
            return false;
        }
    }
}