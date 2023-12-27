package edu.nuaa.agent.track;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/26 19:10
 */
public class TrackContext {
    private static final ThreadLocal<String> trackLocal = new ThreadLocal<String>();

    public static void clear(){
        trackLocal.remove();
    }

    public static String getLinkId(){
        return trackLocal.get();
    }

    public static void setLinkId(String linkId){
        trackLocal.set(linkId);
    }


}
