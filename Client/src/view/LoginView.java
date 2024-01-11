package view;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
public class LoginView {
    public static Stage loginStage;

    public static void start(Stage primaryStage)throws IOException
    {
        loginStage=primaryStage;
        Parent root = FXMLLoader.load(LoginView.class.getClassLoader().getResource("fxml/login.fxml") );
        primaryStage.setTitle("小花花聊天室");
        primaryStage.setScene(new Scene(root,400,300));
        primaryStage.show();
    }
}