package com.bos.wandun.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "sys_area")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "seq_area")
public class Area extends NodeTree<Long, Area> {

    /**
     * 区域编码
     */
    private String code;

    /**
     * 区域类型（1：国家；2：省份、直辖市；3：地市；4：区县）
     */
    private String type;

    private Set<Office> offices = Sets.newHashSet(); // 部门列表


    @Column(length = 200)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    @Column(length = 200)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "area", fetch = FetchType.LAZY)
    @Where(clause = "del_flag='" + DEL_FLAG_NORMAL + "'")
    @OrderBy(value = "code")
    @Fetch(FetchMode.SUBSELECT)
    public Set<Office> getOffices() {
        return offices;
    }

    public void setOffices(Set<Office> offices) {
        this.offices = offices;
    }

    public Area() {
        super();
    }
}
