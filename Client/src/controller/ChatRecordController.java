package controller;
import common.Message;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import org.apache.commons.io.FileUtils;
import request.MessageClientService;
import transfer.CommonEvent;
import utils.ManageClientToThread;
import utils.ThreadClientConnect;
import view.ChatRoomView;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static controller.ChatRoomController.getMy;

/**
 * @author 张培灵
 * @date 2024/01/09
 * 聊天记录控制器
 */
public class ChatRecordController implements Initializable {
    /**
     *聊天界面控制类实例
     */
    public ChatRoomController controller=ChatRoomView.getController();
    /**
     *是不是
     */
    private boolean lastCopy=true;
    /**
     *消息记录
     */
    ArrayList<Message>arrayList;
    /**
     *滚动面板，控制的是聊天界面
     */
    @FXML
    private ScrollPane scroller;

    /**
     *flow界面，用于装载聊天界面，来让滚动界面丝滑控制
     */
    @FXML
    private FlowPane flow;

    /**
     *消息列表的搜索框
     */
    @FXML
    private TextField searchChat;


    /**
     * @param location
     * @param resources
     * 初始化界面，将好友列表加载出来，并且将聊天界面用一个面板盖住
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Pattern pp=Pattern.compile("\\d{10}");
        Matcher m=pp.matcher(getMy.getAccount());
        boolean mm=m.matches();
        if(mm)
        {
            arrayList=new MessageClientService().initMessagerecord(ManageClientToThread.u.getUserAccount(),getMy.getAccount());

        }
       else
        {
            arrayList=new MessageClientService().initMessageRecordGroup(getMy.getAccount());

        }
        if(arrayList!=null&&mm)
        {
            for (Message msg : arrayList) {
                System.out.println("加一个消息");
                if (msg.getMesType() == null || msg.getMesType().equals("text")) addBox(msg);
                else if (msg.getMesType().equals("image")) receiveFavor(msg);
                else fileReceive(msg);
            }
            System.out.println("到底来了不？");
        }
        else
        {
            for (Message msg : arrayList) {
                System.out.println("加一个消息");
                if (msg.getMesType() == null || msg.getMesType().equals("text")) addBoxGroup(msg);
                else if (msg.getMesType().equals("image")) receiveFavorGroup(msg);
                else fileReceiveGroup(msg);
            }
            System.out.println("到底来了不群聊？");

        }
        scroller.vvalueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
            if (lastCopy) {
                scroller.setVvalue(1.0);
                lastCopy = false;
            }
        });
        scroller.setVvalue(1);



    }

    /**
     * @param event
     * 搜索聊天的方法
     */
    @FXML
    void searchChat(KeyEvent event) {
        Pattern pp=Pattern.compile("\\d{10}");
        Matcher m=pp.matcher(getMy.getAccount());
        boolean mm=m.matches();
        if(event.getCode()== KeyCode.ENTER)
        {
            flow.getChildren().clear();
            if(mm)
            {
                for(Message msg: arrayList)
                {
                    if (msg.getContent().contains(searchChat.getText()))
                    {
                        if (msg.getMesType() == null || msg.getMesType().equals("text")) addBox(msg);
                        else if (msg.getMesType().equals("image")) receiveFavor(msg);
                        else fileReceive(msg);
                    }
                }
            }
            else
            {
                for(Message msg: arrayList)
                {
                    if (msg.getContent().contains(searchChat.getText()))
                    {
                        if (msg.getMesType() == null || msg.getMesType().equals("text")) addBoxGroup(msg);
                        else if (msg.getMesType().equals("image")) receiveFavorGroup(msg);
                        else fileReceiveGroup(msg);
                    }
                }
            }


        }


    }

