package com.bos.wandun.entity;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class DataEntity<E extends Serializable, T> extends BaseEntity<E, T> implements Serializable {


}
