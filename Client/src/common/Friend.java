package common;

import java.io.Serializable;


/**
 * @author ������
 *
 * ���ں��Ѽ�����Ϣ
 */
public class Friend implements Serializable {

    /**
     *�汾�ţ��������л�
     */
    private static final long serialVersionUID = 1L;
    /**
     *���ѵ��˺�
     */
    private String account;
    /**
     *����ͷ��
     */
    private String avatar;
    /**
     *�����ǳ�
     */
    private String nickname;
    /**
     *�ͺ��ѵĹ�ϵ
     */
    private String status;
    /**
     *�����Ⱥ�Ļ�����ôΪȺ���˺�
     */
    private String groupAccount;
    /**
     *�����Ƿ�����
     */
    private boolean Online;
    // ���캯����getter��setter����


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