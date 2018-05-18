package com.hunter.sagittarius.service.service;


import com.hunter.sagittarius.service.bean.Id;

import java.util.Date;

/**
 * 发号器接口
 */
public interface IdService {

    long genId();

    Id expId(long Id);

    long makeId(long time, long seq);

    long makeId(long time, long seq, long machine);

    long makeId(long time, long seq, long machine, long genMethod);

    long makeId(long time, long seq, long machine, long genMethod, long type);

    long makeId(long time, long seq, long machine, long genMethod, long type, long version);

    Date transTime(long time);

}
