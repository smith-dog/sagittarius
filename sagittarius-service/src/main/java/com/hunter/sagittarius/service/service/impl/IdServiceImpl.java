package com.hunter.sagittarius.service.service.impl;


import com.hunter.sagittarius.service.bean.Id;
import com.hunter.sagittarius.service.bean.IdType;
import com.hunter.sagittarius.service.populator.AtomicIdPopulator;
import com.hunter.sagittarius.service.populator.IdPopulator;
import com.hunter.sagittarius.service.populator.LockIdPopulator;
import com.hunter.sagittarius.service.populator.SyncIdPopulator;
import com.hunter.sagittarius.service.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("IdServiceImpl")
public class IdServiceImpl extends AbstractIdServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(IdServiceImpl.class);

    private static final String SYNC_LOCK_IMPL_KEY = "vesta.sync.impl.key";

    private static final String ATOMIC_IMPL_KEY = "vesta.atomic.impl.key";

    private IdPopulator idPopulator;

    @Override
    protected void populateId(Id id) {
        idPopulator.populateId(timer, id, this.idMeta);
    }

    public IdServiceImpl() {
        super();
    }

    public IdServiceImpl(String type) {
        super(type);
    }

    public IdServiceImpl(long type) {
        super(type);
    }

    public IdServiceImpl(IdType type) {
        super(type);
    }

    @Override
    public void init() {
        super.init();
        initPopulator();
    }

    /**
     * 初始化id填充器
     */
    public void initPopulator() {
        if (idPopulator != null){
            logger.info("The " + idPopulator.getClass().getCanonicalName() + " is used.");
        } else if (CommonUtils.isPropKeyOn(SYNC_LOCK_IMPL_KEY)) {
            logger.info("The SyncIdPopulator is used.");
            idPopulator = new SyncIdPopulator();
        } else if (CommonUtils.isPropKeyOn(ATOMIC_IMPL_KEY)) {
            logger.info("The AtomicIdPopulator is used.");
            idPopulator = new AtomicIdPopulator();
        } else {
            logger.info("The default LockIdPopulator is used.");
            idPopulator = new LockIdPopulator();
        }
    }

}
