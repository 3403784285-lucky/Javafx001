package view;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
public class GroupMemberView {
    public static Stage groupMemberStage;
    public static void start(Stage primaryStage)throws IOException
    {
        groupMemberStage=primaryStage;
        Parent root = FXMLLoader.load(GroupMemberView.class.getClassLoader().getResource("fxml/groupmember.fxml") );
        primaryStage.setTitle("小花花聊天室");
        primaryStage.setScene(new Scene(root,600,400));
        primaryStage.show();
    }
}
