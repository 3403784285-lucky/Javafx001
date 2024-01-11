package utils;
import common.User;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
public class ManageServerToThread {
    private static ConcurrentHashMap<String,ThreadServerConnect> hm=new ConcurrentHashMap();
    //添加线程对象到集合
    public static void addServerThread(String account, ThreadServerConnect task)
    {
        hm.put(account,task);

    }
    public static ThreadServerConnect getTsc(String account) {
        return hm.get(account);
    }
    public static void removeServerThread(String account)
    {
            hm.remove(account);
    }

    public static boolean isExist(String account) {
        System.out.println(hm+"是否存在表");
        if(hm.containsKey(account))return true;
      return  false;
    }
}
