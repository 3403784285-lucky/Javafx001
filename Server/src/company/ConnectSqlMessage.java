package company;

import common.Message;

import java.sql.*;
import java.util.ArrayList;

public class ConnectSqlMessage
{
    public static boolean receiveMessageText(Message msg)
    {
        Connection conn=null;
        PreparedStatement ps=null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chatroomperson", "root", "root");
            String sql = "insert into messagerecordperson (content,sender,receiver,sendtime,contenttype)values(?,?,?,?,?)";//?占位符
            ps=conn.prepareStatement(sql);
            ps.setString(1,msg.getContent());
            ps.setString(2,msg.getSender());
            ps.setString(3,msg.getGetter());
            ps.setTimestamp(4,msg.getSendTime());
            ps.setString(5,msg.getMesType());
            ps.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("数据库连接错误2------");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("数据库连接错误1------");

        } finally {
            try {
                if(ps!=null)
                    ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                System.out.println("数据库流关闭错误1");
            }
            try {
                if ((conn!=null))
                    conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                System.out.println("数据库流关闭错误2");
            }

        }
        return true;
    }
    public static void insertMessageGroup(Message[]tempGroupMessage)
    {
        System.out.println(tempGroupMessage+"数据库");
        Connection conn=null;
        PreparedStatement ps=null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chatroomperson", "root", "root");
            String sql = "insert into messagerecordgroup (groupaccount,sender,content,sendtime,contenttype,byte)values(?,?,?,?,?,?)";//?占位符
            ps=conn.prepareStatement(sql);
            conn.setAutoCommit(false);
            for(Message msg:tempGroupMessage)
            {
                System.out.println("开始传到数据库了");
                ps.setString(1,msg.getGetter());
                ps.setString(2,msg.getSender());
                ps.setString(3,msg.getContent());
                ps.setTimestamp(4,msg.getSendTime());
                ps.setString(5,msg.getMesType());
                ps.setBytes(6, msg.getTransfer());
                System.out.println("服务器数据库测试听过");
                ps.addBatch();

            }
            System.out.println("似乎没进去");
            ps.executeBatch();
            conn.commit();



        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("数据库连接错误2------");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("数据库连接错误1------");
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
            try {
                if(ps!=null)
                    ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                System.out.println("数据库流关闭错误1");
            }
            try {
                if ((conn!=null))
                    conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                System.out.println("数据库流关闭错误2");
            }

        }
    }
    public static ArrayList<Message>initMessageRecordGroup(String groupAccount)
    {
        ArrayList<Message>arrayList=new ArrayList<>();
        Connection conn=null;
        PreparedStatement ps=null;
        try {


            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chatroomperson", "root", "root");
            String sql = "select *from messagerecordgroup where groupaccount=?";//?占位符
            ps=conn.prepareStatement(sql);
            ps.setString(1,groupAccount);
            ResultSet rs=ps.executeQuery();
            while(rs.next())
            {
                Message m=new Message();
                m.setContent(rs.getString("content"));
                m.setGetter(groupAccount);
                m.setSender(rs.getString("sender"));
                m.setMesType(rs.getString("contenttype"));
                m.setSendTime(rs.getTimestamp("sendtime"));
                m.setTransfer(rs.getBytes("byte"));
                arrayList.add(m);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("数据库连接错误2------");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("数据库连接错误1------");

        } finally {
            try {
                if(ps!=null)
                    ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                System.out.println("数据库流关闭错误1");
            }
            try {
                if ((conn!=null))
                    conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                System.out.println("数据库流关闭错误2");
            }

        }
        return arrayList;

    }
    public static ArrayList<Message> initMessagerecord(String account)
    {
        ArrayList<Message>arrayList=new ArrayList<>();
        Connection conn=null;
        PreparedStatement ps=null;
        try {

            String[]array=account.split("&");
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println(array[0]);
            System.out.println(array[1]+"就看一眼账号是不是不一样");

            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chatroomperson", "root", "root");
            String sql = "select *from messagerecordperson where (sender=? and receiver=?)or(sender=? and receiver=?)";//?占位符
            ps=conn.prepareStatement(sql);
            ps.setString(1,array[0]);
            ps.setString(2,array[1]);
            ps.setString(3,array[1]);
            ps.setString(4,array[0]);
            ResultSet rs=ps.executeQuery();
            int count=0;
            while(rs.next())
            {
                Message m=new Message();
                m.setContent(rs.getString("content"));
                m.setGetter(rs.getString("receiver"));
                m.setSender(rs.getString("sender"));
                m.setMesType(rs.getString("contenttype"));
                m.setSendTime(rs.getTimestamp("sendtime"));
                m.setTransfer(rs.getBytes("byte"));
                arrayList.add(m);
                System.out.println(m.getContent());
            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("数据库连接错误2------");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("数据库连接错误1------");

        } finally {
            try {
                if(ps!=null)
                    ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                System.out.println("数据库流关闭错误1");
            }
            try {
                if ((conn!=null))
                    conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                System.out.println("数据库流关闭错误2");
            }

        }
        return arrayList;
    }
    public static boolean insertMessage(Message[] temp)
    {
        System.out.println(temp+"数据库");
        Connection conn=null;
        PreparedStatement ps=null;
        try {


            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chatroomperson", "root", "root");
            String sql = "insert into messagerecordperson (content,sender,receiver,sendtime,contenttype,byte)values(?,?,?,?,?,?)";//?占位符
            ps=conn.prepareStatement(sql);
            conn.setAutoCommit(false);
            for(Message msg:temp)
            {
                System.out.println("开始传到数据库了");
                ps.setString(1,msg.getContent());
                ps.setString(2,msg.getSender());
                ps.setString(3,msg.getGetter());
                ps.setTimestamp(4,msg.getSendTime());
                ps.setString(5,msg.getMesType());
                ps.setBytes(6, msg.getTransfer());
                System.out.println(msg.getContent());
                ps.addBatch();

            }
            System.out.println("似乎没进去");
            ps.executeBatch();
            conn.commit();



        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("数据库连接错误2------");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("数据库连接错误1------");
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
            try {
                if(ps!=null)
                    ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                System.out.println("数据库流关闭错误1");
            }
            try {
                if ((conn!=null))
                    conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                System.out.println("数据库流关闭错误2");
            }

        }
        return true;
    }
}