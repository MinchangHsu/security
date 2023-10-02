package com.caster.security.service;

import com.caster.security.entity.User;
import com.caster.security.model.view.UserRolesView;
import com.github.yulichang.base.MPJBaseService;

import java.util.List;

/**
 * <p>
 * 系統使用者資訊 服务类
 * </p>
 *
 * @author Caster
 * @since 2022-07-05
 */
public interface UserService extends MPJBaseService<User> {

    UserRolesView getUserRolesView(User user);

    void insertUserRoles(Integer userId, List<String> roleNames);

    User getUserByLoginId(String loginId, boolean checkStatus);

    User getUserById(Integer userId, boolean checkStatus);

}
