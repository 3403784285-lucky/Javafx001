package company;
import common.*;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import utils.GetMyAccount;
import utils.ManageServerToThread;
import utils.SnowFlakeId;


import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectSql {

    private static Connection utilsConnect()
    {
        Connection connection=null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

             connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/chatroomperson", "root", "root");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("���Ӵ���1");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("���Ӵ���2");
        }
        return connection;

    }
    private static void utilsRelease(PreparedStatement pss,Connection conn)
    {
        try {
            if(pss!=null)
            pss.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("���ݿ����رմ���2");

        }
        try {
            if(conn!=null)
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("���ݿ����رմ���3");
        }
    }
    public static boolean storeInfo(User u) {
        Connection conn=null;
        PreparedStatement pss=null;
        PreparedStatement ps=null;
        boolean flag=false;

        try {

            conn= utilsConnect();

            String slq = "select Email from person where Email=?";
             pss = conn.prepareStatement(slq);
            pss.setString(1,u.getUserEmail());
            ResultSet rs=pss.executeQuery();
            if (!rs.next()) {
                u.setUserName("�û�"+u.getUserEmail());
                u.setUserImage("C:\\Users\\zplaz\\IdeaProjects\\Javafx001\\Client\\src\\backphoto\\back4.jpg");
                u.setUserBirthday((Date)(Date.valueOf("2000-01-01")));
                String sql = "insert into person (Account,Password,Email,name,birthday,image)values(?,?,?,?,?,?)";//?ռλ��
                 ps = conn.prepareStatement(sql);
                System.out.println("��������");
                ps.setString(1, new GetMyAccount().getMyAccount());//����������һ��ʼ�㣻
                ps.setString(2, u.getPassword());
                ps.setString(3, u.getUserEmail());
                ps.setString(4, u.getUserName());
                ps.setDate(5,u.getUserBirthday());
                ps.setString(6,u.getUserImage());
                ps.execute();ps.close();
                flag=true;

            }

        }  catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("���ݿ����Ӵ���1------");
        }


      utilsRelease(pss,conn);
        return flag;

    }
    public static boolean selectGroupAccount(String accountGroup)
    {
        boolean mark=false;
        Connection conn=null;
        PreparedStatement ps=null;
        System.out.println("�����ʺŵ�һ��");
        try {
            conn= utilsConnect();

            String sql = "select groupaccount from groupprofile  where groupaccount=?";//?ռλ��

            ps=conn.prepareStatement(sql);
            ps.setString(1,accountGroup);
            System.out.println("�ڶ���");
            mark=ps.executeQuery().next();
            System.out.println("��һ��");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("���ݿ����Ӵ���1------");

        } finally {
           utilsRelease(ps,conn);

        }
        System.out.println("���ס");

        return mark;
    }
    public static boolean selectAccount(String account)
    {
        System.out.println(account);
        boolean mark=false;
        Connection conn=null;
        PreparedStatement ps=null;
        System.out.println("�����ʺŵ�һ��");
        try {
            conn= utilsConnect();

            String sql = "select account from person where account=?";//?ռλ��

            ps=conn.prepareStatement(sql);
            ps.setString(1,account);
            System.out.println("�ڶ���");
             mark=ps.executeQuery().next();
           System.out.println("��һ��");
        }catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("���ݿ����Ӵ���1------");

        } finally {
            utilsRelease(ps,conn);
        }
        System.out.println("���ס");
        return mark;

    }
    public static boolean modifyInfo(User u)  {
        Connection conn=null;boolean mark=false;
        PreparedStatement ps=null,pss=null;
        try {
            conn= utilsConnect();

            String slq="select email from person where email=?";
            String sql = "update person set password=? where email=?";//?ռλ��
             ps=conn.prepareStatement(slq);
            ps.setString(1,u.getUserEmail());
            mark=ps.executeQuery().next();
            if(mark)
            {
                pss=conn.prepareStatement(sql);
                pss.setString(1,u.getPassword());
                pss.setString(2,u.getUserEmail());
                pss.execute();pss.close();


            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("���ݿ����Ӵ���1------");

        } finally {
           utilsRelease(ps,conn);
        }

        return mark;

    }
        public static boolean selectInfo1(User u)
        {
            PreparedStatement ps=null;
            Connection conn=null;
            boolean mark=false;
            try {
             conn= utilsConnect();
               String slq="select Account from person where Account=? and Password=?";
                 ps=conn.prepareStatement(slq);
                ps.setString(1,u.getUserAccount());
                ps.setString(2,u.getPassword());
                mark=ps.executeQuery().next();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
                System.out.println("���ݿ����Ӵ���1------");
            }finally {
              utilsRelease(ps,conn);
            }
          return mark;

        }
    public static boolean selectInfo2(User u)
    {
        Connection conn=null;
        PreparedStatement ps=null;
        boolean mark=false;
        try {
            conn= utilsConnect();

            String slq="select Email from person where Email=?";
         ps=conn.prepareStatement(slq);
            ps.setString(1,u.getUserEmail());
            mark=ps.executeQuery().next();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("���ݿ����Ӵ���1------");
        }finally {
           utilsRelease(ps,conn);
        }

        return mark;//������Ǿ��ǳɹ�������������˺Ŵ���


    }
    public static User searchProfilee(User u)
    {
        Connection conn=null;
        PreparedStatement pss=null;
        try {
            conn= utilsConnect();

            String sql="select *from person where email=?";
            pss=conn.prepareStatement(sql);
            pss.setString(1,u.getUserEmail());
            System.out.println(u.getUserName()+"---------------------------->");
            ResultSet rss=pss.executeQuery();
            rss.next();
            u.setUserAccount(rss.getString("account"));
            u.setUserSignature(rss.getString("signature"));
            u.setUserName(rss.getString("name"));
            u.setUserImage(rss.getString("image"));
            u.setUserBirthday(rss.getDate("birthday"));
            u.setGender(rss.getString("gender"));

        }catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("���ݿ����Ӵ���1------");
        }finally {


          utilsRelease(pss,conn);
        }
        return u;

    }
    public static User searchProfilea(User u)
    {
        Connection conn=null;
        PreparedStatement pss=null;
        try {
            conn= utilsConnect();

            String sql="select *from person where account=?";
            pss=conn.prepareStatement(sql);
            pss.setString(1,u.getUserAccount());
            System.out.println(u.getUserName()+"---------------------------->");
                ResultSet rss=pss.executeQuery();
                rss.next();
                u.setUserEmail(rss.getString("email"));
                u.setUserSignature(rss.getString("signature"));
                u.setUserName(rss.getString("name"));
                u.setUserImage(rss.getString("image"));
                u.setUserBirthday(rss.getDate("birthday"));
                u.setGender(rss.getString("gender"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("���ݿ����Ӵ���1------");
        }finally {


          utilsRelease(pss,conn);
        }
      return u;

    }
    public static String searchAccount(String email)
    {
        Connection conn=null;
        boolean mark=false;
        PreparedStatement pss=null;
        String account=null;
        try {
            conn= utilsConnect();

            String sql="select account from person where email=?";
            pss=conn.prepareStatement(sql);
            pss.setString(1,email);
            ResultSet rs=pss.executeQuery();
            rs.next();
             account=rs.getString("account");


        }catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("���ݿ����Ӵ���1------");
        }finally {

            utilsRelease(pss,conn);
        }
       return account;

    }

    public static void restoreProfileGroup(GroupCommon group)
    {

        Connection conn=null;
        PreparedStatement pss=null;
        try {
            conn= utilsConnect();
            String slq="update groupprofile set groupname=?,groupimage=?,groupsignature=? where groupaccount=?";
            pss=conn.prepareStatement(slq);
            pss.setString(1,group.getGroupName());
            pss.setString(2,group.getImageGroup());
            pss.setString(3,group.getSignature());
            pss.setString(4,group.getGroupAccount());
            pss.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("���ݿ����Ӵ���1------");
        }finally {
            utilsRelease(pss,conn);
        }

    }
    public static boolean judgeMember(String account)
    {
        boolean mark=false;
        String[]array=account.split("&");
        String accountg=array[0];
        String accountm=array[1];
        Connection conn=null;
        PreparedStatement pss=null;
        try {
            conn= utilsConnect();
            String slq="select * from groupmember where groupaccount=? and personaccount=?";
            pss=conn.prepareStatement(slq);
            pss.setString(1,accountg);
            pss.setString(2,accountm);
            System.out.println(accountm);
            ResultSet rs= pss.executeQuery();
            if(rs.next())
            {
                if(rs.getString("status").equals("4"));
                    else
                mark=true;
                    System.out.println(mark);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("���ݿ����Ӵ���1------");
        }finally {
            utilsRelease(pss,conn);
        }
        System.out.println(mark);

        return mark;
    }
    public static ArrayList<String>groupSearchAccount(String groupAccount)
    {
        Connection conn=null;
        PreparedStatement pss=null;
        ArrayList<String>arrayList=new ArrayList<>();
        try {
            conn= utilsConnect();
            String slq="select personaccount from groupmember where groupaccount=? and status!=?";
            pss=conn.prepareStatement(slq);
            pss.setString(1,groupAccount);
            pss.setString(2,"4");
            ResultSet rs=pss.executeQuery();
            while(rs.next())
            {
                String account=rs.getString("personaccount");
                arrayList.add(account);
                System.out.println(account+"�������˵��ʺ�");
            }

        }catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("���ݿ����Ӵ���1------");
        }finally {

            utilsRelease(pss,conn);
        }
        System.out.println("��ѯ�˲鲻��");
       return arrayList;
    }
    public static User storeProfilea(User u)
    {
        Connection conn=null;
        boolean mark=false;
        PreparedStatement pss=null;
        try {
            conn= utilsConnect();

            String sql="update person set name =?,birthday=?,signature=?,gender=?,image=? where account=?";
            String slq="select name from person where name=?";
            pss=conn.prepareStatement(slq);
            pss.setString(1,u.getUserName());
            System.out.println("�ҵ�Ҫ�����������"+u.getUserName());
            System.out.println(u.getUserName()+"---------------------------->");
            ResultSet rs=pss.executeQuery();
            rs.next();
            mark=rs.next();
            System.out.println("���ѵ���û----��"+mark);
            if(!mark)
            {
                pss=conn.prepareStatement(sql);
                pss.setString(6,u.getUserAccount());
                pss.setString(1,u.getUserName());
                pss.setDate(2,u.getUserBirthday());
                pss.setString(3,u.getUserSignature());
                pss.setString(4,u.isGender());
                pss.setString(5,u.getUserImage());
               pss.executeUpdate();

            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("���ݿ����Ӵ���1------");
        }finally {
            utilsRelease(pss,conn);
        }
        if(!mark)return u;
        else return null;

    }
    public static Friend  searchfgResult(String account)
    {
        Connection conn=null;
        boolean mark=false;
        PreparedStatement pss=null;
        Friend fg=null;
        try {
            conn= utilsConnect();

            String slq="select * from person where account=?";
            pss=conn.prepareStatement(slq);
            pss.setString(1,account);
            ResultSet rs=pss.executeQuery();
            mark=rs.next();
            System.out.println(mark);

            if(!mark)
            {
                String qqq="select *from groupprofile where groupaccount=?";
                pss=conn.prepareStatement(qqq);
                pss.setString(1,account);
                rs=pss.executeQuery();
                if(rs.next())
                {
                    fg=new Friend("","","");
                    fg.setAvatar(rs.getString("groupimage"));
                    fg.setNickname(rs.getString("groupname"));
                    fg.setAccount(account);
                }

            }
            else
            {
                fg=new Friend("","","");
                System.out.println("������������������Ⱥ�ķ�����");
                fg.setAvatar(rs.getString("image"));
                System.out.println("���￨ס��");
                fg.setNickname(rs.getString("name"));
                fg.setAccount(account);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("���ݿ����Ӵ���1------");
        }finally {


          utilsRelease(pss,conn);
        }
       return fg;

    }
    public static Friend searchfg(String mafAccount)
    {
        Connection conn=null;
        boolean mark=false;
        PreparedStatement pss=null;
        String[]array=mafAccount.split("&");
        String accountm=array[0];
        String accountf=array[1];
        Friend fm=new Friend("","","");
        try {
            conn= utilsConnect();

            String slq="select * from friendship where useraccount=? and friendaccount=? and relationstatus=?";
            pss=conn.prepareStatement(slq);
            pss.setString(1,accountm);
            pss.setString(2,accountf);
            pss.setString(3,"2");
            ResultSet rs=pss.executeQuery();
            mark=rs.next();
            System.out.println(mark);
            if(!mark)
            {
             String qqq="select* from person where account=?";
             pss=conn.prepareStatement(qqq);
             pss.setString(1,accountm);
             rs=pss.executeQuery();
             rs.next();
              fm.setAvatar(rs.getString("image"));
              fm.setNickname(rs.getString("name"));
              fm.setAccount(accountm);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("���ݿ����Ӵ���1------");
        }finally {


          utilsRelease(pss,conn);
        }
        if(!mark)return fm;
        else return null;
    }

    public static Friend[] friendSearch(String account)
    {
        Connection conn=null;
        PreparedStatement pss=null;
        ArrayList<Friend>arrayList=new ArrayList<>();
        try {
            conn= utilsConnect();
            String slq="select * from friendship where useraccount=?and relationstatus=?";
            pss=conn.prepareStatement(slq);
            pss.setString(1,account);
            pss.setString(2,"1");
            ResultSet rs=pss.executeQuery();
           while(rs.next())
            {
                Friend fm=new Friend("","","");
                fm.setAccount(rs.getString("friendaccount"));
                System.out.println(fm.getAccount()+"���еĺ���");
                arrayList.add(fm);
            }
           System.out.println(arrayList.get(0));
           System.out.println(arrayList.get(1));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("���ݿ����Ӵ���1------");
        }finally {
            utilsRelease(pss,conn);
        }
        return arrayList.toArray(new Friend[0]);
    }
    public static Friend initGroup(Friend group)
    {
        System.out.println("��ʼ��ʼ��������");
        System.out.println("�ǲ��Ǳ�����");

        group.setAccount(new GetMyAccount().getAccountGroup());
        System.out.println("����û�б���");
        Connection conn = null;

        PreparedStatement pss = null;
        try {
            conn= utilsConnect();
            String slq = "insert into groupprofile (groupaccount,groupname,groupimage) values (?,?,?)";
            pss=conn.prepareStatement(slq);
            pss.setString(1,group.getAccount());
            pss.setString(2,group.getNickname());
            pss.setString(3, group.getAvatar());
            pss.execute();

        }catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("���ݿ����Ӵ���1------");
        }finally {
            utilsRelease(pss,conn);
        }
        System.out.println(group.getGroupAccount()+"���ݿ����");
        return group;

    }
    public static void transferGroup(ArrayList<Friend>arrayList)
    {

        Connection conn = null;
        Friend ff=arrayList.get(0);
        PreparedStatement pss = null;
        try {
            conn= utilsConnect();
            String slq = "update groupmember set status =? where groupaccount=? and personaccount=? ";
            pss=conn.prepareStatement(slq);
            pss.setString(1,"1");
            pss.setString(2,ff.getGroupAccount());
            pss.setString(3, ff.getAccount());
            pss.execute();
            pss.setString(1,"3");
            pss.setString(2,ff.getGroupAccount());
            pss.setString(3,Channel.u.getUserAccount());
            pss.execute();

        }catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("���ݿ����Ӵ���1------");
        }finally {
            utilsRelease(pss,conn);
        }


    }
    public static void memberListInit(ArrayList<Friend> memberList)
    {
        Connection conn = null;
        PreparedStatement pss = null;//һ��ʾȺ����2��ʾ����Ա������ʾȺ��Ա��
        try {
            conn= utilsConnect();

            String slq = "insert into groupmember (groupaccount,personaccount,status) values (?,?,?)";
            pss=conn.prepareStatement(slq);
            conn.setAutoCommit(false);
            int size=memberList.size();
            for(int i=0;i<size;i++)
            {
                System.out.println("��ʼ�������ݿ���");

                pss.setString(1,memberList.get(i).getGroupAccount());
                pss.setString(2,memberList.get(i).getAccount());
                if(i==size-1)
                    pss.setString(3,"1");
                else
                pss.setString(3,"3");

                pss.addBatch();

            }

            pss.executeBatch();
            conn.commit();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("���ݿ����Ӵ���1------");
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

        } finally {
            try {
                if (conn!=null)
                    conn.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
           utilsRelease(pss,conn);

        }
    }
    public static String typeJudge(String account)
    {
        String temp="";
        String[]array=account.split("&");
        String accountm=array[1];
        String accountf=array[0];
        Connection conn = null;
        PreparedStatement pss = null;
        try {
            conn= utilsConnect();

            String slq = "select * from friendship where useraccount=? and friendaccount=?";
            pss=conn.prepareStatement(slq);
            pss.setString(1,accountm);
            pss.setString(2,accountf);
            ResultSet rs=pss.executeQuery();
            boolean mark=false;
            mark=rs.next();
            if(mark)
            {
                if(rs.getString("relationstatus").equals("1"))temp= MessageType.MESSAGE_FRIEND_FRI;
                else if(rs.getString("relationstatus").equals("3"))temp=MessageType.MESSAGE_FRIEND_APP;
                else temp=MessageType.MESSAGE_FRIEND_STRANGER;
            }
            else
            {
                temp=MessageType.MESSAGE_FRIEND_STRANGER;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("���ݿ����Ӵ���1------");
        }finally {
           utilsRelease(pss,conn);
        }
        return temp;

    }


    public static boolean leaveGroup(String account)
    {
        String[]array=account.split("&");
        String accountg=array[1];
        String accountm=array[0];
        boolean mark=false;
        Connection conn = null;
        PreparedStatement pss = null;
        try {
            conn= utilsConnect();

            String qqq="select * from groupmember where groupaccount=? and status=?";
            String slq = "delete from groupmember where personaccount=?";
            pss=conn.prepareStatement(qqq);
            pss.setString(1,accountg);
            pss.setString(2,"1");
            ResultSet rs=pss.executeQuery();
            rs.next();
            String owner=rs.getString("personaccount");
            if(owner.equals(accountm))
            {
                String qqqq="select * from groupmember where groupaccount=?";
                pss=conn.prepareStatement(qqqq);
                pss.setString(1,accountg);
                rs=pss.executeQuery();
                rs.next();
               if(rs.next())
               {
                   String member=rs.getString("personaccount");
                   String qqqqq="update groupmember set status=? where personaccount=?";
                   pss=conn.prepareStatement(qqqqq);
                   pss.setString(1,"1");
                   pss.setString(2,member);
                   pss.execute();
                   mark=true;
               }

            }
            else
            {
                pss=conn.prepareStatement(slq);
                pss.setString(1,accountm);
                pss.execute();
                mark=true;
            }

        }catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("���ݿ����Ӵ���1------");
        }finally {


            utilsRelease(pss,conn);
        }
        return mark;


    }

    public static Friend approvalApp(String account)
    {
        String temp="";
        String[]array=account.split("&");
        String accountm=array[0];
        String accountf=array[1];
        Connection conn = null;
        PreparedStatement pss = null;
        Friend fm=null;
        try {
            conn= utilsConnect();

            String slq = "update friendship set relationstatus=? where useraccount=? and friendaccount=?";
            String sql= "update friendship set relationstatus=? where useraccount=? and friendaccount=?";
            pss=conn.prepareStatement(slq);
            pss.setString(1,"1");
            pss.setString(2,accountm);
            pss.setString(3,accountf);
            pss.executeUpdate();
            pss=conn.prepareStatement(sql);
            pss.setString(1,"1");
            pss.setString(2,accountf);
            pss.setString(3,accountm);
            pss.executeUpdate();
            String qqq="select * from person where account=?";
            pss= conn.prepareStatement(qqq);
            pss.setString(1,accountm);
            ResultSet rs=pss.executeQuery();
            rs.next();
             fm=new Friend(accountf,rs.getString("image"), rs.getString("name"));

        }catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("���ݿ����Ӵ���1------");
        }finally {
        utilsRelease(pss,conn);
        }
        return fm;
    }
    public static String askRelationGroup(String account)
    {
        String mm=null;
        String array[]=account.split("&");
        String accountg= array[0];
        String accountm=array[1];
        Connection conn = null;
        PreparedStatement pss = null;
        try {
            conn = utilsConnect();
            String slq = "select * from groupmember where groupaccount=? and personaccount=?";
            pss = conn.prepareStatement(slq);
            pss.setString(1, accountg);
            pss.setString(2, accountm);
            ResultSet rs = pss.executeQuery();
            if(rs.next())
            {
                mm=rs.getString("status");

            }else
            {
                mm="0";
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("���ݿ����Ӵ���");
        } finally {
            utilsRelease(pss,conn);
        }
        return mm;
    }
    public static String askRelation(String account)
    {
        String mm=null;
        String array[]=account.split("&");
        String accountf= array[0];
        String accountm=array[1];
        Connection conn = null;
        PreparedStatement pss = null;
        try {
            conn = utilsConnect();
            String slq = "select * from friendship where useraccount=? and friendaccount=?";
            pss = conn.prepareStatement(slq);
            pss.setString(1, accountm);
            pss.setString(2, accountf);
            ResultSet rs = pss.executeQuery();
            if(rs.next())
            {
                mm=rs.getString("relationstatus");

            }else
            {
                mm="0";
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
                System.out.println("���ݿ����Ӵ���");
        } finally {
            utilsRelease(pss,conn);
        }
        return mm;
    }
    public static void setManager(ArrayList<Friend>arrayList) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = utilsConnect();
            String sql = "update groupmember set status=? where personaccount=? and groupaccount=?";//?ռλ��
            ps = conn.prepareStatement(sql);
            for (Friend f : arrayList) {
                System.out.println("��ʼ�������ݿ���Ц��");
                ps.setString(1,"2");
                ps.setString(2, f.getAccount());
                ps.setString(3, f.getGroupAccount());
                ps.execute();
                break;


            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("���ݿ����Ӵ���1------");

        } finally {

            utilsRelease(ps,conn);
        }
    }
    public static void substractMemberGroup(ArrayList<Friend>arrayList)
    {
        Connection conn=null;
        PreparedStatement ps=null;
        try {
            conn=utilsConnect();
            String sql = "delete from groupmember where groupaccount=? and personaccount=? and status!=?;";//?ռλ��
            System.out.println("��ȥ");
            ps=conn.prepareStatement(sql);
            conn.setAutoCommit(false);
            for(Friend f:arrayList)
            {
                System.out.println("��ʼ�������ݿ���Ц��");
                ps.setString(1,f.getGroupAccount());
                ps.setString(2,f.getAccount());
                ps.setString(3,"1");
                ps.addBatch();

            }
            System.out.println("�ƺ�û��ȥ");
            ps.executeBatch();
            conn.commit();

        }catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("���ݿ����Ӵ���1------");
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

        } finally {
            try {
                if (conn!=null)
                    conn.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            utilsRelease(ps,conn);
        }

    }
    public static long getPosition(int mark)
    {
        long temp=0;
            Connection conn=null;
            PreparedStatement ps=null;
            try {
                conn=utilsConnect();
                String sql = "select*from fileposition where id=?";//?ռλ��
                ps=conn.prepareStatement(sql);
               ps.setInt(1,mark);
               ResultSet rs=ps.executeQuery();
               rs.next();
               temp=rs.getLong("positionID");

            }catch (SQLException throwables) {
                throwables.printStackTrace();
                System.out.println("���ݿ����Ӵ���1------");
            } finally {
                utilsRelease(ps,conn);
            }

        return temp;
    }
    public static void freshFilePosition(Long temp,int mark)
    {
        Connection conn=null;
        PreparedStatement ps=null;
        try {
            conn=utilsConnect();
            String sql = "update fileposition set positionID=? where id=?";//?ռλ��
            ps=conn.prepareStatement(sql);
            ps.setLong(1,temp);
            if(mark==1)
            {
                ps.setInt(2,1);
            }
            else
            {
                ps.setInt(2,2);
            }
            ps.execute();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("���ݿ����Ӵ���1------");
        } finally {
            utilsRelease(ps,conn);
        }

    }

    public static void addMemberGroup(ArrayList<Friend>arrayList)
    {
        Connection conn=null;
        PreparedStatement ps=null;
        try {
            conn=utilsConnect();
            String sql = "insert into groupmember (groupaccount,personaccount,status)values(?,?,?)";//?ռλ��
            ps=conn.prepareStatement(sql);
            conn.setAutoCommit(false);
            for(Friend f:arrayList)
            {
                System.out.println("��ʼ�������ݿ���Ц��");
                ps.setString(1,f.getGroupAccount());
                ps.setString(2,f.getAccount());
                ps.setString(3,"3");
                ps.addBatch();

            }
            System.out.println("�ƺ�û��ȥ");
            ps.executeBatch();
            conn.commit();



        }catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("���ݿ����Ӵ���1------");
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

        } finally {
            try {
                if (conn!=null)
                    conn.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            utilsRelease(ps,conn);
        }

    }
    public static ArrayList<Friend>indicateMemberGroup(String account)
    {
        Connection conn = null;
        PreparedStatement pss = null;
        ArrayList <Friend>arrayList=new ArrayList<>();
        try {
            conn= utilsConnect();
            String qqq="select *from groupmember,person where groupmember.groupaccount=? and person.account=groupmember.personaccount";
            pss=conn.prepareStatement(qqq);
            pss.setString(1,account);
            ResultSet rs=pss.executeQuery();
            while(rs.next())
            {
                Friend f=new Friend(rs.getString("personaccount"),rs.getString("image"),rs.getString("name"));
                f.setGroupAccount(account);
                arrayList.add(f);
            }

        }catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("���ݿ����Ӵ���1------");
        }finally {
            utilsRelease(pss,conn);
        }

        return arrayList;
    }
    public static void quitGroup(String account)
    {
        Connection conn = null;
        PreparedStatement pss = null;
        try {
            conn= utilsConnect();
            String qqq="delete from groupmember where groupaccount=?";
            pss=conn.prepareStatement(qqq);
            pss.setString(1,account);
            pss.execute();
            String qql="delete from messagerecordgroup where groupaccount=?";
            pss=conn.prepareStatement(qql);
            pss.setString(1,account);
            pss.execute();
            String lll="delete from groupprofile where groupaccount=?";
            pss=conn.prepareStatement(lll);
            pss.setString(1,account);
            pss.execute();

        }catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("���ݿ����Ӵ���1------");
        }finally {
            utilsRelease(pss,conn);
        }
    }
    public static void applyGroup(Friend f)
    {
        Connection conn = null;
        PreparedStatement pss = null;
        try {
            conn= utilsConnect();
            String qqq="insert into groupmember (groupaccount,personaccount,status)values(?,?,?)";
            pss=conn.prepareStatement(qqq);
            pss.setString(1,f.getGroupAccount());
            pss.setString(2,f.getAccount());
            pss.setString(3,"4");
            pss.execute();

        }catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("���ݿ����Ӵ���1------");
        }finally {
            utilsRelease(pss,conn);
        }

    }
    public static GroupCommon profileGroup(String accountGroup)
    {
        Connection conn = null;
        PreparedStatement pss = null;
        GroupCommon gg=new GroupCommon();
        try {
            conn= utilsConnect();
            String slq = "select * from groupprofile where groupaccount=?";
            pss=conn.prepareStatement(slq);
            pss.setString(1,accountGroup);
            ResultSet rs=pss.executeQuery();
            rs.next();
            System.out.println("���˵�һ��");
            gg.setGroupAccount(rs.getString("groupaccount"));
            gg.setGroupName(rs.getString("groupname"));
            gg.setSignature(rs.getString("groupsignature"));
            gg.setImageGroup(rs.getString(("groupimage")));
            String qqq="select * from groupmember where groupaccount=? and status=?";
            pss=conn.prepareStatement(qqq);
            pss.setString(1,accountGroup);
            pss.setString(2,"1");
            rs=pss.executeQuery();
            System.out.println(rs.next()+"��������鲻����");
            System.out.println("���ǵڶ���");
            gg.setOwnerAccount(rs.getString("personaccount"));
            String lll="select * from groupmember where groupaccount=? and status=?";
            pss=conn.prepareStatement(lll);
            pss.setString(1,accountGroup);
            pss.setString(2,"2");
            rs=pss.executeQuery();

            if(rs.next())
            {
                gg.setManagerAccount((rs.getString("personaccount")));
            }

            System.out.println("û�����һ��"+gg);

        }catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("���ݿ����Ӵ���1------");
        }finally {
            utilsRelease(pss,conn);
        }
        return gg;
    }
    public static UserCopy applyInfo(UserCopy uuu) {
        Connection conn = null;
        PreparedStatement pss = null;
        try {
            conn= utilsConnect();

            String slq = "select * from person where account=?";
            pss=conn.prepareStatement(slq);
            pss.setString(1,uuu.getUserAccount());
            ResultSet rs=pss.executeQuery();
            rs.next();
            uuu.setUserBirthday(rs.getDate("birthday"));
            uuu.setUserName(rs.getString("name"));
            uuu.setGender(rs.getString("gender"));
            uuu.setUserSignature(rs.getString("signature"));
            uuu.setUserImage(rs.getString("image"));
            uuu.setOnline(ManageServerToThread.isExist(uuu.getUserAccount()));
        }catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("���ݿ����Ӵ���1------");
        }finally {
            utilsRelease(pss,conn);
        }
        return uuu;
    }

    public static ConcurrentHashMap<String, String> imageAvar(String[] temp)
    {
        Connection conn=null;
        PreparedStatement pss=null;
        ConcurrentHashMap<String, String> resultMapping=null;
        try {
            conn= utilsConnect();

            String qqq="select * from person where account=?";
            pss= conn.prepareStatement(qqq);

            resultMapping = new ConcurrentHashMap<>(); // �����˺ź�ͷ��Ķ�Ӧ��ϵ
            System.out.println("�˳�ͷ����û��ȥ");
            for(String account:temp) {
                pss.setString(1, account);
                ResultSet resultSet = pss.executeQuery(); // ִ�в�ѯ���õ������
                if (resultSet.next()) {
                    String username = resultSet.getString("account"); // ��ȡ�˺�ֵ
                    String avatar = resultSet.getString("image"); // ��ȡͷ��ֵ
                    resultMapping.put(username, avatar); // �����˺ź�ͷ��Ķ�Ӧ��ϵ
                     System.out.println("����������������˵��ʺ�ֵ��"+username+"--->"+avatar);
                }

                resultSet.close(); // �رս����
            }

        }catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("���ݿ����Ӵ���1------");
        }finally {
            utilsRelease(pss,conn);
        }
        return resultMapping;
    }
    public static boolean applied(String f,String u)
    {
        Connection conn=null;
        boolean mark=false;
        PreparedStatement pss=null;
        try {
            conn= utilsConnect();


            String qql="select *from friendship where useraccount =? and friendaccount=? ";
            String slq="insert into friendship (useraccount,friendaccount,relationstatus)values(?,?,?)";
            String sql="insert into friendship (useraccount,friendaccount,relationstatus)values(?,?,?)";
            pss= conn.prepareStatement(qql);
            pss.setString(1,u);
            pss.setString(2,f);
            ResultSet rs=pss.executeQuery();
            if(rs.next())
            {
                if(rs.getString("relationstatus").equals("2")||rs.getString("relationstatus").equals("3"))mark=false;
                else
                {
                    String qqq="update friendship set relationstatus=? where useraccount=? and friendaccount=?";
                    pss= conn.prepareStatement(qqq);
                    pss.setString(1,"2");
                    pss.setString(2,u);
                    pss.setString(3,f);
                    pss.execute();
                    pss.setString(1,"3");
                    pss.setString(2,f);
                    pss.setString(3,u);
                    pss.execute();
                }
            }
            else
            {
                pss=conn.prepareStatement(slq);
                pss.setString(1,u);
                pss.setString(2,f);
                pss.setString(3,"2");
                pss.executeUpdate();

                pss=conn.prepareStatement(sql);
                pss.setString(1,f);
                pss.setString(2,u);
                pss.setString(3,"3");
                pss.execute();
                mark=true;

            }

        }catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("���ݿ����Ӵ���1------");
        }finally {
            utilsRelease(pss,conn);
        }
        return mark;//�۾���˵

    }
    public static void deleteFriend(String f,String u)
    {
        Connection conn=null;
        PreparedStatement pss=null;
        try {
            conn= utilsConnect();


            String slq="update friendship set relationstatus=?where useraccount=? and friendaccount=?";
            pss= conn.prepareStatement(slq);
            pss.setString(2,u);
            pss.setString(3,f);
            pss.setString(1,"5");
            pss.execute();
            pss.setString(2,f);
            pss.setString(3,u);
            pss.setString(1,"4");
            pss.execute();

        }catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("���ݿ����Ӵ���1------");
        }finally {
            utilsRelease(pss,conn);
        }

    }
    public static Friend[] fgIndicate1(String account)
    {
        ArrayList<Friend> friendList = new ArrayList<>();
        Connection conn=null;
        PreparedStatement pss=null;
        try {
            conn= utilsConnect();

            String sql="select * from person";
           String slq="select *from groupprofile";
            pss=conn.prepareStatement(sql);
            ResultSet rs=pss.executeQuery();
            while(rs.next())
            {
                Friend ff=new Friend("","","");
                ff.setAccount(rs.getString("account"));
                ff.setNickname(rs.getString("name"));
                ff.setAvatar(rs.getString("image"));
                friendList.add(ff);
            }
            pss=conn.prepareStatement(slq);
            rs=pss.executeQuery();
            while(rs.next())
            {
                Friend ff=new Friend(rs.getString("groupaccount"),rs.getString("groupimage"),rs.getString("groupname"));
                friendList.add(ff);
            }

        }  catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("���ݿ����Ӵ���1------");
        }finally {
            utilsRelease(pss,conn);
        }
        System.out.println("�����ﶼû����������");
        Friend[]ff= friendList.toArray(new Friend[0]);
        return ff;

    }
    public static Friend[] indicatefgCopy(String account)
    {
        String []array=account.split("&");
        String accountg=array[0];
        String accountm=array[1];
        ArrayList<Friend> friendList = new ArrayList<>();
        Connection conn=null;
        PreparedStatement pss=null;
        try {
            conn= utilsConnect();

            String sql="SELECT * FROM person,friendship WHERE friendship.useraccount=? AND friendship.relationstatus=? AND person.account=friendship.friendaccount";
            pss=conn.prepareStatement(sql);
            pss.setString(1,accountm);
            pss.setString(2,"1");
            ResultSet rs=pss.executeQuery();
            while(rs.next())
            {
                Friend ff=new Friend("","","");
                ff.setAccount(rs.getString("friendaccount"));
                ff.setNickname(rs.getString("name"));
                ff.setAvatar(rs.getString("image"));
                ff.setGroupAccount(accountg);
                ff.setOnline(ManageServerToThread.isExist(ff.getAccount()));
                System.out.println("�ҿ������ݿ���߸ոճ������ڲ�����"+ff.isOnline());
                System.out.println(ff+"���������ݿ������Ӧ�ÿ���������ʾ");
                friendList.add(ff);

            }
        }  catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("���ݿ����Ӵ���1------");
        }finally {
            utilsRelease(pss,conn);
        }
        System.out.println("�����ﶼû����������"+friendList);

        return friendList.toArray(new Friend[0]);

    }

    public static Friend[] fgIndicate(String account)
    {
        ArrayList<Friend> friendList = new ArrayList<>();
        Connection conn=null;
        PreparedStatement pss=null;
        try {
            conn= utilsConnect();

            String sql="SELECT * FROM person,friendship WHERE friendship.useraccount=? AND friendship.relationstatus=? AND person.account=friendship.friendaccount";
            pss=conn.prepareStatement(sql);
            pss.setString(1,account);
            pss.setString(2,"1");
            ResultSet rs=pss.executeQuery();
            while(rs.next())
            {
                Friend ff=new Friend("","","");
                ff.setAccount(rs.getString("friendaccount"));
                ff.setNickname(rs.getString("name"));
                ff.setAvatar(rs.getString("image"));
                ff.setOnline(ManageServerToThread.isExist(ff.getAccount()));
                System.out.println("�ҿ������ݿ���߸ոճ������ڲ�����"+ff.isOnline());
                System.out.println(ff+"���������ݿ������Ӧ�ÿ���������ʾ");
                friendList.add(ff);

            }
        }  catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("���ݿ����Ӵ���1------");
        }finally {
        utilsRelease(pss,conn);
        }
        System.out.println("�����ﶼû����������"+friendList);

       return friendList.toArray(new Friend[0]);

    }
    public static ArrayList<String>initAlways(String account)
    {
        System.out.println("��С���ĳ�����");
        Connection conn=null;
        PreparedStatement ps=null;
        conn=utilsConnect();
        ArrayList<String>arrayList=new ArrayList<>();
        System.out.println("�ڶ�");
        String sql="select *from alwaysform where personaccount=? ";
        String qqq="insert into alwaysform (alwayssentence,textid,personaccount)values(?,?,?)";
        System.out.println("����С");
        try {
            System.out.println("����С");
            ps=conn.prepareStatement(sql);
            System.out.println("����");
            ps.setString(1,account);
            System.out.println("����");
            ResultSet rs=ps.executeQuery();
            System.out.println("���������ݿ�����ĳ�ʼ��������");
            if(rs.next())
            {
                System.out.println("��һ�����");
                arrayList.add(rs.getString("alwayssentence"));
                while(rs.next())
                {
                    arrayList.add(rs.getString("alwayssentence"));
                }
            }
            else
            {
                System.out.println("�ڶ������");
                ps=conn.prepareStatement(qqq);
                conn.setAutoCommit(false);
                arrayList.add("лл");
                arrayList.add("����");
                arrayList.add("���");
                arrayList.add("����һ��С�컨");
                int i=1;
                for(String text:arrayList)
                {
                    ps.setString(1,text);
                    ps.setString(2,i+"");
                    ps.setString(3,account);
                    ps.addBatch();
                    i++;
                }
                ps.executeBatch();
                conn.commit();

            }


        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                if (conn!=null)
                    conn.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            utilsRelease(ps,conn);
        }
        System.out.println("���һ��");
        return arrayList;

    }
    public static void refuseGroup(String account)
    {
        String[]array=account.split("&");
        String accountg=array[0];
        String accountf=array[1];
        Connection conn=null;
        conn=utilsConnect();
        PreparedStatement ps=null;
        String sql="delete from groupmember where groupaccount=? and personaccount=?";
        try {
            ps=conn.prepareStatement(sql);
            ps.setString(1,accountg);
            ps.setString(2,accountf);
            System.out.println("��ʼ�������ݿ���");
            ps.execute();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {

            utilsRelease(ps,conn);
        }
    }
    public static void refuseFriend(String account)
    {
        String[]array=account.split("&");
        String accountf=array[0];
        String accountm=array[1];
        Connection conn=null;
        conn=utilsConnect();
        PreparedStatement ps=null;
        String sql="delete from friendship where useraccount=? and friendaccount=?";
        try {
            ps=conn.prepareStatement(sql);
            ps.setString(1,accountf);
            ps.setString(2,accountm);
            System.out.println("��ʼ�������ݿ���");
            ps.execute();
            ps.setString(1,accountm);
            ps.setString(2,accountf);
            ps.execute();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {

            utilsRelease(ps,conn);
        }
    }
    public static void approvalGroup(String account)
    {
        String[]array=account.split("&");
        String accountg=array[0];
        String accountf=array[1];
        Connection conn=null;
        conn=utilsConnect();
        PreparedStatement ps=null;
        String sql="update groupmember set status=? where groupaccount=? and personaccount=?";
        try {
            ps=conn.prepareStatement(sql);
            ps.setString(1,"3");
            ps.setString(2,accountg);
            ps.setString(3,accountf);
                System.out.println("��ʼ�������ݿ���");
              ps.execute();


        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {

            utilsRelease(ps,conn);
        }



    }
    public static void modifyAlways(ArrayList<String>arrayList)
    {
        Connection conn=null;
        PreparedStatement ps=null;
        conn=utilsConnect();
        String sql="update alwaysform set alwayssentence=? and textid=? and personaccount=?";
        try {
            ps=conn.prepareStatement(sql);
            conn.setAutoCommit(false);
            int i=0;
            for(String text:arrayList)
            {
                if(i==0)continue;
                System.out.println("��ʼ�������ݿ���");
                ps.setString(1,text);
                ps.setString(2,""+i);
                ps.setString(3,arrayList.get(0));
                ps.addBatch();
                i++;

            }
            ps.executeBatch();
            conn.commit();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
        try {
            if (conn!=null)
                conn.setAutoCommit(true);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
            utilsRelease(ps,conn);
        }

    }
    public static ArrayList<Friend> fgIndicate3(String account)
    {//Ⱥ�Ľ���ȷʵ������
        ArrayList<Friend> friendList = new ArrayList<>();
        Connection conn=null;
        PreparedStatement pss=null;
        try {
            conn= utilsConnect();
            String sql="SELECT * FROM groupmember ,groupprofile where groupmember.personaccount=?  and status!=? and groupprofile.groupaccount=groupmember.groupaccount";
            pss=conn.prepareStatement(sql);
            pss.setString(1,account);
            pss.setString(2,"4");
            ResultSet rs=pss.executeQuery();
            while(rs.next())
            {
                Friend ff=new Friend("","","");
                ff.setAccount(rs.getString("groupaccount"));
                ff.setNickname(rs.getString("groupname"));
                ff.setAvatar(rs.getString("groupimage"));
                friendList.add(ff);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("���ݿ����Ӵ���1------");
        }finally {
        utilsRelease(pss,conn);
        }
        System.out.println("�����ﶼû����������");
        return friendList;

    }
    public static ArrayList<Friend> fgIndicate2(String account)
    {
        ArrayList<Friend> friendList = new ArrayList<>();
        Connection conn=null;
        PreparedStatement pss=null;
        try {
            conn= utilsConnect();

            String sql="SELECT * FROM person,friendship WHERE friendship.useraccount=? AND friendship.relationstatus=? AND person.account=friendship.friendaccount";
            pss=conn.prepareStatement(sql);
            pss.setString(1,account);
            pss.setString(2,"3");

            ResultSet rs=pss.executeQuery();
            while(rs.next())
            {
                Friend ff=new Friend("","","");
                ff.setAccount(rs.getString("friendaccount"));
                ff.setNickname(rs.getString("name"));
                ff.setAvatar(rs.getString("image"));
                ff.setStatus(rs.getString("relationstatus"));
                ff.setOnline(ManageServerToThread.isExist(ff.getAccount()));
                friendList.add(ff);

            }
            String qqq="select *from groupmember,person where groupmember.status=? and person.account=groupmember.personaccount";
            pss=conn.prepareStatement(qqq);
            pss.setString(1,"4");
            rs=pss.executeQuery();
            while(rs.next())
            {
                if(ConnectSql.askRelationGroup(rs.getString("groupaccount")+"&"+account).equals("1"))
                {
                    Friend ff=new Friend("","","");
                    ff.setAccount(rs.getString("personaccount"));
                    ff.setNickname(rs.getString("name"));
                    ff.setAvatar(rs.getString("image"));
                    ff.setOnline(ManageServerToThread.isExist(ff.getAccount()));
                    GroupCommon ggg=profileGroup(rs.getString("groupaccount"));
                    ff.setGroupAccount(ggg.getGroupName()+"&"+ggg.getGroupAccount());
                    friendList.add(ff);
                }
            }



        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("���ݿ����Ӵ���1------");
        }finally {
        utilsRelease(pss,conn);
        }
        System.out.println("�����ﶼû����������");
        return friendList;
    }


    public static User storeProfilee(User u)
    {
        Connection conn=null;
        boolean mark=false;
        PreparedStatement pss=null;
        try {
            conn= utilsConnect();
            String sql="update person set name =?,birthday=?,signature=?,gender=?,image=? where email=?";
            String slq="select name from person where name=?";
            pss=conn.prepareStatement(slq);
            pss.setString(1,u.getUserName());
            System.out.println(u.getUserName()+"---------------------------->");
            ResultSet rs=pss.executeQuery();
            rs.next();
            mark=rs.next();

            if(!mark)
            {
                pss=conn.prepareStatement(sql);
                pss.setString(1,u.getUserName());
                pss.setDate(2,u.getUserBirthday());
                pss.setString(3,u.getUserSignature());
                pss.setString(4,u.isGender());
                pss.setString(5,u.getUserImage());
                pss.executeUpdate();

            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("���ݿ����Ӵ���1------");
        }finally {
            utilsRelease(pss,conn);
        }
        if(!mark)return u;
        else return null;

    }

}

