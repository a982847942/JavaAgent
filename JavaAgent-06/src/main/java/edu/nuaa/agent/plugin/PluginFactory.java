package edu.nuaa.agent.plugin;

import edu.nuaa.agent.plugin.impl.jvm.JvmPlugin;
import edu.nuaa.agent.plugin.impl.link.LinkPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/26 19:39
 */
public class PluginFactory {
    public static List<IPlugin> pluginGroup = new ArrayList<>();

    static {
        //链路监控
        pluginGroup.add(new LinkPlugin());
        //Jvm监控
        pluginGroup.add(new JvmPlugin());
    }
}
