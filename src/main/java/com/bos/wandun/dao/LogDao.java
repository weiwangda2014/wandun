package com.bos.wandun.dao;

import com.bos.wandun.entity.Log;

public interface LogDao extends BaseDao<Log, Long> {
    void removeAll();
}
