package com.caster.security.model.response;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.caster.security.enums.ErrorCodeMsg;
import com.caster.security.enums.SuccessCodeMsg;
import com.caster.security.exception.SystemException;
import com.caster.security.utils.DateTimeUtil;
import com.caster.security.utils.json.JsonUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Getter
@ToString
public class JSONResult {

	private String time = DateTimeUtil.getNowString();
	private Integer code;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer errorCode;
	private String message;


	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long page; // 當前頁數

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long pageSize; // 頁面筆數

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long totalPage; // 總頁數

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long totalSize; // 總筆數

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Object result;
//	@JsonInclude(JsonInclude.Include.NON_NULL)
//	private List<Object> results;

//    @JsonIgnore
//    private UserActionLogView actionLogView;

	private JSONResult() {
		super();
	}

	/***
	 * @param errorEnum
	 * @param params
	 * @return JSONResult
	 */
	public static JSONResult createResult(ErrorCodeMsg errorEnum, String... params) {
		JSONResult resultObj = new JSONResult();
		resultObj.message = errorEnum.getErrorMsg(params);
		resultObj.code = errorEnum.getCode();
		resultObj.errorCode = errorEnum.getErrorCode();
		return resultObj;
	}

	/**
	 * @param successEnum
	 * @param params
	 * @return
	 */
	public static JSONResult createResult(SuccessCodeMsg successEnum, String... params) {
		JSONResult resultObj = new JSONResult();
		resultObj.message = successEnum.getMsg();
		resultObj.code = successEnum.getCode();
		return resultObj;
	}

	/**
	 * @param exception
	 * @return
	 */
	public static JSONResult createExceptionResult(SystemException exception) {
		JSONResult resultObj = new JSONResult();
		resultObj.message = exception.getErrorMsg();
		resultObj.code = 0;
		resultObj.errorCode = exception.getErrorCode();
		return resultObj;
	}

	/**
	 * @param errorCode
	 * @param errorMsg
	 * @return
	 */
	public static JSONResult createExceptionResult(Integer errorCode, String errorMsg) {
		JSONResult resultObj = new JSONResult();
		resultObj.message = errorMsg;
		resultObj.code = 0;
		resultObj.errorCode = errorCode;
		return resultObj;
	}

	/**
	 * 一般回傳
	 *
	 * @param key
	 * @param obj
	 * @return
	 */
	public JSONResult addResult(String key, Object obj) {
		if (Objects.isNull(obj)) return this;
		Map<String, Object> map = (Map<String, Object>) Optional.ofNullable(result)
				.orElseGet(() -> this.result = new HashMap<>());
		map.put(key, obj);
		return this;
	}

	public JSONResult addResult(Object obj) {
		ObjectMapper oMapper = JsonMapper.builder() // or different mapper for other format
				.addModule(new ParameterNamesModule())
				.addModule(new Jdk8Module())
				.addModule(new JavaTimeModule())
				// and possibly other configuration, modules, then:
				.build();

		if (Objects.isNull(obj)) return this;

		if (obj instanceof Iterable || obj.getClass().isArray()) {
			this.result = oMapper.convertValue(obj, new TypeReference<>(){});
			return this;
		}

		Map<String, Object> map = oMapper.convertValue(obj, new TypeReference<>(){});
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			this.addResult(entry.getKey(), entry.getValue());
		}
		return this;
	}

	/**
	 * 自訂分頁Object
	 * 將分頁查詢結果回傳
	 * @param pageObj
	 * @return
	 */
	public JSONResult addPageResult(IPage pageObj) {
		this.page = pageObj.getCurrent();
		this.pageSize = pageObj.getSize();
		this.totalPage = pageObj.getPages();
		this.totalSize = pageObj.getTotal();
		return this.addResult(pageObj.getRecords());
	}

	public JSONResult addPageResult(IPage pageObj, Object list) {
		this.page = pageObj.getCurrent();
		this.pageSize = pageObj.getSize();
		this.totalPage = pageObj.getPages();
		this.totalSize = pageObj.getTotal();
		return this.addResult(list);
	}

	public String toJsonForLog() {
		return JsonUtil.toJsonLog(this);
	}

	@JsonIgnore
	public boolean isOk() {
		return Objects.equals(code, SuccessCodeMsg.COMMON_OK.getCode());
	}


}
