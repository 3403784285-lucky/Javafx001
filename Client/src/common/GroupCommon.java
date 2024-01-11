package common;

import java.io.Serializable;

/**
 * @author ������
 *
 * Ⱥ������
 */
public class GroupCommon implements Serializable {
    /**
     *�汾�ţ��������л�
     */
    private static final long serialVersionUID=1L;
    /**
     *Ⱥ���˺�
     */
    private String groupAccount;//ֻ����һ��Ⱥ��һ������Ա��
    /**
     *Ⱥ������
     */
    private String groupName;
    /**
     *Ⱥ�ļ��
     */
    private String signature;
    /**
     *Ⱥ���˺�
     */
    private String ownerAccount;
    /**
     *����Ա�˺�
     */
    private String managerAccount;
    /**
     *Ⱥ��ͼƬ
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
