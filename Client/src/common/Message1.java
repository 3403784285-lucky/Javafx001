package common;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 表示服务器和客户端通信时的消息对象，但是由于两个相同类型的对象冲突识别不了，这里分两种传输
 */
public class Message1 extends Message implements Serializable {
    public Message1() {
        super();
    }
}

