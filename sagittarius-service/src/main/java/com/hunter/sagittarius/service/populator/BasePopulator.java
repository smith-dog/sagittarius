package com.hunter.sagittarius.service.populator;


import com.hunter.sagittarius.service.bean.Id;
import com.hunter.sagittarius.service.bean.IdMeta;
import com.hunter.sagittarius.service.time.Timer;

public class BasePopulator implements IdPopulator {

    protected long sequence = 0;
    protected long lastTimestamp = -1;

    public BasePopulator() {
        super();
    }

    @Override
    public void populateId(Timer timer, Id id, IdMeta idMeta) {
        long timestamp = timer.genTime();
        timer.validateTimestamp(lastTimestamp, timestamp);

        //还没有到达下一个时间单位
        if (timestamp == lastTimestamp) {
            //时间累加
            sequence++;
            sequence &= idMeta.getSeqBitsMask();
            //时间累加越界
            if (sequence == 0L) {
                timestamp = timer.tillNextTimeUnit(lastTimestamp);
            }
        } else {
            //到达下一个时间单位，清空序列
            lastTimestamp = timestamp;
            sequence = 0L;
        }

        id.setSequence(sequence);
        id.setTime(timestamp);
    }
}
