package utils;
import common.User;
import java.util.concurrent.ConcurrentHashMap;

//����ͻ������ӵ����������̵߳��ࣻ
public class ManageClientToThread {
   //�Ѷ���̷߳��뼯����
   public static User u;
   public static User uOriginal;
   private static ConcurrentHashMap<User, ThreadClientConnect>hm=new ConcurrentHashMap<>();
   //��ĳ���̼߳��뵽������
   public static void addClientThread(User ui, ThreadClientConnect conn)//����Ҳ��
   {
      uOriginal=u=ui;
      hm.put(uOriginal,conn);

   }
   //ͨ��һ�������˺ŵõ��߳�
   public static ThreadClientConnect getConn(User ui)
   {
      return hm.get(ui);
   }



}
