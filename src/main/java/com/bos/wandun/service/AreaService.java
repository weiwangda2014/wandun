package com.bos.wandun.service;

import com.bos.wandun.entity.Area;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface AreaService extends BaseService<Area, Long> {
    @Cacheable(value = "findChildren", key = "#id")
    List<Area> findChildren(Long id);
}
