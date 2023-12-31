package edu.nuaa.agent.track;

import java.util.Date;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/26 19:38
 */
public class Span {
    private String linkId;  //链路ID
    private Date enterTime; //方法进入时间

    public Span(){}

    public Span(String linkId){
        this.linkId = linkId;
        this.enterTime = new Date();
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public Date getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(Date enterTime) {
        this.enterTime = enterTime;
    }
}
