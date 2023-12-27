package edu.nuaa.agent;

import java.lang.instrument.Instrumentation;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/26 17:02
 */
public class MyAgent {
    public static void premain(String agentArgs, Instrumentation inst){
        System.out.println("this is my agent" + agentArgs);
        MyMonitorTransformer monitor = new MyMonitorTransformer();
        inst.addTransformer(monitor);
    }
    //如果代理类没有实现上面的方法，那么 JVM 将尝试调用该方法
    public static void premain(String agentArgs) {
    }
}
