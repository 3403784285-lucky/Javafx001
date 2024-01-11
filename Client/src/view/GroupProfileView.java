package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GroupProfileView {
    public static Stage groupProfileStage;

    public static void start(Stage primaryStage)throws IOException
    {
        groupProfileStage=primaryStage;
        Parent root = FXMLLoader.load(GroupProfileView.class.getClassLoader().getResource("fxml/profilegroup.fxml") );
        primaryStage.setTitle("小花花聊天室");
        primaryStage.setScene(new Scene(root,600,400));
        primaryStage.show();
    }
}
