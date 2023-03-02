package com.zerobase.carrot_auction.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
public class JwtExceptionFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		log.info("Jwt Exception Filter");
		try {
			log.info("Jwt Exception Filter try");
			filterChain.doFilter(request, response);
		} catch (JwtException e) {
			log.info("Jwt Exception Filter Catch");
			setErrorResponse(request, response, e);
		}
	}

	private void setErrorResponse(HttpServletRequest request, HttpServletResponse response,
		JwtException e) throws IOException {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		final Map<String, Object> body = new HashMap<>();
		body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
		body.put("error", "Unauthorized");
		body.put("message", e.getMessage());
		body.put("path", request.getServletPath());

		final ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getOutputStream(), body);
		response.setStatus(HttpServletResponse.SC_OK);
		log.debug(response.toString());
	}
}
