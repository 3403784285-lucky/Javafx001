package request;
import common.*;
import controller.LoginController;
import transfer.CommonEvent;
import utils.ManageClientToThread;
import utils.ThreadClientConnect;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

public class UserClientService {
    //可以处理登陆，注册，修改密码；
    private User u=new User();//
    private GroupCommon gu=new GroupCommon();
    private Friend f=new Friend("","","");
    private Socket client;
    private ObjectOutputStream dos;

    private ObjectInputStream dis;

    public GroupCommon getGu() {
        return gu;
    }

    public Friend getF() {
        return f;
    }

    public User getU() {
        return u;
    }

    public ThreadClientConnect utilConnect1()
    {
        ThreadClientConnect tcc;
        tcc=ManageClientToThread.getConn(ManageClientToThread.uOriginal);//开多个线程，而且之前的都没关
        return tcc;


    }
    public ObjectOutputStream utilConnect2(ThreadClientConnect tcc)
    {
        ObjectOutputStream oos;
        oos= tcc.getDos();
        return oos;
    }
    public boolean checkUserLoginEmail(String userEmail)
    {
        //连接服务器
        try {
            client=new Socket("localhost",9999);
            System.out.println("一小步------");
            dos=new ObjectOutputStream(client.getOutputStream());
            System.out.println("一大步------");
            dis=new ObjectInputStream(client.getInputStream());
            System.out.println("客户端第一关--------");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("客户端连接服务器异常-----------");
        }
        boolean temp=false;
        u.setUserEmail(userEmail);
        CommonEvent ce=new CommonEvent("loginemail",u);
        send(ce);
        CommonEvent cb=receive();
        String data=(String)cb.getT();
        if(data.equals(MessageType.MESSAGE_LOGIN_SUCCEED)){
            //登陆成功启动一个线程,让后续操作一直继续操作通讯
            //创建一个线程类
            ThreadClientConnect conn= null;
            try {
                conn = new ThreadClientConnect(new Socket("localhost",8888));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("线程启动失败");
            }
            conn.start();
            ManageClientToThread.addClientThread(u,conn);
            temp=true;

        }release();

        return temp;


    }
    public boolean restoreProfile(User ucopy)
    {
        ThreadClientConnect tcc=null;
        ObjectOutputStream oos=null;
        tcc=utilConnect1();
        oos=utilConnect2(tcc);
        try {
            CommonEvent ce1=null;
            tcc.ms[1]=null;
            if(LoginController.yesorno)
                ce1= new CommonEvent("loginprofileem",ucopy);
            else ce1=new CommonEvent("loginprofileam",ucopy);
            oos.writeObject(ce1);oos.flush();
            while(tcc.ms[1]==null)System.out.println("等待接入保存内容");
            CommonEvent cc= tcc.ms[1];
            if(cc.getType().equals(MessageType.MESSAGE_PROFILE_SUCCEED)){ManageClientToThread.u=(User) (cc.getT());return true;}
            else {System.out.println(cc.getType());return false;}
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("保持通讯发送消息失败");
        }
        return false;

    }
    public void approvalAPP(String accountm,String accountf)
    {
        ThreadClientConnect tcc=null;
        ObjectOutputStream oos= null;
        tcc=utilConnect1();
        oos=utilConnect2(tcc);
        CommonEvent ce1=null;
        ce1= new CommonEvent("approvalApp",accountm+"&"+accountf);
        try {
            oos.writeObject(ce1);
            oos.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.out.println("客户端写入流失败");
        }



    }
    public void approvalGroup(String account)
    {
        ThreadClientConnect tcc=null;
        ObjectOutputStream oos= null;
        tcc=utilConnect1();
        oos=utilConnect2(tcc);
        CommonEvent ce1=null;
        ce1= new CommonEvent("approvalGroup",account);
        try {
            oos.writeObject(ce1);
            oos.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.out.println("客户端写入流失败");
        }

    }
    public void refuseGroup(String account)
    {
        ThreadClientConnect tcc=null;
        ObjectOutputStream oos= null;
        tcc=utilConnect1();
        oos=utilConnect2(tcc);
        CommonEvent ce1=null;
        ce1= new CommonEvent("refuseGroup",account);
        try {
            oos.writeObject(ce1);
            oos.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.out.println("客户端写入流失败");
        }
    }
    public void refuseFriend(String account)
    {
        ThreadClientConnect tcc=null;
        ObjectOutputStream oos= null;
        tcc=utilConnect1();
        oos=utilConnect2(tcc);
        CommonEvent ce1=null;
        ce1= new CommonEvent("refuseFriend",account);
        try {
            oos.writeObject(ce1);
            oos.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.out.println("客户端写入流失败");
        }
    }

