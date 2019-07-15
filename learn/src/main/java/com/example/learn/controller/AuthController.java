package com.example.learn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.learn.Util.JwtUtil;
import com.example.learn.metaData.JwtToken;
import com.example.learn.metaData.ResponseBody;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/login")
	public ResponseEntity<ResponseBody<JwtToken>> signIn(@RequestParam("username") String username,
			@RequestParam("password") String password) {

		final Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		Claims claims = Jwts.claims();
		claims.setSubject(username);
		final JwtToken token = jwtUtil.doGenerateToken(claims, username);
		ResponseBody<JwtToken> b = new ResponseBody<>();
		b.setEntity(token);
		return ResponseEntity.ok(b);
	}

}