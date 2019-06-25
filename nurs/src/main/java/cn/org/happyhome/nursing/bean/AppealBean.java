package cn.org.happyhome.nursing.bean;


import java.util.ArrayList;

public class AppealBean {


    private String orderid;

    private String userid;

    private String content;

    private ArrayList<String> keys;


    public AppealBean(String orderid, String userid, String content, ArrayList<String> keys) {
        this.orderid = orderid;
        this.content = content;
        this.userid = userid;
        this.keys = keys;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<String> getKeys() {
        return keys;
    }

    public void setKeys(ArrayList<String> keys) {
        this.keys = keys;
    }
}
