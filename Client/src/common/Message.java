package common;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * ��ʾ�������Ϳͻ���ͨ��ʱ����Ϣ����
 *
 * @author ������

 */
public class Message implements Serializable {
    /**
     *��ǿ������
     */
    private static final long serialVersionUID=1L;
    /**
     *������
     */
    private String sender;
    /**
     *������
     */
    private String getter;
    /**
     *��������
     */
    private String content;
    /**
     *���͵��ֽ�����;
     */
    private byte[] transfer;
    /**
     *����ʱ��
     */
    private Timestamp sendTime;
    /**
     *��Ϣ����{�����ڽӿ��ж�����Ϣ����}
     */
    private String mesType;
    /**
     *��ʱ����ͼƬ·����
     */
    private  String tempImage;

    public String getTempImage() {
        return tempImage;
    }

    public void setTempImage(String tempImage) {
        this.tempImage = tempImage;
    }

    public String getMesType() {
        return mesType;
    }

    public void setMesType(String mesType) {
        this.mesType = mesType;
    }


    public byte[] getTransfer() {
        return transfer;
    }

    public void setTransfer(byte[] transfer) {
        this.transfer = transfer;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGetter() {
        return getter;
    }

    public void setGetter(String getter) {
        this.getter = getter;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getSendTime() {
        return sendTime;
    }

    public void setSendTime(Timestamp sendTime) {
        this.sendTime = sendTime;
    }
}

