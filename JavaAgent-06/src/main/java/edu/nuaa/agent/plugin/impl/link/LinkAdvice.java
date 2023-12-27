package edu.nuaa.agent.plugin.impl.link;

import edu.nuaa.agent.track.Span;
import edu.nuaa.agent.track.TrackContext;
import edu.nuaa.agent.track.TrackManager;
import net.bytebuddy.asm.Advice;

import java.util.UUID;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/26 19:40
 */
public class LinkAdvice {
    @Advice.OnMethodEnter()
    public static void enter(@Advice.Origin("#t") String className, @Advice.Origin("#m") String methodName) {
        Span currentSpan = TrackManager.getCurrentSpan();
        if (null == currentSpan) {
            String linkId = UUID.randomUUID().toString();
            TrackContext.setLinkId(linkId);
        }
        TrackManager.createEntrySpan();
    }

    @Advice.OnMethodExit()
    public static void exit(@Advice.Origin("#t") String className, @Advice.Origin("#m") String methodName) {
        Span exitSpan = TrackManager.getExitSpan();
        if (null == exitSpan) return;
        System.out.println("链路追踪(MQ)：" + exitSpan.getLinkId() + " " + className + "." + methodName + " 耗时：" + (System.currentTimeMillis() - exitSpan.getEnterTime().getTime()) + "ms");
    }

}