    public String typeJudge(String account,String accountm)
    {

        ThreadClientConnect tcc=null;
        ObjectOutputStream oos= null;
        tcc=utilConnect1();
        oos=utilConnect2(tcc);
        tcc.ms[3]=null;

        CommonEvent ce1=null;
        ce1= new CommonEvent("typeJudge",account+"&"+accountm);
        try {
            oos.writeObject(ce1);
            oos.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.out.println("客户端写入流失败");
        }
        while(tcc.ms[3]==null)System.out.println("等待接入判断好友类型");
        CommonEvent cc= tcc.ms[3];
        System.out.println(cc.getType()+"---------------------->>>>>>>>>>>>>>>>>>");

       return (String)cc.getT();




    }

    public boolean judgeMember(String account)
    {
        ThreadClientConnect tcc=null;
        ObjectOutputStream oos= null;
        tcc=utilConnect1();
        oos=utilConnect2(tcc);
        CommonEvent ce1=null;
        tcc.ms[21]=null;
        ce1= new CommonEvent("judgeMember",account);
        try {
            oos.writeObject(ce1);
            oos.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.out.println("客户端写入流失败");
        }
        while(tcc.ms[21]==null)System.out.println("等待接入判断自己是否属于群聊");
        CommonEvent cc= tcc.ms[21];
        System.out.println(cc.getT());
        return (boolean)cc.getT();

    }

    public boolean leaveGroup(String account)
    {
        ThreadClientConnect tcc=null;
        ObjectOutputStream oos= null;
        tcc=utilConnect1();
        oos=utilConnect2(tcc);
        CommonEvent ce1=null;
        tcc.ms[20]=null;
        ce1= new CommonEvent("leaveGroup",account);
        try {
            oos.writeObject(ce1);
            oos.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.out.println("客户端写入流失败");
        }
        while(tcc.ms[20]==null)System.out.println("等待接入判断好友类型");
        CommonEvent cc= tcc.ms[20];
        return (boolean)cc.getT();





    }
    public GroupCommon profileGroup(String account)
    {
        ThreadClientConnect tcc=null;
        ObjectOutputStream oos= null;
        tcc=utilConnect1();
        oos=utilConnect2(tcc);
        CommonEvent ce1=null; tcc.ms[19]=null;
        ce1= new CommonEvent("profileGroup",account);
        try {
            oos.writeObject(ce1);
            oos.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.out.println("客户端写入流失败");
        }
        while(tcc.ms[19]==null)System.out.println("请求群聊资料");
        CommonEvent cc= tcc.ms[19];
        gu=(GroupCommon) cc.getT();
        return (GroupCommon)cc.getT();
    }
    public void restoreProfileGroup(GroupCommon group)
    {
        ThreadClientConnect tcc=null;
        ObjectOutputStream oos= null;
        tcc=utilConnect1();
        oos=utilConnect2(tcc);
        CommonEvent ce1=null;
        ce1= new CommonEvent("restoreProfileGroup",group);
        try {
            oos.writeObject(ce1);
            oos.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.out.println("客户端写入流失败");
        }


    }

