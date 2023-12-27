package edu.nuaa.agent;

import java.lang.instrument.Instrumentation;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/26 15:25
 */
public class MyAgent {
    //JVM 首先尝试在代理类上调用以下方法
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("嗨！JavaAgent " + agentArgs);
    }

    //如果代理类没有实现上面的方法，那么 JVM 将尝试调用该方法
    public static void premain(String agentArgs) {
    }
}
