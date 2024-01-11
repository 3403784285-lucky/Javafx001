package common;

import java.io.Serializable;

/**
 * @author 张培灵
 *
 * 群聊类型
 */
public class GroupCommon implements Serializable {
    /**
     *版本号，用于序列化
     */
    private static final long serialVersionUID=1L;
    /**
     *群聊账号
     */
    private String groupAccount;//只能有一个群主一个管理员；
    /**
     *群聊名称
     */
    private String groupName;
    /**
     *群聊简介
     */
    private String signature;
    /**
     *群主账号
     */
    private String ownerAccount;
    /**
     *管理员账号
     */
    private String managerAccount;
    /**
     *群聊图片
     */
    private String imageGroup;

    public GroupCommon() {
    }

    public String getImageGroup() {
        return imageGroup;
    }

    public void setImageGroup(String imageGroup) {
        this.imageGroup = imageGroup;
    }

    public String getGroupAccount() {
        return groupAccount;
    }

    public void setGroupAccount(String groupAccount) {
        this.groupAccount = groupAccount;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getOwnerAccount() {
        return ownerAccount;
    }

    public void setOwnerAccount(String ownerAccount) {
        this.ownerAccount = ownerAccount;
    }

    public String getManagerAccount() {
        return managerAccount;
    }

    public void setManagerAccount(String managerAccount) {
        this.managerAccount = managerAccount;
    }

    public GroupCommon(String groupAccount, String groupName, String signature,String imageGroup) {
        this.groupAccount = groupAccount;
        this.groupName = groupName;
        this.signature = signature;
        this.imageGroup=imageGroup;
    }
}
