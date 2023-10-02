package com.caster.security.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * Author: Caster
 * Date: 2021/9/5
 * Comment:
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RolesInfoView {
    private Integer id;
    private String name;
    private String roleDescription;
    private String userType;
    List<FunctionInfoView> functionInfoViews;

}
