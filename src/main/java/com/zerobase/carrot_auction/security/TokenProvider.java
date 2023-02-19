package com.zerobase.carrot_auction.security;

import com.zerobase.carrot_auction.repository.entity.RoleEntity;
import com.zerobase.carrot_auction.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class TokenProvider {

	private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 2; //2시간 만료
	private static final String KEY_ROLES = "roles";

	private final UserService userService;
	@Value("{spring.jwt.secret}")
	private String secretKey;

	public String generateToken(String email, List<RoleEntity> roles) {
		Claims claims = Jwts.claims();
		claims.setSubject(email);
		claims.put(KEY_ROLES, roles
				.stream().map(RoleEntity::getRoleName)
				.collect(Collectors.toList()));

		Date now = new Date();
		Date expiredDate = new Date(now.getTime() + TOKEN_EXPIRE_TIME);

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(expiredDate)
			.signWith(SignatureAlgorithm.HS512, this.secretKey)
			.compact();
	}

	public boolean verifyToken(String token) {
		if (!StringUtils.hasText(token)) {
			return false;
		}

		Claims claims = this.parseClaims(token);
		return claims.getExpiration().before(new Date());
	}

	private Claims parseClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJwt(token).getBody();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}

	public String getEmail(String token) {
		return this.parseClaims(token).getSubject();
	}

	public Authentication getAuthentication(String token) {
		UserDetails userDetails = this.userService.loadUserByUsername(this.getEmail(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "",
			userDetails.getAuthorities());
	}
}
