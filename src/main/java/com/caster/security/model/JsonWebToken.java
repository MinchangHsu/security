package com.caster.security.model;

import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Base64;
import java.util.Map;

/**
 * Author: Caster
 * Date: 2022/5/13
 * Comment:
 */
@Data
@Accessors(chain = true)
public class JsonWebToken {

	private SignatureAlgorithm algorithm;

	private String secretStr;

	private Long expireTimeMs;

	private Map<String, Object> extra;

	public String getSecretStr() {
		return Base64.getEncoder().encodeToString(secretStr.getBytes());
	}
}
