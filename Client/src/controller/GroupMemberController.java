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
 * Ⱥ���Ա�������Ŀ��������������Ⱥ���Ա�������ĸ��ֽ����������¼���Ӧ��
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
     * ��ɲ����ķ�����������ӳ�Ա�����ù���Ա��ת��Ⱥ�����߳���Ա��
     *
     * @param event ��ť����¼�
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
            alert.setTitle("С����������");
            alert.setHeaderText("��ӳ�Ա�ɹ�");
            alert.show();

        }
        else
        {
            if(setOr)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                new UserClientService().setManager(new ArrayList<>(selectedMemberList));
                alert.setTitle("С����������");
                alert.setHeaderText("���ù���Ա�ɹ�");
                alert.show();

            }
            else
            {
                if(transferOr)
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    new UserClientService().transferGroup(new ArrayList<>(selectedMemberList));
                    alert.setTitle("С����������");
                    alert.setHeaderText("ת��Ⱥ���ɹ�(ֻ��ת����ѡ��ĵ�һ��Ŷ)");
                    alert.show();

                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    new UserClientService().substractMemberGroup(new ArrayList<>(selectedMemberList));
                    alert.setTitle("С����������");
                    alert.setHeaderText("�߳���Ա�ɹ�(ǧ��Ҫ�����߳�Ⱥ��Ŷ)");
                    alert.show();
                }



            }

        }


    }
    /**
     * ȡ�������ķ�����
     *
     * @param event ��ť����¼�
     */
    @FXML
    void cancelAction(ActionEvent event) {
        GroupMemberView.groupMemberStage.close();
        try {
            ChatRoomView.start(new Stage());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("������󣬽����ʧ��");
        }
    }
    /**
     * ��ʼ�����������ڼ���Ⱥ���Ա�б�Ȳ�����
     *
     * @param location  URL��λ��
     * @param resources ��Դ����
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Task<Void> initializationTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                UserClientService ucs = new UserClientService();
                if (ProfileGroupController.addPerson) {
                    quitmember.setPromptText("ѡ����Ҫ��ӵĺ���");
                    System.out.println();
                    System.out.println(ProfileGroupController.gc.getGroupAccount());
                    allMemberList = FXCollections.observableArrayList(ucs.indicatefgCopy(ProfileGroupController.gc.getGroupAccount()));
                } else {
                    if (setOr) {
                        quitmember.setPromptText("ѡ����Ҫ���õĹ���Ա(����ֻ������һλŶ)");
                    } else {
                        if(transferOr)
                        {
                            quitmember.setPromptText("ѡ����Ҫ��Ⱥ��ת�õ���");
                        }
                        else
                        {
                            quitmember.setPromptText("ѡ����Ҫ�߳��ĳ�Ա");
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
