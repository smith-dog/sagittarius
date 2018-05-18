package com.hunter.sagittarius.service.converter;


import com.hunter.sagittarius.service.bean.Id;
import com.hunter.sagittarius.service.bean.IdMeta;

/**
 * id转换器
 */
public interface IdConverter {

    /**
     * id对象转换成长整形
     *
     * @param id
     * @return
     */
    long convert(Id id, IdMeta idMeta);

    /**
     * 长整形id转换成id对象
     *
     * @param id
     * @return
     */
    Id convert(long id, IdMeta idMeta);
}
