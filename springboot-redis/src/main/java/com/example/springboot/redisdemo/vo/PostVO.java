package com.example.springboot.redisdemo.vo;

import com.example.springboot.redisdemo.domain.Post;

/**
 * Created by liuguo on 2017/8/2.
 */
public class PostVO {
    private String content;
    private String name;
    private String replyTo;
    private String replyPid;
    private String pid;
    private String time;
    private String timeArg;

    public PostVO() {
    }

    public PostVO(Post post) {

        String tempTime = VOUtils.timeInWords(Long.valueOf(post.getTime()));
        int lastIndexOf = tempTime.lastIndexOf("#");
        if (lastIndexOf > 0) {
            this.time = tempTime.substring(0, lastIndexOf);
            this.timeArg = tempTime.substring(lastIndexOf + 1);
        }
        else {
            this.time = tempTime;
            this.timeArg = "";
        }
        this.replyPid = post.getReplyPid();
        this.content = post.getContent();
    }

    /**
     * Returns the replyPid.
     *
     * @return Returns the replyPid
     */
    public String getReplyPid() {
        return replyPid;
    }

    /**
     * @param replyPid The replyPid to set.
     */
    public void setReplyPid(String replyPid) {
        this.replyPid = replyPid;
    }

    /**
     * Returns the name.
     *
     * @return Returns the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the replyTo.
     *
     * @return Returns the replyTo
     */
    public String getReplyTo() {
        return replyTo;
    }

    /**
     * Returns the content.
     *
     * @return Returns the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content The content to set.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Returns the pid.
     *
     * @return Returns the pid
     */
    public String getPid() {
        return pid;
    }

    /**
     * @param pid The pid to set.
     */
    public void setPid(String pid) {
        this.pid = pid;
    }

    /**
     * Returns the time.
     *
     * @return Returns the time
     */
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTimeArg() {
        return timeArg;
    }

    /**
     * @param replyName The replyTo to set.
     */
    public void setReplyTo(String replyName) {
        this.replyTo = replyName;
    }

    public Post asPost() {
        Post post = new Post();
        post.setReplyPid(replyPid);
        post.setContent(content);
        return post;
    }

    @Override
    public String toString() {
        return "WebPost [content=" + content + ", name=" + name + ", pid=" + pid + ", replyTo=" + replyTo
                + ", replyPid=" + replyPid + ", time=" + time + "]";
    }
}
