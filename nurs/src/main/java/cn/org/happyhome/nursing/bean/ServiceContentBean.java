package cn.org.happyhome.nursing.bean;

import java.io.Serializable;

public class ServiceContentBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String tusId;

    private String servicecontentid;

    private String servicecontentname;

    private String note1;

    private String note2;


    public String getTusId() {
        return tusId;
    }

    public void setTusId(String tusId) {
        this.tusId = tusId;
    }

    public String getServicecontentid() {
        return servicecontentid;
    }

    public void setServicecontentid(String servicecontentid) {
        this.servicecontentid = servicecontentid;
    }

    public String getServicecontentname() {
        return servicecontentname;
    }

    public void setServicecontentname(String servicecontentname) {
        this.servicecontentname = servicecontentname;
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

    @Override
    public String toString() {
        return "VUserserviceconter{" +
                "tusId=" + tusId +
                ", servicecontentid=" + servicecontentid +
                ", servicecontentname=" + servicecontentname +
                ", note1=" + note1 +
                ", note2=" + note2 +
                "}";
    }
}
