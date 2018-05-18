package com.hunter.sagittarius.service.converter.impl;

import com.hunter.sagittarius.service.bean.Id;
import com.hunter.sagittarius.service.bean.IdMeta;
import com.hunter.sagittarius.service.converter.IdConverter;

public class IdConverterImpl implements IdConverter {

    @Override
    public long convert(Id id, IdMeta idMeta) {
        return doConvert(id, idMeta);
    }

    @Override
    public Id convert(long id, IdMeta idMeta) {
        return doConvert(id, idMeta);
    }

    protected long doConvert(Id id, IdMeta idMeta) {
        long ret = 0;

        ret |= id.getMachine();
        //定义序列，左移，或运算
        ret |= id.getSequence() << idMeta.getSeqBitsStartPos();
        ret |= id.getTime() << idMeta.getTimeBitsStartPos();
        ret |= id.getGenMethod() << idMeta.getGenMethodBitsStartPos();
        ret |= id.getType() << idMeta.getTypeBitsStartPos();
        ret |= id.getVersion() << idMeta.getVersionBitsStartPos();

        //最终的形式：version/type/genMethod/time/sequence/machine
        return ret;
    }

    protected Id doConvert(long id, IdMeta idMeta) {
        Id ret = new Id();
        //long类型id的形式：version/type/genMethod/time/sequence/machine
        ret.setMachine(id & idMeta.getMachineBitsMask());
        //无符号的右移，左边补零，与运算
        ret.setSequence(id >>> idMeta.getSeqBitsStartPos() & idMeta.getSeqBitsMask());
        ret.setTime(id >>> idMeta.getTimeBitsStartPos() & idMeta.getTimeBitsMask());
        ret.setGenMethod(id >>> idMeta.getGenMethodBitsStartPos() & idMeta.getGenMethodBitsMask());
        ret.setType(id >>> idMeta.getTypeBitsStartPos() & idMeta.getTypeBitsMask());
        ret.setVersion(id >>> idMeta.getVersionBitsStartPos() & idMeta.getVersionBitsMask());

        return ret;
    }

}
