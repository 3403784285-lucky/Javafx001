package utils;
import javafx.application.Application;
import javafx.stage.Stage;
import view.ChatRoomView;
import view.LoginView;
import view.ModifyView;
import view.RegisterView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main extends Application {
    public static void main(String[] args) throws IOException {
        launch();
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        LoginView.start(primaryStage);
    }
}
