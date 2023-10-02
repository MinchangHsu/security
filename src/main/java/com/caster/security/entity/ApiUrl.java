package com.caster.security.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系統RestApi
 * </p>
 *
 * @author Caster
 * @since 2022-07-05
 */
@Data
@TableName("api_url")
@Accessors(chain = true)
public class ApiUrl {

    /**
     * id
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * RestUrl
     */
    private String apiUrl;

    /**
     * GET,HEAD,POST,PUT,PATCH,DELETE,OPTIONS,TRACE(可多個，以,分隔null代表無限制)
     */
    private String apiHttpMethod;

    /**
     * Api功能描述
     */
    private String apiDescription;


}
