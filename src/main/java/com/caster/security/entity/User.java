package com.caster.security.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 系統使用者資訊
 * </p>
 *
 * @author Caster
 * @since 2022-07-05
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    /**
     * 識別ID, 自動遞增
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 姓名或暱稱
     */
    private String name;

    /**
     * 使用者描述
     */
    private String description;

    /**
     * 登入帳號
     */
    private String loginId;

    /**
     * 登入密碼
     */
    @JsonIgnore
    private String pwd;

    /**
     * 通訊email
     */
    private String email;

    /**
     * 出生日期(西元)
     */
    private LocalDateTime birthday;

    /**
     * 性別: 0:不明, 1:男性, 2:女性
     */
    private String gender;

    /**
     * 類型: 1:系統管理者, 2:高級管理者, 3:普通管理者, 4.客服人員, 5:會員
     */
    private String userType;

    /**
     * 帳號的狀態, 0:停用, 1:啟用
     */
    private String status;

    /**
     * google authenticator secret
     */
    private String googleAuthSecretKey;

    /**
     * 使用者個人icon
     */
    private String iconPath;

    /**
     * 帳號所屬的權限群組
     */
    private String userGroup;

    /**
     * 紀錄最後登入的ip
     */
    private String loginIpAddress;

    /**
     * 最後操作系統時間
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastAccessDate;

    /**
     * refreshToken
     */
    private String refreshToken;

    /**
     * 建立日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;

    /**
     * 最後修改日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDate;


}