    /**
     * @param message1
     * 将消息加入到群聊天
     */
    public void addBoxGroup(Message message1) {
        System.out.println("这一次我是真进来了");
        Pattern ppq = Pattern.compile("\\d{10}");
        Matcher mq = null;
        if (getMy == null)
            mq = ppq.matcher(" ");
        else {
            mq = ppq.matcher(getMy.getAccount());
        }
        boolean mmq = mq.matches();
        System.out.println("准备进入图片");
        Platform.runLater(() -> {
            boolean ismine=false;
            HBox timebox = new HBox();
            Label timesend = new Label(message1.getSendTime().toString());
            timesend.setStyle("-fx-font-size: 10px;");
            timebox.getChildren().add(timesend);
            timebox.setAlignment(Pos.TOP_CENTER);
            String sender = message1.getSender();
            ImageView headp = new ImageView();
            Image head = null;
            head = new Image(new File(controller.getImageB().get(sender)).toURI().toString());
            headp.setImage(head);
            headp.setFitWidth(40);
            headp.setFitHeight(40);
            if (sender.equals(ManageClientToThread.u.getUserAccount()))
                ismine = true;
            else
                ismine = false;
            Pattern pp = Pattern.compile("\\d{1,}\\.png");
            Pattern ppp = Pattern.compile("[A-Z]:\\\\.+");
            String[] segments = message1.getContent().split("[\\[\\]]");
            HBox messageBox = new HBox();
            if (!ismine) {
                timebox.setStyle("-fx-background-color: white");
                messageBox.setStyle("-fx-background-color: white ");
                messageBox.setAlignment(Pos.TOP_LEFT);
            }
            messageBox.getChildren().add(headp);
            for (String segment : segments) {
                Matcher m = pp.matcher(segment);
                if (m.matches()) {

                    ImageView emojiView =new ImageView();
                    emojiView.setFitWidth(15);
                    emojiView.setFitHeight(15);
                    emojiView.setImage(new Image(new File("C:\\Users\\zplaz\\Documents\\tencent files\\3403784285\\filerecv\\expression\\static\\"+segment).toURI().toString()));
                    messageBox.getChildren().add(emojiView);
                } else {
                    if(!segment.equals(""))
                    {
                        Label messageBubble=new Label();
                        // 普通文本
                        messageBubble.setText(segment);
                        messageBubble.setWrapText(true);
                        messageBubble.setMaxWidth(220);
                        messageBubble.setPadding(new Insets(6));
                        messageBubble.setFont(new Font(14));
                        messageBox.getChildren().add(messageBubble);
                    }
                }

            }
            if(ismine){
                timebox.setStyle("-fx-background-color: white");
                messageBox.setStyle("-fx-background-color: lightblue");
                messageBox.setAlignment(Pos.TOP_LEFT);
            }
            messageBox.setPrefWidth(350);
            lastCopy = scroller.getVvalue() == 1.0;
            if(flow==null)System.out.println("流动平板为空");
            messageBox.setSpacing(0);
            System.out.println("加入聊天盒子");
            flow.getChildren().add(timebox);
            System.out.println("消息盒子加入完毕");
            flow.getChildren().add(messageBox);
            System.out.println("消息盒子继续加入完毕");

        });
    }

