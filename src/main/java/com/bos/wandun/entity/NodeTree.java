package com.bos.wandun.entity;

import com.bos.wandun.listener.HierarchyListener;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.CascadeType;
import javax.persistence.OrderBy;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@ToString
@EntityListeners(HierarchyListener.class)
@MappedSuperclass
public abstract class NodeTree<E extends Serializable, T extends NodeTree<E, T>> extends BaseEntity<E, T> {

    /**
     * 树路径分隔符
     */
    public static final String TREE_PATH_SEPARATOR = ",";

    private String name;
    /**
     * 全称
     */
    private String fullName;
    /**
     * 树路径
     */
    private String path;
    /**
     * 层级
     */
    private Integer grade;
    @ToString.Exclude
    private T parent;

    @ToString.Exclude
    private Set<T> children = new HashSet<>();

    @NotEmpty
    @Length(max = 200)
    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取全称
     *
     * @return 全称
     */
    @Column(nullable = false, length = 4000)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * 获取树路径
     *
     * @return 树路径
     */
    @Column(nullable = false)
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 获取层级
     *
     * @return 层级
     */
    @Column(nullable = false)
    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    /**
     * 父节点
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "parent_id")
    public T getParent() {
        return parent;
    }

    public void setParent(T parent) {
        this.parent = parent;
    }

    /**
     * 子节点
     */
    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @Where(clause = "del_flag='" + DEL_FLAG_NORMAL + "'")
    @OrderBy(value = "sort")
    @Fetch(FetchMode.SUBSELECT)
    public Set<T> getChildren() {
        return children;
    }

    public void setChildren(Set<T> children) {
        this.children = children;
    }


    /**
     * 获取所有上级地区ID
     *
     * @return 所有上级地区ID
     */
    @Transient
    public Long[] getParentIds() {
        String[] parentIds = StringUtils.split(getPath(), TREE_PATH_SEPARATOR);
        Long[] result = new Long[parentIds.length];
        for (int i = 0; i < parentIds.length; i++) {
            result[i] = Long.valueOf(parentIds[i]);
        }
        return result;
    }

    /**
     * 是否存在子节点
     *
     * @return
     */
    @Transient
    public boolean getHasChildren() {
        if (CollectionUtils.isNotEmpty(this.children)) {
            return true;
        }
        return false;
    }

    @Transient
    public String getLevel() {
        String nav = "";
        switch (this.grade) {
            case 0:
                nav = "";
                break;
            case 1:
                nav = "nav-second-level";
                break;
            case 2:
                nav = "nav-third-level";
                break;
            case 3:
                nav = "";
                break;
            default:
                break;
        }
        return nav;
    }
}