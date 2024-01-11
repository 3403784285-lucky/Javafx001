package view;
import controller.ChatRecordController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
public class ChatRecordView {
    public static Stage chatRecordStage;
    private static ChatRecordController controller; // ���һ�����������������
    public static void start(Stage primaryStage) throws IOException {
        chatRecordStage = primaryStage;
        FXMLLoader loader = new FXMLLoader(ChatRecordView.class.getClassLoader().getResource("fxml/chatrecord.fxml"));
        Parent root = loader.load();
        controller = loader.getController(); // ��ȡFXML�ļ������Ŀ���������
        primaryStage.setTitle("С����������");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static ChatRecordController getController() {
        return controller;
    }
}
