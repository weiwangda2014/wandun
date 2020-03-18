package com.bos.wandun.service;

import com.bos.wandun.Filter;
import com.bos.wandun.entity.Dict;

import java.util.List;

public interface DictService extends BaseService<Dict, Long> {
    Dict findByLabel(String label);

    List<Dict> findByType(String type);
}
