package controller;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import request.UserClientService;
import utils.ManageClientToThread;
import view.ChatRoomView;
import view.alwaysView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


/**
 * @author ������
 *
 * ������༭����������
 */
public class alwaysController implements Initializable {

    /**
     *��������ʾ
     */
    ArrayList<String>texts=new ArrayList<>();
    /**
     *�ı��༭���
     */
    @FXML
    private TextField text3;

    /**
     *�ı��༭���
     */
    @FXML
    private TextField text4;

    /**
     *�ı��༭���
     */
    @FXML
    private TextField text1;

    /**
     *�ı��༭���
     */
    @FXML
    private TextField text2;

    @FXML
    void modifyButton(ActionEvent event) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                new UserClientService().modifyAlways(texts);
                return null;
            }
        };
        // ����������ɺ�Ļص�����JavaFX�߳���ִ�У�
        task.setOnSucceeded(e -> {
            ChatRoomController controller = ChatRoomView.getController();
            controller.setMi1(text1.getText());
            controller.setMi2(text2.getText());
            controller.setMi3(text3.getText());
            controller.setMi4(text4.getText());
            alwaysView.alwaysStage.close();
        });
        // �����߳���ִ������
        Thread thread = new Thread(task);
        thread.start();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ChatRoomController controller= ChatRoomView.getController();
        texts.add(ManageClientToThread.u.getUserAccount());
        text1.setText(controller.getMi1().getText());
        texts.add(controller.getMi1().getText());
        text2.setText(controller.getMi2().getText());
        texts.add(controller.getMi2().getText());
        text3.setText(controller.getMi3().getText());
        texts.add(controller.getMi3().getText());
        text4.setText(controller.getMi4().getText());
        texts.add(controller.getMi4().getText());

    }
}
