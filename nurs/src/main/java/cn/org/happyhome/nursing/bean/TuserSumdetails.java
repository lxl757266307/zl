package cn.org.happyhome.nursing.bean;


import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 规则表
 * </p>
 *
 * @author 张占利
 * @since 2018-10-30
 */
public class TuserSumdetails implements Serializable {

    private static final long serialVersionUID = 1L;

    private String Pointsid;

    private String userid;

    private String originate;

    private Double integration;

    private String note;

    private long creattime;


    public String getPointsid() {
        return Pointsid;
    }

    public void setPointsid(String Pointsid) {
        this.Pointsid = Pointsid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getOriginate() {
        return originate;
    }

    public void setOriginate(String originate) {
        this.originate = originate;
    }

    public Double getIntegration() {
        return integration;
    }

    public void setIntegration(Double integration) {
        this.integration = integration;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getCreattime() {
        return creattime;
    }

    public void setCreattime(long creattime) {
        this.creattime = creattime;
    }

    @Override
    public String toString() {
        return "Tusersumdetails{" +
        "Pointsid=" + Pointsid +
        ", userid=" + userid +
        ", originate=" + originate +
        ", integration=" + integration +
        ", note=" + note +
        ", creattime=" + creattime +
        "}";
    }
}
