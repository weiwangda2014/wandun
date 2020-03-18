/*
 *
 *
 *
 */
package com.bos.wandun.entity;

import com.bos.wandun.listener.EntityListener;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.groups.Default;
import java.io.Serializable;
import java.util.Date;

/**
 * Entity - 基类
 *
 * @author Hello King
 * @version 4.0
 */
@EntityListeners(EntityListener.class)
@MappedSuperclass
public abstract class BaseEntity<ID extends Serializable, T> implements Serializable {

    /**
     * 删除标记（0：正常；1：删除；2：审核；）
     */
    public static final int DEL_FLAG_NORMAL = 0;
    public static final int DEL_FLAG_DELETE = 1;
    public static final int DEL_FLAG_AUDIT = 2;


    /**
     * 显示/隐藏
     */
    public static final String SHOW = "1";
    public static final String HIDE = "0";

    /**
     * 是/否
     */
    public static final String YES = "1";
    public static final String NO = "0";

    /**
     * 删除标记（0：正常；1：删除；2：审核；）
     */
    public static final String FIELD_DEL_FLAG = "delFlag";

    /**
     * "ID"属性名称
     */
    public static final String ID_PROPERTY_NAME = "id";

    /**
     * "创建日期"属性名称
     */
    public static final String CREATE_DATE_PROPERTY_NAME = "createDate";

    /**
     * "修改日期"属性名称
     */
    public static final String MODIFY_DATE_PROPERTY_NAME = "updateDate";

    /**
     * "版本"属性名称
     */
    public static final String VERSION_PROPERTY_NAME = "version";

    /**
     * "备注"属性名称
     */
    public static final String REMARKS_PROPERTY_NAME = "remarks";

    public BaseEntity(ID id) {
        this();
        this.id = id;
    }

    public BaseEntity() {

    }

    /**
     * 保存验证组
     */
    public interface Save extends Default {

    }

    /**
     * 更新验证组
     */
    public interface Update extends Default {

    }

    /**
     * 唯一编号
     */
    protected ID id;
    /**
     * 创建人员
     */
    private Long createBy;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新人员
     */
    private Long updateBy;
    /**
     * 更新时间
     */
    private Date updateDate;
    /**
     * 删除标记（0：正常；1：删除；2：审核）
     */
    private Integer delFlag = DEL_FLAG_NORMAL;
    /**
     * 版本
     */
    private Long version;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 备注
     */
    private String remarks;


    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sequenceGenerator")
    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    /**
     * 创建人员
     */
    @JsonIgnore
    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    /**
     * 创建日期
     */
    @JsonIgnore
    @Column(nullable = false, updatable = false)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 更新人员
     */
    @JsonIgnore
    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 修改日期
     */
    @JsonIgnore
    @Column(nullable = false)
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 数据状态
     */
    @JsonIgnore
    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * 版本
     */
    @JsonIgnore
    @Version
    @Column(nullable = false)
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    /**
     * 排序
     */
    @JsonIgnore
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 备注
     */
    @JsonIgnore
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * 判断是否为新建对象
     *
     * @return 是否为新建对象
     */
    @Transient
    public boolean isNew() {
        return getId() == null;
    }


    /**
     * 重写toString方法
     *
     * @return 字符串
     */
    @Override
    public String toString() {
        return String.format("Entity of type %s with id: %s", getClass().getName(), getId());
    }

    /**
     * 重写equals方法
     *
     * @param obj 对象
     * @return 是否相等
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!BaseEntity.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        BaseEntity<?, ?> other = (BaseEntity<?, ?>) obj;
        return getId() != null ? getId().equals(other.getId()) : false;
    }

    /**
     * 重写hashCode方法
     *
     * @return HashCode
     */
    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode += getId() != null ? getId().hashCode() * 31 : 0;
        return hashCode;
    }
}
