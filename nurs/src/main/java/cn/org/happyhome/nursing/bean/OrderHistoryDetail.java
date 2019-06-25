package cn.org.happyhome.nursing.bean;

import java.io.Serializable;

public class OrderHistoryDetail implements Serializable {

    private String orderid;

    private String servicetypeid;

    private String userid;

    private String patient;

    private String servicetype;

    private long orderdate;

    private Double price;

    private long begindate;

    private long enddate;

    private String tpye;

    private String note;

    private String stateid;

    @Override
    public String toString() {
        return "OrderHistoryDetail{" +
                "orderid='" + orderid + '\'' +
                ", servicetypeid='" + servicetypeid + '\'' +
                ", userid='" + userid + '\'' +
                ", patient='" + patient + '\'' +
                ", servicetype='" + servicetype + '\'' +
                ", orderdate=" + orderdate +
                ", price=" + price +
                ", begindate=" + begindate +
                ", enddate=" + enddate +
                ", tpye='" + tpye + '\'' +
                ", note='" + note + '\'' +
                ", stateid='" + stateid + '\'' +
                '}';
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getServicetypeid() {
        return servicetypeid;
    }

    public void setServicetypeid(String servicetypeid) {
        this.servicetypeid = servicetypeid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getServicetype() {
        return servicetype;
    }

    public void setServicetype(String servicetype) {
        this.servicetype = servicetype;
    }

    public long getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(long orderdate) {
        this.orderdate = orderdate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public long getBegindate() {
        return begindate;
    }

    public void setBegindate(long begindate) {
        this.begindate = begindate;
    }

    public long getEnddate() {
        return enddate;
    }

    public void setEnddate(long enddate) {
        this.enddate = enddate;
    }

    public String getTpye() {
        return tpye;
    }

    public void setTpye(String tpye) {
        this.tpye = tpye;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStateid() {
        return stateid;
    }

    public void setStateid(String stateid) {
        this.stateid = stateid;
    }
}
