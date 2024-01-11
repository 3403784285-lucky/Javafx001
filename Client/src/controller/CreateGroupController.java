package controller;
import common.Friend;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import request.UserClientService;
import utils.FriendListCellMultiS;
import utils.ManageClientToThread;
import view.ChatRoomView;
import view.GroupCreateView;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
/**
 * ����Ⱥ�����Ŀ��������������Ⱥ�鴴������ĸ��ֽ����������¼���Ӧ��
 */
public class CreateGroupController implements Initializable {
    @FXML
    private ListView<Friend> selectedFriend;
    @FXML
    private TextField groupName;
    ObservableList<Friend> allFriend;
    ObservableList<Friend> selectedFreindList;
    @FXML
    private TextField selectFriendGroup;
    @FXML
    private ListView<Friend> selectFriend;
    /**
     * ��ʼ�����������ڼ��غ����б�Ȳ�����
     *
     * @param location  URL��λ��
     * @param resources ��Դ����
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //��������б����б����ListView
        Task<Void> initTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // ִ�к�ʱ����
                UserClientService ucs = new UserClientService();
                allFriend = FXCollections.observableArrayList(ucs.indicatefg());
                selectedFreindList = FXCollections.observableArrayList();
                // ...
                return null;
            }
        };

        initTask.setOnSucceeded(event -> {
            // ��������ɺ����UI
            Platform.runLater(() -> {
                Platform.runLater(()->
                {
                    selectFriendGroup.textProperty().addListener((observable, oldValue, newValue) -> searchFriends(newValue, selectFriend));
                    selectFriend.setCellFactory(param -> new FriendListCellMultiS());
                    selectedFriend.setCellFactory(param -> new FriendListCellMultiS());
                    selectFriend.setItems(allFriend);
                    selectedFriend.setItems(selectedFreindList);
                    // Handle Enter key press to perform search
                    selectFriendGroup.setOnKeyPressed(e -> {
                        searchFriends(selectFriendGroup.getText(), selectFriend);
                    });
                    selectFriend.setOnMouseClicked(e ->
                    {
                        Friend selectedFriend1 = selectFriend.getSelectionModel().getSelectedItem();
                        if (selectedFriend1 != null && !selectedFreindList.contains(selectedFriend1)) {
                            selectedFreindList.add(selectedFriend1);
                            allFriend.remove(selectedFriend1);
                        }
                    });
                    // Handle friend selection from the selectedFriendsListView
                    selectedFriend.setOnMouseClicked(e ->
                    {
                        Friend selectedFriend1 = selectedFriend.getSelectionModel().getSelectedItem();
                        if (selectedFriend1 != null && !allFriend.contains(selectedFriend1)) {
                            allFriend.add(selectedFriend1);
                            selectedFreindList.remove(selectedFriend1);
                        }
                    });
                });
            });

        });
        Thread initThread = new Thread(initTask);
        initThread.start();
    }
    /**
     * �������ѵķ�����
     *
     * @param keyword         �ؼ���
     * @param friendsListView �����б���ͼ
     */
    private void searchFriends(String keyword, ListView<Friend> friendsListView) {
        ObservableList<Friend> searchResults = FXCollections.observableArrayList();
        for (Friend friend : allFriend) {
            if (friend.getNickname().contains(keyword)) {
                searchResults.add(friend);
            }
        }
        friendsListView.setItems(searchResults);
    }
    /**
     * ȡ������Ⱥ�������
     *
     * @param event ȡ����ť����¼�
     */
    @FXML
    void cancelCreate(ActionEvent event) {
        GroupCreateView.createGroupStage.close();
        try {
            ChatRoomView.start(new Stage());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("������󣬽����ʧ��");
        }

    }
    /**
     * ��ɴ���Ⱥ�������
     *
     * @param event ������ť����¼�
     */
    @FXML
    void defCreate(ActionEvent event) {

            if (selectedFriend.getItems().size() < 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("С����������");
                alert.setHeaderText("��������������һ��Ŷ����");
                alert.show();
            } else {
                Task<Void> task = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        Friend group = new Friend("", "C:\\Users\\zplaz\\IdeaProjects\\Javafx001\\Client\\src\\backphoto\\back16.jpg", groupName.getText());
                        UserClientService usc = new UserClientService();
                        Friend bGroup = usc.initGroup(group);
                        System.out.println(bGroup.getGroupAccount() + "->" + bGroup.getAvatar());
                        ArrayList<Friend> arrayList = new ArrayList<>(selectedFreindList);
                        Friend ff = new Friend(ManageClientToThread.u.getUserAccount(), "C:\\Users\\zplaz\\IdeaProjects\\Javafx001\\Client\\src\\backphoto\\back16.jpg", groupName.getText());
                        arrayList.add(ff);
                        for (Friend f : arrayList) {
                            f.setGroupAccount(bGroup.getAccount());
                        }
                        usc.memberListInit(arrayList);
                        return null;
                    }
                };

                task.setOnSucceeded(e -> {
                    GroupCreateView.createGroupStage.close();
                    try {
                        ChatRoomView.start(new Stage());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        System.out.println("������󣬽����ʧ��");
                    }
                });

                Thread thread = new Thread(task);
                thread.start();
            }
        }
    }

