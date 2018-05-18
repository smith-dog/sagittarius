package com.hunter.sagittarius.service.service.impl;

import com.hunter.sagittarius.service.bean.Id;
import com.hunter.sagittarius.service.bean.IdMeta;
import com.hunter.sagittarius.service.bean.IdType;
import com.hunter.sagittarius.service.converter.IdConverter;
import com.hunter.sagittarius.service.converter.impl.IdConverterImpl;
import com.hunter.sagittarius.service.factory.IdMetaFactory;
import com.hunter.sagittarius.service.provider.MachineIdProvider;
import com.hunter.sagittarius.service.service.IdService;
import com.hunter.sagittarius.service.time.SimpleTimer;
import com.hunter.sagittarius.service.time.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public abstract class AbstractIdServiceImpl implements IdService {

    private final static Logger logger = LoggerFactory.getLogger(AbstractIdServiceImpl.class);

    protected IdConverter idConverter;
    protected IdType idType;
    protected IdMeta idMeta;
    protected Timer timer;

    protected MachineIdProvider machineIdProvider;
    /**
     * 机器id
     */
    protected long machineId = -1;
    /**
     * 定义生成方式
     */
    protected long genMethod = 0;
    /**
     * 版本
     */
    protected long version = 0;

    public AbstractIdServiceImpl() {
        idType = IdType.SECONDS;
    }

    public AbstractIdServiceImpl(String type) {
        idType = IdType.parse(type);
    }

    public AbstractIdServiceImpl(long type) {
        idType = IdType.parse(type);
    }

    public AbstractIdServiceImpl(IdType type) {
        idType = type;
    }

    public void init() {
        if (this.idMeta == null) {
            setIdMeta(IdMetaFactory.getIdMeta(idType));
        }
        if (this.idConverter == null) {
            setIdConverter(new IdConverterImpl());
        }
        if (this.timer == null) {
            setTimer(new SimpleTimer());
        }
        this.timer.init(idMeta, idType);

        this.machineId = machineIdProvider.getMachineId();
        validateMachineId(this.machineId);
    }

    @Override
    public long genId() {
        Id id = new Id();

        id.setMachine(machineId);
        id.setGenMethod(genMethod);
        id.setType(idType.value());
        id.setVersion(version);

        //生成方式,由子类实现
        populateId(id);

        long ret = idConverter.convert(id, this.idMeta);

        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Id: %s => %d", id, ret));
        }

        return ret;
    }


    @Override
    public Id expId(long id) {
        return idConverter.convert(id, this.idMeta);
    }

    @Override
    public long makeId(long time, long sequence) {
        return makeId(time, sequence, machineId);
    }

    @Override
    public long makeId(long time, long sequence, long machine) {
        return makeId(time, sequence, machine, genMethod);
    }

    @Override
    public long makeId(long time, long sequence, long machine, long genMethod) {
        return makeId(time, sequence, machine, genMethod, idType.value());
    }

    @Override
    public long makeId(long time, long sequence, long machine, long genMethod, long type) {
        return makeId(time, sequence, machine, genMethod, type, version);
    }

    @Override
    public long makeId(long time, long sequence, long machine, long genMethod, long type, long version) {
        Id id = new Id(machine, sequence, time, genMethod, type, version);
        return idConverter.convert(id, this.idMeta);
    }


    @Override
    public Date transTime(long time) {
        return null;
    }

    public void validateMachineId(long machineId) {
        if (machineId < 0) {
            logger.error("The machine ID is not configured properly (" + machineId + " < 0) so that Vesta Service refuses to start.");

            throw new IllegalStateException(
                    "The machine ID is not configured properly (" + machineId + " < 0) so that Vesta Service refuses to start.");

        } else if (machineId >= (1 << this.idMeta.getMachineBits())) {
            logger.error("The machine ID is not configured properly ("
                    + machineId + " >= " + (1 << this.idMeta.getMachineBits()) + ") so that Vesta Service refuses to start.");

            throw new IllegalStateException("The machine ID is not configured properly ("
                    + machineId + " >= " + (1 << this.idMeta.getMachineBits()) + ") so that Vesta Service refuses to start.");

        }
    }

    public void setMachineId(long machineId) {
        this.machineId = machineId;
    }

    public void setGenMethod(long genMethod) {
        this.genMethod = genMethod;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public void setIdConverter(IdConverter idConverter) {
        this.idConverter = idConverter;
    }

    public void setIdMeta(IdMeta idMeta) {
        this.idMeta = idMeta;
    }

    public void setMachineIdProvider(MachineIdProvider machineIdProvider) {
        this.machineIdProvider = machineIdProvider;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    protected abstract void populateId(Id id);
}
