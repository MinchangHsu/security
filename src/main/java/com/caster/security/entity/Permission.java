package com.caster.security.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 權限表
 * </p>
 *
 * @author Caster
 * @since 2022-07-05
 */
@Data
@Accessors(chain = true)
@TableName("permission")
public class Permission {

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    private String permission;

    private String permissionDescription;

    private Integer functionId;


}
