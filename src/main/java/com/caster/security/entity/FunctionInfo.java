package com.caster.security.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * <p>
 * 功能表
 * </p>
 *
 * @author Caster
 * @since 2022-07-05
 */
@Data
@TableName("function_info")
public class FunctionInfo {

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 功能名稱
     */
    private String functionName;


}
