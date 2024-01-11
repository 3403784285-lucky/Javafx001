package loare;

import company.ConnectSql;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

public class DealWithRegister {

//    public static String  dealWithRegister(String password1,String email1)  {
//
//
//
//        String password= new Md5Code(password1).getMd5Password();
//        SnowFlakeId id=new SnowFlakeId(0);
//        String iid=(id.nextId()+"");
//        String trueid=iid.substring(0,10);
//        try {
//           return ConnectSql.storeInfo(trueid,password,email1);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//
//    }
}
