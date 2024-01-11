package common;

import java.io.Serializable;
import java.sql.Date;

/**
 * 表示一个用户信息，也是为了区别各种冲突类型的解决方法
 */
public class UserCopy implements Serializable {
    /**
     *用于序列化的版本
     */
    //增强兼容性
    private static final long serialVersionUID=1L;
    /**
     *用户账号
     *///用户ID
    private String userAccount;

    /**
     *用户是否在线
     */
    private boolean Online;
    /**
     *用户密码
     *///密码
    private String password;
    /**
     *用户邮箱
     *///邮箱
    private String userEmail;

    /**
     *用户头像
     */
    private  String userImage;

    /**
     *用户生日
     */
    private Date userBirthday;

    /**
     *用户签名
     */
    private String userSignature;

    /**
     *用户名称
     */
    private String userName;

    /**
     *用户性别
     */
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

    public UserCopy(String userId, String password) {


        this.userAccount = userId;
        this.password = password;
    }

    public UserCopy() {
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
