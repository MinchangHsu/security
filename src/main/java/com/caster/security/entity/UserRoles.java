package com.caster.security.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author Caster
 * @since 2022-07-05
 */
@Data
@Accessors(chain = true)
@TableName("user_roles")
public class UserRoles {

    private Integer userId;

    private Integer roleId;


}
