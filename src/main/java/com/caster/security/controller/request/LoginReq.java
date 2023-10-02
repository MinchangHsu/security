package com.caster.security.controller.request;

import com.caster.security.model.BaseModelPattern;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LoginReq extends BaseModelPattern {

    @JsonInclude
    private String loginId;

    @JsonInclude
    private String password;

	String googleValidateCode;

}
