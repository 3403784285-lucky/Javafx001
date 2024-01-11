package utils;
import common.*;
import company.Channel;
import company.ConnectSql;
import company.ConnectSqlMessage;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import transfer.CommonEvent;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ThreadServerConnect implements Runnable {
    private Socket client;
    private boolean isRunning;
    private ObjectOutputStream dos;
    private ObjectInputStream dis;


    public ObjectOutputStream getDos() {
        return dos;
    }

    public Socket getClient() {
        return client;
    }

    public ThreadServerConnect(Socket client) {
        this.client = client;
        isRunning = true;
        try {

            dos = new ObjectOutputStream(client.getOutputStream());
            dis = new ObjectInputStream(client.getInputStream());
            System.out.println("2222222222222222");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("----����������ͨѶ�̳߳�ʼ��ʧ��");
            isRunning = false;
            release();
        }
    }
    public void run() {
        Friend[]haofriends=ConnectSql.fgIndicate(Channel.u.getUserAccount());
        for(Friend fffff:haofriends)
        {
            if(ManageServerToThread.isExist(fffff.getAccount()))
            {
                CommonEvent kao=new CommonEvent("refreshFriendListLogin",Channel.u.getUserAccount());
                ThreadServerConnect cst=ManageServerToThread.getTsc(fffff.getAccount());
                ObjectOutputStream ooo=cst.getDos();
                try {
                    ooo.writeObject(kao);
                    ooo.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        while (isRunning) {
            System.out.println("�������Ϳͻ��˱���ͨѶ");
            CommonEvent cb =null;
            cb = receive();
            CommonEvent cbb=cb;

            try {
                handleEvent(cbb);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("�������");
            }


        }

    }
    private void handleEvent(CommonEvent cb) throws IOException {
        synchronized (cb)
        {
            if (cb != null && cb.getType().equals("loginprofilee")) {

                User U;
                U = ConnectSql.searchProfilee((User) cb.getT());
                CommonEvent cb1 = new CommonEvent("MESSAGE_PROFILE_SUCCEED", U);
                CommonEvent cb2 = new CommonEvent("MESSAGE_PROFILE_FAIL", null);
                if (U == null) {
                    send(cb2);
                } else {
                    send(cb1);
                }
                // ע�������u��ͬһ��
            } else if (cb != null && cb.getType().equals("loginprofilea")) {
                System.out.println(cb.getT());

                User uxx = (User) cb.getT();
                System.out.println(uxx.getUserAccount() + "------------------");
                User U;
                U = ConnectSql.searchProfilea((User) (cb.getT()));
                CommonEvent cb1 = new CommonEvent("MESSAGE_PROFILE_SUCCEED", U);
                CommonEvent cb2 = new CommonEvent("MESSAGE_PROFILE_FAIL", null);
                send(cb1);
            }
            else if (cb != null && cb.getType().equals("modifyAlways")) {
            System.out.println(cb.getT());
            ArrayList<String>arrayList=(ArrayList<String>) cb.getT();
            ConnectSql.modifyAlways(arrayList);
        }

            else if(cb!=null&&cb.getType().equals("initAlways"))
        {
            System.out.println(cb.getT());
            String temp=(String)cb.getT();
            System.out.println("�����ǳ�ʼ��������ķ�������"+temp);
            CommonEvent cc=new CommonEvent("initAlways",ConnectSql.initAlways(temp));
            send(cc);

        }
        else if(cb!=null&&cb.getType().equals("preReceiveFile")) {
                Message msg=(Message) cb.getT();
                try (FileInputStream fileInputStream = new FileInputStream("D:\\server\\" + msg.getContent())) {
                    long resumePosition = ConnectSql.getPosition(2);
                    CommonEvent commonEvent = new CommonEvent("preReceiveFile", msg);
                    fileInputStream.skip(resumePosition);
                    System.out.println(resumePosition);
                    send(commonEvent);
                    byte[] buffer = new byte[1024 * 1024];
                    int bytesRead;
                    while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                        FileData customData = new FileData(bytesRead, buffer);
                        send((customData));
                        if(((String)dis.readObject()).equals("ok"))
                        {
                            ConnectSql.freshFilePosition((resumePosition+bytesRead),2);
                        }
                    }
                    // ������ɵı��
                    send(null);
                    Long ok=(Long) dis.readObject();
                    ConnectSql.freshFilePosition(ok,2);
                    System.out.println("�������");

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else if(cb!=null&&cb.getType().equals("loginprofileam"))
            {


                System.out.println(cb.getT());
                System.out.println(((User) cb.getT()).getUserSignature()+"---------------------------<>");
                User U;
                U = ConnectSql.storeProfilea(((User) cb.getT()));
                CommonEvent cb1 = new CommonEvent(MessageType.MESSAGE_PROFILE_SUCCEED, U);
                CommonEvent cb2 = new CommonEvent(MessageType.MESSAGE_PROFILE_FAIL, null);
                if(U==null)send(cb2);
                if(U==null){send(cb2);System.out.println("��С�ĳ�Ϊ�˿�-----------------------------------xiangsi");}
                else send(cb1);

            }

            else if(cb!=null&&cb.getType().equals("loginprofileem"))
            {

                User uxx=(User) cb.getT();
                User U;
                U = ConnectSql.storeProfilee((User) (cb.getT()));
                CommonEvent cb1 = new CommonEvent(MessageType.MESSAGE_PROFILE_SUCCEED, U);
                CommonEvent cb2 = new CommonEvent(MessageType.MESSAGE_PROFILE_FAIL, null);
                System.out.println(cb1.getType()+"+++++++++++++++++++++++++++++++++++++++++");
                if(U==null){send(cb2);System.out.println("��С�ĳ�Ϊ�˿�-----------------------------------xiangsi");}
                else send(cb1);

            }
            else if(cb!=null&&cb.getType().equals("searchfg"))
            {String temp=(String) cb.getT();
                String[]array=temp.split("&");
                String accountm=array[0];
                String accountf=array[1];
                System.out.println("�������������߶��˹�����22222222322222222------------");
                Friend fm;
                fm = ConnectSql.searchfg(temp);
                CommonEvent cb1 = new CommonEvent(MessageType.MESSAGE_SEARCH_SUCCEED, fm);
                CommonEvent cb2 = new CommonEvent(MessageType.MESSAGE_SEARCH_FAIL, null);
                if(fm==null)send(cb2);
                else send(cb1);
                if(ManageServerToThread.isExist(accountf))
                {
                    ObjectOutputStream oss=ManageServerToThread.getTsc(accountf).getDos();
                    CommonEvent commonEvent=new CommonEvent("warningApply",fm);
                    System.out.println("���������˼���");
                    oss.writeObject(commonEvent);oss.flush();
                }

            }

            else if(cb!=null&&cb.getType().equals("searchfgResult")) {
                String temp = (String) cb.getT();System.out.println(temp+"������");
                Friend f=ConnectSql.searchfgResult(temp);
                CommonEvent commonEvent=new CommonEvent("searchfgResult",f);
                System.out.println(f+"������");
                send(commonEvent);
            }

            else if(cb!=null&&cb.getType().equals("typeJudge"))
            {
                String temp=(String)cb.getT();
                String hh=ConnectSql.typeJudge(temp);
                CommonEvent cb1 = new CommonEvent("typeJudge", hh);
                send(cb1);


            }
            else if(cb!=null&&cb.getType().equals("approvalApp"))
            {
                String temp=(String)cb.getT();
                String[]array=temp.split("&");
                String accountm=array[0];
                String accountf=array[1];
                System.out.println("������"+accountf+"�����˺�----------------------------------------------------------------- ");

               Friend fm=ConnectSql.approvalApp(temp);

              if(ManageServerToThread.isExist(accountf))
              {
                  System.out.println("�����Ƿ��������յ���ظ�------------------------------------------------------------------����");
                  ThreadServerConnect tsc=ManageServerToThread.getTsc(accountf);
                  ObjectOutputStream objectOutputStream=tsc.getDos();
                  CommonEvent cb1 = new CommonEvent("warningLittle", fm);
                  objectOutputStream.writeObject(cb1);objectOutputStream.flush();
              }


            }
            else if(cb!=null&&cb.getType().equals("fgApply"))
            {

                System.out.println("�������������߶��˹�����22299922222------------");
                String temp=(String)(cb.getT());
                String[]array=temp.split("&");
                String fAccount,uAccount;
                fAccount=array[0];
                uAccount=array[1];
                System.out.println(fAccount+"------haoyou-----------------ziji----------"+uAccount);
                User U=null;
                CommonEvent cb1 = new CommonEvent(MessageType.MESSAGE_APPLY_SUCCEED1, U);
                CommonEvent cb2 = new CommonEvent(MessageType.MESSAGE_APPLY_FAIL1, U);
                if( ConnectSql.applied(fAccount,uAccount))send(cb1);
                else send(cb2);

            }
            else if(cb!=null&&cb.getType().equals("fgIndicate"))
            {
                System.out.println(cb.getT());
                System.out.println("�������������߶��˹�����22299922222------------");
                String temp=(String) (cb.getT());
                System.out.println("��������ʾ���ѽ���ķ�����"+temp);
                Friend[]arrayList=ConnectSql.fgIndicate(temp);
                System.out.println(arrayList+"��������");
                CommonEvent cb1 = new CommonEvent("fgIndicate", arrayList);
                send(cb1);

            }
            else if(cb!=null&&cb.getType().equals("fgIndicate1"))
            {
                System.out.println("�������������߶��˹�����22299922222------------");
                String temp=(String) (cb.getT());
                CommonEvent cb1 = new CommonEvent("fgIndicate1", ConnectSql.fgIndicate1(temp));
                send(cb1);

            }
            else if(cb!=null&&cb.getType().equals("fgIndicate2"))
            {
                System.out.println(cb.getT());
                System.out.println("�������������߶��˹�����222eeeeeeeeeeeeeeeee2------------");
                String temp=(String) (cb.getT());
                CommonEvent cb1 = new CommonEvent("fgIndicate2", ConnectSql.fgIndicate2(temp));
                send(cb1);
            }
            else if(cb!=null&&cb.getType().equals("refuseFriend"))
            {
                System.out.println("�������������߶��˹�����222eeeeeeeeeeeeeeeee2------------");
                String temp=(String) (cb.getT());
                ConnectSql.refuseFriend(temp);

            }
            else if(cb!=null&&cb.getType().equals("approvalGroup"))
            {
                System.out.println("�������������߶��˹�����222eeeeeeeeeeeeeeeee2------------");
                String temp=(String) (cb.getT());
                String[]a=temp.split("&");
                ConnectSql.approvalGroup(temp);
                if(ManageServerToThread.isExist(a[1]))
                {
                    ThreadServerConnect tcc=ManageServerToThread.getTsc(a[1]);
                    ObjectOutputStream oo=tcc.getDos();
                    GroupCommon gc=ConnectSql.profileGroup(a[0]);
                    Friend ff=new Friend(gc.getGroupAccount(),gc.getImageGroup(),gc.getGroupName());
                    CommonEvent cc = new CommonEvent("approvalGroup",ff);
                    oo.writeObject(cc);oo.flush();
                }

            }
            else if(cb!=null&&cb.getType().equals("refuseGroup"))
            {

                String temp=(String) (cb.getT());
                ConnectSql.refuseGroup(temp);


            }
            else if(cb!=null&&cb.getType().equals("fgIndicate3"))
            {
                System.out.println(cb.getT());
                System.out.println("�������������߶��˹�����222eeeeeeeeeeeeeeeee2------------");
                String temp=(String) (cb.getT());
                CommonEvent cb1 = new CommonEvent("fgIndicate3", ConnectSql.fgIndicate3(temp));
                System.out.println("���׷���û");
                send(cb1);
                System.out.println();
                System.out.println("��ʾ�������ʾ����");

            }
            else if(cb!=null&&cb.getType().equals("isExistOnly"))
            {
                System.out.println(cb.getT());
                System.out.println("�������������߶��˹�����222eeeeeeeeeeeeeeeee2------------");
                String temp=(String) (cb.getT());
                CommonEvent cb1 = new CommonEvent("isExistOnly", ManageServerToThread.isExist(temp));
                send(cb1);
                System.out.println();


            }
            else if(cb!=null&&cb.getType().equals("modifypasswordonline"))
            {
                System.out.println(cb.getT());
                User temp=(User) (cb.getT());
                CommonEvent cb2=null;
                if(ConnectSql.modifyInfo(temp))
                {
                      cb2=new CommonEvent("modifypasswordonline",true);

                } else {
                     cb2=new CommonEvent("modifypasswordonline",false);
                }
                send(cb2);

            }

            else if(cb!=null&&cb.getType().equals("applyInfo"))
            {
                System.out.println(((UserCopy)(cb.getT())).getUserName()+"------>����˺�������");
                System.out.println("�������������߶��˹�����222eennnnnnnnnnnnnnneeeee2------------");
                UserCopy temp=(UserCopy) (cb.getT());
                System.out.println(temp.getUserAccount()+"�ҵ�Ҫ����������˺�");
                CommonEvent cb1 = new CommonEvent("applyInfo", ConnectSql.applyInfo(temp));
                send(cb1);

            }
            else if(cb!=null&&cb.getType().equals("askRelation"))
            {

                System.out.println("�������������߶��˹�����222eennnnnnnnnnnnnnneeeee2------------");
                String temp=(String) (cb.getT());
                System.out.println("��������"+temp);
                CommonEvent cb1 = new CommonEvent("askRelation", ConnectSql.askRelation(temp));
                send(cb1);
            }
            else if(cb!=null&&cb.getType().equals("askRelationGroup"))
            {
                String temp=(String) (cb.getT());
                System.out.println("��������"+temp);
                CommonEvent cb1 = new CommonEvent("askRelationGroup", ConnectSql.askRelationGroup(temp));
                send(cb1);
            }
            else if(cb!=null&&cb.getType().equals("profileGroup"))
            {
                String temp=(String) (cb.getT());
                System.out.println("�������������߶��˹�����222eennnnnnnnnnnnnnneeeee2------------");
                System.out.println("����Ⱥ���˺�"+temp);
                CommonEvent cb1 = new CommonEvent("profileGroup", ConnectSql.profileGroup(temp));
                send(cb1);
            }
            else if(cb!=null&&cb.getType().equals("deleteFriend"))
            {String temp=(String) (cb.getT());
                String[]array=temp.split("&");
                String accountf=array[0];
                String accountm=array[1];
                System.out.println(temp);
                ConnectSql.deleteFriend(accountf,accountm);
               if( ManageServerToThread.isExist(accountf))
               {
                   CommonEvent cc = new CommonEvent("deleteFriend", accountm);
                   ThreadServerConnect tcc=ManageServerToThread.getTsc(accountf);
                   ObjectOutputStream oo=tcc.getDos();
                   oo.writeObject(cc);oo.flush();
               }
            }
            else if(cb!=null&&cb.getType().equals("leaveGroup"))
            {
            String temp=(String)(cb.getT());

            CommonEvent cm=new CommonEvent("leaveGroup",ConnectSql.leaveGroup(temp));
                    send(cm);
            }
            else if(cb!=null&&cb.getType().equals("sendMessageText"))
            {

              Message temp=(Message) (cb.getT());
              ConnectSqlMessage.receiveMessageText(temp);
                CommonEvent cb1 = new CommonEvent("sendMessageText","");
                send(cb1);

            }
            else if(cb!=null&&cb.getType().equals("initMessagerecord"))
            {
                String temp=(String) (cb.getT());
                CommonEvent cb1 = new CommonEvent("initMessagerecord",  ConnectSqlMessage.initMessagerecord(temp));
                send(cb1);
            }
            else if(cb!=null&&cb.getType().equals("insertMessageGroup"))
            {
                Message[] temp=(Message[]) (cb.getT());
                System.out.println("���������Բ���Ⱥ����Ϣ"+temp);
                ConnectSqlMessage.insertMessageGroup(temp);

            }
            else if(cb!=null&&cb.getType().equals("applyGroup"))
            {
                Friend temp=(Friend) cb.getT();
                System.out.println("�����Ƿ�����-------------------------------------------------?"+temp);
                ConnectSql.applyGroup(temp);
                GroupCommon gg=ConnectSql.profileGroup(temp.getGroupAccount());
                temp.setGroupAccount(gg.getGroupName()+"&"+gg.getGroupAccount());
                if(ManageServerToThread.isExist(gg.getOwnerAccount()))
                {
                    System.out.println("Ⱥ��������");
                    System.out.println(temp+"�ͻ��˽��մ�");
                   ThreadServerConnect tcc= ManageServerToThread.getTsc(gg.getOwnerAccount());
                    ObjectOutputStream oo=tcc.getDos();
                    CommonEvent cc=new CommonEvent("applyGroup",temp);
                    oo.writeObject(cc);oo.flush();
                }
            }
            else if(cb!=null&&cb.getType().equals("indicateMemberGroup"))
            {
                String temp=(String) cb.getT();
                System.out.println("�����Ƿ�����-------------------------------------------------?"+temp);
                ArrayList<Friend>arrayList=ConnectSql.indicateMemberGroup(temp);
                System.out.println(arrayList);
                CommonEvent commonEvent=new CommonEvent("indicateMemberGroup",arrayList);
                send(commonEvent);
            }
            else if(cb!=null&&cb.getType().equals("indicatefgCopy"))
            {
                String temp=(String) cb.getT();
                Friend[]arrayList=ConnectSql.indicatefgCopy(temp);
                System.out.println(temp);
                CommonEvent commonEvent=new CommonEvent("indicatefgCopy",arrayList);
                send(commonEvent);
            }
            else if(cb!=null&&cb.getType().equals("addMemberGroup"))
            {
                ArrayList<Friend> temp=(ArrayList<Friend>) cb.getT();
                ConnectSql.addMemberGroup(temp);
                for(Friend f:temp)
                {
                    if(ManageServerToThread.isExist(f.getAccount()))
                    {
                        ThreadServerConnect tcc=ManageServerToThread.getTsc(f.getAccount());
                        ObjectOutputStream oo=tcc.getDos();
                        GroupCommon gg=ConnectSql.profileGroup(f.getGroupAccount());
                        Friend ff=new Friend(gg.getGroupAccount(), gg.getImageGroup(), gg.getGroupName());
                        CommonEvent commonEvent=new CommonEvent("addMemberGroup",ff) ;
                        oo.writeObject(commonEvent);oo.flush();
                    }
                }

            }
            else if(cb!=null&&cb.getType().equals("substractMemberGroup"))
            {
                ArrayList<Friend> temp=(ArrayList<Friend>) cb.getT();
                ConnectSql.substractMemberGroup(temp);
                for(Friend f:temp)
                {
                    if(ManageServerToThread.isExist(f.getAccount()))
                    {
                        ThreadServerConnect tcc=ManageServerToThread.getTsc(f.getAccount());
                        ObjectOutputStream oo=tcc.getDos();
                        GroupCommon gg=ConnectSql.profileGroup(f.getGroupAccount());
                        Friend ff=new Friend(gg.getGroupAccount(), gg.getImageGroup(), gg.getGroupName());
                        CommonEvent commonEvent=new CommonEvent("substractMemberGroup",ff);
                        oo.writeObject(commonEvent);oo.flush();
                    }
                }
            }
            else if(cb!=null&&cb.getType().equals("setManager"))
            {
                ArrayList<Friend> temp=(ArrayList<Friend>) cb.getT();
                ConnectSql.setManager(temp);

            }
            else if(cb!=null&&cb.getType().equals("quitGroup"))
            {
                String temp=(String) cb.getT();

                ArrayList<Friend>friends=ConnectSql.indicateMemberGroup(temp);
                ConnectSql.quitGroup(temp);
                for(Friend f:friends)
                {
                    if(ManageServerToThread.isExist(f.getAccount()))
                    {
                        ThreadServerConnect tcc=ManageServerToThread.getTsc(f.getAccount());
                        ObjectOutputStream oo=tcc.getDos();
                        GroupCommon gg=ConnectSql.profileGroup(f.getGroupAccount());
                        Friend ff=new Friend(gg.getGroupAccount(), gg.getImageGroup(), gg.getGroupName());
                        CommonEvent commonEvent=new CommonEvent("quitGroup",ff);
                        oo.writeObject(commonEvent);oo.flush();
                    }
                }
            }
            else if(cb!=null&&cb.getType().equals("logout"))
            {

                    String temp=(String) (cb.getT());
                    System.out.println(temp+"������");
                    Friend[]arrayList= ConnectSql.friendSearch(temp);
                    ManageServerToThread.removeServerThread(temp);
                    System.out.println(arrayList[1].getAccount());
                    System.out.println(arrayList[0].getAccount());
                    System.out.println("���ߵ����ж��ٺ���"+arrayList.length);
                    for (Friend f:arrayList)
                    {
                        System.out.println(f.getAccount());
                        System.out.println("����һ�������Ƿ����"+ManageServerToThread.isExist(f.getAccount()));
                        if (ManageServerToThread.isExist(f.getAccount()))
                        {
                            System.out.println("�۾Ϳ���������ߵ���");
                            ThreadServerConnect tsc=ManageServerToThread.getTsc(f.getAccount());
                            System.out.println("�����ߵ�����"+f.getNickname()+"�����Ƿ�������½��ȥ");
                            ObjectOutputStream oos=tsc.getDos();
                            CommonEvent aa=new CommonEvent("refreshFriendList",temp);
                            try {
                                oos.writeObject(aa);
                                oos.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                release();

            }
            else if(cb!=null&&(cb.getType().equals("insertMessage"))) {
                Message[] temp = (Message[]) (cb.getT());
                System.out.println("������"+cb.getT());
                ConnectSqlMessage.insertMessage(temp);
            }
            else if(cb!=null&&(cb.getType().equals("initMessagerecordGroup"))) {
              String temp = (String) (cb.getT());

                System.out.println("������"+cb.getT());
                ArrayList<Message> arrayList=ConnectSqlMessage.initMessageRecordGroup(temp);
                CommonEvent commonEvent=new CommonEvent("initMessagerecordGroup",arrayList);
                send(commonEvent);
            }
            else if(cb!=null&&cb.getType().equals("imageAvar")) {
                String[] temp = (String[]) (cb.getT());
                System.out.println("���Է��������˺�"+temp);
                System.out.println("��������ͷ��");
                CommonEvent cc=new CommonEvent("imageAvar",ConnectSql.imageAvar(temp));
                send(cc);
            }
            else if(cb!=null&&cb.getType().equals("imageSend")) {
                Message msg=(Message)cb.getT();
                System.out.println("�����ߵ��˺�"+msg.getGetter()+"ͼƬ");
                Pattern pp=Pattern.compile("\\d{10}");
                Matcher m=pp.matcher(msg.getGetter());
                boolean mm=m.matches();

                    if(mm)
                    {
                        System.out.println("�����е����⣬���Կ���ͼ����û");
                        ThreadServerConnect tsc=ManageServerToThread.getTsc(msg.getGetter());
                        ObjectOutputStream oos=tsc.getDos();
                        oos.writeObject(cb);oos.flush();
                    }
                    else
                    {
                        ArrayList<Friend> arrayList=ConnectSql.indicateMemberGroup(msg.getGetter());
                        for(Friend friend:arrayList)
                        {
                            if(ManageServerToThread.isExist(friend.getAccount()))
                            {
                                ThreadServerConnect tcc=ManageServerToThread.getTsc(friend.getAccount());
                                ObjectOutputStream oo=tcc.getDos();
                                oo.writeObject(cb);oo.flush();
                            }
                        }
                    }



            }
            else if(cb!=null&&cb.getType().equals("getPosition")) {
                Long temp= ConnectSql.getPosition(1);
                CommonEvent cc=new CommonEvent("getPosition",temp);
                send(cc);
            }
            else if(cb!=null&&cb.getType().equals("fileSend"))
            {
                Message temp=(Message) cb.getT();
                System.out.println("�������յ�������������");
              try{
                    FileOutputStream fileOutputStream = new FileOutputStream("D://server//"+temp.getContent());
                        while (true) {
                            FileData customData = (FileData) dis.readObject();
                            if (customData == null) {
                                Long tm=0L;
                                ConnectSql.freshFilePosition(tm,1);
                                break; // ������ɵı��
                            }
                            int bytesRead = customData.getNumber();
                            byte[] buffer = customData.getData();
                            fileOutputStream.write(buffer, 0, bytesRead);
                            Long t=(Long) dis.readObject();
                            System.out.println(t);
                            ConnectSql.freshFilePosition(t,1);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                Pattern pp=Pattern.compile("\\d{10}");
                Matcher mmm=pp.matcher(temp.getGetter());
                boolean mmq= mmm.matches();
                CommonEvent comeback=new CommonEvent("fileReceive",temp);
                if(mmq)
                {
                    if(ManageServerToThread.isExist(temp.getGetter()))
                    {
                        ThreadServerConnect tsc=ManageServerToThread.getTsc(temp.getGetter());
                        ObjectOutputStream os=tsc.getDos();
                        os.writeObject(comeback);os.flush();
                    }
                }
                else{
                    ArrayList<Friend>arrayList=ConnectSql.indicateMemberGroup(temp.getGetter());
                    for(Friend friend:arrayList)
                    {
                        if(ManageServerToThread.isExist(friend.getAccount()))
                        {
                            ThreadServerConnect threadServerConnect=ManageServerToThread.getTsc(friend.getAccount());
                            ObjectOutputStream oo=threadServerConnect.getDos();
                            oo.writeObject(comeback);
                            oo.flush();
                        }


                    }

                }
            }
            else if(cb!=null&&cb.getType().equals("judgeIsExistent")) {
                String image=(String)cb.getT();
                System.out.println("����");
                CommonEvent commonEvent=receive();
                System.out.println("�ȹ���");
                Message temp=(Message) commonEvent.getT();
                boolean m=false;
                Pattern pp=Pattern.compile("G\\d{10}");
                Matcher mmm=pp.matcher(temp.getGetter());
                boolean groupOrPerson=mmm.matches();
                if(groupOrPerson)
                {
                    System.out.println("��������------------------------��������");
                    ArrayList<String>memberAccount=ConnectSql.groupSearchAccount(temp.getGetter());
                    System.out.println(memberAccount+"�������ٴβ���");
                    for(String memberAccountExample:memberAccount)
                    {
                        System.out.println(memberAccountExample+"������ת����Ϣ");
                        if (ManageServerToThread.isExist(memberAccountExample))
                        {
                            System.out.println("������");
                            CommonEvent cb1 = new CommonEvent("sendOther",temp);
                            ThreadServerConnect tsc=ManageServerToThread.getTsc(memberAccountExample);
                            System.out.println("�����ߵ��˺�"+memberAccountExample);
                            ObjectOutputStream oos=tsc.getDos();
                            oos.writeObject(cb1);oos.flush();
                                CommonEvent commonEvent1=new CommonEvent("sendOther",image);
                            oos.writeObject(commonEvent1);oos.flush();
                        }

                    }
                }
                else
                {
                     m=ManageServerToThread.isExist(temp.getGetter());
                }
                if(m)
                {
                    System.out.println("������");
                    CommonEvent cb1 = new CommonEvent("sendOther",temp);
                     ThreadServerConnect tsc=ManageServerToThread.getTsc(temp.getGetter());
                     System.out.println("�����ߵ��˺�"+temp.getGetter());
                        ObjectOutputStream oos=tsc.getDos();
                        oos.writeObject(cb1);oos.flush();
                    CommonEvent commonEvent1=new CommonEvent("sendOther",image);
                    oos.writeObject(commonEvent1);oos.flush();
                }



            }
            else if(cb!=null&&cb.getType().equals("initGroup"))
            {
                Friend temp = (Friend) (cb.getT());
                System.out.println(temp+"������");
                Friend f=ConnectSql.initGroup(temp);
                CommonEvent commonEvent=new CommonEvent("initGroup",f);
                System.out.println(f.getGroupAccount()+"������");
                send(commonEvent);



            }
            else if(cb!=null&&cb.getType().equals("judgeMember"))
            {
                String temp = (String) (cb.getT());
                boolean h=ConnectSql.judgeMember(temp);
                CommonEvent commonEvent=new CommonEvent("judgeMember",h);
                send(commonEvent);


            }
            else if(cb!=null&&cb.getType().equals("restoreProfileGroup"))
            {
                GroupCommon gg=(GroupCommon) cb.getT();
                ConnectSql.restoreProfileGroup(gg);




            }

            else if(cb!=null&&cb.getType().equals("groupSearchAccount"))
            {
                String temp = (String) (cb.getT());
                System.out.println(temp+"������");
                ArrayList<String>arrayList=ConnectSql.groupSearchAccount(temp);
                System.out.println("����ٲ���һ��");
                CommonEvent commonEvent=new CommonEvent("groupSearchAccount",arrayList);
                send(commonEvent);



            }
            else if(cb!=null&&cb.getType().equals("transferGroup"))
            {
                ArrayList<Friend> temp = (ArrayList<Friend>) (cb.getT());
                ConnectSql.transferGroup(temp);

            }
            else if(cb!=null&&cb.getType().equals("memberListInit"))
            {
                ArrayList<Friend>temp=(ArrayList<Friend>)(cb.getT()) ;
                ConnectSql.memberListInit(temp);
                for(Friend ff:temp)
                {
                    if(ManageServerToThread.isExist(ff.getAccount()))
                    {
                        GroupCommon ggg=ConnectSql.profileGroup(ff.getGroupAccount());
                        Friend hh=new Friend(ff.getGroupAccount(),ggg.getImageGroup(),ggg.getGroupName());
                        CommonEvent cc=new CommonEvent("defineMember",hh);
                        ThreadServerConnect tsc=ManageServerToThread.getTsc(ff.getAccount());
                        ObjectOutputStream oo=tsc.getDos();
                        oo.writeObject(cc);oo.flush();
                    }
                }


            }



        }



        }

    public void send(Object msg)  {
        try {
            dos.writeObject(msg);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("-----������������Ϣʧ��");
            release();

        }
    }

    public CommonEvent receive() {
        CommonEvent msg = null;
        try {
            msg = (CommonEvent) dis.readObject();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("---------------------------������������ϢΪ��,��ô�ֱ�����");
            isRunning=false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("---------------------------������������ϢΪ��,��ô�ֱ�����");
            isRunning=false;
        }
        return msg;
    }
    public void release() {
        isRunning=false;
        try {
            dos.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("�������ر�����������1");
        }

        try {
            if(dis!=null)
                dis.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("�������ر�����������2");
        }
        try {
            if(client!=null)
                client.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("�������ر�����������3");
        }
    }
}
