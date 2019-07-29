package product.prison.bean;

import java.io.Serializable;

/**
 * Created by zhu on 2017/9/18.
 */

public class User implements Serializable {
    private int id;

    private String name;

    private int groupId;

    private String mac;

    private int status;

    private String version;

    private int sex;

    private int roomStatus;

    private int online;

    private long connectTime;

    private long disconectTime;

    private long roomStart;

    private long roomEnd;

    private String userName;

    private int voice;

    private long openTime;

    private long closeTime;

    private int type;

    private String ip;

    private String idcard;

    private String programList;

    private Product product;

    private HouseType houseType;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getGroupId() {
        return this.groupId;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getMac() {
        return this.mac;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return this.version;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getSex() {
        return this.sex;
    }

    public void setRoomStatus(int roomStatus) {
        this.roomStatus = roomStatus;
    }

    public int getRoomStatus() {
        return this.roomStatus;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public int getOnline() {
        return this.online;
    }

    public void setConnectTime(long connectTime) {
        this.connectTime = connectTime;
    }

    public long getConnectTime() {
        return this.connectTime;
    }

    public void setDisconectTime(long disconectTime) {
        this.disconectTime = disconectTime;
    }

    public long getDisconectTime() {
        return this.disconectTime;
    }

    public void setRoomStart(long roomStart) {
        this.roomStart = roomStart;
    }

    public long getRoomStart() {
        return this.roomStart;
    }

    public void setRoomEnd(long roomEnd) {
        this.roomEnd = roomEnd;
    }

    public long getRoomEnd() {
        return this.roomEnd;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setVoice(int voice) {
        this.voice = voice;
    }

    public int getVoice() {
        return this.voice;
    }

    public void setOpenTime(long openTime) {
        this.openTime = openTime;
    }

    public long getOpenTime() {
        return this.openTime;
    }

    public void setCloseTime(long closeTime) {
        this.closeTime = closeTime;
    }

    public long getCloseTime() {
        return this.closeTime;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return this.type;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getIdcard() {
        return this.idcard;
    }

    public void setProgramList(String programList) {
        this.programList = programList;
    }

    public String getProgramList() {
        return this.programList;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setHouseType(HouseType houseType) {
        this.houseType = houseType;
    }

    public HouseType getHouseType() {
        return this.houseType;
    }
}
