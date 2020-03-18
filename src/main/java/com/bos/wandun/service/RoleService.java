package com.bos.wandun.service;

import com.bos.wandun.entity.Role;

public interface RoleService extends BaseService<Role, Long> {
    Role findRoleByName(String name);

    Role findRoleByEnname(String enname);
}
