package com.bos.wandun.service;

import com.bos.wandun.entity.Log;

/**
 * Created by Fant.J.
 */
public interface LogService extends BaseService<Log, Long> {

    void clear();
}