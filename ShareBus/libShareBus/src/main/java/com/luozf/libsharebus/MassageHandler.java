package com.luozf.libsharebus;

/**
 * 处理消息回调
 */
public interface MassageHandler {

    /**
     * 处理消息
     * @param message
     */
    void handleMessage(String message);
}
