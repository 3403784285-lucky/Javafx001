package controller;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import common.User;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import request.UserClientService;
import utils.ManageClientToThread;
import view.ChatRoomView;
import view.ModifyView;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class ModifyProFileController implements Initializable {
    @FXML
    private Button chooseImage;
    @FXML
    private TextField gender;
    @FXML
    private ImageView userImage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {//根据登陆界面
        Platform.runLater(()->
        {
            userImage.setImage( new Image(new File(ManageClientToThread.u.getUserImage()).toURI().toString()));
            username.setText(ManageClientToThread.u.getUserName());
            account.setText(ManageClientToThread.u.getUserAccount());
            signature.setText(ManageClientToThread.u.getUserSignature());
            LocalDate localDate = ManageClientToThread.u.getUserBirthday().toLocalDate();
            birthday.setValue(localDate);
            gender.setText(ManageClientToThread.u.isGender()+"");
        });
    }

    @FXML
    private DatePicker birthday;

    @FXML
    private TextField signature;

    @FXML
    private TextField account;

    @FXML
    private Button saveInformation;

    @FXML
    private TextField username;

    private String imagePath;

    @FXML
    void saveInformation(ActionEvent event) throws IOException {

        Task<Boolean> saveTask = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                if (username.getText().isEmpty() || gender.getText().isEmpty()) {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("小花花聊天室");
                        alert.setHeaderText("用户名或性别不能为空");
                        alert.show();
                    });
                    return false;
                }

                if (!gender.getText().equals("男") && !gender.getText().equals("女")) {
                    return false;
                }

                UserClientService ucs = new UserClientService();
                User user = ManageClientToThread.u;
                user.setUserName(username.getText());
                System.out.println("快说改之后的名字------>" + user.getUserName());
                if (imagePath != null) {
                    user.setUserImage(imagePath);
                }
                user.setGender(gender.getText());
                user.setUserSignature(signature.getText());
                user.setUserAccount(account.getText());

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date utilDate;
                try {
                    utilDate = dateFormat.parse(String.valueOf(birthday.getValue()));
                    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                    user.setUserBirthday(sqlDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                    System.out.println("日期转换失败");
                    return false;
                }

                return ucs.restoreProfile(user);
            }
        };

        saveTask.setOnSucceeded(e -> {
            boolean mark = saveTask.getValue();
            if (mark) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("小花花聊天室");
                alert.setHeaderText("已保存您的设置");
                alert.show();
                ChatRoomView.getController().getUserImage().setImage(new Image(new File(ManageClientToThread.u.getUserImage()).toURI().toString()));
                ModifyView.modifyStage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("小花花聊天室");
                alert.setHeaderText("保存设置失败");
                alert.show();
            }
        });

        saveTask.setOnFailed(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("小花花聊天室");
            alert.setHeaderText("保存设置失败");
            alert.show();
        });

        new Thread(saveTask).start();
    }


    @FXML
    void favorImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

        Platform.runLater(() -> {
            File selectedFile = fileChooser.showOpenDialog(ModifyView.modifyStage);
            if (selectedFile != null) {
                imagePath = selectedFile.getAbsolutePath();
                Image image = new Image(selectedFile.toURI().toString());
                userImage.setImage(image);
            }
        });
    }




}
