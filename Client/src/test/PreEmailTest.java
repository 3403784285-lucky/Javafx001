package test;

import utils.PreEmail;

import static org.junit.Assert.*;


/**
 * @author 张培灵
 * @date 2024/01/09
 * 测试发送邮箱
 */
public class PreEmailTest {

   PreEmail preEmail=new PreEmail("3403784285@qq.com");

    @org.junit.Test
    public void getEmail() {
      preEmail.getEmail();
    }
}