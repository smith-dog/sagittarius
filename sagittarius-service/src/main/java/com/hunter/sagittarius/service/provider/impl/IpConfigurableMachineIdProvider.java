package com.hunter.sagittarius.service.provider.impl;

import com.hunter.sagittarius.service.provider.MachineIdProvider;
import com.hunter.sagittarius.service.utils.IpUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class IpConfigurableMachineIdProvider implements MachineIdProvider {

    private static final Logger logger = LoggerFactory.getLogger(IpConfigurableMachineIdProvider.class);

    private long machineId;

    private Map<String, Long> ipsMap = new HashMap<String, Long>();

    public IpConfigurableMachineIdProvider() {
        logger.debug("IpConfigurableMachineIdProvider constructed.");
    }

    public IpConfigurableMachineIdProvider(String ips) {
        setIps(ips);
        init();
    }

    public void init() {
        String ip = IpUtils.getHostIp();

        //获取不到ip,或者集合中不包含此ip
        if (StringUtils.isEmpty(ip) || !ipsMap.containsKey(ip)) {
            if (logger.isErrorEnabled()) {
                String msg = new String(String.format("Fail to configure ID for host IP address %s.Stop to initialize the IpConfigurableMachineIdProvider provider"));
                logger.error(msg);
                throw new IllegalStateException(msg);
            }
        }

        machineId = ipsMap.get(ip);

        logger.info("IpConfigurableMachineIdProvider.init ip{} id{}", ip, machineId);

    }

    public void setIps(String ips) {
        if (!StringUtils.isEmpty(ips)) {
            String[] ipArray = ips.split(",");

            for (int i = 0; i < ipArray.length; i++) {
                ipsMap.put(ipArray[i], (long) i);
            }


        }
    }

    @Override
    public long getMachineId() {
        return machineId;
    }

    public void setMachineId(long machineId) {
        this.machineId = machineId;
    }
}
