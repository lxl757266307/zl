package cn.org.happyhome.nursing.bean;

import java.io.Serializable;

public class ServiceTypeBean  implements Serializable{



    private String servicetypeid;
    private String servicetypename;

    private String tusId;

    private String note1;

    private String note2;

    @Override
    public String toString() {
        return "ServiceTypeBean{" +
                "servicetypeid='" + servicetypeid + '\'' +
                ", tusId='" + tusId + '\'' +
                ", note1='" + note1 + '\'' +
                ", note2='" + note2 + '\'' +
                ", servicetypename='" + servicetypename + '\'' +
                '}';
    }

    public String getServicetypename() {
        return servicetypename;
    }

    public void setServicetypename(String servicetypename) {
        this.servicetypename = servicetypename;
    }

    public String getServicetypeid() {
        return servicetypeid;
    }

    public void setServicetypeid(String servicetypeid) {
        this.servicetypeid = servicetypeid;
    }

    public String getTusId() {
        return tusId;
    }

    public void setTusId(String tusId) {
        this.tusId = tusId;
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
