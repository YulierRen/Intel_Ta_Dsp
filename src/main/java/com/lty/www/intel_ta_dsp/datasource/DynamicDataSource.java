package com.lty.www.intel_ta_dsp.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        // 每次获取连接时，都会来这里拿 key
        return DynamicDataSourceContextHolder.get();
    }
}
