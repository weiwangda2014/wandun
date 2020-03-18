package com.bos.wandun.dao;

import com.bos.wandun.entity.Role;

public interface RoleDao extends BaseDao<Role, Long> {
    Role getByEnname(String r);

    Role getByName(String name);
}
