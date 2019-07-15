package com.example.learn.config;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {

		String message;
		if (exception.getCause() != null) {
			message = exception.getCause().getMessage();
			response.setStatus(403);
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, message);
		} else {
			message = exception.getMessage();
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, message);
		}

		// byte[] body = new
		// ObjectMapper().writeValueAsBytes(Collections.singletonMap("error", message));
		// response.getOutputStream().write(body);

	}

}
