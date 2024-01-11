package company;
import common.MessageType;
import common.User;
import transfer.CommonEvent;

import java.io.*;
import java.net.Socket;

public class Channel implements Runnable{
    private Socket client;
    private ObjectOutputStream dos;
    private ObjectInputStream dis;

    public static User u=new User();
    public Channel(Socket client)  {
        try {//服务器和客户端不可以同时刚开始创建输入流，否则会堵塞通道；
            this.client=client;
            System.out.println("一小步------");
            System.out.println("一大步------");
            dos=new ObjectOutputStream(client.getOutputStream());
            dis= new ObjectInputStream(client.getInputStream());
            System.out.println("-----------");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("----服务器初始化失败");
            release();
        }
    }

    @Override
    public void run() {

        CommonEvent ce=(CommonEvent) receive();
        String data=ce.getType();
        if(data.equals("loginaccount"))
        {

           CommonEvent cb1=new CommonEvent("message",MessageType.MESSAGE_LOGIN_SUCCEED);
           CommonEvent cb2=new CommonEvent("message",MessageType.MESSAGE_LOGIN_FAIL);
            if(ConnectSql.selectInfo1((User) ce.getT()))
            {
                //成功之后创建一个线程和客户端保持通讯
                //该线程需要持有socket对象
                u=(User) ce.getT();
                System.out.println(u+"------------------>这是真要生气了");
                send(cb1);

            }
            else {send(cb2);}
        }
        else if(data.equals("loginemail"))
        {
            CommonEvent cb1=new CommonEvent("message",MessageType.MESSAGE_LOGIN_SUCCEED);
            CommonEvent cb2=new CommonEvent("message",MessageType.MESSAGE_LOGIN_FAIL);
            if(ConnectSql.selectInfo2((User) ce.getT()))
            {

                //成功之后创建一个线程和客户端保持通讯
                //该线程需要持有socket对象
                u=(User) ce.getT();
                u.setUserAccount(ConnectSql.searchAccount(u.getUserEmail()));
                send(cb1);

            }
            else {send(cb2);}
        }
        else if(data.equals("modifypassword"))
        {
            CommonEvent cb1=new CommonEvent("message",MessageType.MESSAGE_MODIFY_SUCCEED);
            CommonEvent cb2=new CommonEvent("message",MessageType.MESSAGE_MODIFY_FAIL);
            if(ConnectSql.modifyInfo((User) ce.getT()))
            {
                send(cb1);
            }
            else send(cb2);

        }
        else if(data.equals("register"))
        {
            CommonEvent cb1=new CommonEvent("message",MessageType.MESSAGE_REGISTER_SUCCEED);
            CommonEvent cb2=new CommonEvent("message",MessageType.MESSAGE_REGISTER_FAIL);
            if(ConnectSql.storeInfo((User) ce.getT()))
            {

                u=(User) ce.getT();
                u.setUserAccount(ConnectSql.searchAccount(u.getUserEmail()));
                send(cb1);
            }
            else
            {
                ConnectSql.modifyInfo((User) ce.getT());send(cb2);

            }


        }

       release();

    }

    public void send(Object msg)  {
        try {
            dos.writeObject(msg);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("-----服务器发送消息失败");
            release();

        }
    }

    public CommonEvent receive() {
        CommonEvent msg=null;
        try {
            msg=(CommonEvent) dis.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("-----服务器接收消息失败");
            release();
        }
        return msg;

    }

    public void release() {
        try {
            if(dos!=null)
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("服务器关闭流发生错误1");
        }
        try {
            if(dis!=null)
            dis.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("服务器关闭流发生错误2");
        }
        try {
            if(client!=null)
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("服务器关闭流发生错误3");
        }
    }
}
