package com.hunter.sagittarius.service.bean;

/**
 * 定义了二进制数据中每段的位置
 */
public class IdMeta {

    private byte machineBits;

    private byte seqBits;

    private byte timeBits;

    private byte genMethodBits;

    private byte typeBits;

    private byte versionBits;

    public IdMeta(byte machineBits, byte seqBits, byte timeBits, byte genMethodBits, byte typeBits, byte versionBits) {
        super();

        this.machineBits = machineBits;
        this.seqBits = seqBits;
        this.timeBits = timeBits;
        this.genMethodBits = genMethodBits;
        this.typeBits = typeBits;
        this.versionBits = versionBits;
    }

    private long machineBitsMask;

    private long seqBitsStartPos;

    private long seqBitsMask;

    private long timeBitsStartPos;

    private long timeBitsMask;

    private long genMethodBitsStartPos;

    private long genMethodBitsMask;

    private long typeBitsStartPos;

    private long typeBitsMask;

    private long VersionBitsStartPos;

    private long VersionBitsMask;

    public byte getMachineBits() {
        return machineBits;
    }

    public void setMachineBits(byte machineBits) {
        this.machineBits = machineBits;
    }

    public byte getSeqBits() {
        return seqBits;
    }

    public void setSeqBits(byte seqBits) {
        this.seqBits = seqBits;
    }

    public byte getTimeBits() {
        return timeBits;
    }

    public void setTimeBits(byte timeBits) {
        this.timeBits = timeBits;
    }

    public byte getGenMethodBits() {
        return genMethodBits;
    }

    public void setGenMethodBits(byte genMethodBits) {
        this.genMethodBits = genMethodBits;
    }

    public byte getTypeBits() {
        return typeBits;
    }

    public void setTypeBits(byte typeBits) {
        this.typeBits = typeBits;
    }

    public byte getVersionBits() {
        return versionBits;
    }

    public void setVersionBits(byte versionBits) {
        this.versionBits = versionBits;
    }


    public long getMachineBitsMask() {
        return -1L ^ -1L << machineBits;
    }

    public long getSeqBitsStartPos() {
        return seqBitsStartPos;
    }

    public long getSeqBitsMask() {
        return -1L ^ -1L << seqBits;
    }

    public long getTimeBitsStartPos() {
        return timeBitsStartPos;
    }

    public long getTimeBitsMask() {
        return -1L ^ -1L << timeBits;
    }

    public long getGenMethodBitsStartPos() {
        return genMethodBitsStartPos;
    }

    public long getGenMethodBitsMask() {
        return -1L ^ -1L << genMethodBits;
    }

    public long getTypeBitsStartPos() {
        return typeBitsStartPos;
    }

    public long getTypeBitsMask() {
        return -1L ^ -1L << typeBits;
    }

    public long getVersionBitsStartPos() {
        return VersionBitsStartPos;
    }

    public long getVersionBitsMask() {
        return -1L ^ -1L << versionBits;
    }
}
