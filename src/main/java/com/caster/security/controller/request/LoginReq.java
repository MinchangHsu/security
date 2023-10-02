package com.caster.security.controller.request;

import com.caster.security.model.BaseModelPattern;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LoginReq extends BaseModelPattern {

    @JsonInclude
    private String loginId;

    @JsonInclude
    @ToString.Exclude
    private String password;

	String googleValidateCode;

}
