package utils;
import common.Friend;
import controller.ProfileController;
import javafx.geometry.Insets;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FriendListCellMultiS extends ListCell<Friend>{
    private ImageView avatarImageView = new ImageView();
    private Label nameLabel = new Label();
    public FriendListCellMultiS() {
        // ... 其他初始化代码
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        setPrefHeight(50);
        HBox hbox = new HBox(0);
        hbox.setPadding(new Insets(5));
        hbox.getChildren().addAll(avatarImageView, nameLabel);
        setGraphic(hbox);
        // 单击事件处理

    }
    @Override
    protected void updateItem(Friend friend, boolean empty) {
        super.updateItem(friend, empty);
        if (empty || friend == null) {
            setGraphic(null);
        } else {
            avatarImageView.setImage(new Image(new File(friend.getAvatar()).toURI().toString()));
            avatarImageView.setFitWidth(50);  // 设置宽度为50
            avatarImageView.setFitHeight(50); // 设置高度为50
            Pattern pp=Pattern.compile("\\d{10}");
            Matcher m=pp.matcher(friend.getAccount());
            boolean mm=m.matches();
            if (mm)
            {
                if(friend.isOnline())
                {
                    nameLabel.setText(friend.getNickname()+"（在线）");
                }
                else
                {
                    nameLabel.setText(friend.getNickname()+"（离线）");
                }
            }
            else nameLabel.setText(friend.getNickname());
            HBox hbox = new HBox(0);
            hbox.setMaxWidth(150);   // 设置最大宽度为100
            hbox.setMaxHeight(50);  // 设置最大高度为100
            hbox.setPadding(new Insets(5));
            HBox.setMargin(avatarImageView, new Insets(0, 0, 0, 0));
            hbox.getChildren().addAll(avatarImageView, nameLabel);
            setGraphic(hbox);


        }


    }

}
