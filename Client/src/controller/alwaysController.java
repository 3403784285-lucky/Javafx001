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
 * @author 张培灵
 *
 * 常用语编辑控制器类型
 */
public class alwaysController implements Initializable {

    /**
     *常用语显示
     */
    ArrayList<String>texts=new ArrayList<>();
    /**
     *文本编辑添加
     */
    @FXML
    private TextField text3;

    /**
     *文本编辑添加
     */
    @FXML
    private TextField text4;

    /**
     *文本编辑添加
     */
    @FXML
    private TextField text1;

    /**
     *文本编辑添加
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
        // 设置任务完成后的回调（在JavaFX线程中执行）
        task.setOnSucceeded(e -> {
            ChatRoomController controller = ChatRoomView.getController();
            controller.setMi1(text1.getText());
            controller.setMi2(text2.getText());
            controller.setMi3(text3.getText());
            controller.setMi4(text4.getText());
            alwaysView.alwaysStage.close();
        });
        // 在新线程中执行任务
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
