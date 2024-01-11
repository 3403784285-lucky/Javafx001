package view;

import controller.ChatRoomController;
import controller.ProfileController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sun.java2d.cmm.Profile;

import java.io.IOException;

public class ProFileView {
    public static Stage profileStage;

    public static void start(Stage primaryStage)throws IOException
    {
       profileStage=primaryStage;

        Parent root = FXMLLoader.load(ModifyView.class.getClassLoader().getResource("fxml/profile.fxml") );
        primaryStage.setTitle("×ÊÁÏÐÞ¸Ä");
        primaryStage.setScene(new Scene(root,800,600));
        primaryStage.show();
    }
}
