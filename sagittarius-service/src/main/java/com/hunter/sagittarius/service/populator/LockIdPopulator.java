package com.hunter.sagittarius.service.populator;


import com.hunter.sagittarius.service.bean.Id;
import com.hunter.sagittarius.service.bean.IdMeta;
import com.hunter.sagittarius.service.time.Timer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class LockIdPopulator extends BasePopulator {

    private long sequence = 0L;

    private long lastTimestamp = -1L;

    private Lock lock = new ReentrantLock();

    public LockIdPopulator() {
        super();
    }

    @Override
    public void populateId(Timer timer, Id id, IdMeta idMeta) {
        lock.lock();
        try {
            super.populateId(timer, id, idMeta);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
