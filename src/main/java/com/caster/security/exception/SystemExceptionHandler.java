package com.caster.security.exception;

import com.caster.security.enums.ErrorCodeMsg;
import com.caster.security.model.response.JSONResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Author: Caster
 * Date: 2022/7/8
 * Comment:
 */
@Slf4j
@RestControllerAdvice
public class SystemExceptionHandler extends ResponseEntityExceptionHandler {

	protected static String ERROR_MSG_TITLE = "[System]處理請求發生錯誤，錯誤訊息：";

	/***
	 * 定義要捕獲的異常 可以多個 @ExceptionHandler({})
	 * @param request
	 * @param response
	 * @param e exceptoin
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler({SystemException.class})
	public ResponseEntity gameSystemExceptionHandler(HttpServletRequest request,
													 HttpServletResponse response,
													 final Exception e) {
		//response.setStatus(HttpStatus.BAD_REQUEST.value());

		SystemException exception = (SystemException) e;
		log.error(ERROR_MSG_TITLE, exception);

		return ResponseEntity.ok().body(JSONResult.createExceptionResult(exception));
	}

	@ResponseBody
	@ExceptionHandler({DataAccessException.class, SQLException.class})
	public ResponseEntity databaseExceptionHandler(HttpServletRequest request,
												   HttpServletResponse response,
												   final Exception e) {
		//response.setStatus(HttpStatus.BAD_REQUEST.value());
		log.error(ERROR_MSG_TITLE, e);

		return ResponseEntity.ok()
				.body(JSONResult.createResult(ErrorCodeMsg.DATABASE_ERROR));
	}

	@ResponseBody
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity runtimeExceptionHandler(HttpServletRequest request,
												  HttpServletResponse response,
												  final Exception e) {
		//response.setStatus(HttpStatus.BAD_REQUEST.value());
		RuntimeException exception = (RuntimeException) e;
		log.error(ERROR_MSG_TITLE, exception);

		// 需要用拋出errorCode參數進exception的場合
		ErrorCodeMsg errorEnum = ErrorCodeMsg.getInstanceOf(e.getMessage());
		if (Objects.nonNull(errorEnum)) {
			return ResponseEntity.ok(JSONResult.createResult(errorEnum));
		}

		return ResponseEntity.ok()
				.body(JSONResult.createResult(ErrorCodeMsg.SYSTEM_OPERATION_ERROR));
	}

	/**
	 * 通用的介面對映異常處理方
	 */
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
															 Object body,
															 HttpHeaders headers,
															 HttpStatus status,
															 WebRequest request) {
		if (ex instanceof MethodArgumentNotValidException) {
			MethodArgumentNotValidException exception = (MethodArgumentNotValidException) ex;
			log.error("方法參數無效：", ex);

			return ResponseEntity.status(status)
					.body(JSONResult.createExceptionResult(status.value(), exception.getBindingResult()
							.getAllErrors()
							.get(0)
							.getDefaultMessage()));
		}

		if (ex instanceof MethodArgumentTypeMismatchException) {
			MethodArgumentTypeMismatchException exception = (MethodArgumentTypeMismatchException) ex;
			log.error("引數轉換失敗，方法：{}, 引數：{}, 資訊：{}",
					exception.getParameter()
							.getMethod()
							.getName(),
					exception.getName(),
					exception.getLocalizedMessage());

			return ResponseEntity.status(status)
					.body(JSONResult.createExceptionResult(status.value(), "引數轉換失敗"));
		}

		//改成自己的errorCode
		if (ex instanceof BindException) {
			String errorMsg = genErrorMsg(((BindException) ex).getBindingResult());
			log.error("缺少必要參數, 錯誤訊息：{}", errorMsg);
			return ResponseEntity.status(status)
					.body(JSONResult.createExceptionResult(status.value(), errorMsg));
		}

		if (ex instanceof HttpMessageNotReadableException) {
			String errorMsg = ex.getMessage();
			log.error("讀取且驗證參數, 錯誤訊息：{}", errorMsg);
			return ResponseEntity.status(status)
					.body(JSONResult.createExceptionResult(status.value(), "讀取且驗證參數有誤, 請重新檢查請求參數."));
		}

		log.error("系統發生錯誤，錯誤訊息：", ex);
		return ResponseEntity.status(status)
				.body(JSONResult.createExceptionResult(status.value(),
						ErrorCodeMsg.SYSTEM_OPERATION_ERROR.getErrorMsg()));
	}

	private String genErrorMsg(BindingResult bindingResult) {
		StringBuilder sb = new StringBuilder();
		bindingResult.getFieldErrors()
				.forEach(fieldError ->
						sb.append(fieldError.getDefaultMessage())
								.append(System.lineSeparator())
				);

		return sb.toString();
	}
}
