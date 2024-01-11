package controller;
import common.Friend;
import common.GroupCommon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import request.UserClientService;
import utils.ManageClientToThread;
import view.ChatRoomView;
import view.GroupMemberView;
import view.GroupProfileView;
import view.ModifyView;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import static controller.ChatRoomController.getMy;
/**
 * ProfileGroupController 处理群组资料视图的功能和交互。
 *
 * 该控制器管理与群组资料操作相关的各种操作，如申请加入群组、退出群组、添加或删除成员、更改群组信息等。
 *
 * @author 张培灵
 * @version 1.0
 * @since 2024.1.9
 */
public class ProfileGroupController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        quitGroup.setVisible(false);
        quitGroup.setDisable(true);
        substractMember.setVisible(false);
        substractMember.setDisable(true);
        addMember.setVisible(false);
        addMember.setDisable(true);
        applyGroup.setDisable(false);
        applyGroup.setVisible(true);
        leaveGroup.setVisible(true);
        leaveGroup.setDisable(false);
        imageGroup.setDisable(true);
        groupAccount.setEditable(false);
        groupName.setEditable(false);
        groupSignature.setEditable(false);
        setManager.setVisible(false);
        setManager.setDisable(true);
        transferGroup.setDisable(true);
        transferGroup.setVisible(false);
        GroupCommon gg=new UserClientService().profileGroup(getMy.getAccount());
        gc=gg;
        imageGroup.setImage(new Image(new File(gg.getImageGroup()).toURI().toString()));
        groupAccount.setText(gg.getGroupAccount());
        groupName.setText(gg.getGroupName());
        groupSignature.setText(gg.getSignature());
        if(ManageClientToThread.u.getUserAccount().equals(gg.getOwnerAccount()))
        {
            applyGroup.setDisable(true);
            applyGroup.setVisible(false);
            imageGroup.setDisable(false);
            groupName.setEditable(true);
            groupSignature.setEditable(true);
            quitGroup.setVisible(true);
            quitGroup.setDisable(false);
            substractMember.setVisible(true);
            substractMember.setDisable(false);
            addMember.setVisible(true);
            addMember.setDisable(false);
            setManager.setVisible(true);
            setManager.setDisable(false);
            transferGroup.setVisible(true);
            transferGroup.setDisable(false);



        }
        else
        {
            if(ManageClientToThread.u.getUserAccount().equals(gg.getManagerAccount()))
            {
                applyGroup.setDisable(true);
                applyGroup.setVisible(false);
                imageGroup.setDisable(false);
                groupName.setEditable(true);
                groupSignature.setEditable(true);
                substractMember.setVisible(true);
                substractMember.setDisable(false);
                addMember.setVisible(true);
                addMember.setDisable(false);

            }
            else
            {
                if(new UserClientService().judgeMember(gg.getGroupAccount()+"&"+ManageClientToThread.u.getUserAccount()))
                {
                   //改了一下
                    System.out.println("咱就是说这里是群成员");
                    applyGroup.setDisable(true);
                    applyGroup.setVisible(false);

                }
                else
                {
                    System.out.println("这里不是群成员");
                    leaveGroup.setVisible(false);
                    leaveGroup.setDisable(true);

                }
            }

        }



    }
    public static boolean addPerson;

    @FXML
    private Button applyGroup;
    @FXML
    private Button transferGroup;

    @FXML
    private ImageView imageGroup;

    @FXML
    private TextField groupAccount;

    @FXML
    private Button addMember;

    @FXML
    private TextField groupName;

    @FXML
    private TextArea groupSignature;

    @FXML
    private Button leaveGroup;
    public static GroupCommon gc;

    @FXML
    private Button quitGroup;
    @FXML
    private Button setManager;



    @FXML
    private Button substractMember;
    public static boolean setOr;
    public static boolean transferOr;


    public static boolean isAddPerson() {
        return addPerson;
    }
    @FXML
    void transferGroupAction(ActionEvent event) {
        transferOr=true;
        GroupProfileView.groupProfileStage.close();
        try {
           GroupMemberView.start(new Stage());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @FXML
    void applyGroupAction(ActionEvent event) {
        Friend f=new Friend(ManageClientToThread.u.getUserAccount(),ManageClientToThread.u.getUserImage(),ManageClientToThread.u.getUserName());
        f.setGroupAccount(groupAccount.getText());
        new UserClientService().applyGroup(f);
        GroupProfileView.groupProfileStage.close();

        try {
            ChatRoomView.start(new Stage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("小花花聊天室");
        alert.setHeaderText("已申请加入群聊");
        alert.show();
    }

    @FXML
    void addMemberAction(ActionEvent event) {
        addPerson=true;
        GroupProfileView.groupProfileStage.close();
        try {
            GroupMemberView.start(new Stage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("小花花聊天室");
        alert.setHeaderText("成功添加群成员");
        alert.show();
    }

    @FXML
    void substractMemberAction(ActionEvent event) {
        addPerson = false;
        GroupProfileView.groupProfileStage.close();
        try {
            GroupMemberView.start(new Stage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("小花花聊天室");
        alert.setHeaderText("成功踢出群成员");
        alert.show();


    }

    @FXML
    void leaveGroupAction(ActionEvent event) {
        if(new UserClientService().leaveGroup(ManageClientToThread.u.getUserAccount()+"&"+groupAccount.getText()))
        {
            GroupMemberView.groupMemberStage.close();

            try {
                ChatRoomView.start(new Stage());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("小花花聊天室");
            alert.setHeaderText("成功离开该群聊");
            alert.show();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("小花花聊天室");
            alert.setHeaderText("无法离开该群，猜测您是群主，可以解散群聊");
            alert.show();
        }


    }
    @FXML
    void imageChange(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(GroupProfileView.groupProfileStage);
        if (selectedFile != null) {
            String imagePath = selectedFile.getAbsolutePath();
            Image imageCopy = new Image(selectedFile.toURI().toString());
            imageGroup.setImage(imageCopy);
            gc.setImageGroup(imagePath);

        }



    }

    @FXML
    void quitGroupAction(ActionEvent event) {
        new UserClientService().quitGroup(groupAccount.getText());
        GroupProfileView.groupProfileStage.close();
        try {
            ChatRoomView.start(new Stage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("小花花聊天室");
        alert.setHeaderText("群聊解散成功");
        alert.show();

    }
    @FXML
    void backAction(ActionEvent event) {

        if(ManageClientToThread.u.getUserAccount().equals(gc.getOwnerAccount())||ManageClientToThread.u.getUserAccount().equals(gc.getManagerAccount()))
        {
            gc.setGroupName(groupName.getText());
            gc.setSignature(groupSignature.getText());
            new UserClientService().restoreProfileGroup(gc);
        }

        GroupProfileView.groupProfileStage.close();
        try {
            ChatRoomView.start(new Stage());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("网络错误，界面打开失败");
        }

    }

    @FXML
    void setManagerAction(ActionEvent event) {
        setOr=true;
        GroupProfileView.groupProfileStage.close();
        try {
            GroupMemberView.start(new Stage());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}