    /**
     * @param message1
     * 将消息加入到聊天
     */
    public void addBox(Message message1)
    {

        System.out.println("这一次我是真进来了");
        Pattern ppq = Pattern.compile("\\d{10}");
        Matcher mq = null;
        if (getMy == null)
            mq = ppq.matcher(" ");
        else {
            mq = ppq.matcher(getMy.getAccount());
        }
        boolean mmq = mq.matches();
        System.out.println("准备进入图片");
        Platform.runLater(() -> {
            boolean ismine=false;
            HBox timebox = new HBox();
            Label timesend = new Label(message1.getSendTime().toString());
            timesend.setStyle("-fx-font-size: 10px;");
            timebox.getChildren().add(timesend);
            timebox.setAlignment(Pos.TOP_CENTER);
            String sender = message1.getSender();
            ImageView headp = new ImageView();
            Image head = null;
            head = new Image(new File(controller.getImageA().get(sender)).toURI().toString());
            headp.setImage(head);
            headp.setFitWidth(40);
            headp.setFitHeight(40);
            if (sender.equals(ManageClientToThread.u.getUserAccount()))
                ismine = true;
            else
                ismine = false;
            Pattern pp = Pattern.compile("\\d{1,}\\.png");
            Pattern ppp = Pattern.compile("[A-Z]:\\\\.+");
            String[] segments = message1.getContent().split("[\\[\\]]");
            HBox messageBox = new HBox();
            if (!ismine) {
                timebox.setStyle("-fx-background-color: white");
                messageBox.setStyle("-fx-background-color: white ");
                messageBox.setAlignment(Pos.TOP_LEFT);
            }
            messageBox.getChildren().add(headp);
            for (String segment : segments) {
                Matcher m = pp.matcher(segment);
                if (m.matches()) {

                    ImageView emojiView =new ImageView();
                    emojiView.setFitWidth(15);
                    emojiView.setFitHeight(15);
                    emojiView.setImage(new Image(new File("C:\\Users\\zplaz\\Documents\\tencent files\\3403784285\\filerecv\\expression\\static\\"+segment).toURI().toString()));
                    messageBox.getChildren().add(emojiView);
                } else {
                    if(!segment.equals(""))
                    {
                        Label messageBubble=new Label();
                        // 普通文本
                        messageBubble.setText(segment);
                        messageBubble.setWrapText(true);
                        messageBubble.setMaxWidth(220);
                        messageBubble.setPadding(new Insets(6));
                        messageBubble.setFont(new Font(14));
                        messageBox.getChildren().add(messageBubble);
                    }
                }

            }
            if(ismine){
                timebox.setStyle("-fx-background-color: white");
                messageBox.setStyle("-fx-background-color: lightblue");
                messageBox.setAlignment(Pos.TOP_LEFT);
            }
            messageBox.setPrefWidth(350);
            lastCopy = scroller.getVvalue() == 1.0;
            if(flow==null)System.out.println("流动平板为空");
            messageBox.setSpacing(0);
            System.out.println("加入聊天盒子");
            flow.getChildren().add(timebox);
            System.out.println("消息盒子加入完毕");
            flow.getChildren().add(messageBox);
            System.out.println("消息盒子继续加入完毕");

        });
    }

