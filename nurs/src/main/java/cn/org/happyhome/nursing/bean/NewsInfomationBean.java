package cn.org.happyhome.nursing.bean;

import java.io.Serializable;

public class NewsInfomationBean implements Serializable{

    private String id;

    private String type;

    private String addressID;

    private String title;

    private String content;

    private String other;

    private String note1;

    private String note2;

    @Override
    public String toString() {
        return "NewsInfomationBean{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", addressID='" + addressID + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", other='" + other + '\'' +
                ", note1='" + note1 + '\'' +
                ", note2='" + note2 + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddressID() {
        return addressID;
    }

    public void setAddressID(String addressID) {
        this.addressID = addressID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getNote1() {
        return note1;
    }

    public void setNote1(String note1) {
        this.note1 = note1;
    }

    public String getNote2() {
        return note2;
    }

    public void setNote2(String note2) {
        this.note2 = note2;
    }
}
