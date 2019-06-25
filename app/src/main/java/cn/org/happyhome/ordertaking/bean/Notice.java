package cn.org.happyhome.ordertaking.bean;

import java.io.Serializable;

public class Notice implements Serializable {
    String id;
    String noticeTitle;
    String publisher;
    String publishTime;
    String content;

    @Override
    public String toString() {
        return "Notice{" +
                "id='" + id + '\'' +
                ", noticeTitle='" + noticeTitle + '\'' +
                ", publisher='" + publisher + '\'' +
                ", publishTime='" + publishTime + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
