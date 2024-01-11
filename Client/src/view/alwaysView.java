package view;

import controller.ChatRoomController;
import controller.alwaysController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class alwaysView {
    public static Stage alwaysStage;
    private static alwaysController controller; // ���һ�����������������

    public static void start(Stage primaryStage) throws IOException {
        alwaysStage = primaryStage;
        FXMLLoader loader = new FXMLLoader(alwaysView.class.getClassLoader().getResource("fxml/always.fxml"));
        Parent root = loader.load();

        controller = loader.getController(); // ��ȡFXML�ļ������Ŀ���������

        primaryStage.setTitle("С����������");
        primaryStage.setScene(new Scene(root, 200, 300));
        primaryStage.show();
    }

    public static alwaysController getController() {
        return controller;
    }
}
