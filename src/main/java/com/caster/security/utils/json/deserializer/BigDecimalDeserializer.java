package com.caster.security.utils.json.deserializer;

import com.caster.security.enums.ErrorCodeMsg;
import com.caster.security.exception.SystemException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * Author: Caster
 * Date: 2022/7/8
 * Comment:
 */
@Slf4j
public class BigDecimalDeserializer extends JsonDeserializer<BigDecimal> {


	public BigDecimalDeserializer() {
	}

	@Override
	public BigDecimal deserialize(JsonParser jp, DeserializationContext dc) throws IOException {
		JsonNode jsonNode = jp.getCodec().readTree(jp);
		String originDateStr = jsonNode.asText();
//		log.debug("target:{}", originDateStr);
		String regex = "^\\d*\\.?\\d*$";

		var pattern = Pattern.compile(regex);
		var matcher = pattern.matcher(originDateStr);

		if(!matcher.find())
			throw new SystemException(ErrorCodeMsg.JSON_DATE_TRANSFER_OPERATION_ERROR, originDateStr, "String to BigDecimal");

		return new BigDecimal(originDateStr);
	}
}
