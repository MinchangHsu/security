package com.caster.security.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 使用者類型權限對應表
 * </p>
 *
 * @author caster
 * @since 2022-11-14
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("user_type_permission_map")
public class UserTypePermissionMap {

    private String userType;

    private Integer permissionId;


}
