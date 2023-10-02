package com.caster.security.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * <p>
 * 權限Api對應表
 * </p>
 *
 * @author Caster
 * @since 2022-07-05
 */
@Data
@TableName("permission_api")
public class PermissionApi {

    private Integer permissionId;

    private Integer apiId;


}