    public void deleteFriend(String account)
    {
        ThreadClientConnect tcc=null;
        ObjectOutputStream oos= null;
        tcc=utilConnect1();
        oos=utilConnect2(tcc);
        CommonEvent ce1=null;
        ce1= new CommonEvent("deleteFriend",account);
        try {
            oos.writeObject(ce1);
            oos.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.out.println("客户端写入流失败");
        }

    }
    public boolean fgSearchResult(String account)
    {

        ThreadClientConnect tcc=null;
        ObjectOutputStream oos= null;
        tcc=utilConnect1();
        oos=utilConnect2(tcc);
        CommonEvent ce1=null; tcc.ms[2]=null;
        ce1= new CommonEvent("searchfgResult",account);
        System.out.println(((String)(ce1.getT())));
        try {
            oos.writeObject(ce1);
            oos.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.out.println("客户端写入流失败");
        }
        while(tcc.ms[2]==null)System.out.println("搜索好友群聊");
        CommonEvent cc= tcc.ms[2];
       f=(Friend)cc.getT();
       if(f==null)return false;
       else return true;

    }
    public boolean fgSearch(String textAccount)
    {//这里还有大问题
        String maf=ManageClientToThread.uOriginal.getUserAccount()+"&"+textAccount;
        ThreadClientConnect tcc=null;
        ObjectOutputStream oos= null;
        tcc=utilConnect1();
        oos=utilConnect2(tcc);

        CommonEvent ce1=null; tcc.ms[4]=null;
        ce1= new CommonEvent("searchfg",maf);
        System.out.println(((String)(ce1.getT())));
        try {
            oos.writeObject(ce1);
            oos.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.out.println("客户端写入流失败");
        }
        while(tcc.ms[4]==null)System.out.println("看看是不是申请了");
        CommonEvent cc= tcc.ms[4];
        if(cc.getType().equals(MessageType.MESSAGE_SEARCH_SUCCEED)){return true;}//这里切记千万不要把自己的信息弄丢了
        else return false;



    }

    public boolean fgApply(String fgAccount)
    {
        ThreadClientConnect tcc=null;
        ObjectOutputStream oos= null;
        tcc=utilConnect1();
        oos=utilConnect2(tcc);
        CommonEvent ce1=null;
        tcc.ms[5]=null;
        ce1= new CommonEvent("fgApply",fgAccount+"&"+ManageClientToThread.u.getUserAccount());
        System.out.println(ce1.getType());
        try {
            oos.writeObject(ce1);
            oos.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.out.println("客户端写入流失败");
        }
        while(tcc.ms[5]==null)System.out.println("搜索账号");
        CommonEvent cc= tcc.ms[5];
        if (cc.getType().equals(MessageType.MESSAGE_APPLY_SUCCEED1))return true;
        else return false;

    }

