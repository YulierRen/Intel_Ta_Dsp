package com.lty.www.intel_ta_dsp.datasource;

public class DynamicDataSourceContextHolder {
    private static final ThreadLocal<DataSourceType> CONTEXT_HOLDER = new ThreadLocal<>();

    public static void set(DataSourceType dataSourceType) {
        CONTEXT_HOLDER.set(dataSourceType);
    }

    public static DataSourceType get() {
        return CONTEXT_HOLDER.get();
    }

    public static void clear() {
        CONTEXT_HOLDER.remove();
    }
}
