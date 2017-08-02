package com.example.springboot.redisdemo.domain;

/**
 * Created by liuguo on 2017/8/2.
 */
public class Post {
    private String uid;
    private String content;
    private String time = String.valueOf(System.currentTimeMillis());
    private String replyPid;
    private String replyUid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getReplyPid() {
        return replyPid;
    }

    public void setReplyPid(String replyPid) {
        this.replyPid = replyPid;
    }

    public String getReplyUid() {
        return replyUid;
    }

    public void setReplyUid(String replyUid) {
        this.replyUid = replyUid;
    }
}
