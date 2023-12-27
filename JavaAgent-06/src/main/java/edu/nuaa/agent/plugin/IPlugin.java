package edu.nuaa.agent.plugin;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/26 19:39
 */
public interface IPlugin {
    //名称
    String name();

    //监控点
    InterceptPoint[] buildInterceptPoint();

    //拦截器类
    Class adviceClass();
}
