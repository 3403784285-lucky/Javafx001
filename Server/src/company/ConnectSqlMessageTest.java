package company;

import common.Message;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class ConnectSqlMessageTest {

    @Test
    public void receiveMessageText() {
        Message message=new Message();
        message.setContent("哈哈哈哈----");
        ConnectSqlMessage.receiveMessageText(message);
    }

    @Test
    public void initMessageRecordGroup() {
        ArrayList<Message>messages=ConnectSqlMessage.initMessageRecordGroup("G5428296880");
        System.out.println(messages);
    }

    @Test
    public void initMessagerecord() {
        ArrayList<Message>messages=ConnectSqlMessage.initMessageRecordGroup("5407221521");
        System.out.println(messages);
    }
}