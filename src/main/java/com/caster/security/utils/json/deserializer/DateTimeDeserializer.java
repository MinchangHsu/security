package com.caster.security.utils.json.deserializer;

import com.caster.security.utils.DateTimeUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Author: Caster
 * Date: 2022/7/8
 * Comment:
 */
@Slf4j
public class DateTimeDeserializer extends JsonDeserializer<LocalDateTime> {


	public DateTimeDeserializer() {
	}

	@Override
	public LocalDateTime deserialize(JsonParser jp, DeserializationContext dc) throws IOException {
		LocalDateTime result = null;
		JsonNode jsonNode = jp.getCodec().readTree(jp);
		String originDateStr = jsonNode.asText();
//		log.debug("target:{}", originDateStr);
		String[] regexs = {"\\.\\d{1}-", "\\.\\d{2}-", "\\.\\d{3}-"};

		for(String o : regexs){
			try{
				var pattern = Pattern.compile(o);
				var matcher = pattern.matcher(originDateStr);

//				log.debug("regex:{}, matcher.find(19):{}", o, matcher.find(19));

				if(matcher.find(19)) {
					String originRegexStr = matcher.group();
					String o2 = originRegexStr.replaceAll("\\D", "");
					String o3 = "." + StringUtils.leftPad(o2, 3, "0") + "-";
					String o4 = originDateStr.replaceAll(o, o3);
					result = LocalDateTime.parse(o4, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));
					break;
				}
			}catch (DateTimeParseException dtp){
				log.debug("origin text:{}, regex:{}", originDateStr, o);
//				dtp.printStackTrace();
			}
		}
		if(Objects.nonNull(result))
			return result;
		return DateTimeUtil.getNowLocal();
	}
}
