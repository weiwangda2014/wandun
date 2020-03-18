package com.bos.wandun.service;

import com.bos.wandun.entity.Menu;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface MenuService extends BaseService<Menu, Long> {
    @Cacheable(value = "findByUserId", key = "#id")
    List<Menu> findByUserId(Long id);
}
