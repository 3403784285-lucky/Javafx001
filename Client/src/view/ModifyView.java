package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ModifyView {
    public static Stage modifyStage;

    public static void start(Stage primaryStage)throws IOException
    {
        modifyStage=primaryStage;

        Parent root = FXMLLoader.load(ModifyView.class.getClassLoader().getResource("fxml/modifyprofile.fxml") );
        primaryStage.setTitle("×ÊÁÏÐÞ¸Ä");
        primaryStage.setScene(new Scene(root,400,400));
        primaryStage.show();
    }
}