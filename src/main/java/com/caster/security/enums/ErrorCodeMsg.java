package com.caster.security.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public enum ErrorCodeMsg {

	SYSTEM_OPERATION_ERROR(0, 400, "系統操作錯誤，請洽詢系統人員"),
	DATABASE_ERROR(0, 477, "系統操作錯誤，請洽詢系統人員"),
	COMMON_FAILED(0, 404, "回應失敗"),

	//5000~5099：共用
	DB_DEFAULT_ERROR(0, 5000, "資料庫錯誤 %s"),
	//5100~5199：新增
	DB_INSERT_ERROR(0, 5100, "新增失敗 %s"),
	//5200~5299：查詢
	DB_QUERY_ERROR(0, 5200, "查詢失敗 %s"),
	//5300~5399：修改
	DB_UPDATE_ERROR(0, 5300, "修改失敗 %s"),
	//5400~5499：刪除
	DB_DELETE_ERROR(0, 5400, "刪除失敗 %s"),

	PAYMENT_REQ_FAILED_ERROR(0, 2010, "支付請求失敗,原因:%s"),
	WITHDRAW_REQ_FAILED_ERROR(0, 2013, "代付申請失敗[%s]"),
	WITHDRAW_FAILED_ERROR(0, 2014, "代付打款失敗[%s]"),
	WITHDRAW_IN_PROGRESS(2, 2015, "代付打款中"),
	WITHDRAW_NOTIFY_FAILED(0, 2016, "代付通知失敗"),
	WITHDRAW_QUERY_FAILED(0, 2017, "代付查詢失敗[%s]"),
	WITHDRAW_AMOUNT_NOT_ENOUGH_ERROR(0, 2018, "代付取款餘額不足"),
	FPAYWAY_ID_HAVE_NO_PAYWAY(0, 2021, "通道類型[fpaywayId=%s]沒有可用的支付通道"),
	RUN_OUT_OF_MERCHANT_ERROR(0, 2022, "發送給所有商戶通道的請求皆失敗, 請稍後再嘗試"),
	PAYMENT_AMOUNT_NOT_IN_RANGE_ERROR(0, 2008, "支付通道不支援支付金額[amount=%s]"),
	NO_AVAILABLE_MERCHANT_ERROR(0, 2004, "遊戲平台沒有開啟可用商戶"),
	ORDER_INFO_NOT_FOUND_ERROR(0, 2009, "查無訂單號[orderNo=%s]相關資訊"),
	ANONYMOUS_FUNCTION_NOT_EXISTED(0,2010, "匿名Function不得空值"),

	// 5000 Socket.io 錯誤訊息
	SOCKET_CONNECT_TOKEN_NOT_EXISTED(0, 5000, "連線取得查無請求參數[%s]"),
	SOCKET_CONNECT_AUTH_INVALID(0, 5001, "連線身份驗證錯誤, 請洽詢客服人員."),

	// 6000 外部 Game 錯誤訊息
	FUN_TA_GET_MEMBER_INFO_ERROR(0, 6000, "getMemberInfo Error, [error_core:%s, error_msg:%s, info>code:%s]"),
	FUN_TA_NOT_FOUND_MEMBER_INFO(0, 6001, "Member info not found, [username:%s]"),
	FUN_TA_MEMBER_INFO_IS_EMPTY(0, 6002, "Member info is empty, [username:%s]"),
	FUN_TA_ADD_MEMBER_FAIL(0, 6003, "Add Member Fail, [username:%s]"),
	FUN_TA_KEY_NOT_EXISTED(0, 6004, "Response Data Not found key:%s"),
	FUN_TA_GET_BETTING_RECORD_ERROR(0, 6005, "getBettingRecord Error, [error_core:%s, error_msg:%s]"),
	FUN_TA_GET_BETTING_RECORD_QUERY_LIMIT_ERROR(0, 6006, "getBettingRecord The maximum query interval of the time interval is 5 minutes, and the date must be within the last 14 days."),
	FUN_TA_GET_GAME_REPORT_DATETIME_NOT_EMPTY_ERROR(0, 6007, "請求日期不允許空直"),
	FUN_TA_GET_GAME_REPORT_QUERY_LIMIT_ERROR(0, 6008, "getGameReport The maximum query interval of the time interval is 1 days, and minimum is 1 minutes."),
	SUPER_SPORT_OPERATION_ERROR(0, 6009, "操作[%s], 回傳錯誤[%s, uuid:%s]."),
	SUPER_SPORT_RESPONSE_DATA_NOT_EXISTED(0, 6010, "回傳資料缺少[%s, uuid:%s]發生錯誤."),
	SUPER_SPORT_ACCOUNT_INVALID(0, 6011, "Super sport 帳號狀態不合法[%s], 請洽詢客服人員."),
	BE_GAME_ACCOUNT_NOT_EXISTED(0, 6012, "BE 遊戲查物此會員[%s], 請洽詢客服人員."),
	SA_GAME_REQUEST_ERROR(0, 6013, "SA Game 查詢發生錯誤, 錯誤訊息:[%s], 請洽詢客服人員."),
	SA_GAME_USERNAME_NOT_EXISTED(0, 6013, "SA Game 查詢Username[%s]不存在, 錯誤訊息:[%s], 請洽詢客服人員."),
	GR_GAME_REQUEST_ERROR(0, 6014, "GoodRoad Game 查詢發生錯誤, 錯誤訊息:[code:%s, message:%s], 請洽詢客服人員."),

	TG_OPERATION_ERROR(0, 6015, "ThothGaming 操作[%s], 回傳錯誤[%s]."),
	JSON_DATE_TRANSFER_OPERATION_ERROR(0, 6016, "JSON 資料[%s]轉換[%s]發生錯誤"),
	RSG_OPERATION_ERROR(0, 6017, "RoyalSlotGaming 操作[%s], 回傳錯誤[%s]."),
	RSG_INCONSISTENT_TRANSACTION_RESULTS(0, 6018, "RoyalSlotGaming, 交易結果不一致, 交易回滾."),

	//7000開頭為後台使用
	TOKEN_NOT_FOUND(0, 7000, "查無Token"),
	TOKEN_EXPIRED(0, 7001, "Token過期"),
	TOKEN_ERROR(0, 7002, "Token驗證錯誤"),
	NOT_FOUND_ADMIN_USER(0, 7003, "查無此使用者."),
	ADMIN_USER_SHUTDOWN(0, 7004, "用戶已失效"),
	UNAUTHORIZED(0, 7005, "用戶未登入"),
	FORBIDDEN(0, 7006, "用戶無操作權限"),
	VALIDATED_LOGIN_PWD_FAIL(0, 7007, "登入密碼驗證錯誤"),
	VALIDATED_GOOGLE_AUTH_CODE_FAIL(0, 7008, "Google Auth Code 驗證錯誤"),
	GOOGLE_SECRET_KEY_EMPTY_ERROR(0, 7009, "使用者未綁定谷歌驗證碼錯誤"),

	PARAMETER_NOT_FOUND(0, 7010, "請求參數不得為空, 重新刷新頁面且再次操作."),
	PARAMETER_PATTERN_ERROR(0, 7011, "參數格式錯誤[%s]"),
	PARAMETER_TYPE_ERROR(0, 7012, "參數型態錯誤[參數名稱:%s]，預期為[%s]型態，接收到值[\"%s\"]"),
	PRODUCT_INFO_NOT_FOUND_ERROR(0, 7013, "查無資訊[id:%s]"),
	FILE_SIZE_OVERT_LIMIT(0, 7014, "檔案大小超過限制"),
	INVALID_IMAGE_FORMAT(0, 7015, "檔案格式不符"),
	UPLOAD_FILE_FAIL(0, 7016, "檔案上傳失敗, 請重新上傳."),
	MEMBER_INFO_NOT_EXISTED(0, 7017, "查無此會員."),
	MEMBER_DISABLE(0, 7018, "此會員已停用."),
	MEMBER_PENDING_VERIFICATION(0, 7019, "此會員待驗證."),

	ADVERTISE_ID_NOT_FOUND(0, 7020, "Advertise查無此id:%s"),
	ADVERTISE_TYPE_ID_NOT_FOUND(0, 7021, "AdvertiseType查無此id:%s"),
	ADVERTISE_TYPE_IS_NOT_UNUSED(0, 7022, "AdvertiseType尚有使用中,不得刪除."),
	MEMBER_EMAIL_DIFFERENT(0, 7023, "電子信箱與註冊時不一致."),
	MEMBER_INFO_EXISTED(0, 7024, "%s 已有人使用, 請重新輸入."),
	SEND_EMAIL_HAPPENED_UNKNOWN＿ERROR(0, 7025, "寄送EMAIL時發生不明錯誤, 請重新操作."),
	SYSTEM_CONFIG_NOT_EXISTED(0, 7026, "查無此系統設定[id:%s]"),
	PLATFORM_MAIL_NOT_EXISTED(0, 7027, "查無此平台信件[id:%s]"),
	PLATFORM_MAIL_PARAMETER_NOT_EXISTED(0, 7028, "平台信件請求參數, 不可為空."),
	GIFT_INFO_NOT_EXISTED(0, 7029, "查無此禮物紀錄[id:%s]"),

	GIFT_INFO_MEMBER_ID_NOT_MATCH(0, 7030, "確認%s時, 會員Id與禮物資訊不符.[memberId:%s]"),
	GIFT_INFO_STATUS_NOT_FOUND(0, 7031, "禮物資訊狀態無法確認, 請洽詢系統人員."),
	MEMBER_BALANCE_NOT_ENOUGH(0, 7032, "會員當前餘額不足[balance:%s], 請重新操作."),
	GIFT_INFO_STATUS_INVALID(0, 7032, "禮物資訊狀態不允許此操作."),
	MEMBER_IS_GAMING_CAN_NOT_MODIFY(0, 7033, "會員目前遊玩中, 此不允許."),
	LEVEL_INFO_NOT_EXISTED(0, 7034, "查無此等級資訊[id:%s]."),
	LEVEL_INFO_USED(0, 7035, "等級資訊[id:%s], 尚有會員使用中, 不允許刪除."),
	LEVEL_INFO_NAME_DUPLICATE(0, 7036, "等級資訊名稱重複[name:%s]."),
	PLATFORM_POST_NOT_EXISTED(0, 7037, "查無此平台公告資訊[id:%s], 或當前公告狀態不允許此操作."),
	ACTIVITY_TYPE_NOT_EXISTED(0, 7038, "查無此活動類型資訊[id:%s]."),
	ACTIVITY_INFO_NOT_EXISTED(0, 7039, "查無此活動資訊or當前活動狀態不允許更動資訊[id:%s]."),

	ACTIVITY_TYPE_USED(0, 7040, "活動類型[id:%s], 尚有活動使用中, 不允許刪除."),
	ACTIVITY_STATUS_INVALID(0, 7041, "活動狀態不允許切換[id:%s]"),
	ORDER_HAS_BEEN_TAKEN_ACTIVITY(0, 7042, "當前訂單[orderNo:%s]已參加過此活動[id:%s]"),
	ORDER_NOT_FIRST_RECHARGE(0, 7044, "當前訂單[orderNo:%s]不是第一筆儲值訂單"),
	GAME_INFO_NOT_EXISTED(0, 7045, "查無此遊戲平台資訊[id:%s]."),
	ORDER_INFO_NOT_EXISTED(0, 7046, "查無此訂單資訊[id:%s]."),
	MERCHANT_INFO_NOT_EXISTED(0, 7047, "查無此商戶資訊[id:%s]."),
	ORDER_STATUS_INVALID(0, 7048, "此訂單狀態不允許更改狀態."),
	ORDER_INFO_NOT_EXISTED_OR_LOCK_FAIL(0, 7049, "查無此訂單資訊或鎖定失敗[id:%s]."),

	MEMBER_BALANCE_CHECK_OR_LOCK_FAIL(0, 7050, "檢核此會員[id:%s]餘額資料或鎖定資料失敗."),
	MEMBER_WITHDRAW_FAIL(0, 7051, "會員[id:%s]從遊戲平台提款發生錯誤."),
	MEMBER_FRIEND_IS_EXISTED(0, 7052, "與會員[%s]已經為朋友狀態, 請勿重複建立."),
	ADVERTISE_INFO_NOT_EXISTED(0, 7053, "查無此廣告資訊[id:%s]"),
	ADVERTISE_INFO_SETTING_INVALID(0, 7054, "廣告時間資訊設置不合法, 請檢查[%s > id:%s]狀態及時間區間."),
	ADVERTISE_TYPE_INVALID(0, 7055, "廣告類型不合法."),
	MARQUEE_INFO_NOT_EXISTED(0, 7056, "查無此跑馬燈資訊[id:%s]"),
	MERCHANT_STATUS_INVALID(0, 7057, "商戶狀態[status:%s]不允許更改內容"),
	SEND_GIFT_TO_THE_MAX_LIMIT(0, 7058, "贈禮[%s]已達到當前等級上限[%s]."),
	MEMBER_BALANCE_NOT_EXISTED(0, 7059, "查無此會員餘額資訊[id:%s]"),

	GIFT_MODIFY_STATUS_INVALID(0, 7060, "禮物異動狀態不合法."),
	FORTH_PAYMENT_INFO_NOT_EXISTED(0, 7061, "查無此四方資訊[id:%s]."),
	FORTH_PAYMENT_STATUS_INVALID(0, 7062, "修改四方資訊須先停用"),
	PLATFORM_REPLY_MAIL_NOT_EXISTED(0, 7063, "查無此平台回覆信件[id:%s]"),
	CHAT_ROOM_TO_MEMBER_INFO_NOT_EXISTED(0, 7064, "查無對方基本資訊, 會員id[%s]"),
	CHAT_ROOM_INFO_NOT_EXISTED(0, 7065, "查無此聊天室資訊, 聊天室id[%s]"),
	CHAT_MESSAGE_INFO_NOT_EXISTED(0, 7066, "查無此回覆訊息id[%s]"),
	MEMBER_NOT_IN_THIS_ROOM(0, 7067, "會員並未在此房間, 不允許傳送訊息."),
	UPLOAD_IMAGE_SIZE_LIMIT_INVALID(0, 7068, "圖片上傳數量不符合規定, 單次上傳限1-3張."),
	TO_MEMBER_INFO_NOT_EXISTED(0, 7069, "查無此會員[toMemberId:%s]."),
	DAILY_BONUS_IS_PICKED(0, 7070, "每日獎金已領取, 不得重複領取."),
	SEND_SMS_FILE(0, 7071, "發送簡訊出現錯誤[phone:%s], 請洽詢客服人員."),
	BALANCE_BIG_THEN_DAILY_BONUS(0, 7072, "餘額[%s]大於或等於每日獎金[%s], 不得領取."),
	BIND_PHONE_VERIFY_CODE_INVALID(0, 7073, "綁定門號驗證碼[%s]錯誤, 請重新輸入."),
	REQUEST_PARAMETER_VALIDATE_FAIL(0, 7074, "請求參數檢核錯誤, 說明[%s], 請重新輸入."),
	GAME_COMPANY_NOT_SUPPORT_THIS_OPERATE(0, 7075, "當前遊戲公司不提供此操作, 請洽詢系統人員."),
	DEFAULT_LEVEL_INFO_IS_EMPTY(0, 7076, "請先新增Default level."),
	ADMIN_USER_STATUS_INVALID(0, 7077, "使用者狀態不允許此操作[%s]."),
	ROLE_NAME_INVALID(0, 7078, "角色名稱重複[%s], 請重新輸入."),
	ROLE_INFO_NOT_EXISTED(0, 7079, "查無此角色資訊[id:%s], 請重新輸入."),
	ROLE_INFO_USED(0, 7080, "角色資訊[id:%s]尚有使用者使用, 不允許刪除, 請重新輸入."),

	USER_INFO_NOT_EXISTED(0, 7081, "查無此使用者."),
	USER_DISABLE(0, 7082, "使用者已停用."),
	API_URL_NOT_EXISTED(0, 7083, "查無此 API URL資訊, [url:%s, httpMethod:%s]"),
	BIND_INTRODUCER_INVALID(0, 7084, "綁定推薦碼錯誤[%s], 請重新輸入."),
	MODIFY_MEMBER_BALANCE_NOT_ENOUGH(0, 7085, "會員當前%s餘額不足, 請確認會員餘額, 再重新操作."),





	THE_END(0,9999, "");



	private Integer code; //api對外回應的代碼
	private Integer errorCode;
	private String errorMsg;

	ErrorCodeMsg(Integer code, Integer errorCode, String errorMsg) {
		this.code = code;
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	private static Map<String, ErrorCodeMsg> map;

	static {
		map = Arrays.stream(ErrorCodeMsg.values())
				.collect(Collectors.toMap(ErrorCodeMsg::toString, e -> e));
	}

	public static ErrorCodeMsg getInstanceOf(String whichEnum) {
		return map.get(whichEnum);
	}

	public String getErrorMsg(String... context) {
		if (context.length > 0) {
			// 找出有多少個%s
			int signCount = errorMsg.split("%s").length - 1;
			int supplementCount = signCount - context.length;
			// 傳入的參數個數不足，需產生新陣列，長度必須至少跟%s個數一樣(否則format會出錯)，並將null替換成""
			if (supplementCount > 0) {
				Object[] newContext = null;
				List<String> al = Arrays.asList(Arrays.copyOf(context, signCount));
				newContext = al.stream()
						.map(StringUtils::defaultString)
						.toArray();
				// String[] newContext = ArrayLis
				return String.format(errorMsg, newContext);
			}

			return String.format(errorMsg, (Object[]) context);
		} else {
			errorMsg = errorMsg.replaceAll("%s", "\"\"");
			return errorMsg;
		}
	}

	public String getErrorCodeMsg(String... context) {
		String errorMsg = getErrorMsg(context);
		return String.format("ErrorCode:%s; ErrorMsg:%s", errorCode, errorMsg);
	}
}
