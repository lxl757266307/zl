package cn.org.happyhome.nursing.bean;

import java.io.Serializable;

public class ServiceItemBean implements Serializable {


    String seq;
    String orderID;
    String userID;

    @Override
    public String  toString() {
        return "ServiceItemBean{" +
                "seq='" + seq + '\'' +
                ", orderID='" + orderID + '\'' +
                ", userID='" + userID + '\'' +
                '}';
    }

    public ServiceItemBean(String seq, String orderID, String userID) {
        this.seq = seq;
        this.orderID = orderID;
        this.userID = userID;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }


}
