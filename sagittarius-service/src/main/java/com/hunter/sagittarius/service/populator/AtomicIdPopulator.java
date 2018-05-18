package com.hunter.sagittarius.service.populator;


import com.hunter.sagittarius.service.bean.Id;
import com.hunter.sagittarius.service.bean.IdMeta;
import com.hunter.sagittarius.service.time.Timer;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

@Component
public class AtomicIdPopulator implements IdPopulator {

    AtomicReference<Variant> variantAtomic = new AtomicReference<>(new Variant());

    public AtomicIdPopulator() {
        super();
    }

    @Override
    public void populateId(Timer timer, Id id, IdMeta idMeta) {
        Variant varOld, varNew;
        long timestamp, sequence;

        while (true) {
            //保存旧的值
            varOld = variantAtomic.get();

            //获取当前的variantAtomic
            timestamp = timer.genTime();
            timer.validateTimestamp(varOld.lastTimestamp, timestamp);

            sequence = varOld.sequence;

            //还没有到达下一个时间单位
            if (timestamp == varOld.lastTimestamp) {
                //时间累加
                sequence++;
                sequence &= idMeta.getSeqBitsMask();
                //时间累加越界
                if (sequence == 0L) {
                    timestamp = timer.tillNextTimeUnit(varOld.lastTimestamp);
                } else {
                    sequence = 0L;
                }
            }

            varNew = new Variant();
            varNew.sequence = sequence;
            varNew.lastTimestamp = timestamp;

            //cas更新
            if (variantAtomic.compareAndSet(varOld, varNew)) {
                id.setSequence(sequence);
                id.setTime(timestamp);
                break;
            }

        }
    }


    class Variant {
        private long sequence = 0L;
        private long lastTimestamp = -1L;
    }
}
