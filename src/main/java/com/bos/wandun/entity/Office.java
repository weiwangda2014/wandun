package com.bos.wandun.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "sys_office")
@DynamicInsert
@DynamicUpdate
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "seq_office")
public class Office extends NodeTree<Long, Office> {

    // private String parentIds; // 所有父级编号
    private Area area;        // 归属区域
    private String code;    // 机构编码
    private String type;    // 机构类型（1：公司；2：部门；3：小组）

    /**
     * 全称
     */
    private String fullName;
    private String address; // 联系地址
    private String zipCode; // 邮政编码
    private String master;    // 负责人
    private String phone;    // 电话
    private String fax;    // 传真
    private String email;    // 邮箱
    private String useable;//是否可用

    private User primaryPerson;//主负责人
    private User deputyPerson;//副负责人

    private Set<User> users = new HashSet<>();   // 拥有用户列表
    private Set<Role> roles = new HashSet<>(); // 拥有角色列表


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id")
    @NotFound(action = NotFoundAction.IGNORE)
    @NotNull
    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    @Length(max = 200)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    @Length(max = 100)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Length(max = 500)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Length(max = 100)
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Length(max = 100)
    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    @Length(max = 100)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Length(max = 100)
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Email
    @Length(max = 100)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUseable() {
        return useable;
    }

    public void setUseable(String useable) {
        this.useable = useable;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "primary_id")
    @NotFound(action = NotFoundAction.IGNORE)
    public User getPrimaryPerson() {
        return primaryPerson;
    }

    public void setPrimaryPerson(User primaryPerson) {
        this.primaryPerson = primaryPerson;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deputy_id")
    @NotFound(action = NotFoundAction.IGNORE)
    public User getDeputyPerson() {
        return deputyPerson;
    }

    public void setDeputyPerson(User deputyPerson) {
        this.deputyPerson = deputyPerson;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "office", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @JsonIgnore
    @ManyToMany(mappedBy = "offices", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

}
