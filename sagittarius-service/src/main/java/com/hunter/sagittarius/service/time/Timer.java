package com.hunter.sagittarius.service.time;


import com.hunter.sagittarius.service.bean.IdMeta;
import com.hunter.sagittarius.service.bean.IdType;

import java.util.Date;

public interface Timer {

    long EPOCH = 1420041600000L;

    void init(IdMeta idMeta, IdType idType);

    Date transTime(long time);

    void validateTimestamp(long lastTimestamp, long timestamp);

    long tillNextTimeUnit(long lastTimestamp);

    long genTime();

}
