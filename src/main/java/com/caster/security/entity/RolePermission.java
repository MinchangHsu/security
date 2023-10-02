package com.caster.security.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色權限對應表
 * </p>
 *
 * @author Caster
 * @since 2022-07-05
 */
@Data
@TableName("role_permission")
@Accessors(chain = true)
public class RolePermission {

    private Integer roleId;

    private Integer permissionId;


}
