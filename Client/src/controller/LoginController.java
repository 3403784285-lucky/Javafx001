package controller;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import request.UserClientService;
import utils.Md5Code;
import utils.PreEmail;
import utils.RegexEmail;
import view.ChatRoomView;
import view.LoginView;
import view.RegisterView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginController implements Initializable {
    public static boolean variable=false;

    @FXML
    private Text password;

    @FXML
    private TextField textAccount;

    @FXML
    private PasswordField textPassword;

    @FXML
    private Text forgetPassword;

    @FXML
    private Text loginBig;

    @FXML
    private Button login;

    @FXML
    private Text account;

    @FXML
    private Button register;

    @FXML
    private Button identifyingcode;

    @FXML
    private TextField hideText;



    private static String verificationPswd;

    public  static boolean logining;

    public static boolean yesorno;

    private static String reEmail;

    @FXML
    private ImageView convert2;

    @FXML
    private ImageView convert1;





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        identifyingcode.setDisable(true);
        logining=false;
        convert2.setDisable(false);
        convert1.setDisable(true);
        convert1.setVisible(false);
        hideText.setDisable(true);
        hideText.setVisible(false);
        textPassword.setDisable(false);
        textPassword.setVisible(true);



    }
    @FXML
    void convert1Action(MouseEvent event) {

        hideText.setDisable(true);
        textPassword.setDisable(false);
        textPassword.setText(hideText.getText());
        convert2.setDisable(false);
        convert1.setDisable(true);
        convert1.setVisible(false);
        hideText.setVisible(false);
        textPassword.setVisible(true);

    }

    @FXML
    void convert2Action(MouseEvent event) {
        hideText.setDisable(false);
        textPassword.setDisable(true);
        hideText.setText(textPassword.getText());
        convert1.setDisable(false);
        convert1.setVisible(true);
        convert2.setDisable(true);
        hideText.setVisible(true);
        textPassword.setVisible(false);
    }



    @FXML
    void identifyingAction(ActionEvent event) {
        RegexEmail pp=new RegexEmail(textAccount.getText(),"0");
        logining=true;
        if(pp.regexEmail())
        {
            reEmail=textAccount.getText();

            PreEmail p=new PreEmail(reEmail);
            verificationPswd=p.getConfirmPassword();
            p.getEmail();
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("小花花聊天室");
            alert.setHeaderText("邮箱格式输入正确,验证码有效期只有60秒哦，六十秒之后可以继续发送验证码");
            alert.setContentText("快些去您的邮箱查看验证码吧，小花花已经等不及要和您邂逅了呢！！");
            alert.show();
            new Thread(()->
        {
            identifyingcode.setDisable(true);
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("客户端线程延迟错误----");
            }
            verificationPswd="0";
            identifyingcode.setDisable(false);

        }).start();

        }
        else
        {
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("小花花聊天室");
            alert.setHeaderText("请再次检查您的邮箱格式");
            alert.setContentText("邮箱输入格式错误！");
            alert.show();
        }
        logining=false;



    }
    @FXML
    void loginEnter(ActionEvent event) {

        if (!convert1.isDisable())//以防由于眼睛图标的切换而导致的输入不属实
        {
            textPassword.setText(hideText.getText());
        }
        if (textPassword.getText().equals("") || textAccount.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("小花花聊天室");
            alert.setHeaderText("登陆失败，账号密码不能为空");
            alert.setContentText("快去重新登录吧");
            alert.show();
        }
        else if (!yesorno) {

            if (new UserClientService().checkUserLoginAccount(textAccount.getText(), new Md5Code(textPassword.getText()).getMd5Password())) {

                Runnable showDialog = new Runnable() {
                    @Override
                    public void run() {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("小花花聊天室");
                        alert.setHeaderText("登陆成功，欢迎回来");
                        alert.setContentText("");
                        alert.show();
                    }
                };
                Platform.runLater(showDialog);

                LoginView.loginStage.close();
                try {
                    ChatRoomView.start(new Stage());
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("页面加载错误");
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("小花花聊天室");
                alert.setHeaderText("登陆失败，账号或密码错误");
                alert.setContentText("快去重新登录吧");
                alert.show();
            }
        } else {

            if (reEmail.equals(textAccount.getText())) {
                if (textPassword.getText().equals(verificationPswd)) {


                    if (new UserClientService().checkUserLoginEmail(textAccount.getText()))//检查邮箱是否有；
                    {
                        Runnable showDialog = new Runnable() {
                            @Override
                            public void run() {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("小花花聊天室");
                                alert.setHeaderText("登陆成功，欢迎回来");
                                alert.setContentText("");
                                alert.show();
                            }
                        };
                        Platform.runLater(showDialog);
                        LoginView.loginStage.close();
                        try {
                            ChatRoomView.start(new Stage());
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("界面加载错误");
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("小花花聊天室");
                        alert.setHeaderText("登陆失败，邮箱输入错误");
                        alert.setContentText("分析原因如下：您是新用户，请去注册界面先注册");
                        alert.show();

                    }


                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("小花花聊天室");
                    alert.setHeaderText("登陆失败，验证码错误");
                    alert.setContentText("");
                    alert.show();
                }


            } else {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("小花花聊天室");
                alert.setHeaderText("登陆失败，邮箱和验证码不匹配");
                alert.setContentText("");
                alert.show();
            }
            if (!new RegexEmail(textAccount.getText(), "0").regexEmail()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("小花花聊天室");
                alert.setHeaderText("请再次检查您的邮箱格式");
                alert.setContentText("邮箱输入格式错误！");
                alert.show();
            }


        }
    }
    @FXML
    void forgetPasswordAction(MouseEvent event) throws IOException {
        variable=true;
        LoginView.loginStage.close();
        RegisterView.start(new Stage());


    }

    @FXML
    void registerEnter(ActionEvent event) throws IOException {
        variable=false;
      LoginView.loginStage.close();
      RegisterView.start(new Stage());

    }

    @FXML
    void passwordReleased(KeyEvent event) {

    }

    @FXML
    void accountReleased(KeyEvent event) {
        Pattern pp=Pattern.compile("@qq\\.com");
        Matcher m=pp.matcher(textAccount.getText());
        yesorno=m.find();
         if(yesorno)identifyingcode.setDisable(false);
         else identifyingcode.setDisable(true);


    }
    @FXML
    void quitAction(ActionEvent event) {
        System.exit(0);

    }


    @FXML
    void accountAction(ActionEvent event) {

    }

    @FXML
    void passwordAction(ActionEvent event) {

    }


}
