package com.hunter.sagittarius.service.populator;


import com.hunter.sagittarius.service.bean.Id;
import com.hunter.sagittarius.service.bean.IdMeta;
import com.hunter.sagittarius.service.time.Timer;
import org.springframework.stereotype.Component;

@Component
public class SyncIdPopulator extends BasePopulator {

    private long sequence = 0;

    private long lastTimestamp = -1;


    public SyncIdPopulator() {
        super();
    }

    @Override
    public synchronized void populateId(Timer timer, Id id, IdMeta idMeta) {
        super.populateId(timer, id, idMeta);
    }
}
