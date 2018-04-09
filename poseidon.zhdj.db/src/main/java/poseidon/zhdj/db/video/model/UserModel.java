package poseidon.zhdj.db.video.model;

import java.util.Date;

public class UserModel {
    private String uid;

    private String userName;

    private String unitName;

    private String department;

    private String headImg;

    private Date loginTime;

    private Integer gift1;

    private Integer gift2;

    private Integer gift3;

    private Integer gift4;

    private Integer gift5;

    private Integer gift6;

    private Integer sendGift;//某个用户送出的礼物数

    private String viewVid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getUserName() {
        return userName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg == null ? null : headImg.trim();
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Integer getGift1() {
        return gift1;
    }

    public void setGift1(Integer gift1) {
        this.gift1 = gift1;
    }

    public Integer getGift2() {
        return gift2;
    }

    public void setGift2(Integer gift2) {
        this.gift2 = gift2;
    }

    public Integer getGift3() {
        return gift3;
    }

    public void setGift3(Integer gift3) {
        this.gift3 = gift3;
    }

    public Integer getGift4() {
        return gift4;
    }

    public void setGift4(Integer gift4) {
        this.gift4 = gift4;
    }

    public Integer getGift5() {
        return gift5;
    }

    public void setGift5(Integer gift5) {
        this.gift5 = gift5;
    }

    public Integer getGift6() {
        return gift6;
    }

    public void setGift6(Integer gift6) {
        this.gift6 = gift6;
    }

    public Integer getSendGift() {
        return sendGift;
    }

    public void setSendGift(Integer sendGift) {
        this.sendGift = sendGift;
    }

    public String getViewVid() {
        return viewVid;
    }

    public void setViewVid(String viewVid) {
        this.viewVid = viewVid;
    }
}