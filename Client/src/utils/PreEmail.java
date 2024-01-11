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
        // ����Properties �����ڼ�¼�����һЩ����
        Properties props = new Properties();
        // ��ʾSMTP�����ʼ���������������֤
        props.put("mail.smtp.auth", "true");
        //�˴���дSMTP������
        props.put("mail.smtp.host", "smtp.qq.com");
        //�˿ںţ�QQ����˿�587
        props.put("mail.smtp.port", "587");
        // �˴���д��д���˵��˺�
        props.put("mail.user", "3458821194@qq.com");
        // �˴���д16λSTMP����
        props.put("mail.password", "qqlkremqmdtmdbaj");

        // ������Ȩ��Ϣ�����ڽ���SMTP���������֤
        Authenticator authenticator = new Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                // �û���������
                String userName = props.getProperty("mail.user");
                String password = props.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };
        // ʹ�û������Ժ���Ȩ��Ϣ�������ʼ��Ự
        Session mailSession = Session.getInstance(props, authenticator);
        // �����ʼ���Ϣ
        MimeMessage message = new MimeMessage(mailSession);
        // ���÷�����
        InternetAddress form = null;
        try {
            form = new InternetAddress(props.getProperty("mail.user"));
            message.setFrom(form);

            // �����ռ��˵�����
            InternetAddress to = new InternetAddress(toServer);
            message.setRecipient(RecipientType.TO, to);

            // �����ʼ�����
            message.setSubject("С����������");

            // �����ʼ���������

            if(LoginController.logining)
            {
                message.setContent("�װ������û����ã���ӭ��¼����С���������ң���֤��Ϊ"+ConfirmPassword+"�����ｫ��������벻���ľ�ϲŶ������������֤��Ͻ���Ӱɣ�����", "text/html;charset=UTF-8");
            }
            else
            {
                if(!LoginController.variable)
                    message.setContent("�װ������û����ã���ӭע������С���������ң���⵽������ע�ᣬ��֤��Ϊ"+ConfirmPassword+"�����ｫ��������벻���ľ�ϲŶ������������֤��Ͻ���Ӱɣ�����", "text/html;charset=UTF-8");
                if(LoginController.variable)
                    message.setContent("�װ����û����ã�С������⵽�������޸����룬��֤��Ϊ"+ConfirmPassword+"����������ʱ��������������Ŷ��", "text/html;charset=UTF-8");
            }


            // ���Ȼ���Ƿ����ʼ���
            send(message);
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }

}
