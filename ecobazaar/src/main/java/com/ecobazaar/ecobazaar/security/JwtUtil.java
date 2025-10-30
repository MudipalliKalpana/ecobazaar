package com.ecobazaar.ecobazaar.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private final SecretKey key;
	private final long expirationMs;

	public JwtUtil(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration-ms}") long expirationMs) {
		this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
		this.expirationMs = expirationMs;
	}

//	@SuppressWarnings("deprecation")
	public String generateToken(String email, String role, Long userId) {
		return Jwts.builder()
				.subject(email)
				.claim("role", role)
				.claim("userId", userId)
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + expirationMs))
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();

	}

//	public String generateToken(String email, String role, Long userId) {
//		Date now = new Date();
//		Date expirationDate = new Date(now.getTime() + expirationMs);
//
//		return Jwts.builder()
//				.claims(Map.of(
//						Claims.SUBJECT, email,
//						"role", role,
//						"userId", userId
//				))
//				.issuedAt(now)
//				.expiration(expirationDate)
//				.signWith(key)
//				.compact();
//	}
	public Boolean validateToken(String token) {
		try {
			Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}

	public Claims getClaims(String token) {
		Jws<Claims> jws = Jwts.parser()
				.verifyWith(key)
				.build()
				.parseSignedClaims(token);

		return jws.getPayload();

	}

}