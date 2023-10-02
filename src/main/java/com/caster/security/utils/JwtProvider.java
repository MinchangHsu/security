package com.caster.security.utils;


import com.caster.security.bean.ConfigConstant;
import com.caster.security.enums.TokenType;
import com.caster.security.model.JsonWebToken;
import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

/**
 * Author: Caster
 * Date: 2021/8/27
 * Comment:
 */
@Component
public class JwtProvider {
	private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

	public String generateUserLoginToken(Long id, JsonWebToken jsonWebToken) {
		return builder(id, jsonWebToken);
	}

	public String generateVerifyToken(Long id, JsonWebToken jwtObj) {

		// 隔日凌晨00:00
		LocalDateTime tomorrowMidnight =
				LocalDateTime.of(DateTimeUtil.getNowLocal().toLocalDate().plusDays(1), LocalTime.MIDNIGHT);
		tomorrowMidnight.atZone(ZoneId.systemDefault());

		Map<String, Object> extra = new HashMap<>();
		extra.put("type", TokenType.VERIFICATION.getType());

		jwtObj.setExtra(extra);
		jwtObj.setExpireTimeMs(DateTimeUtil.convertLocalDateTimeToDate(tomorrowMidnight).getTime());

		return builder(id, jwtObj);
	}

	public String generateFreshToken(Long id, JsonWebToken jwtObj) {

		// 隔日凌晨00:00
		LocalDateTime tomorrowMidnight =
				LocalDateTime.of(DateTimeUtil.getNowLocal().toLocalDate().plusDays(1), LocalTime.MIDNIGHT);
		tomorrowMidnight.atZone(ZoneId.systemDefault());

		Map<String, Object> extra = new HashMap<>();
		extra.put("type", TokenType.REFRESH.getType());

		jwtObj.setExtra(extra);
		jwtObj.setExpireTimeMs(DateTimeUtil.convertLocalDateTimeToDate(tomorrowMidnight).getTime());

		return builder(id, jwtObj);
	}

	private String builder(Long userId, JsonWebToken jwtObj) {
		jwtObj = validateJwtObj(jwtObj);
		Date now = DateTimeUtil.getNowDate();
		return Jwts.builder()
//				.setIssuer(ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString())
				.setIssuer(ConfigConstant.domain)
				.setSubject(Long.toString(userId))
				.setIssuedAt(now)
				.setExpiration(new Date(now.getTime() + jwtObj.getExpireTimeMs()))
				.addClaims(jwtObj.getExtra())
				.signWith(SignatureAlgorithm.HS512, jwtObj.getSecretStr())
				.compact();
	}

	public String generateFunTaToken(JsonWebToken jwtObj) {
		jwtObj = validateJwtObj(jwtObj);
		Date now = DateTimeUtil.getNowDate();
		return Jwts.builder()
//				.setIssuer(ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString())
				.setIssuer(ConfigConstant.domain)
				.setIssuedAt(now)
				.setExpiration(new Date(now.getTime() + jwtObj.getExpireTimeMs()))
				.addClaims(jwtObj.getExtra())
				.signWith(jwtObj.getAlgorithm(), jwtObj.getSecretStr())
				.compact();
	}

	private JsonWebToken validateJwtObj(JsonWebToken jwtObj) {
		if (Objects.isNull(jwtObj)) {
			return new JsonWebToken()
					.setExtra(new HashMap<>())
					.setExpireTimeMs(DateTimeUtil.getSecondsOfDay(1))
					.setSecretStr("ABCDCBA");
		} else {
			if (StringUtils.isBlank(jwtObj.getSecretStr()))
				jwtObj.setSecretStr("ABCDCBA");

			if (jwtObj.getExtra() == null)
				jwtObj.setExtra(new HashMap<>());

			if (jwtObj.getExpireTimeMs() == null)
				jwtObj.setExpireTimeMs(DateTimeUtil.getSecondsOfDay(1));
		}
		return jwtObj;
	}

	public Claims getClaims(String token, String secret) {
		return Jwts.parser()
				.setSigningKey(Base64.getEncoder().encodeToString(secret.getBytes()))
				.parseClaimsJws(token)
				.getBody();
	}

	public Long getUserIdFromJWT(String token, String secret) {
		try {
			Claims claims = getClaims(token, secret);
			return Long.parseLong(claims.getSubject());
		} catch (ExpiredJwtException ex) {
			return Long.parseLong(ex.getClaims().getSubject());
		}
	}

	public boolean validate(String authToken, String secret) {
		try {
			getClaims(authToken, secret);
			return true;
		} catch (SignatureException ex) {
			logger.error("Invalid JWT signature");
			throw ex;
		} catch (MalformedJwtException ex) {
			logger.error("Invalid JWT token");
			throw ex;
		} catch (ExpiredJwtException ex) {
			logger.error("Expired JWT token");
			throw ex;
		} catch (UnsupportedJwtException ex) {
			logger.error("Unsupported JWT token");
			throw ex;
		} catch (IllegalArgumentException ex) {
			logger.error("JWT claims string is empty.");
			throw ex;
		}
	}

	public String builder_2(Long userId, JsonWebToken jwtObj) {
		jwtObj = validateJwtObj(jwtObj);
		Date now = DateTimeUtil.getNowDate();
		return Jwts.builder()
//				.setIssuer(ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString())
				.setIssuer("http://localhost:8080")
				.setSubject(Long.toString(userId))
				.setIssuedAt(now)
				.setId("setId_" + userId)
				.setAudience("123456_setAudience")
				.setExpiration(new Date(now.getTime() + jwtObj.getExpireTimeMs()))
				.addClaims(jwtObj.getExtra())
				.signWith(SignatureAlgorithm.HS512, jwtObj.getSecretStr())
				.compact();
	}

	public static void main(String[] args) {
		JwtProvider provider = new JwtProvider();

		JsonWebToken jwtObj = new JsonWebToken();
		Map<String, Object> extra = new HashMap<>();

		extra.put("type", TokenType.LOGIN.getType());
		jwtObj.setExtra(extra);
		jwtObj.setExpireTimeMs(86400000l);
		jwtObj.setSecretStr("ZUQLoWUTPXSgvCTnBvohNBz5JGzVKYZUvk90NScLG0seFuz19U22PfH5IWgQpbnTEgnst1xAwyXrsPJh-hKKmg");

		String loginToken = provider.builder_2(5l, jwtObj);
		System.out.println(loginToken);
	}
}
