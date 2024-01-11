package controller;
import common.Friend;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import request.UserClientService;
import utils.FriendListCellMultiS;
import view.ChatRoomView;
import view.GroupMemberView;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import static controller.ProfileGroupController.setOr;
import static controller.ProfileGroupController.transferOr;
/**
 * 群组成员管理界面的控制器，负责管理群组成员管理界面的各种交互操作和事件响应。
 */
public class GroupMemberController implements Initializable {
    @FXML
    private TextField quitmember;

    @FXML
    private ListView<Friend> selectedMember;

    @FXML
    private ListView<Friend> allMember;
    ObservableList<Friend> allMemberList;
    ObservableList<Friend> selectedMemberList;
    /**
     * 完成操作的方法，包括添加成员、设置管理员、转让群主和踢出成员。
     *
     * @param event 按钮点击事件
     */
    @FXML
    void defineAction(ActionEvent event) {
        GroupMemberView.groupMemberStage.close();
        try {
            ChatRoomView.start(new Stage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(ProfileGroupController.isAddPerson())
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            new UserClientService().addMemberGroup(new ArrayList<>(selectedMemberList));
            alert.setTitle("小花花聊天室");
            alert.setHeaderText("添加成员成功");
            alert.show();

        }
        else
        {
            if(setOr)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                new UserClientService().setManager(new ArrayList<>(selectedMemberList));
                alert.setTitle("小花花聊天室");
                alert.setHeaderText("设置管理员成功");
                alert.show();

            }
            else
            {
                if(transferOr)
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    new UserClientService().transferGroup(new ArrayList<>(selectedMemberList));
                    alert.setTitle("小花花聊天室");
                    alert.setHeaderText("转让群主成功(只能转给你选择的第一个哦)");
                    alert.show();

                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    new UserClientService().substractMemberGroup(new ArrayList<>(selectedMemberList));
                    alert.setTitle("小花花聊天室");
                    alert.setHeaderText("踢出成员成功(千万不要妄想踢出群主哦)");
                    alert.show();
                }



            }

        }


    }
    /**
     * 取消操作的方法。
     *
     * @param event 按钮点击事件
     */
    @FXML
    void cancelAction(ActionEvent event) {
        GroupMemberView.groupMemberStage.close();
        try {
            ChatRoomView.start(new Stage());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("网络错误，界面打开失败");
        }
    }
    /**
     * 初始化方法，用于加载群组成员列表等操作。
     *
     * @param location  URL定位器
     * @param resources 资源绑定器
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Task<Void> initializationTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                UserClientService ucs = new UserClientService();
                if (ProfileGroupController.addPerson) {
                    quitmember.setPromptText("选择你要添加的好友");
                    System.out.println();
                    System.out.println(ProfileGroupController.gc.getGroupAccount());
                    allMemberList = FXCollections.observableArrayList(ucs.indicatefgCopy(ProfileGroupController.gc.getGroupAccount()));
                } else {
                    if (setOr) {
                        quitmember.setPromptText("选择你要设置的管理员(暂且只能设置一位哦)");
                    } else {
                        if(transferOr)
                        {
                            quitmember.setPromptText("选择你要将群主转让的人");
                        }
                        else
                        {
                            quitmember.setPromptText("选择你要踢出的成员");
                        }

                    }
                    allMemberList = FXCollections.observableArrayList(ucs.indicateMemberGroup(ProfileGroupController.gc.getGroupAccount()));
                }
                selectedMemberList = FXCollections.observableArrayList();
                quitmember.textProperty().addListener((observable, oldValue, newValue) -> searchFriends(newValue, allMember));
                allMember.setCellFactory(param -> new FriendListCellMultiS());
                selectedMember.setCellFactory(param -> new FriendListCellMultiS());
                allMember.setItems(allMemberList);
                selectedMember.setItems(selectedMemberList);
                // Handle Enter key press to perform search
                quitmember.setOnKeyPressed(event -> {
                    searchFriends(quitmember.getText(), allMember);
                });
                allMember.setOnMouseClicked(event -> {
                    Friend selectedFriend1 = allMember.getSelectionModel().getSelectedItem();
                    if (selectedFriend1 != null && !selectedMemberList.contains(selectedFriend1)) {
                        selectedMemberList.add(selectedFriend1);
                        allMemberList.remove(selectedFriend1);
                    }
                });
                selectedMember.setOnMouseClicked(event -> {
                    Friend selectedFriend1 = selectedMember.getSelectionModel().getSelectedItem();
                    if (selectedFriend1 != null && !allMemberList.contains(selectedFriend1)) {
                        allMemberList.add(selectedFriend1);
                        selectedMemberList.remove(selectedFriend1);
                    }
                });
                return null;
            }
        };

        Thread initializationThread = new Thread(initializationTask);
        initializationThread.start();
    }

    private void searchFriends(String keyword, ListView<Friend> friendsListView) {
        ObservableList<Friend> searchResults = FXCollections.observableArrayList();
        for (Friend friend : allMemberList) {
            if (friend.getNickname().contains(keyword)) {
                searchResults.add(friend);
            }
        }
        friendsListView.setItems(searchResults);
    }


}
