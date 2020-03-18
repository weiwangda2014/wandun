package com.bos.wandun.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "sys_dict")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "seq_dict")
public class Dict extends DataEntity<Long, Dict> {

    private String label;    // 标签名
    private String value;    // 数据值
    private String type;    // 类型
    private String description;// 描述
    private Integer sort;    // 排序
    private Integer parentId;//父Id


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}
