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
            alert.setTitle("С����������");
            alert.setHeaderText("�����ʽ������ȷ,��֤����Ч��ֻ��60��Ŷ����ʮ��֮����Լ���������֤��");
            alert.setContentText("��Щȥ��������鿴��֤��ɣ�С�����Ѿ��Ȳ���Ҫ�����������أ���");
            alert.show();
            new Thread(()->
        {
            identifyingcode.setDisable(true);
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("�ͻ����߳��ӳٴ���----");
            }
            verificationPswd="0";
            identifyingcode.setDisable(false);

        }).start();

        }
        else
        {
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("С����������");
            alert.setHeaderText("���ٴμ�����������ʽ");
            alert.setContentText("���������ʽ����");
            alert.show();
        }
        logining=false;



    }
    @FXML
    void loginEnter(ActionEvent event) {

        if (!convert1.isDisable())//�Է������۾�ͼ����л������µ����벻��ʵ
        {
            textPassword.setText(hideText.getText());
        }
        if (textPassword.getText().equals("") || textAccount.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("С����������");
            alert.setHeaderText("��½ʧ�ܣ��˺����벻��Ϊ��");
            alert.setContentText("��ȥ���µ�¼��");
            alert.show();
        }
        else if (!yesorno) {

            if (new UserClientService().checkUserLoginAccount(textAccount.getText(), new Md5Code(textPassword.getText()).getMd5Password())) {

                Runnable showDialog = new Runnable() {
                    @Override
                    public void run() {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("С����������");
                        alert.setHeaderText("��½�ɹ�����ӭ����");
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
                    System.out.println("ҳ����ش���");
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("С����������");
                alert.setHeaderText("��½ʧ�ܣ��˺Ż��������");
                alert.setContentText("��ȥ���µ�¼��");
                alert.show();
            }
        } else {

            if (reEmail.equals(textAccount.getText())) {
                if (textPassword.getText().equals(verificationPswd)) {


                    if (new UserClientService().checkUserLoginEmail(textAccount.getText()))//��������Ƿ��У�
                    {
                        Runnable showDialog = new Runnable() {
                            @Override
                            public void run() {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("С����������");
                                alert.setHeaderText("��½�ɹ�����ӭ����");
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
                            System.out.println("������ش���");
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("С����������");
                        alert.setHeaderText("��½ʧ�ܣ������������");
                        alert.setContentText("����ԭ�����£��������û�����ȥע�������ע��");
                        alert.show();

                    }


                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("С����������");
                    alert.setHeaderText("��½ʧ�ܣ���֤�����");
                    alert.setContentText("");
                    alert.show();
                }


            } else {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("С����������");
                alert.setHeaderText("��½ʧ�ܣ��������֤�벻ƥ��");
                alert.setContentText("");
                alert.show();
            }
            if (!new RegexEmail(textAccount.getText(), "0").regexEmail()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("С����������");
                alert.setHeaderText("���ٴμ�����������ʽ");
                alert.setContentText("���������ʽ����");
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
