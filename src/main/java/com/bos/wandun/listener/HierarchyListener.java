package com.bos.wandun.listener;

import com.bos.wandun.entity.NodeTree;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class HierarchyListener {


    /**
     * 保存前处理
     *
     * @param entity 实体对象
     */
    @PrePersist
    public void prePersist(NodeTree<?, ?> entity) {
        setValue(entity);
    }

    /**
     * 更新前处理
     *
     * @param entity 实体对象
     */
    @PreUpdate
    public void preUpdate(NodeTree<?, ?> entity) {
        setValue(entity);
        for (NodeTree children : entity.getChildren()) {
            setValue(children);
        }
    }

    /**
     * 设置值
     *
     * @param entity 树形实体
     */
    protected void setValue(NodeTree<?, ?> entity) {
        if (entity == null) {
            return;
        }
        NodeTree parent = entity.getParent();
        if (parent != null) {
            entity.setFullName(parent.getFullName() + entity.getName());
            entity.setPath(parent.getPath() == null ? "" : parent.getPath() + parent.getId() + entity.TREE_PATH_SEPARATOR);
        } else {
            entity.setFullName(entity.getName());
            entity.setPath(entity.TREE_PATH_SEPARATOR);
        }
        entity.setGrade(entity.getParentIds().length);
    }
}