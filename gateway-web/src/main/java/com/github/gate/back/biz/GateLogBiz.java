package com.github.gate.back.biz;

import com.github.gate.back.entity.GateLog;
import com.github.gate.back.mapper.GateLogMapper;
import com.github.gate.common.biz.BaseBiz;
import org.springframework.stereotype.Service;

/**
 * ${DESCRIPTION}
 *
 * @author luozhonghua
 * @create 2017-07-01 14:36
 */
@Service
public class GateLogBiz extends BaseBiz<GateLogMapper,GateLog> {

    @Override
    public void insert(GateLog entity) {
        mapper.insert(entity);
    }

    @Override
    public void insertSelective(GateLog entity) {
        mapper.insertSelective(entity);
    }
}
