package com.example.learn.Util;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.learn.metaData.JwtToken;
import com.example.learn.model.ApplicationUser;
import com.example.learn.model.Constants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	public static void main(String[] args) {
		compare();
	}
	public static void compare() {
		Long gid  = (long) 345;
		String g1 = "345";
		if(g1.equals(gid)) {
			System.out.println("Yup");
		}
	}
	
	// retrieve username from jwt token
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	// retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	// for retrieveing any information from token we will need the secret key
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(Constants.SECRET).parseClaimsJws(token).getBody();
	}

	// check if the token has expired
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public JwtToken doGenerateToken(Claims claims, String subject) {

		// The JWT signature algorithm we will be using to sign the token
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(Constants.SECRET);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		// Let's set the JWT Claims
		JwtBuilder builder = Jwts.builder().setIssuedAt(new Date(System.currentTimeMillis())).setSubject(subject)
				.setIssuer("auth").setClaims(claims)
				.setExpiration(new Date(System.currentTimeMillis() + Constants.JWT_TOKEN_VALIDITY * 1000))
				.setHeaderParam("Type", "Jwt").signWith(signingKey, signatureAlgorithm);

		JwtToken jwt = new JwtToken();
		jwt.setJwtToken(builder.compact());
		return jwt;
	}
}
