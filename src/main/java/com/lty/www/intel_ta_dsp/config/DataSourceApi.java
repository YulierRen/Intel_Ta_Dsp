package com.lty.www.intel_ta_dsp.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DataSourceApi {
    @Value("${master.url}")
    private String masterUrl;

    @Value("${master.username}")
    private String masterUsername;

    @Value("${master.password}")
    private String masterPassword;

    @Value("${slave.url}")
    private String slaveUrl;

    @Value("${slave.username}")
    private String slaveUsername;

    @Value("${slave.password}")
    private String slavePassword;

    @Value("${redis.ip}")
    private String redisIp;

    @Value("${redis.port}")
    private int redisPort;

    public String getRedisIp() {
        return redisIp;
    }

    public int getRedisPort() {
        return redisPort;
    }

    public String getMasterUrl() {
        return masterUrl;
    }

    public String getMasterUsername() {
        return masterUsername;
    }

    public String getMasterPassword() {
        return masterPassword;
    }

    public String getSlaveUrl() {
        return slaveUrl;
    }

    public String getSlaveUsername() {
        return slaveUsername;
    }

    public String getSlavePassword() {
        return slavePassword;
    }
}
