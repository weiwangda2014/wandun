package com.bos.wandun.service;

import com.bos.wandun.entity.Office;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface OfficeService extends BaseService<Office, Long> {
    List<Office> findByisAll(Boolean isAll);
}
