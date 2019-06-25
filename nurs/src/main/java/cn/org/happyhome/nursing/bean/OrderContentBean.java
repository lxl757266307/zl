package cn.org.happyhome.nursing.bean;

import java.io.Serializable;
import java.util.Date;

public class OrderContentBean implements Serializable {

    private String orderid;

    private String seq;

    private String serviceid;

    private String servicecontentname;

    private Double price;

    private String patientname;

    private String userid;

    private String patientsex;

    private String patientweight;

    private String weightage;

    private String selfhelp;

    private String isolation;

    private String psychosis;

    private String phone;

    private String note1;

    private long begindate;

    private long enddate;

    private String tpye;

    public String getPatientname() {
        return patientname;
    }

    public void setPatientname(String patientname) {
        this.patientname = patientname;
    }

    public String getTpye() {
        return tpye;
    }

    public void setTpye(String tpye) {
        this.tpye = tpye;
    }

    private transient boolean isCheck;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getServiceid() {
        return serviceid;
    }

    public void setServiceid(String serviceid) {
        this.serviceid = serviceid;
    }

    public String getServicecontentname() {
        return servicecontentname;
    }

    public void setServicecontentname(String servicecontentname) {
        this.servicecontentname = servicecontentname;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPatientsex() {
        return patientsex;
    }

    public void setPatientsex(String patientsex) {
        this.patientsex = patientsex;
    }

    public String getPatientweight() {
        return patientweight;
    }

    public void setPatientweight(String patientweight) {
        this.patientweight = patientweight;
    }

    public String getWeightage() {
        return weightage;
    }

    public void setWeightage(String weightage) {
        this.weightage = weightage;
    }

    public String getSelfhelp() {
        return selfhelp;
    }

    public void setSelfhelp(String selfhelp) {
        this.selfhelp = selfhelp;
    }

    public String getIsolation() {
        return isolation;
    }

    public void setIsolation(String isolation) {
        this.isolation = isolation;
    }

    public String getPsychosis() {
        return psychosis;
    }

    public void setPsychosis(String psychosis) {
        this.psychosis = psychosis;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNote1() {
        return note1;
    }

    public void setNote1(String note1) {
        this.note1 = note1;
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

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    @Override
    public String toString() {
        return "{" +
                "orderid"+":"+ orderid + '\'' +
                ", seq='" + seq + '\'' +
                ", serviceid='" + serviceid + '\'' +
                ", servicecontentname='" + servicecontentname + '\'' +
                ", price=" + price +
                ", userid='" + userid + '\'' +
                ", patientsex='" + patientsex + '\'' +
                ", patientweight='" + patientweight + '\'' +
                ", weightage='" + weightage + '\'' +
                ", selfhelp='" + selfhelp + '\'' +
                ", isolation='" + isolation + '\'' +
                ", psychosis='" + psychosis + '\'' +
                ", phone='" + phone + '\'' +
                ", note1='" + note1 + '\'' +
                ", begindate=" + begindate +
                ", enddate=" + enddate +
                ", isCheck=" + isCheck +
                ", patientname=" + patientname +
                ", type=" + tpye +
                '}';
    }
}
