package utils;
import company.ConnectSql;
public class GetMyAccount {
    private String  account;
    private boolean isRunning=true;
    public String getMyAccount() {
        System.out.println("��ү����");
        while (isRunning) {
            String array = new SnowFlakeId(0).nextId() + "";
            System.out.println("������");
            account = array.substring(0, 10);
            System.out.println(account);
            if (ConnectSql.selectAccount(account));
            else isRunning = false;
            System.out.println("�Ҿ�ϲ����");
        }

        return account;

    }
    public String getAccountGroup() {
        while (isRunning) {
            String array = new SnowFlakeId(0).nextId() + "";
            System.out.println("������");
            account = "G"+array.substring(0, 10);
            System.out.println(account);
            if (ConnectSql.selectGroupAccount(account)) ;
            else isRunning = false;
            System.out.println("�Ҿ�ϲ����");
        }
        return account;
    }

}
