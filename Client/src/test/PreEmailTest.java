package test;

import utils.PreEmail;

import static org.junit.Assert.*;


/**
 * @author ’≈≈‡¡È
 *
 * ≤‚ ‘∑¢ÀÕ” œ‰
 */
public class PreEmailTest {

   PreEmail preEmail=new PreEmail("3403784285@qq.com");

    @org.junit.Test
    public void getEmail() {
      preEmail.getEmail();
    }
}