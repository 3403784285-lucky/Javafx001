package controller;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import request.MessageClientService;
import request.UserClientService;
import utils.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import view.ChatRoomView;
import view.LoginView;
import view.RegisterView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import static controller.LoginController.variable;

public class RegisterController implements Initializable {
    private boolean flag1=false;
    private boolean flag2=false;

    @FXML
    private PasswordField confirmPassword1;

    @FXML
    private  TextField email1;

    @FXML
    private ImageView reddot;

    @FXML
    private Button registerButton;

    @FXML
    private Button modify;

    @FXML
    private PasswordField password1;

    @FXML
    private Button button1;

    @FXML
    private Button return1;

    @FXML
    private TextField verificationCode1;
    @FXML
    private Text hello;

    @FXML
    private ImageView convert2;

    @FXML
    private ImageView convert1;


    private String Remail;


    @FXML
    private TextField hideText;

    private String verificationPswd;

    @FXML
    void return1Action(ActionEvent event) {
        RegisterView.registerStage.close();
        try {
            LoginView.start(new Stage());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("����򿪴���");
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        registerButton.setDisable(true);
        modify.setDisable(true);
        modify.setVisible(false);
        registerButton.setVisible(false);
        hello.setVisible(false);


        if(variable)
        {
            hello.setVisible(false);
            registerButton.setVisible(false);
            modify.setVisible(true);

        }
        else
        {
            hello.setVisible(true);
            registerButton.setVisible(true);
            modify.setVisible(false);

        }
        convert2.setDisable(false);
        convert1.setDisable(true);
        convert1.setVisible(false);
        hideText.setDisable(true);
        hideText.setVisible(false);
        password1.setDisable(false);
        password1.setVisible(true);

    }

    @FXML
    void convert2Action(MouseEvent event) {
        hideText.setDisable(false);
        password1.setDisable(true);
        hideText.setText(password1.getText());
        convert1.setDisable(false);
        convert1.setVisible(true);
        convert2.setDisable(true);
        hideText.setVisible(true);
        password1.setVisible(false);

    }

    @FXML
    void convert1Action(MouseEvent event) {
        hideText.setDisable(true);
        password1.setDisable(false);
        password1.setText(hideText.getText());
        convert2.setDisable(false);
        convert1.setDisable(true);
        convert1.setVisible(false);
        hideText.setVisible(false);
        password1.setVisible(true);

    }

    @FXML
    void modifyAction(ActionEvent event) {


        if (Remail.equals(email1.getText())) {
            if(verificationPswd.equals(verificationCode1.getText()))
            {
                if(new UserClientService().checkUserModifyPassword(email1.getText(),new Md5Code(password1.getText()).getMd5Password()))
                {

                   if(!new MessageClientService().isExistOnly(ManageClientToThread.u.getUserAccount()))
                   {


                    RegisterView.registerStage.close();
                    try {
                        ChatRoomView.start(new Stage());
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("�����ʧ��");
                    }

                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("С����������");
                            alert.setHeaderText("�����޸ĳɹ�����ȥ��¼��");
                            alert.setContentText("");
                            alert.show();

                    }
                   else
                   {
                       RegisterView.registerStage.close();
                       try {
                           LoginView.start(new Stage());
                       } catch (IOException e) {
                           e.printStackTrace();
                           System.out.println("�����ʧ��");
                       }

                       Alert alert = new Alert(Alert.AlertType.INFORMATION);
                       alert.setTitle("С����������");
                       alert.setHeaderText("�����޸ĳɹ�");
                       alert.setContentText("");
                       alert.show();

                   }


                }

                else
                {  RegisterView.registerStage.close();
                    try {
                        LoginView.start(new Stage());
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("ҳ����ش���---ע��֮��");
                    }
                    Runnable showDialog = new Runnable() {
                    @Override
                    public void run() {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("С����������");
                        alert.setHeaderText("�������û�Ŷ������ȥע���");
                        alert.setContentText("");
                        alert.show();
                    }
                };

                }



            }
            else
            {
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("С����������");
                alert.setHeaderText("��֤�����");
                alert.setContentText("������֤���Ƿ���ڻ�����֤�����");
                alert.show();
            }
        }

        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("С����������");
            alert.setHeaderText("��½ʧ�ܣ��������֤�벻ƥ��");
            alert.setContentText("");
            alert.show();
        }

      }

