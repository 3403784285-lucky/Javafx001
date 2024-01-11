package utils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexEmail
{
    private static String email;
    private static String password;

    public RegexEmail(String email,String password) {
        this.email=email;
        this.password=password;

    }

    public boolean regexEmail()
    {
        Pattern pp=Pattern.compile("\\d{10}@qq\\.com");
        Matcher m=pp.matcher(this.email);
        boolean mm=m.matches();
        return mm;
    }
    public boolean regexPassword()
    {
        Pattern pp=Pattern.compile("^.*(?=.{6,})(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*? ]).*$");
        Matcher m=pp.matcher( this.password);
        boolean mm=m.matches();
        return mm;
    }

}