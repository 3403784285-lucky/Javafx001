package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class RegisterView {
    public static Stage registerStage;
    public static void start(Stage primaryStage)throws IOException
    {
        registerStage=primaryStage;
        Parent root = FXMLLoader.load(RegisterView.class.getClassLoader().getResource("fxml/register.fxml"));
        primaryStage.setTitle("×¢²áÐÂÓÃ»§");
        primaryStage.setScene(new Scene(root,400,300));
        primaryStage.show();
    }
}
