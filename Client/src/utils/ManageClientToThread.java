package utils;
import common.User;
import java.util.concurrent.ConcurrentHashMap;

//管理客户端连接到服务器现线程的类；
public class ManageClientToThread {
   //把多个线程放入集合中
   public static User u;
   public static User uOriginal;
   private static ConcurrentHashMap<User, ThreadClientConnect>hm=new ConcurrentHashMap<>();
   //将某个线程加入到集合中
   public static void addClientThread(User ui, ThreadClientConnect conn)//邮箱也可
   {
      uOriginal=u=ui;
      hm.put(uOriginal,conn);

   }
   //通过一个方法账号得到线程
   public static ThreadClientConnect getConn(User ui)
   {
      return hm.get(ui);
   }



}