    /**
     * @param msg
     * 接受群聊消息的图片消息和表情包处理，对于发送方进行颜色处理，也就是蓝色为己方发送，绿色为他方发送
     */
    public  void receiveFavorGroup(Message msg)
    {

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                File receiveImage = new File(msg.getContent());
                try {
                    FileUtils.writeByteArrayToFile(receiveImage, msg.getTransfer());
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("网络错误，图片写入失败");
                }

                // 在 UI 线程中更新 UI
                Platform.runLater(() -> {
                    boolean ismine=false;
                    HBox timebox = new HBox();
                    Label timesend = new Label(msg.getSendTime().toString());
                    timesend.setStyle("-fx-font-size: 10px;");
                    timebox.getChildren().add(timesend);
                    timebox.setAlignment(Pos.TOP_CENTER);
                    String sender = msg.getSender();
                    ImageView headp = new ImageView();
                    flow.setHgap(0);
                    Image head = null;
                    head = new Image(new File(controller.getImageB().get(sender)).toURI().toString());
                    headp.setImage(head);
                    headp.setFitWidth(40);
                    headp.setFitHeight(40);
                    if (sender.equals(ManageClientToThread.u.getUserAccount())) ismine = true;
                    else ismine = false;
                    HBox messageBox = new HBox();
                    HBox.setHgrow(messageBox, Priority.ALWAYS);
                    if (!ismine) {
                        timebox.setStyle("-fx-background-color: white");
                        messageBox.setStyle("-fx-background-color: white ");

                    }
                    messageBox.getChildren().add(headp);
                    messageBox.setAlignment(Pos.TOP_LEFT);
                    ImageView imageView = new ImageView();
                    imageView.setFitWidth(60);
                    imageView.setFitHeight(60);
                    imageView.setImage(new Image(receiveImage.toURI().toString()));
                    messageBox.getChildren().add(imageView);
                    if(ismine){
                        timebox.setStyle("-fx-background-color: white");
                        messageBox.setStyle("-fx-background-color: lightblue");
                    }
                    messageBox.setPrefWidth(350);
                    lastCopy = scroller.getVvalue() == 1.0;
                    messageBox.setSpacing(0);
                    messageBox.setUserData(msg);
                    messageBox.setOnMouseClicked(e->
                    {
                        File  file=new File(msg.getContent());
                        if (Desktop.isDesktopSupported()) {
                            Desktop desktop = Desktop.getDesktop();
                            try {
                                desktop.open(file);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }

                    });
                    flow.getChildren().add(timebox);
                    flow.getChildren().add(messageBox);
                });

                return null;
            }
        };
        // 启动线程
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * @param msg
     *
     * 接受群聊文件，一下下载，两下加载
     */
    public void fileReceiveGroup(Message msg)
    {
        Pattern pp=Pattern.compile("\\d{10}");
        Matcher m=pp.matcher(getMy.getAccount());
        boolean mm=m.matches();
        Platform.runLater(()->
        {
            boolean ismine=false;
            HBox timebox=new HBox();
            Label timesend=new Label(msg.getSendTime().toString());
            timesend.setStyle("-fx-font-size: 10px;");
            timebox.getChildren().add(timesend);
            timebox.setAlignment(Pos.TOP_CENTER);
            String sender=msg.getSender();
            ImageView headp=new ImageView();
            flow.setHgap(0);
            Image head=null;
            head=new Image(new File(controller.getImageB().get(sender)).toURI().toString());
            headp.setImage(head);
            headp.setFitWidth(40);
            headp.setFitHeight(40);
            if(sender.equals(ManageClientToThread.u.getUserAccount()))ismine=true;
            else ismine=false;
            HBox messageBox=new HBox();
            HBox.setHgrow(messageBox, Priority.ALWAYS);
            if(!ismine){
                timebox.setStyle("-fx-background-color: white");
                messageBox.setStyle("-fx-background-color: white ");
            }
            messageBox.getChildren().add(headp);
            messageBox.setAlignment(Pos.TOP_LEFT);
            ImageView emojiView =new ImageView();
            emojiView.setFitWidth(30);
            emojiView.setFitHeight(30);
            emojiView.setImage(new Image(new File("C:\\Users\\zplaz\\Pictures\\Camera Roll\\OIP-C (3).jpg").toURI().toString()));
            messageBox.getChildren().add(emojiView);
            Label messageBubble=new Label();
            // 普通文本
            messageBubble.setText(msg.getContent());
            messageBubble.setWrapText(true);
            messageBubble.setMaxWidth(220);
            messageBubble.setPadding(new Insets(6));
            messageBubble.setFont(new Font(14));
            messageBox.getChildren().add(messageBubble);
            if(ismine){
                timebox.setStyle("-fx-background-color: white");
                messageBox.setStyle("-fx-background-color: lightblue");
            }
            messageBox.setPrefWidth(350);
            messageBox.setPadding(new Insets(10, 0, 10, 0));
            lastCopy = scroller.getVvalue() == 1.0;
            if(flow==null)System.out.println("流动平板为空");
            messageBox.setSpacing(0);
            messageBox.setOnMouseClicked(e -> {
                Task<Void> task = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        // 执行耗时操作
                        if (!ManageClientToThread.u.getUserAccount().equals(msg.getSender())) {
                            File file = new File("D:\\clientreceiver\\" + msg.getContent());
                            if (!file.exists()) {
                                ThreadClientConnect threadClientConnect = ManageClientToThread.getConn(ManageClientToThread.uOriginal);
                                ObjectOutputStream oo = threadClientConnect.getDos();
                                CommonEvent cc = new CommonEvent("preReceiveFile", msg);
                                try {
                                    oo.writeObject(cc);
                                    oo.flush();
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("小花花聊天室");
                                alert.setHeaderText("文件下载成功");
                                alert.show();
                                while (file == null) System.out.println("要学会等待，这个世界真的很美好");
                                System.out.println("接受进行中");
                            }else
                            {
                                if (Desktop.isDesktopSupported()) {
                                    Desktop desktop = Desktop.getDesktop();
                                    try {
                                        desktop.open(file);
                                    } catch (IOException ioException) {
                                        ioException.printStackTrace();
                                    }
                                }
                            }
                        }

                        return null;
                    }
                };

                // 设置任务完成后的处理逻辑
                task.setOnSucceeded(taskEvent -> {

                });
                // 启动任务，运行在后台线程上
                new Thread(task).start();
            });
            flow.getChildren().add(timebox);
            flow.getChildren().add(messageBox);
        });
        //自己的头像，头像大小40*40；

    }

    /**
     * @param msg
     * 接受图片或信息或表情
     */
    public void receiveFavor(Message msg) {
        // 创建一个 Task 对象
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                File receiveImage = new File(msg.getContent());
                try {
                    FileUtils.writeByteArrayToFile(receiveImage, msg.getTransfer());
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("网络错误，图片写入失败");
                }

                // 在 UI 线程中更新 UI
                Platform.runLater(() -> {
                    boolean isMine=false;
                    HBox timebox = new HBox();
                    Label timesend = new Label(msg.getSendTime().toString());
                    timesend.setStyle("-fx-font-size: 10px;");
                    timebox.getChildren().add(timesend);
                    timebox.setAlignment(Pos.TOP_CENTER);
                    String sender = msg.getSender();
                    ImageView headp = new ImageView();
                    flow.setHgap(0);
                    Image head = null;
                    head = new Image(new File(controller.getImageA().get(sender)).toURI().toString());
                    headp.setImage(head);
                    headp.setFitWidth(40);
                    headp.setFitHeight(40);
                    if (sender.equals(ManageClientToThread.u.getUserAccount())) isMine = true;
                    else isMine = false;
                    HBox messageBox = new HBox();
                    HBox.setHgrow(messageBox, Priority.ALWAYS);
                    if (!isMine) {
                        timebox.setStyle("-fx-background-color: white");
                        messageBox.setStyle("-fx-background-color: white ");

                    }
                    messageBox.getChildren().add(headp);
                    messageBox.setAlignment(Pos.TOP_LEFT);
                    ImageView imageView = new ImageView();
                    imageView.setFitWidth(60);
                    imageView.setFitHeight(60);
                    imageView.setImage(new Image(receiveImage.toURI().toString()));
                    messageBox.getChildren().add(imageView);
                    if(isMine){
                        timebox.setStyle("-fx-background-color: white");
                        messageBox.setStyle("-fx-background-color: lightblue");
                    }
                    messageBox.setPrefWidth(350);
                    lastCopy = scroller.getVvalue() == 1.0;
                    messageBox.setSpacing(0);
                    messageBox.setUserData(msg);
                    messageBox.setOnMouseClicked(e->
                    {
                        File  file=new File(msg.getContent());
                        if (Desktop.isDesktopSupported()) {
                            Desktop desktop = Desktop.getDesktop();
                            try {
                                desktop.open(file);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }

                    });
                    flow.getChildren().add(timebox);
                    flow.getChildren().add(messageBox);
                });

                return null;
            }
        };
        // 启动线程
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * @param msg
     * 个人接收文件
     */
    public void fileReceive(Message msg)
    {
        System.out.println("看看这边走到哪里了");
        //自己的头像，头像大小40*40；
        Platform.runLater(()->
        {
            boolean isMine=false;
            HBox timebox = new HBox();
            Label timesend = new Label(msg.getSendTime().toString());
            timesend.setStyle("-fx-font-size: 10px;");
            timebox.getChildren().add(timesend);
            timebox.setAlignment(Pos.TOP_CENTER);
            String sender = msg.getSender();
            ImageView headp = new ImageView();
            flow.setHgap(0);
            Image head = null;
            head = new Image(new File(controller.getImageA().get(sender)).toURI().toString());
            headp.setImage(head);
            headp.setFitWidth(40);
            headp.setFitHeight(40);
            if (sender.equals(ManageClientToThread.u.getUserAccount())) isMine = true;
            else isMine = false;
            HBox messageBox = new HBox();
            HBox.setHgrow(messageBox, Priority.ALWAYS);
            if (!isMine) {
                timebox.setStyle("-fx-background-color: white");
                messageBox.setStyle("-fx-background-color: white ");
            }
            messageBox.setAlignment(Pos.TOP_LEFT);
            messageBox.getChildren().add(headp);
            ImageView emojiView = new ImageView();
            emojiView.setFitWidth(30);
            emojiView.setFitHeight(30);
            emojiView.setImage(new Image(new File("C:\\Users\\zplaz\\Pictures\\Camera Roll\\OIP-C (3).jpg").toURI().toString()));
            messageBox.getChildren().add(emojiView);
            Label messageBubble = new Label();
            messageBubble.setText(msg.getContent());
            messageBubble.setWrapText(true);
            messageBubble.setMaxWidth(220);
            messageBubble.setPadding(new Insets(6));
            messageBubble.setFont(new Font(14));
            messageBox.getChildren().add(messageBubble);
            if (isMine) {
                timebox.setStyle("-fx-background-color: white");
                messageBox.setStyle("-fx-background-color: lightblue");
            }
            messageBox.setPrefWidth(350);
            messageBox.setPadding(new Insets(10, 0, 10, 0));
            lastCopy = scroller.getVvalue() == 1.0;
            if (flow == null) System.out.println("流动平板为空");
            messageBox.setSpacing(0);
            messageBox.setUserData(msg);
            System.out.println("暂不慌");
            messageBox.setOnMouseClicked(e -> {
                Task<Void> task = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        // 执行耗时操作
                        if (!ManageClientToThread.u.getUserAccount().equals(msg.getSender())) {
                            File file = new File("D:\\clientreceiver\\" + msg.getContent());
                            if (!file.exists()) {
                                ThreadClientConnect threadClientConnect = ManageClientToThread.getConn(ManageClientToThread.uOriginal);
                                ObjectOutputStream oo = threadClientConnect.getDos();
                                CommonEvent cc = new CommonEvent("preReceiveFile", msg);
                                try {
                                    oo.writeObject(cc);
                                    oo.flush();
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("小花花聊天室");
                                alert.setHeaderText("文件下载成功");
                                alert.show();
                                while (file == null) System.out.println("要学会等待，这个世界真的很美好");
                                System.out.println("接受进行中");
                            }else
                            {
                                if (Desktop.isDesktopSupported()) {
                                    Desktop desktop = Desktop.getDesktop();
                                    try {
                                        desktop.open(file);
                                    } catch (IOException ioException) {
                                        ioException.printStackTrace();
                                    }
                                }
                            }
                        }

                        return null;
                    }
                };

                // 设置任务完成后的处理逻辑
                task.setOnSucceeded(taskEvent -> {

                });
                // 启动任务，运行在后台线程上
                new Thread(task).start();
            });
            flow.getChildren().add(timebox);
            flow.getChildren().add(messageBox);
        });


    }


}
