package common;

import java.io.Serializable;


public class Friend implements Serializable {

    private static final long serialVersionUID = 1L;
    private String account;
    private String avatar;
    private String nickname;
    private String status;
    private String groupAccount;
    private boolean Online;
    // 构造函数、getter和setter方法


    public boolean isOnline() {
        return Online;
    }

    public void setOnline(boolean online) {
        Online = online;
    }

    public Friend(String account, String avatar, String nickname) {
        this.account = account;
        this.avatar = avatar;
        this.nickname = nickname;
    }

    public String getGroupAccount() {
        return groupAccount;
    }

    public void setGroupAccount(String groupAccount) {
        this.groupAccount = groupAccount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}