package utils;
import company.ConnectSql;
public class GetMyAccount {
    private String  account;
    private boolean isRunning=true;
    public String getMyAccount() {
        System.out.println("给爷试试");
        while (isRunning) {
            String array = new SnowFlakeId(0).nextId() + "";
            System.out.println("想死了");
            account = array.substring(0, 10);
            System.out.println(account);
            if (ConnectSql.selectAccount(account));
            else isRunning = false;
            System.out.println("我就喜欢搜");
        }

        return account;

    }
    public String getAccountGroup() {
        while (isRunning) {
            String array = new SnowFlakeId(0).nextId() + "";
            System.out.println("想死了");
            account = "G"+array.substring(0, 10);
            System.out.println(account);
            if (ConnectSql.selectGroupAccount(account)) ;
            else isRunning = false;
            System.out.println("我就喜欢搜");
        }
        return account;
    }

}
