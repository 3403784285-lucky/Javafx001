package common;

import java.io.Serializable;


/**
 * @author 张培灵
 *
 * 用于好友及其信息
 */
public class Friend implements Serializable {

    /**
     *版本号，用于序列化
     */
    private static final long serialVersionUID = 1L;
    /**
     *好友的账号
     */
    private String account;
    /**
     *好友头像
     */
    private String avatar;
    /**
     *好友昵称
     */
    private String nickname;
    /**
     *和好友的关系
     */
    private String status;
    /**
     *如果是群的话，那么为群的账号
     */
    private String groupAccount;
    /**
     *好友是否在线
     */
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