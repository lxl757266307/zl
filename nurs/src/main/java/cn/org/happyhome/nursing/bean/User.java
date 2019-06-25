package cn.org.happyhome.nursing.bean;

import java.io.Serializable;

public class User implements Serializable {

    @Override
    public String toString() {
        return "DataBean{" +
                "id='" + id + '\'' +
                ", usertypeid='" + usertypeid + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", headaimage='" + headaimage + '\'' +
                ", useraccounts='" + useraccounts + '\'' +
                ", phone='" + phone + '\'' +
                ", position='" + position + '\'' +
                ", forbidden='" + forbidden + '\'' +
                ", status='" + status + '\'' +
                ", manytomany='" + manytomany + '\'' +
                ", onetone='" + onetone + '\'' +
                ", userlevel='" + userlevel + '\'' +
                ", note1='" + note1 + '\'' +
                ", note2='" + note2 + '\'' +
                ", total=" + total +
                ", idcard='" + idcard + '\'' +
                ", leave='" + leave + '\'' +
                '}';
    }

    /**
     * id : 3
     * usertypeid : 1
     * name : 韩娇娇
     * password : 123321
     * nickname : hjj
     * headaimage : 1
     * useraccounts : 11
     * phone : 17691098568
     * position : 名园新居
     * forbidden : 1
     * status : 1
     * manytomany : 1
     * onetone : 1
     * userlevel : 1
     * note1 : 11
     * note2 : 11
     * total : 13
     * idcard : 12322
     */

    private String id;
    private String leave;
    private String usertypeid;
    private String name;
    private String password;
    private String nickname;
    private String headaimage;
    private String useraccounts;
    private String phone;
    private String position;
    private String forbidden;
    private String status;
    private String manytomany;
    private String onetone;
    private String userlevel;
    private String note1;
    private String note2;
    private int total;
    private String idcard;

    public User() {
    }

    public User(String id, String status) {
        this.id = id;
        this.status = status;
    }

    public String getLeave() {
        return leave;
    }

    public void setLeave(String leave) {
        this.leave = leave;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsertypeid() {
        return usertypeid;
    }

    public void setUsertypeid(String usertypeid) {
        this.usertypeid = usertypeid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadaimage() {
        return headaimage;
    }

    public void setHeadaimage(String headaimage) {
        this.headaimage = headaimage;
    }

    public String getUseraccounts() {
        return useraccounts;
    }

    public void setUseraccounts(String useraccounts) {
        this.useraccounts = useraccounts;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getForbidden() {
        return forbidden;
    }

    public void setForbidden(String forbidden) {
        this.forbidden = forbidden;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getManytomany() {
        return manytomany;
    }

    public void setManytomany(String manytomany) {
        this.manytomany = manytomany;
    }

    public String getOnetone() {
        return onetone;
    }

    public void setOnetone(String onetone) {
        this.onetone = onetone;
    }

    public String getUserlevel() {
        return userlevel;
    }

    public void setUserlevel(String userlevel) {
        this.userlevel = userlevel;
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }
}
