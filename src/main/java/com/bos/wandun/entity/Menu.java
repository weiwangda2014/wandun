package com.bos.wandun.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.*;


import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sys_menu")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "seq_menu")
public class Menu extends NodeTree<Long, Menu> {
    /**
     * 树路径分隔符
     */
    public static final String TREE_PATH_SEPARATOR = ",";

    private String href;    // 链接
    private String target;    // 目标（ mainFrame、_blank、_self、_parent、_top）
    private String icon;    // 图标
    private String display;    // 是否在菜单中显示（1：显示；0：不显示）
    private String permission; // 权限标识
    private Set<Role> roles = new HashSet<>(); // 拥有角色列表

    public Menu() {
        super();
    }

    @Column(length = 200)
    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Column(length = 200)
    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Column(length = 200)
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


    @Column(length = 200)
    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }


    @Column(length = 200)
    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }


    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToMany(mappedBy = "menus", fetch = FetchType.LAZY)
    @Where(clause = "del_flag='" + DEL_FLAG_NORMAL + "'")
    @OrderBy("id")
    @Fetch(FetchMode.SUBSELECT)
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
