package utils;
import controller.LoginController;

import java.util.Properties;
import java.util.Random;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import static javax.mail.Transport.send;


public class PreEmail
{

    private String toServer;
    private static String ConfirmPassword;


    public PreEmail(String toServer)
    {
        this.toServer = toServer;
        ConfirmPassword=verificationCode()+"";
    }

    public int verificationCode()
    {

        int r= (int)((Math.random()*9+1)*1000);
        return r;
    }

    public String getConfirmPassword() {
        return ConfirmPassword;
    }

    public void getEmail()  {
        // 创建Properties 类用于记录邮箱的一些属性
        Properties props = new Properties();
        // 表示SMTP发送邮件，必须进行身份验证
        props.put("mail.smtp.auth", "true");
        //此处填写SMTP服务器
        props.put("mail.smtp.host", "smtp.qq.com");
        //端口号，QQ邮箱端口587
        props.put("mail.smtp.port", "587");
        // 此处填写，写信人的账号
        props.put("mail.user", "3458821194@qq.com");
        // 此处填写16位STMP口令
        props.put("mail.password", "qqlkremqmdtmdbaj");

        // 构建授权信息，用于进行SMTP进行身份验证
        Authenticator authenticator = new Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名、密码
                String userName = props.getProperty("mail.user");
                String password = props.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };
        // 使用环境属性和授权信息，创建邮件会话
        Session mailSession = Session.getInstance(props, authenticator);
        // 创建邮件消息
        MimeMessage message = new MimeMessage(mailSession);
        // 设置发件人
        InternetAddress form = null;
        try {
            form = new InternetAddress(props.getProperty("mail.user"));
            message.setFrom(form);

            // 设置收件人的邮箱
            InternetAddress to = new InternetAddress(toServer);
            message.setRecipient(RecipientType.TO, to);

            // 设置邮件标题
            message.setSubject("小花花聊天室");

            // 设置邮件的内容体

            if(LoginController.logining)
            {
                message.setContent("亲爱的新用户您好，欢迎登录我们小花花聊天室，验证码为"+ConfirmPassword+"，这里将会给您意想不到的惊喜哦，填下您的验证码赶紧入坑吧！！！", "text/html;charset=UTF-8");
            }
            else
            {
                if(!LoginController.variable)
                    message.setContent("亲爱的新用户您好，欢迎注册我们小花花聊天室，检测到您正在注册，验证码为"+ConfirmPassword+"，这里将会给您意想不到的惊喜哦，填下您的验证码赶紧入坑吧！！！", "text/html;charset=UTF-8");
                if(LoginController.variable)
                    message.setContent("亲爱的用户您好，小花花检测到你正在修改密码，验证码为"+ConfirmPassword+"，有问题随时可以向咱们求助哦！", "text/html;charset=UTF-8");
            }


            // 最后当然就是发送邮件啦
            send(message);
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }

}
