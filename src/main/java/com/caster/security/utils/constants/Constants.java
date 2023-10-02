package com.caster.security.utils.constants;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Constants {
    public static final String UTF_8 = "UTF-8";
    public static final String NOTIFY_GP_SUCCESS_MESSAGE = "success";
    public static final Integer ZERO_WIDTH_CHAR_VALUE = 8203;
    public static final char BYTE_ORDER_MARK = '\ufeff';

    public static final String ICON_ROOT = "iconRootPath";
    public static final String FOR_UPDATE = "for update";


    // 前台 簽檔來源
    public static String ISSUER = "S Platform";
    // 前台TOKEN 密鑰
    public static String FRONT_SECRET_KEY = ISSUER + "_p@ssw0rd";

    public static final String SYSTEM_DEFAULT_ROOM = "systemRoom";
    public static final String BACKSTAGE = "backstage";
    public static final String REST_API = "restApi";
    public static final List<String> SYSTEM_LIST = Collections.unmodifiableList(Arrays.asList(BACKSTAGE, REST_API));
    public static final String GETTER_PREFIX = "get";
    public static final String OLD_STR = "old";
    public static final String NEW_STR = "new";


    // system config
    public final static String TYPE_WEB = "WEB";
    public final static String TYPE_DOMAIN = "DOMAIN";
    public final static String TYPE_BACKSTAGE = "BACKSTAGE";

    public final static String KEY_RESTAPI_DOMAIN = "RESTAPI_DOMAIN";
    public final static String KEY_FRONTEND_HOME_PAGE = "FRONTEND_HOME_PAGE";
    public final static String KEY_FRONTEND_LOGIN_PAGE = "FRONTEND_LOGIN_PAGE";
    public final static String KEY_CHOSEN_ACTORS = "CHOSEN_ACTORS";
    public final static String KEY_CHOSEN_OF_ACTORS = "CHOSEN_OF_ACTORS"; // OF 是網紅意思
    public final static String KEY_PAYMENT_DOMAIN = "PAYMENT_DOMAIN";
    public final static String KEY_PAYMENT_CONTEXT_PATH = "PAYMENT_CONTEXT_PATH";
    public final static String KEY_CALLBACK_AMOUNT_GAP_LIMIT = "CALLBACK_AMOUNT_GAP_LIMIT";

    public final static String PUBLIC_CHAT_ROOM = "publicRoom";


    // forth payment
    public static final String KEY = "key";

    public static final Integer LEVEL_3 = 3;
    public static final BigDecimal ONE_HUNDRED = new BigDecimal(100).setScale(2, RoundingMode.HALF_UP);
    public static final BigDecimal ONE_MILLION = new BigDecimal(1000000).setScale(2, RoundingMode.HALF_UP);
    public static final BigDecimal ONE_HUNDRED_THOUSAND = new BigDecimal(100000).setScale(2, RoundingMode.HALF_UP);
    public static final BigDecimal SIX_HUNDRED_THOUSAND = new BigDecimal(600000).setScale(2, RoundingMode.HALF_UP);

}
