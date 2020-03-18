package com.bos.wandun.entity;

import com.bos.wandun.Global;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "sys_role")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "seq_role")
public class Role extends DataEntity<Long, Role> {

    private Office office;    // 归属机构
    private String name;    // 角色名称
    private String enname;    // 英文名称
    private String dataScope; // 数据范围


    private String oldName;    // 原角色名称
    private String oldEnname;    // 原英文名称
    private String sysData;        //是否是系统数据
    private String useable;        //是否是可用


    private Set<User> users = new HashSet<>(); // 拥有用户列表
    private Set<Menu> menus = new HashSet<>(); // 拥有菜单列表
    private Set<Office> offices = new HashSet<>(); // 按明细设置数据范围


    public Role() {
        super();
        this.dataScope = DATA_SCOPE_SELF;
        this.useable = Global.YES;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_id")
    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnname() {
        return enname;
    }

    public void setEnname(String enname) {
        this.enname = enname;
    }

    public String getDataScope() {
        return dataScope;
    }

    public void setDataScope(String dataScope) {
        this.dataScope = dataScope;
    }


    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getOldEnname() {
        return oldEnname;
    }

    public void setOldEnname(String oldEnname) {
        this.oldEnname = oldEnname;
    }

    public String getSysData() {
        return sysData;
    }

    public void setSysData(String sysData) {
        this.sysData = sysData;
    }

    public String getUseable() {
        return useable;
    }

    public void setUseable(String useable) {
        this.useable = useable;
    }

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    @JsonIgnore
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(name = "sys_role_menu", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "menu_id")})
    public Set<Menu> getMenus() {
        return menus;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }

    @Transient
    public String getMenuIds() {
        List<Long> nameIdList = new ArrayList<>();
        for (Menu menu : menus) {
            nameIdList.add(menu.getId());
        }
        return StringUtils.join(nameIdList, ",");
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(name = "sys_role_office", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "office_id")})
    public Set<Office> getOffices() {
        return offices;
    }


    public void setOffices(Set<Office> offices) {
        this.offices = offices;
    }


    /**
     * 获取权限字符串列表
     */
    @Transient
    public List<String> getPermissions() {
        List<String> permissions = new ArrayList<>();
        for (Menu menu : menus) {
            if (menu.getPermission() != null && !"".equals(menu.getPermission())) {
                permissions.add(menu.getPermission());
            }
        }
        return permissions;
    }

    @Transient
    public boolean isAdmin() {
        return isAdmin(this.id);
    }

    @Transient
    public static boolean isAdmin(Long id) {
        return id != null && id.equals("1");
    }


    // 数据范围（1：所有数据；2：所在公司及以下数据；3：所在公司数据；4：所在部门及以下数据；5：所在部门数据；8：仅本人数据；9：按明细设置）
    public static final String DATA_SCOPE_ALL = "1";
    public static final String DATA_SCOPE_COMPANY_AND_CHILD = "2";
    public static final String DATA_SCOPE_COMPANY = "3";
    public static final String DATA_SCOPE_OFFICE_AND_CHILD = "4";
    public static final String DATA_SCOPE_OFFICE = "5";
    public static final String DATA_SCOPE_SELF = "8";
    public static final String DATA_SCOPE_CUSTOM = "9";
}