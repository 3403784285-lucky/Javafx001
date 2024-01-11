package view;

import controller.ChatRoomController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
public class ChatRoomView {
    public static Stage chatRoomStage;
    private static ChatRoomController controller; // ���һ�����������������

    public static void start(Stage primaryStage) throws IOException {
        chatRoomStage = primaryStage;
        FXMLLoader loader = new FXMLLoader(ChatRoomView.class.getClassLoader().getResource("fxml/chatroom.fxml"));
        Parent root = loader.load();

        controller = loader.getController(); // ��ȡFXML�ļ������Ŀ���������

        primaryStage.setTitle("С����������");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public static ChatRoomController getController() {
        return controller;
    }
}