    @FXML
    void OnKeyReleased(KeyEvent event) {

            RegexEmail pp=new RegexEmail("0",password1.getText());
            boolean mm=pp.regexPassword();
        if(confirmPassword1.getText().equals(password1.getText())&&password1.getText()!="")flag1=true;
        else flag1=false;
        if(flag1&&flag2){if (!variable)registerButton.setDisable(false);
            if(variable)modify.setDisable(false);}
        else
        {
            registerButton.setDisable(true);modify.setDisable(true);
        }

                if(mm)
                {

                    reddot.setVisible(false);
                    confirmPassword1.setEditable(true);
                }
                else
                {
                    reddot.setVisible(true);
                    confirmPassword1.setEditable(false);
                }


    }
    @FXML
    void hideReleased(KeyEvent event) {
        RegexEmail pp=new RegexEmail("0",password1.getText());
        boolean mm=pp.regexPassword();
        if (!convert1.isDisable()) {
            password1.setText(hideText.getText());
        }
        System.out.println(password1.getText()+"----------");
        if(confirmPassword1.getText().equals(password1.getText())&&password1.getText()!="")flag1=true;
        else flag1=false;
        if(flag1&&flag2){if (!variable)registerButton.setDisable(false);
            if(variable)modify.setDisable(false);}
        else
        {
            registerButton.setDisable(true);modify.setDisable(true);
        }

        if(mm)
        {

            reddot.setVisible(false);
            confirmPassword1.setEditable(true);
        }
        else
        {
            reddot.setVisible(true);
            confirmPassword1.setEditable(false);
        }


    }


    @FXML
    void OnKeyPswdCom(KeyEvent event) {
        if(!convert1.isDisable())
        {
            password1.setText(hideText.getText());
        }

        if(confirmPassword1.getText().equals(password1.getText())&&password1.getText()!="")flag1=true;
        else flag1=false;
        if(flag1&&flag2){if (!variable)registerButton.setDisable(false);
             if(variable)modify.setDisable(false);}
        else
        {
            registerButton.setDisable(true);modify.setDisable(true);
        }


    }
    @FXML
    void ConfirmReleased(KeyEvent event) {


        if(verificationCode1.getText().equals(verificationPswd)&&Remail.equals(email1.getText()))flag2=true;
        else flag2=false;
        if(flag1&&flag2){if (!variable)registerButton.setDisable(false);
            if(variable)modify.setDisable(false);}
        else

        {
            registerButton.setDisable(true);modify.setDisable(true);
        }



    }

    @FXML
    void Register(ActionEvent event) throws IOException {

         LoginController.yesorno=true;
        if (Remail.equals(email1.getText())) {
            if(verificationPswd.equals(verificationCode1.getText()))
            {
                if(new UserClientService().checkUserRegister(email1.getText(),new Md5Code(password1.getText()).getMd5Password()))
                {

                    Runnable showDialog = new Runnable() {
                        @Override
                        public void run() {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("С����������");
                            alert.setHeaderText("ע��ɹ�����������Ͱ�һ����ˣ��");
                            alert.setContentText("");
                            alert.show();
                        }
                    };
                    Platform.runLater(showDialog);


                    RegisterView.registerStage.close();
                    ChatRoomView.start(new Stage());
                }
                else
                {



                    Runnable showDialog = new Runnable() {
                        @Override
                        public void run() {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("С����������");
                            alert.setHeaderText("���������û���");
                            alert.setContentText("���ǻ�ӭ�����������������޸�");
                            alert.show();
                        }
                    };
                    Platform.runLater(showDialog);

                    RegisterView.registerStage.close();
                    ChatRoomView.start(new Stage());

                }

            }
                else
                {
                    Alert alert=new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("С����������");
                    alert.setHeaderText("��֤�����");
                    alert.setContentText("������֤���Ƿ���ڻ�����֤�����");
                    alert.show();
                }
            }

        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("С����������");
            alert.setHeaderText("��½ʧ�ܣ��������֤�벻ƥ��");
            alert.setContentText("");
            alert.show();
        }


        //Ҫ���

    }




    @FXML
    void getButton1(ActionEvent event)  {
       RegexEmail pp=new RegexEmail(email1.getText(),"0");
       LoginController.logining=false;
       if(pp.regexEmail())
       {
           Remail=email1.getText();
           PreEmail p=new PreEmail(Remail);
           verificationPswd=p.getConfirmPassword();
           p.getEmail();
           Alert alert=new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("С����������");
           alert.setHeaderText("�����ʽ������ȷ");
           alert.setContentText("��Щȥ��������鿴��֤��ɣ���֤����Ч��ֻ��60��Ŷ��60���������·��ͣ�С�����Ѿ��Ȳ���Ҫ�����������أ���");
           alert.show();
           new Thread(()->
       {
           button1.setDisable(true);
           try {
               Thread.sleep(60000);
           } catch (InterruptedException e) {
               e.printStackTrace();
               System.out.println("�ͻ����߳��ӳٴ���2----");
           }
           verificationPswd="0";
           button1.setDisable(false);

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


    }


}
