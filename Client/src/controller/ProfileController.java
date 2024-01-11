package controller;

import common.*;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import request.UserClientService;
import utils.ManageClientToThread;
import view.ChatRoomView;
import view.GroupProfileView;
import view.ProFileView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import static controller.ChatRoomController.getMy;


public class ProfileController implements Initializable {

    @FXML
    private Text birthday;

    @FXML
    private Text gender;

    @FXML
    private TextArea signature;

    @FXML
    private ImageView image;

    @FXML
    private Button back;

    @FXML
    private Text name;
    @FXML
    private Button addgf;


    @FXML
    private Button delete;

    @FXML
    private Button approvalGroup;

    @FXML
    private Text onlineStatus;
    @FXML
    private Text group;


    @FXML
    private Button approval;

    @FXML
    private Text age;

    @FXML
    private Text account;

    private Friend selectedFriend;
    @FXML
    private Button refuseGroup;
    @FXML
    private Button refuse;

    public Friend getSelectedFriend() {
        return selectedFriend;
    }

    public void setSelectedFriend(Friend selectedFriend) {
        this.selectedFriend = selectedFriend;
    }

    @FXML
    void refuseAction(ActionEvent event) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // 耗时操作
                new UserClientService().refuseFriend(account.getText() + "&" + ManageClientToThread.u.getUserAccount());
                ProFileView.profileStage.close();
                ChatRoomView.start(new Stage());
                return null;
            }
        };

        task.setOnSucceeded(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("小花花聊天室");
            alert.setHeaderText("您已拒绝对方好友申请");
            alert.setContentText("");
            alert.show();
        });

        // 启动任务
        new Thread(task).start();
    }
    @FXML
    void refuseGroupAction(ActionEvent event) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // 耗时操作
                String []a=getMy.getGroupAccount().split("&");
                String accountg=a[1];
                new UserClientService().refuseGroup(accountg+"&"+account.getText());
                return null;
            }
        };

        task.setOnSucceeded(ee -> {
            try {
                GroupProfileView.groupProfileStage.close();
            ChatRoomView.start(new Stage());
        } catch (IOException e) {
            e.printStackTrace();
        }
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("小花花聊天室");
            alert.setHeaderText("您已拒绝对方群聊申请");
            alert.setContentText("");//这里还有一个bug就是关于为空
            alert.show();

        });

        // 启动任务
        new Thread(task).start();


    }
    @FXML
    void application(ActionEvent event) {
    if(new UserClientService().fgSearch(account.getText()))
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("小花花聊天室");
        alert.setHeaderText("申请成功，等待好友同意");
        alert.setContentText("");
        alert.show();
        new UserClientService().fgApply(account.getText());
        addgf.setDisable(true);
    }
    else
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("小花花聊天室");
        alert.setHeaderText("该好友您已经申请过咯，等待她的同意吧，莫心急");
        alert.setContentText("");
        alert.show();
    }
    }
    @FXML
    void approvalAction(ActionEvent event) {
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    // 耗时操作
                    new UserClientService().approvalAPP(ManageClientToThread.u.getUserAccount(),account.getText());
                    return null;
                }
            };

            task.setOnSucceeded(ee -> {
                ProFileView.profileStage.close();
                try {
                    ChatRoomView.start(new Stage());
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("界面打开失败");
                }
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("小花花聊天室");
                alert.setHeaderText("您已同意申请，你们已经是好友了");
                alert.setContentText("快去聊天吧");//这里还有一个bug就是关于为空
                alert.show();
            });
            // 启动任务
            new Thread(task).start();


    }

    @FXML
    void deleteAction(ActionEvent event) {

            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    // 耗时操作
                    new UserClientService().deleteFriend(account.getText()+"&"+ManageClientToThread.u.getUserAccount());
                    return null;
                }
            };

            task.setOnSucceeded(ee -> {

                ProFileView.profileStage.close();
                try {
                    ChatRoomView.start(new Stage());
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("界面打开失败");
                }
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("小花花聊天室");
                alert.setHeaderText("好友删除成功");
                alert.setContentText("");//这里还有一个bug就是关于为空
                alert.show();
            });

            // 启动任务
            new Thread(task).start();



    }
    @FXML
    void approvalGroupAction(ActionEvent event) {

            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    // 耗时操作
                    String []a=getMy.getGroupAccount().split("&");
                    String accountg=a[1];
                    new UserClientService().approvalGroup(accountg+"&"+account.getText());
                    return null;
                }
            };

            task.setOnSucceeded(ee -> {

                ProFileView.profileStage.close();
                try {
                    ChatRoomView.start(new Stage());
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("界面打开失败");
                }
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("小花花聊天室");
                alert.setHeaderText("已同意加入群聊请求");
                alert.setContentText("");//这里还有一个bug就是关于为空
                alert.show();
            });

            // 启动任务
            new Thread(task).start();


    }

    @FXML
    void backAction(ActionEvent event) {
        ProFileView.profileStage.close();
        try {
            ChatRoomView.start(new Stage());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("界面打开失败");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        group.setVisible(false);
        refuseGroup.setVisible(false);
        refuseGroup.setDisable(true);
        approvalGroup.setDisable(true);
        approvalGroup.setVisible(false);
        if(getMy.getGroupAccount()!=null)
        {
            group.setVisible(true);
            group.setText("该用户申请加入群聊"+getMy.getGroupAccount());
            refuseGroup.setDisable(false);
            refuseGroup.setVisible(true);
            approvalGroup.setDisable(false);
            approvalGroup.setVisible(true);
        }

        String tt=new UserClientService().typeJudge(getMy.getAccount(),ManageClientToThread.u.getUserAccount());
        System.out.println(tt+"-----------受不了一点");

        if(ManageClientToThread.u.getUserAccount().equals(getMy.getAccount())) {
            delete.setDisable(true);
            delete.setVisible(false);
            addgf.setDisable(true);
            addgf.setVisible(false);
            approval.setDisable(true);
            approval.setVisible(false);
            refuse.setDisable(true);
            refuse.setVisible(false);
        }
        else
        {
            if(tt.equals(MessageType.MESSAGE_FRIEND_APP))
            {
                System.out.println("我是被申请");
                delete.setDisable(true);
                delete.setVisible(false);
                addgf.setDisable(true);
                addgf.setVisible(false);
                approval.setDisable(false);
                approval.setVisible(true);
                refuse.setDisable(false);
                refuse.setVisible(true);
            }
            else if(tt.equals(MessageType.MESSAGE_FRIEND_FRI))
            {
                System.out.println("我是好友");
                delete.setDisable(false);
                delete.setVisible(true);
                addgf.setDisable(true);
                addgf.setVisible(false);
                approval.setDisable(true);
                approval.setVisible(false);
                refuse.setDisable(true);
                refuse.setVisible(false);
            }
            else if(tt.equals(MessageType.MESSAGE_FRIEND_STRANGER))
            {
                System.out.println("我是陌生人");
                delete.setDisable(true);
                delete.setVisible(false);
                addgf.setDisable(false);
                addgf.setVisible(true);
                approval.setDisable(true);
                approval.setVisible(false);
                refuse.setDisable(true);
                refuse.setVisible(false);
            }

        }

        UserCopy uu=new UserClientService().applyInfo(getMy.getAccount());

        birthday.setText(uu.getUserBirthday().toString());
        account.setText(uu.getUserAccount());
        age.setText(String.valueOf(Period.between((uu.getUserBirthday().toLocalDate()), LocalDate.now()).getYears()));
        name.setText(uu.getUserName());
        signature.setText(uu.getUserSignature());
        gender.setText(uu.isGender());
        if(uu.isOnline())
            onlineStatus.setText("在线");
        else onlineStatus.setText("离线");
        image.setImage( new Image(new File(uu.getUserImage()).toURI().toString()));
    }
}
