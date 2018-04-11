package com.iip.datafusion.backend.config;


/**
 * 处理能力标识定义
 *
 */
public interface Capabilities {
    // 各个JOBEXECUTOR的个数
    public static final int JOBEXECUTOR_COUNT = Runtime.getRuntime().availableProcessors() / 8 + 1;

    // 单执行器测试
//    public static final int JOBEXECUTOR_COUNT = 1;

    // 注册表在内存中缓存的大小
    public static final int REGISTRY_CACHE_SIZE = 100;
}
