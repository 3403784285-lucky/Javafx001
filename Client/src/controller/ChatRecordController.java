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
 * @author ������
 * @date 2024/01/09
 * �����¼������
 */
public class ChatRecordController implements Initializable {
    /**
     *������������ʵ��
     */
    public ChatRoomController controller=ChatRoomView.getController();
    /**
     *�ǲ���
     */
    private boolean lastCopy=true;
    /**
     *��Ϣ��¼
     */
    ArrayList<Message>arrayList;
    /**
     *������壬���Ƶ����������
     */
    @FXML
    private ScrollPane scroller;

    /**
     *flow���棬����װ��������棬���ù�������˿������
     */
    @FXML
    private FlowPane flow;

    /**
     *��Ϣ�б��������
     */
    @FXML
    private TextField searchChat;


    /**
     * @param location
     * @param resources
     * ��ʼ�����棬�������б���س��������ҽ����������һ������ס
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
                System.out.println("��һ����Ϣ");
                if (msg.getMesType() == null || msg.getMesType().equals("text")) addBox(msg);
                else if (msg.getMesType().equals("image")) receiveFavor(msg);
                else fileReceive(msg);
            }
            System.out.println("�������˲���");
        }
        else
        {
            for (Message msg : arrayList) {
                System.out.println("��һ����Ϣ");
                if (msg.getMesType() == null || msg.getMesType().equals("text")) addBoxGroup(msg);
                else if (msg.getMesType().equals("image")) receiveFavorGroup(msg);
                else fileReceiveGroup(msg);
            }
            System.out.println("�������˲�Ⱥ�ģ�");

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
     * ��������ķ���
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
     * ����Ϣ���뵽Ⱥ����
     */
    public void addBoxGroup(Message message1) {
        System.out.println("��һ�������������");
        Pattern ppq = Pattern.compile("\\d{10}");
        Matcher mq = null;
        if (getMy == null)
            mq = ppq.matcher(" ");
        else {
            mq = ppq.matcher(getMy.getAccount());
        }
        boolean mmq = mq.matches();
        System.out.println("׼������ͼƬ");
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
                        // ��ͨ�ı�
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
            if(flow==null)System.out.println("����ƽ��Ϊ��");
            messageBox.setSpacing(0);
            System.out.println("�����������");
            flow.getChildren().add(timebox);
            System.out.println("��Ϣ���Ӽ������");
            flow.getChildren().add(messageBox);
            System.out.println("��Ϣ���Ӽ����������");

        });
    }

    /**
     * @param message1
     * ����Ϣ���뵽����
     */
    public void addBox(Message message1)
    {

        System.out.println("��һ�������������");
        Pattern ppq = Pattern.compile("\\d{10}");
        Matcher mq = null;
        if (getMy == null)
            mq = ppq.matcher(" ");
        else {
            mq = ppq.matcher(getMy.getAccount());
        }
        boolean mmq = mq.matches();
        System.out.println("׼������ͼƬ");
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
                        // ��ͨ�ı�
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
            if(flow==null)System.out.println("����ƽ��Ϊ��");
            messageBox.setSpacing(0);
            System.out.println("�����������");
            flow.getChildren().add(timebox);
            System.out.println("��Ϣ���Ӽ������");
            flow.getChildren().add(messageBox);
            System.out.println("��Ϣ���Ӽ����������");

        });
    }

    /**
     * @param msg
     * ����Ⱥ����Ϣ��ͼƬ��Ϣ�ͱ�����������ڷ��ͷ�������ɫ����Ҳ������ɫΪ�������ͣ���ɫΪ��������
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
                    System.out.println("�������ͼƬд��ʧ��");
                }

                // �� UI �߳��и��� UI
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
        // �����߳�
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * @param msg
     *
     * ����Ⱥ���ļ���һ�����أ����¼���
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
            // ��ͨ�ı�
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
            if(flow==null)System.out.println("����ƽ��Ϊ��");
            messageBox.setSpacing(0);
            messageBox.setOnMouseClicked(e -> {
                Task<Void> task = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        // ִ�к�ʱ����
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
                                alert.setTitle("С����������");
                                alert.setHeaderText("�ļ����سɹ�");
                                alert.show();
                                while (file == null) System.out.println("Ҫѧ��ȴ������������ĺ�����");
                                System.out.println("���ܽ�����");
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

                // ����������ɺ�Ĵ����߼�
                task.setOnSucceeded(taskEvent -> {

                });
                // �������������ں�̨�߳���
                new Thread(task).start();
            });
            flow.getChildren().add(timebox);
            flow.getChildren().add(messageBox);
        });
        //�Լ���ͷ��ͷ���С40*40��

    }

    /**
     * @param msg
     * ����ͼƬ����Ϣ�����
     */
    public void receiveFavor(Message msg) {
        // ����һ�� Task ����
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                File receiveImage = new File(msg.getContent());
                try {
                    FileUtils.writeByteArrayToFile(receiveImage, msg.getTransfer());
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("�������ͼƬд��ʧ��");
                }

                // �� UI �߳��и��� UI
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
        // �����߳�
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * @param msg
     * ���˽����ļ�
     */
    public void fileReceive(Message msg)
    {
        System.out.println("��������ߵ�������");
        //�Լ���ͷ��ͷ���С40*40��
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
            if (flow == null) System.out.println("����ƽ��Ϊ��");
            messageBox.setSpacing(0);
            messageBox.setUserData(msg);
            System.out.println("�ݲ���");
            messageBox.setOnMouseClicked(e -> {
                Task<Void> task = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        // ִ�к�ʱ����
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
                                alert.setTitle("С����������");
                                alert.setHeaderText("�ļ����سɹ�");
                                alert.show();
                                while (file == null) System.out.println("Ҫѧ��ȴ������������ĺ�����");
                                System.out.println("���ܽ�����");
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

                // ����������ɺ�Ĵ����߼�
                task.setOnSucceeded(taskEvent -> {

                });
                // �������������ں�̨�߳���
                new Thread(task).start();
            });
            flow.getChildren().add(timebox);
            flow.getChildren().add(messageBox);
        });


    }


}
