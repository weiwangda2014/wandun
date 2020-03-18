package com.bos.wandun.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * Entity - 排序基类
 *
 * @author BOS Team
 * @version 4.0
 */
@MappedSuperclass
public abstract class BaseOrderEntity<ID extends Serializable, T> extends BaseEntity<ID, T> implements Comparable<BaseOrderEntity<ID, T>> {


    /**
     * "排序"属性名称
     */
    public static final String ORDER_PROPERTY_NAME = "order";
    /**
     * 排序
     */
    @Min(0)
    @Column(name = "orders")
    private Integer order;

}