package com.personal.financial_dashboard_backend.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtTokenProvider {

	private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);

	@Value("${app.jwt.secret}")
	private String jwtSecret;

	@Value("${app.jwt.expiration}")
	private int jwtExpirationMs;

	private Key key;

	@PostConstruct
	public void init() {
		if (jwtSecret.getBytes().length < 32) {
			throw new IllegalArgumentException("JWT secret key must be at least 256 bits long");
		}
		key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
	}

	public String generateToken(Authentication authentication) {

		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

		Date currentDate = new Date();

		Date expiryDate = new Date(currentDate.getTime() + jwtExpirationMs);

		Map<String, Object> claims = new HashMap<>();
		claims.put("sub", userPrincipal.getUsername());
		claims.put("id", userPrincipal.getId());
		claims.put("created", currentDate);
		claims.put("typ", "access");

		return Jwts.builder().setClaims(claims).setIssuedAt(currentDate).setExpiration(expiryDate)
				.signWith(key, SignatureAlgorithm.HS512).compact();
	}

	public Long getUserIdFromToken(String token) {
		Claims claims = getAllClaimsFromToken(token);
		return ((Number) claims.get("id")).longValue();
	}

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		try {
			return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
		} catch (Exception e) {
			log.error("Failed to parse JWT token: {}", e.getMessage());
			throw new RuntimeException("Invalid JWT token: " + e.getMessage());
		}
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		boolean isExpired = expiration.before(new Date());
		if (isExpired) {
			System.out.println("Token has expired"); // Replace with proper logging
		}
		return isExpired;
	}

}
