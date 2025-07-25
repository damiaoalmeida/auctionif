package br.edu.ifpb.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.edu.ifpb.entity.Role;
import br.edu.ifpb.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;

@Service
public class JwtService {
	
	@Value("${jwtSecret}")
	private String jwtSecret;

	@Value("${jwtExpirationMs}")
	private int jwtExpirationMs;
	
	private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders
        		.BASE64
        		.decode(new String(jwtSecret.getBytes(StandardCharsets.UTF_8)));
        return new SecretKeySpec(keyBytes, "HmacSHA256");
    }

	public String generateToken(Authentication authentication) {
		return generateToken((User)(authentication.getPrincipal()));
//		String[] roles = ((User)(authentication.getPrincipal())).arrayRoles();
//
//		Map<String, String[]> claims = new HashMap<>();
//        claims.put("roles", roles); // Adiciona a lista de roles no token
//
//    	return Jwts.builder()
//                .subject(authentication.getName())
//                .claims(claims)
//                .issuedAt(new Date())
//                .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
//                .signWith(getSigningKey()) // Assina o token com a chave segura
//                .compact();
    }
	
	public String generateToken(User user) {
		String[] roles = user.arrayRoles();

		Map<String, String[]> claims = new HashMap<>();
        claims.put("roles", roles); // Adiciona a lista de roles no token

    	return Jwts.builder()
                .subject(user.getUsername())
                .claims(claims)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSigningKey()) // Assina o token com a chave segura
                .compact();
    }
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
    }

	public boolean validateJwtToken(String token, String username) {
		try {
			Jwts.parser()
				.verifyWith(getSigningKey())
				.build()
				.parse(token);

			return extractUsername(token).equals(username);
		} catch (MalformedJwtException e) {
			System.out.println("Invalid JWT token: {}" + e.getMessage());
		} catch (ExpiredJwtException e) {
			System.out.println("JWT token is expired: {}" + e.getMessage());
		} catch (UnsupportedJwtException e) {
			System.out.println("JWT token is unsupported: {}" + e.getMessage());
		} catch (IllegalArgumentException e) {
			System.out.println("JWT claims string is empty: {}" + e.getMessage());
		}

		return false;
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parser()
        		.verifyWith(getSigningKey())
        		.build()
        		.parseSignedClaims(token)
        		.getPayload();
        return claimsResolver.apply(claims);
    }
}