package request;


import common.Message1;
import org.apache.commons.io.FileUtils;
import transfer.CommonEvent;
import common.Message;
import utils.ManageClientToThread;
import utils.ThreadClientConnect;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MessageClientService {
    public boolean sendMessageText(Message text)
    {
        ThreadClientConnect tcc= ManageClientToThread.getConn(ManageClientToThread.uOriginal);//������̣߳�����֮ǰ�Ķ�û��
        ObjectOutputStream oos= tcc.getDos();
        CommonEvent ce1=null;
        tcc.ms[10]=null;
        ce1= new CommonEvent("sendMessageText",text);
        try {
            oos.writeObject(ce1);
            oos.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.out.println("�ͻ���д����ʧ��");
        }
        while(tcc.ms[10]==null);
        CommonEvent cc= tcc.ms[10];
        return true;


    }
    public void insertMessageGroup(ArrayList<Message>groupTempRecord)
    {
        ThreadClientConnect tcc= ManageClientToThread.getConn(ManageClientToThread.uOriginal);//������̣߳�����֮ǰ�Ķ�û��
        ObjectOutputStream oos= tcc.getDos();
        CommonEvent ce1=null;
        Message[] mm = groupTempRecord.toArray(new Message[groupTempRecord.size()]);
        System.out.println("�����㶫����");
        ce1= new CommonEvent("insertMessageGroup",mm);
            System.out.println(groupTempRecord+"�ͻ����������---------------------------------------------------------------------------------------------------");
        try {
            oos.writeObject(ce1);
            oos.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.out.println("�ͻ���д����ʧ��");
        }

    }
    public ArrayList<Message>initMessageRecordGroup(String accountGroup)
    {
        ThreadClientConnect tcc= ManageClientToThread.getConn(ManageClientToThread.uOriginal);//������̣߳�����֮ǰ�Ķ�û��
        ObjectOutputStream oos= tcc.getDos();
        CommonEvent ce1=null;
        tcc.ms[16]=null;
        ce1= new CommonEvent("initMessagerecordGroup",accountGroup);
        try {
            oos.writeObject(ce1);
            oos.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.out.println("�ͻ���д����ʧ��");
        }
        while(tcc.ms[16]==null)System.out.println("��ʼ�������¼");
        CommonEvent cc= tcc.ms[16];
        ArrayList<Message>array=null;
        array=(ArrayList<Message>) cc.getT();
        return array;
    }
    public ArrayList<Message> initMessagerecord(String accountm,String accountf)//�����кܶ�����ظ������Ƕ����ã�
    {
        ThreadClientConnect tcc= ManageClientToThread.getConn(ManageClientToThread.uOriginal);
        ObjectOutputStream oos= tcc.getDos();
        CommonEvent ce1=null;
        tcc.ms[11]=null;
        ce1= new CommonEvent("initMessagerecord",accountm+"&"+accountf);
        try {
            oos.writeObject(ce1);
            oos.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.out.println("�ͻ���д����ʧ��");
        }
        while(tcc.ms[11]==null)System.out.println("��ʼ�������¼");
        CommonEvent cc= tcc.ms[11];
        ArrayList<Message>array=new ArrayList<>();
            array=(ArrayList<Message>) cc.getT();
        return array;
    }
    public void sendMessageImage(Message msg)
    {
        ThreadClientConnect tcc= ManageClientToThread.getConn(ManageClientToThread.uOriginal);//������̣߳�����֮ǰ�Ķ�û��
        ObjectOutputStream oos= tcc.getDos();
        CommonEvent ce1=null;
        ce1= new CommonEvent("imageSend",msg);


        try {
            oos.writeObject(ce1);
            oos.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.out.println("�ͻ���д����ʧ��");
        }  System.out.println("ͼƬ�ѷ���");



    }
    public void insertMessage(ArrayList<Message> tempMessage)
    {
        ThreadClientConnect tcc= ManageClientToThread.getConn(ManageClientToThread.uOriginal);
        ObjectOutputStream oos= tcc.getDos();
        System.out.println(tempMessage+"�ͻ�������");
        Message[]mm=tempMessage.toArray(new Message[0]);
        CommonEvent comeEvent=null;
        comeEvent= new CommonEvent("insertMessage",mm);

        try {
            oos.writeObject(comeEvent);
            oos.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.out.println("�ͻ���д����ʧ��");
        }


    }
    public void judgeIsExistent(Message msg)
    {
        ThreadClientConnect tcc= ManageClientToThread.getConn(ManageClientToThread.uOriginal);
        ObjectOutputStream oos= tcc.getDos();
        CommonEvent ce1=null;
        ce1= new CommonEvent("judgeIsExistent",msg);
        try {
            oos.writeObject(ce1);
            oos.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.out.println("�ͻ���д����ʧ��");
        }

    }
    public boolean isExistOnly(String account)
    {
        ThreadClientConnect tcc= ManageClientToThread.getConn(ManageClientToThread.uOriginal);
        ObjectOutputStream oos= tcc.getDos();
        tcc.ms[17]=null;
        CommonEvent ce1=null;
        ce1= new CommonEvent("isExistOnly",account);
        try {
            oos.writeObject(ce1);
            oos.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.out.println("�ͻ���д����ʧ��");
        }
        while(tcc.ms[17]==null)System.out.println("���û����߷�");
        CommonEvent cc= tcc.ms[17];
        if((boolean)cc.getT())return true;
        else return false;



    }



}
