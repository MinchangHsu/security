package com.caster.security.model;

public class BaseModelPattern {

    public static final String FIELD_NOT_BLANK = "不允許為空";
    public static final String FIELD_FORMAT_INCORRECT = "格式不正確";
    public static final String VERSION_PATTERN = "([1-9]{1}\\.[0-9]{1})";
    public static final String AMOUNT_PATTERN = "^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){2})?$";
    public static final String HTTP_PATTERN = "\\b(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"; // matches <http://google.com>"
    public static final String HTTP_PATTERN_OR_BLANK = "(\\b(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]|[^|]*)"; // matches <http://google.com>"

}
