package com.bos.wandun.dao;

import com.bos.wandun.entity.Dict;

public interface DictDao extends BaseDao<Dict, Long> {
    Dict findByLabel(String label);
}
