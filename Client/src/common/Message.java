package common;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 表示服务器和客户端通信时的消息对象
 *
 * @author 张培灵

 */
public class Message implements Serializable {
    /**
     *增强兼容性
     */
    private static final long serialVersionUID=1L;
    /**
     *发送者
     */
    private String sender;
    /**
     *接收者
     */
    private String getter;
    /**
     *发送内容
     */
    private String content;
    /**
     *传送的字节数组;
     */
    private byte[] transfer;
    /**
     *发送时间
     */
    private Timestamp sendTime;
    /**
     *消息类型{可以在接口中定义消息类型}
     */
    private String mesType;
    /**
     *临时储存图片路径的
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

