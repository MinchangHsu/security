package com.caster.security.model.view;

import com.caster.security.entity.Roles;
import com.caster.security.entity.User;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class UserRolesView {

    private User user;
    private List<Roles> rolesList;
}
