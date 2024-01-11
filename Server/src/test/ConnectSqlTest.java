package test;

import common.User;
import company.ConnectSql;

import static org.junit.Assert.*;

public class ConnectSqlTest {

    @org.junit.Test
    public void selectGroupAccount() {
        System.out.println("该群聊账号是否存在"+ConnectSql.selectGroupAccount("11111"));

    }

    @org.junit.Test
    public void selectAccount() {
        System.out.println("该个人账号是否存在"+ConnectSql.selectAccount("11111"));
    }

    @org.junit.Test
    public void selectInfo1() {
        User user=new User();
        user.setUserAccount("11111");
        user.setPassword("11111");
        System.out.println("该个人账号并且密码为11111的是否存在"+ConnectSql.selectInfo1(user));
    }

    @org.junit.Test
    public void selectInfo2() {
        User user=new User();
        user.setUserEmail("3403784285@qq.com");
        System.out.println("该邮箱为这个的是否存在"+ConnectSql.selectInfo2(user));
    }

    @org.junit.Test
    public void searchProfilee() {
        User user111=new User();
        user111.setUserEmail("3403784285@qq.com");
        User user=  ConnectSql.searchProfilee(user111);

    }






}