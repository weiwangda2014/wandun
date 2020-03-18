package com.bos.wandun.listener;

import com.bos.wandun.entity.BaseEntity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

/**
 * Listener - 创建日期、修改日期、版本处理
 *
 * @author Hello King
 * @version 4.0
 */
public class EntityListener {

    /**
     * 保存前处理
     *
     * @param entity 实体对象
     */
    @PrePersist
    public void prePersist(BaseEntity<?,?> entity) {
        entity.setCreateDate(new Date());
        entity.setUpdateDate(new Date());
        entity.setVersion(null);
    }

    /**
     * 更新前处理
     *
     * @param entity 实体对象
     */
    @PreUpdate
    public void preUpdate(BaseEntity<?,?> entity) {
        entity.setUpdateDate(new Date());
    }

}