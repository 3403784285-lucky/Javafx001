package common;

import java.io.Serializable;
import java.sql.Date;

/**
 * 表示一个用户信息
 */
public class User implements Serializable {
    //增强兼容性
    private static final long serialVersionUID=1L;
    //用户ID
    private String userAccount;

    private boolean Online;
    //密码
    private String password;
    //邮箱
    private String userEmail;

    private  String userImage;

    private Date userBirthday;

    private String userSignature;

    private String userName;

    private String gender;

    public boolean isOnline() {
        return Online;
    }

    public void setOnline(boolean online) {
        Online = online;
    }

    public String getGender() {
        return gender;
    }

    public User(String userId, String password) {


        this.userAccount = userId;
        this.password = password;
    }

    public User() {
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userId) {
        this.userAccount = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public Date getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(Date userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getUserSignature() {
        return userSignature;
    }

    public void setUserSignature(String userSignature) {
        this.userSignature = userSignature;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String isGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
