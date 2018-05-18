package com.hunter.sagittarius.service.bean;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class Id {

    //version/type/genMethod/time/sequence/machine

    private long machine;

    private long sequence;

    private long time;

    private long genMethod;

    private long type;

    private long version;

    public Id() {
    }

    public Id(long machine, long sequence, long time, long genMethod, long type, long version) {
        this.machine = machine;
        this.sequence = sequence;
        this.time = time;
        this.genMethod = genMethod;
        this.type = type;
        this.version = version;
    }
}
