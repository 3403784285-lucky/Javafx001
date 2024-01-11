package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GroupCreateView {
    public static Stage createGroupStage;

    public static void start(Stage primaryStage)throws IOException
    {
        createGroupStage=primaryStage;
        Parent root = FXMLLoader.load(LoginView.class.getClassLoader().getResource("fxml/creategroup.fxml") );
        primaryStage.setTitle("С����������");
        primaryStage.setScene(new Scene(root,600,800));
        primaryStage.show();
    }
}
