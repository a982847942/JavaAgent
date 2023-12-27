package edu.nuaa.agent.plugin.impl.jvm;

import net.bytebuddy.asm.Advice;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/26 19:40
 */
public class JvmAdvice {
    @Advice.OnMethodExit()
    public static void exit() {
        JvmStack.printMemoryInfo();
        JvmStack.printGCInfo();
    }
}
