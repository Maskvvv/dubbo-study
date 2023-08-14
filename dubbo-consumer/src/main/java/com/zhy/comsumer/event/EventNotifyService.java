package com.zhy.comsumer.event;

public interface EventNotifyService {
    /**
     * 调用之前
     * @param name
     */
    void onInvoke(String name);

    /**
     * 有参数：调用之后
     * @param result 第一个参数 接收 [事件通知]服务接口的方法返回值类型保持一致
     * @param name 第二个或者之后，与[事件通知]服务接口的方法入参保持一致
     */
    void onReturn(String result, String name);

    /**
     * 抛异常
     * @param ex
     * @param name
     */
    void onThrow(Throwable ex, String name);
}
