package product.prison.bean;

import java.io.Serializable;

public class Email implements Serializable {
    private int id;

    private String orderTime;

    private int userId;

    private String backAdmin;

    private long backtime;

    private long createtime;

    private int manageIf;

    private int back_if;

    private String updateadmin;

    private long updateTime;

    private int notifyType;

    private String notifyNews;

    private int readStatus;

    private User user;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderTime() {
        return this.orderTime;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setBackAdmin(String backAdmin) {
        this.backAdmin = backAdmin;
    }

    public String getBackAdmin() {
        return this.backAdmin;
    }

    public void setBacktime(long backtime) {
        this.backtime = backtime;
    }

    public long getBacktime() {
        return this.backtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public long getCreatetime() {
        return this.createtime;
    }

    public void setManageIf(int manageIf) {
        this.manageIf = manageIf;
    }

    public int getManageIf() {
        return this.manageIf;
    }

    public void setBack_if(int back_if) {
        this.back_if = back_if;
    }

    public int getBack_if() {
        return this.back_if;
    }

    public void setUpdateadmin(String updateadmin) {
        this.updateadmin = updateadmin;
    }

    public String getUpdateadmin() {
        return this.updateadmin;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public long getUpdateTime() {
        return this.updateTime;
    }

    public void setNotifyType(int notifyType) {
        this.notifyType = notifyType;
    }

    public int getNotifyType() {
        return this.notifyType;
    }

    public void setNotifyNews(String notifyNews) {
        this.notifyNews = notifyNews;
    }

    public String getNotifyNews() {
        return this.notifyNews;
    }

    public void setReadStatus(int readStatus) {
        this.readStatus = readStatus;
    }

    public int getReadStatus() {
        return this.readStatus;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

}
