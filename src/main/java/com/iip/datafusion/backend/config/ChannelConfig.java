package com.iip.datafusion.backend.config;

public interface ChannelConfig {
    // 各种类型工作通道的个数
    public static final int CHANNEL_SIZE = Runtime.getRuntime().availableProcessors() / 2 + 1;
}