    public void modifyAlways(ArrayList<String>arrayList)
    {
        ThreadClientConnect tcc=null;
        ObjectOutputStream oos= null;
        tcc=utilConnect1();
        oos=utilConnect2(tcc);
        CommonEvent ce1=null;
        ce1= new CommonEvent("modifyAlways",arrayList);
        try {
            oos.writeObject(ce1);
            oos.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.out.println("客户端写入流失败");
        }

    }
    public ArrayList<String>initAlways(String account)
    {
        ThreadClientConnect tcc=null;
        ObjectOutputStream oos= null;
        tcc=utilConnect1();
        oos=utilConnect2(tcc);
        CommonEvent ce1=null;
        tcc.ms[25]=null;
        ce1= new CommonEvent("initAlways",account);
        try {
            oos.writeObject(ce1);
            oos.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.out.println("客户端写入流失败");
        }
        while(tcc.ms[25]==null)System.out.println("初始化常用语");
        CommonEvent cc= tcc.ms[25];
        return (ArrayList<String>) cc.getT();
    }
    public ArrayList<Friend> indicatefg()
    {
        ThreadClientConnect tcc=null;
        ObjectOutputStream oos= null;
        tcc=utilConnect1();
        oos=utilConnect2(tcc);
        CommonEvent ce1=null; tcc.ms[6]=null;
        u=ManageClientToThread.u;
        ce1= new CommonEvent("fgIndicate",(String)u.getUserAccount());
        System.out.println(ce1.getType());
        try {
            oos.writeObject(ce1);
            oos.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.out.println("客户端写入流失败");
        }
        while(tcc.ms[6]==null) {
            try {
                Thread.sleep(100);
                System.out.println("显示好友");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        CommonEvent cc= tcc.ms[6];
        System.out.println(cc.getT()+"这是看看长啥样好友列表");
        Friend[]friends=(Friend[]) (cc.getT());
        ArrayList<Friend>arrayList=new ArrayList<>(Arrays.asList(friends));
        return arrayList;
    }
    public ArrayList<Friend> indicatefgCopy(String account)
    {
        ThreadClientConnect tcc=null;
        ObjectOutputStream oos= null;
        tcc=utilConnect1();
        oos=utilConnect2(tcc);
        CommonEvent ce1=null; tcc.ms[27]=null;
        ce1= new CommonEvent("indicatefgCopy",account+"&"+ManageClientToThread.u.getUserAccount());
        System.out.println(ce1.getType());
        try {
            oos.writeObject(ce1);
            oos.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.out.println("客户端写入流失败");
        }
        while(tcc.ms[27]==null) {
                System.out.println("显示好友");
        }
        CommonEvent cc= tcc.ms[27];
        System.out.println(cc.getT()+"这是看看长啥样好友列表");
        Friend[]friends=(Friend[]) (cc.getT());
        ArrayList<Friend>arrayList=new ArrayList<>(Arrays.asList(friends));
        return arrayList;
    }
    public Friend[] indicatefg1()
    {
        ThreadClientConnect tcc=null;
        ObjectOutputStream oos= null;
        tcc=utilConnect1();
        oos=utilConnect2(tcc);
        CommonEvent ce1=null; tcc.ms[22]=null;
        u=ManageClientToThread.u;
        ce1= new CommonEvent("fgIndicate1",u.getUserAccount());
        System.out.println(ce1.getType());
        try {
            oos.writeObject(ce1);
            oos.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.out.println("客户端写入流失败");
        }
        while(tcc.ms[22]==null)System.out.println("搜索好友群聊显示");
        CommonEvent cc= tcc.ms[22];
        System.out.println(cc.getType());
        return (Friend[])(cc.getT());
    }

    public ArrayList<Friend> indicatefg2()
    {
        ThreadClientConnect tcc=null;
        ObjectOutputStream oos= null;
        tcc=utilConnect1();
        oos=utilConnect2(tcc);
        CommonEvent ce1=null; tcc.ms[7]=null;
        u=ManageClientToThread.u;
        ce1= new CommonEvent("fgIndicate2",(String)u.getUserAccount());
        System.out.println(ce1.getType());
        try {
            oos.writeObject(ce1);
            oos.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.out.println("客户端写入流失败");
        }
        while(tcc.ms[7]==null)System.out.println("显示好友2");
        CommonEvent cc= tcc.ms[7];
        System.out.println(cc.getType());
        return (ArrayList<Friend>) (cc.getT());
    }
    public ArrayList<String>groupSearchAccount(String groupAccount)
    {
        ThreadClientConnect tcc=null;
        ObjectOutputStream oos= null;
        tcc=utilConnect1();
        oos=utilConnect2(tcc);
        CommonEvent ce1=null; tcc.ms[15]=null;

        ce1= new CommonEvent("groupSearchAccount",groupAccount);
        System.out.println(ce1.getType());
        try {
            oos.writeObject(ce1);
            oos.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.out.println("客户端写入流失败");
        }
        while(tcc.ms[15]==null)System.out.println("显示好");
        CommonEvent cc= tcc.ms[15];
        System.out.println(cc.getType());
        return (ArrayList<String>) (cc.getT());
    }
    public ArrayList<Friend> indicatefg3()
    {
        ThreadClientConnect tcc=null;
        ObjectOutputStream oos= null;
        tcc=utilConnect1();
        oos=utilConnect2(tcc);
        CommonEvent ce1=null; tcc.ms[14]=null;
        u=ManageClientToThread.u;
        ce1= new CommonEvent("fgIndicate3",ManageClientToThread.u.getUserAccount());
        System.out.println(ce1.getType());
        try {
            oos.writeObject(ce1);
            oos.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.out.println("客户端写入流失败");
        }
        while(tcc.ms[14]==null)System.out.println("显示好友3");
        CommonEvent cc= tcc.ms[14];
        System.out.println(cc.getType());
        return (ArrayList<Friend>) (cc.getT());
    }
   public Friend initGroup(Friend group)
   {
       ThreadClientConnect tcc=null;
       ObjectOutputStream oos= null;
       tcc=utilConnect1();
       oos=utilConnect2(tcc);
       try { tcc.ms[13]=null;

           System.out.println(group+"客户处理器");
           CommonEvent ce1=new CommonEvent("initGroup",group);
           oos.writeObject(ce1);oos.flush();
       } catch (IOException e) {
           e.printStackTrace();
           System.out.println("保持通讯发送消息失败");
       }
       while(tcc.ms[13]==null)System.out.println("初始化群聊");
       CommonEvent cc= tcc.ms[13];
       System.out.println(((Friend)(cc.getT())).getGroupAccount());

       return (Friend)(cc.getT());




   }
   public void transferGroup(ArrayList<Friend>arrayList)
   {
       ThreadClientConnect tcc=null;
       ObjectOutputStream oos= null;
       tcc=utilConnect1();
       oos=utilConnect2(tcc);
       try {

           CommonEvent ce1=new CommonEvent("transferGroup",arrayList);
           oos.writeObject(ce1);oos.flush();
       } catch (IOException e) {
           e.printStackTrace();
           System.out.println("保持通讯发送消息失败");
       }
   }
    public void memberListInit(ArrayList<Friend>arrayList)
    {
        ThreadClientConnect tcc=null;
        ObjectOutputStream oos= null;
        tcc=utilConnect1();
        oos=utilConnect2(tcc);
        try {

            CommonEvent ce1=new CommonEvent("memberListInit",arrayList);
            System.out.println(arrayList+"这里是初始化列表");
            oos.writeObject(ce1);oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("保持通讯发送消息失败");
        }

    }
    public long getPosition()
    {
        ThreadClientConnect tcc=null;
        ObjectOutputStream oos= null;
        tcc=utilConnect1();
        oos=utilConnect2(tcc);
        tcc.ms[28]=null;
        try {

            CommonEvent ce1=new CommonEvent("getPosition","");
            oos.writeObject(ce1);oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("保持通讯发送消息失败");
        }
        while(tcc.ms[28]==null)System.out.println("获取文件位置");

        return (long)(((CommonEvent)tcc.ms[28]).getT());

    }

    public boolean initProfile() {

            u=ManageClientToThread.uOriginal;
            System.out.println(u+"---------------------------kkkkkkkkkkkkkkkkkkkkkkkkk");
        ThreadClientConnect tcc=null;
        ObjectOutputStream oos= null;
        tcc=utilConnect1();
        oos=utilConnect2(tcc);
        try {

            CommonEvent ce1=null;
            tcc.ms[8]=null;
            if(LoginController.yesorno)
            ce1= new CommonEvent("loginprofilee",u);
            else ce1=new CommonEvent("loginprofilea",u);
            oos.writeObject(ce1);oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("保持通讯发送消息失败");
        }
        while(tcc.ms[8]==null)System.out.println("初始化资料");
       CommonEvent cc= tcc.ms[8];
       u=(User) cc.getT();
       ManageClientToThread.u=u;
        if(cc.getType().equals("MESSAGE_PROFILE_SUCCEED"))return true;
        else return false;



    }

    public boolean checkUserLoginAccount(String userAccount, String pwd)
    {
        //连接服务器
        try {
            client=new Socket("localhost",9999);
            System.out.println("一小步------");
            dos=new ObjectOutputStream(client.getOutputStream());
            System.out.println("一大步------");
            dis=new ObjectInputStream(client.getInputStream());

            System.out.println("客户端第一关--------");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("客户端连接服务器异常-----------");
        }

        boolean temp=false;
        u.setUserAccount(userAccount);
        u.setPassword(pwd);
        //发送User对象
        CommonEvent ce1=new CommonEvent("loginaccount",u);
        send(ce1);
        CommonEvent cb=receive();
        String data=null;
        if(cb!=null)  data=(String) cb.getT();
        System.out.println(data);
        if(data.equals(MessageType.MESSAGE_LOGIN_SUCCEED)){
            //登陆成功启动一个线程,让后续操作一直继续操作通讯
            //创建一个线程类
            ThreadClientConnect conn= null;
            try {
                conn = new ThreadClientConnect(new Socket("localhost",8888));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("线程启动失败");
            }
            conn.start();

            ManageClientToThread.addClientThread(u,conn);
            System.out.println(ManageClientToThread.getConn(u).getClient());

            temp=true;


        }


        //读取从服务器发送的event对象
        release();
        return temp;


    }
    public boolean checkUserModifyPasswordOnline(String userEmail,String pwd)
    {
        //连接服务器

        ThreadClientConnect tcc=null;
        ObjectOutputStream oos= null;
        tcc=utilConnect1();
        oos=utilConnect2(tcc);
            CommonEvent ce1=null;
            tcc.ms[18]=null;
        boolean temp=false;
        u.setUserEmail(userEmail);
        u.setPassword(pwd);
        //发送User对象
        ce1=new CommonEvent("modifypasswordonline",u);
        try {
            oos.writeObject(ce1);oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(tcc.ms[18]==null)System.out.println("在线判断修改密码");
        CommonEvent cc= tcc.ms[18];
       temp=(boolean) cc.getT();

        //读取从服务器发送的event对象
        return temp;
    }

    public boolean checkUserModifyPassword(String userEmail,String pwd)
    {
        //连接服务器
        try {
            client=new Socket("localhost",9999);
            System.out.println("一小步------");
            dos=new ObjectOutputStream(client.getOutputStream());
            System.out.println("一大步------");
            dis=new ObjectInputStream(client.getInputStream());

            System.out.println("客户端第一关--------");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("客户端连接服务器异常-----------");
        }
        boolean temp=false;
        u.setUserEmail(userEmail);
        u.setPassword(pwd);
        //发送User对象
        CommonEvent ce1=new CommonEvent("modifypassword",u);
        send(ce1);
        CommonEvent cb=receive();
        String data=null;
        data=(String) cb.getT();
        if(data.equals(MessageType.MESSAGE_MODIFY_SUCCEED)){
            //登陆成功启动一个线程,让后续操作一直继续操作通讯
            //创建一个线程类
            temp=true;


        }

        //读取从服务器发送的event对象
        return temp;
    }
    public void quitGroup(String account)
    {
        ArrayList<Friend>arrayList=new ArrayList<>();
        ThreadClientConnect tcc=null;
        ObjectOutputStream oos= null;
        tcc=utilConnect1();
        oos=utilConnect2(tcc);
        try {
            CommonEvent ce1=null;
            ce1=new CommonEvent("quitGroup",account);
            oos.writeObject(ce1);oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("保持通讯发送消息失败");
        }

    }
    public ArrayList<Friend> indicateMemberGroup(String account)
    {
        ArrayList<Friend>arrayList=new ArrayList<>();
        ThreadClientConnect tcc=null;
        ObjectOutputStream oos= null;
        tcc.ms[26]=null;
        tcc=utilConnect1();
        oos=utilConnect2(tcc);
        try {
            CommonEvent ce1=null;
            ce1=new CommonEvent("indicateMemberGroup",account);
            oos.writeObject(ce1);oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("保持通讯发送消息失败");
        }
        while(tcc.ms[26]==null)System.out.println("申请资料");
        CommonEvent cc= tcc.ms[26];
        return (ArrayList<Friend>) cc.getT();
    }
    public void setManager(ArrayList<Friend>friends)
    {
        ThreadClientConnect tcc=null;
        ObjectOutputStream oos= null;
        tcc=utilConnect1();
        oos=utilConnect2(tcc);
        try {
            CommonEvent ce1=null;
            ce1=new CommonEvent("setManager",friends);
            oos.writeObject(ce1);oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("保持通讯发送消息失败");
        }
    }
    public void substractMemberGroup(ArrayList<Friend>arrayList)
    {
        ThreadClientConnect tcc=null;
        ObjectOutputStream oos= null;
        tcc=utilConnect1();
        oos=utilConnect2(tcc);
        try {
            CommonEvent ce1=null;
            ce1=new CommonEvent("substractMemberGroup",arrayList);
            oos.writeObject(ce1);oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("保持通讯发送消息失败");
        }
    }
    public void addMemberGroup(ArrayList<Friend>arrayList)
    {
        ThreadClientConnect tcc=null;
        ObjectOutputStream oos= null;
        tcc=utilConnect1();
        oos=utilConnect2(tcc);
        try {

            CommonEvent ce1=null;
            ce1=new CommonEvent("addMemberGroup",arrayList);
            oos.writeObject(ce1);oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("保持通讯发送消息失败");
        }
    }
    public UserCopy applyInfo(String account)
    {UserCopy uu=new UserCopy();
        u=ManageClientToThread.uOriginal;
        System.out.println(u+"---------------------------kkkkkkkkkkkkkkkkkkkkkkkkk");
        ThreadClientConnect tcc=null;
        ObjectOutputStream oos= null;
        tcc=utilConnect1();
        oos=utilConnect2(tcc);
        try {

            CommonEvent ce1=null;
            tcc.ms[9]=null;

          uu.setUserAccount(account);
          ce1=new CommonEvent("applyInfo",uu);
          System.out.println(u+"看看原配---------------》");
          System.out.println(ManageClientToThread.u);

            oos.writeObject(ce1);oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("保持通讯发送消息失败");
        }

        while(tcc.ms[9]==null)System.out.println("申请资料");
        CommonEvent cc= tcc.ms[9];
        uu=(UserCopy) cc.getT();
        return uu;

    }
    public void applyGroup(Friend f)
    {
        ThreadClientConnect tcc=null;
        ObjectOutputStream oos= null;
        tcc=utilConnect1();
        oos=utilConnect2(tcc);
        try {

            CommonEvent ce1=null;
            ce1=new CommonEvent("applyGroup",f);
            oos.writeObject(ce1);oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("保持通讯发送消息失败");
        }
    }
    public void logOut()
    {

        ThreadClientConnect tcc=null;
        ObjectOutputStream oos= null;
        tcc=utilConnect1();
        oos=utilConnect2(tcc);
        try {

            CommonEvent ce1=null;
         ce1=new CommonEvent("logout",ManageClientToThread.uOriginal.getUserAccount());
            oos.writeObject(ce1);oos.flush();
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("保持通讯发送消息失败");
        }


    }
    public  ConcurrentHashMap<String,String> imageAvar(ArrayList<String> account)
    {

        ThreadClientConnect tcc=null;
        ObjectOutputStream oos= null;
        tcc=utilConnect1();
        oos=utilConnect2(tcc);
        try {

            CommonEvent ce1=null;
            tcc.ms[12]=null;
            System.out.println("测试客户端请求处的账号组"+account);
            String[]array= account.toArray(new String[0]);
            ce1=new CommonEvent("imageAvar",array);
            oos.writeObject(ce1);oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("保持通讯发送消息失败");
        }

        while(tcc.ms[12]==null)System.out.println("申请头像");
        CommonEvent cc= tcc.ms[12];
        ConcurrentHashMap<String,String> array=(ConcurrentHashMap<String, String>) cc.getT();
        System.out.println(array);
        return array;

    }
    public String askRelation(String account)
    {//两个人的账号合并

        ThreadClientConnect tcc=null;
        ObjectOutputStream oos= null;
        tcc=utilConnect1();
        oos=utilConnect2(tcc);
        try {

            CommonEvent ce1=null;
            tcc.ms[23]=null;
            System.out.println("测试客户端请求处的账号组"+account);
            ce1=new CommonEvent("askRelation",account);
            oos.writeObject(ce1);oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("保持通讯发送消息失败");
        }
        while(tcc.ms[23]==null)System.out.println("请求关系");
        CommonEvent cc= tcc.ms[23];
        System.out.println("客户端请求处"+cc.getT());
        return (String)cc.getT();

    }
    public String askRelationGroup(String account)
    {
        ThreadClientConnect tcc=null;
        ObjectOutputStream oos= null;
        tcc=utilConnect1();
        oos=utilConnect2(tcc);
        try {

            CommonEvent ce1=null;
            tcc.ms[24]=null;
            System.out.println("测试客户端请求处的账号组"+account);
            ce1=new CommonEvent("askRelationGroup",account);
            oos.writeObject(ce1);oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("保持通讯发送消息失败");
        }
        while(tcc.ms[24]==null)System.out.println("请求关系");
        CommonEvent cc= tcc.ms[24];
        System.out.println("客户端请求处"+cc.getT());
        return (String)cc.getT();
    }

    public boolean checkUserRegister(String userEmail,String pwd)
    {
        //连接服务器
        try {
            client=new Socket("localhost",9999);
            System.out.println("一小步------");
            dos=new ObjectOutputStream(client.getOutputStream());
            System.out.println("一大步------");
            dis=new ObjectInputStream(client.getInputStream());
            System.out.println("客户端第一关--------");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("客户端连接服务器异常-----------");
        }
        boolean temp=false;
        u.setUserEmail(userEmail);
        u.setPassword(pwd);
        //发送User对象
        CommonEvent ce1=new CommonEvent("register",u);
        send(ce1);
        CommonEvent cb=receive();
        String data=null;
        data=(String) cb.getT();
        System.out.println(data);
        if(data.equals(MessageType.MESSAGE_REGISTER_SUCCEED)) {
            //登陆成功启动一个线程,让后续操作一直继续操作通讯
            //创建一个线程类
            temp=true;
        }
        ThreadClientConnect conn= null;
        try {
            conn = new ThreadClientConnect(new Socket("localhost",8888));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("线程启动失败");
        }
        conn.start();
        ManageClientToThread.u=u;
        ManageClientToThread.addClientThread(u, conn);
        //读取从服务器发送的event对象
        return temp;
    }
    public void send(Object msg)  {
        try {
            dos.writeObject(msg);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("-----客户端响应");
            release();
        }
    }
    public CommonEvent receive() {
        CommonEvent msg=null;

        try {
            msg=(CommonEvent) dis.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("客户端读取信息失败1------------");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("客户端读取信息失败2-------------");
        }
        release();
        return msg;
    }
    public void release() {
        try {
            if(dos!=null)
                dos.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("输出流关闭失败-----");
        }
        try {
            if(dis!=null)
                dis.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("输入流关闭失败-----");
        }
        try {
            if(client!=null)
                client.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("请求关闭失败-----");
        }
    }
}
