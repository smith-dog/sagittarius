package com.hunter.sagittarius.service.populator;


import com.hunter.sagittarius.service.bean.Id;
import com.hunter.sagittarius.service.bean.IdMeta;
import com.hunter.sagittarius.service.time.Timer;

/**
 * 定义id的填充方式
 */
public interface IdPopulator {
    void populateId(Timer timer, Id id, IdMeta idMeta);
}
