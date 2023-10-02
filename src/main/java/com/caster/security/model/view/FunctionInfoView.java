package com.caster.security.model.view;

import com.caster.security.entity.Permission;
import lombok.Data;

import java.util.List;

/**
 * Author: Caster
 * Date: 2021/9/5
 * Comment:
 */
@Data
public class FunctionInfoView {

    private Integer id;

    private String functionName;

    private List<Permission> permissionList;
}
