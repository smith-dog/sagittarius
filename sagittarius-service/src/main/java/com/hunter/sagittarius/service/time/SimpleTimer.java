package com.hunter.sagittarius.service.time;

import com.hunter.sagittarius.service.bean.IdMeta;
import com.hunter.sagittarius.service.bean.IdType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class SimpleTimer implements Timer {

    protected static final Logger log = LoggerFactory.getLogger(SimpleTimer.class);
    protected IdMeta idMeta;
    protected IdType idType;
    protected long maxTime;
    protected long epoch = EPOCH;

    private static final Logger logger = LoggerFactory.getLogger(SimpleTimer.class);

    @Override
    public void init(IdMeta idMeta, IdType idType) {
        this.idMeta = idMeta;
        this.maxTime = (1 << idMeta.getTimeBits()) - 1;
        this.idType = idType;
        this.genTime();
        this.timerUsedLog();
    }

    public void timerUsedLog() {
        Date expirationDate = transTime(maxTime);
        long days = ((expirationDate.getTime() - System.currentTimeMillis()) / (1000 * 60 * 60 * 24));
        log.info("The current time bit length is {}, the expiration date is {}, this can be used for {} days.",
                idMeta.getTimeBits(), expirationDate, days);
    }

    public void setEpoch(long epoch) {
        this.epoch = epoch;
    }

    public Date transTime(long time) {
        if (idType == IdType.MILLISECONDS) {
            return new Date(time + epoch);
        } else {
            return new Date(time * 1000 + epoch);
        }
    }

    @Override
    public void validateTimestamp(long lastTimestamp, long timestamp) {
        if (timestamp < lastTimestamp) {
            if (logger.isInfoEnabled()) {
                logger.info(String.format("Clock moved backwards. Eefusing to generate id for %d second/millisecond", lastTimestamp - timestamp));
            }
            throw new IllegalStateException(String.format("Clock moved backwards. Eefusing to generate id for %d second/millisecond", lastTimestamp - timestamp));
        }
    }

    protected void validateTimestamp(long timestamp) {
        if (timestamp > maxTime) {
            String error = String.format(
                    "The current timestamp (%s >= %s) has overflowed, Vesta Service will be terminate.", timestamp, maxTime);
            log.error(error);
            throw new RuntimeException(error);
        }
    }

    @Override
    public long tillNextTimeUnit(long lastTimestamp) {
        if (logger.isInfoEnabled()) {
            logger.info(String.format("Ids are used out during %d. Wating till next second/millisecond.", lastTimestamp));
        }
        long timestemp = genTime();
        //时间未到达，一直获取，直到下个时间帧
        while (timestemp <= lastTimestamp) {
            timestemp = genTime();
        }
        if (logger.isInfoEnabled()) {
            logger.info(String.format("Next second/millisecond %d is up", timestemp));
        }
        return timestemp;
    }

    @Override
    public long genTime() {
        long time;
        if (idType == IdType.MILLISECONDS) {
            time = (System.currentTimeMillis() - epoch);
        } else {
            time = (System.currentTimeMillis() - epoch) / 1000;
        }
        validateTimestamp(time);
        return time;
    }
}
