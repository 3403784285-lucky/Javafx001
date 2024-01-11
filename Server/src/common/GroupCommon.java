package common;

import java.io.Serializable;

public class GroupCommon implements Serializable {
    private static final long serialVersionUID=1L;
    private String groupAccount;//只能有一个群主一个管理员；
    private String groupName;
    private String signature;
    private String ownerAccount;
    private String managerAccount;
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

    public GroupCommon(String groupAccount, String groupName, String signature, String imageGroup) {
        this.groupAccount = groupAccount;
        this.groupName = groupName;
        this.signature = signature;
        this.imageGroup=imageGroup;
    }
}
