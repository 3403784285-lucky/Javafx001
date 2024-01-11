package controller;
import common.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import request.MessageClientService;
import request.UserClientService;
import transfer.CommonEvent;
import utils.*;
import view.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class ChatRoomController implements Initializable {
    @FXML
    private ImageView userImage;
    @FXML
    private ListView<Friend> friendList;
    @FXML
    private VBox contactpane;
    @FXML
    private VBox messagePane;
    @FXML
    private ListView<Friend> applicationaa;
    @FXML
    private ListView<Friend> friendListView;
    @FXML
    private TextField searchFriend;
    @FXML
    private Button photoSend;
    @FXML
    private Button docuSend;
    @FXML
    private TextArea messagess;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private MenuItem profileIdGroup;
    @FXML
    private MenuItem profileId;
    @FXML
    private Button message;
    @FXML
    private MenuItem mi1;

    @FXML
    private MenuItem mi3;

    @FXML
    private Button contact;

    @FXML
    private MenuItem mi2;

    @FXML
    private MenuItem mi4;
    @FXML
    private ScrollPane emojiscroll;
    @FXML
    private ListView<Friend> groupListView;
    @FXML
    private MenuItem changePassword;
    @FXML
    private FlowPane emojiflow;
    @FXML
    private FlowPane flowPane1;
    @FXML
    private ImageView haha;
    @FXML
    private Button sendButton;
    @FXML
    private AnchorPane hidePane;
    private boolean isMine;
    private  ArrayList<String>accountt=new ArrayList<>();
    private  ArrayList<String>accountg=new ArrayList<>();
    private boolean last=true;public static Friend getMy;
    private  ArrayList<Message>tempMessage=new ArrayList<>();
    private ArrayList<Message>tempMessageGroup=new ArrayList<>();
    private ConcurrentHashMap <String,String>imageA;
    private ConcurrentHashMap <String,String>imageB;
    private ObservableList<Friend> observableList ;
    private ObservableList<Friend> observableList1;
    ObservableList<Friend> observableList2;
    ObservableList<Friend> observableList3;

    public ArrayList<String> getAccountt() {
        return accountt;
    }

    public ArrayList<String> getAccountg() {
        return accountg;
    }

    public ConcurrentHashMap<String, String> getImageA() {
        return imageA;
    }

    public ConcurrentHashMap<String, String> getImageB() {
        return imageB;
    }
    @FXML
    void chatHistory(ActionEvent event) {
        try {
            ChatRecordView.start(new Stage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void addObservableList(Friend f)
   {

           observableList.add(f);
           friendList.setItems(observableList);


   }
    public void addObservableList1(Friend f)
    {

        observableList1.add(f);
        friendListView.setItems(observableList1);


    }
   public void addObservableList3(Friend g)
   {

           observableList3.add(g);
           groupListView.setItems(observableList3);

   }
   public void removeObservableList3(Friend g)
   {

           Iterator<Friend> iterator = observableList3.iterator();
           while (iterator.hasNext()) {
               Friend f = iterator.next();
               if (f.getAccount().equals(g.getAccount())) {
                   iterator.remove();
               }
           }
           groupListView.setItems(observableList3);

   }
    public void removeObservableList(String friend)
    {

            Iterator<Friend> iterator = observableList.iterator();
            while (iterator.hasNext()) {
                Friend f = iterator.next();
                if (f.getAccount().equals(friend)) {
                    iterator.remove();
                }
            }
            friendList.setItems(observableList);


    }
    public void addObservableList2(Friend f)
    {

            observableList2.add(f);
            applicationaa.setItems(observableList2);

    }
    public void fileReceive(Message msg)
    {
        System.out.println("��������ߵ�������");
        //�Լ���ͷ��ͷ���С40*40��
        Platform.runLater(()->
        {
            if(referField(msg))
            {
                HBox timebox = new HBox();
                Label timesend = new Label(msg.getSendTime().toString());
                timesend.setStyle("-fx-font-size: 10px;");
                timebox.getChildren().add(timesend);
                timebox.setAlignment(Pos.TOP_CENTER);
                String sender = msg.getSender();
                ImageView headp = new ImageView();
                flowPane1.setHgap(0);
                Image head = null;
                head = new Image(new File(imageA.get(sender)).toURI().toString());
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
                last = scrollPane.getVvalue() == 1.0;
                if (flowPane1 == null) System.out.println("����ƽ��Ϊ��");
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
                                    Platform.runLater(()->
                                    {
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("С����������");
                                        alert.setHeaderText("�ļ����سɹ�");
                                        alert.show();
                                    });
                                    try {
                                        oo.writeObject(cc);
                                        oo.flush();
                                    } catch (IOException ioException) {
                                        ioException.printStackTrace();
                                    }

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
                flowPane1.getChildren().add(timebox);
                flowPane1.getChildren().add(messageBox);
            }

        });


    }
    public void receiveFavor(Message msg) {
        // ����һ�� Task ����
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                if (referField(msg)) {
                    File receiveImage = new File(msg.getContent());
                    try {
                        FileUtils.writeByteArrayToFile(receiveImage, msg.getTransfer());
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("�������ͼƬд��ʧ��");
                    }

                    // �� UI �߳��и��� UI
                    Platform.runLater(() -> {
                        HBox timebox = new HBox();
                        Label timesend = new Label(msg.getSendTime().toString());
                        timesend.setStyle("-fx-font-size: 10px;");
                        timebox.getChildren().add(timesend);
                        timebox.setAlignment(Pos.TOP_CENTER);
                        String sender = msg.getSender();
                        ImageView headp = new ImageView();
                        flowPane1.setHgap(0);
                        Image head = null;
                        head = new Image(new File(imageA.get(sender)).toURI().toString());
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
                        if (isMine) {
                            timebox.setStyle("-fx-background-color: white");
                            messageBox.setStyle("-fx-background-color: lightblue");
                        }
                        messageBox.setPrefWidth(350);
                        last = scrollPane.getVvalue() == 1.0;
                        messageBox.setSpacing(0);
                        messageBox.setUserData(msg);
                        messageBox.setOnMouseClicked(e ->
                        {
                            File file = new File(msg.getContent());
                            if (Desktop.isDesktopSupported()) {
                                Desktop desktop = Desktop.getDesktop();
                                try {
                                    desktop.open(file);
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
                            }

                        });
                        flowPane1.getChildren().add(timebox);
                        flowPane1.getChildren().add(messageBox);
                    });
                }

                    return null;
            }
        };
        // �����߳�
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
    public  void receiveFavorGroup(Message msg)
    {

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                if(referField(msg))
                {
                    File receiveImage = new File(msg.getContent());
                    try {
                        FileUtils.writeByteArrayToFile(receiveImage, msg.getTransfer());
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("�������ͼƬд��ʧ��");
                    }

                    // �� UI �߳��и��� UI
                    Platform.runLater(() -> {
                        HBox timebox = new HBox();
                        Label timesend = new Label(msg.getSendTime().toString());
                        timesend.setStyle("-fx-font-size: 10px;");
                        timebox.getChildren().add(timesend);
                        timebox.setAlignment(Pos.TOP_CENTER);
                        String sender = msg.getSender();
                        ImageView headp = new ImageView();
                        flowPane1.setHgap(0);
                        Image head = null;
                        head = new Image(new File(imageB.get(sender)).toURI().toString());
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
                        last = scrollPane.getVvalue() == 1.0;
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
                        flowPane1.getChildren().add(timebox);
                        flowPane1.getChildren().add(messageBox);
                    });


                }return null;

            }
        };
        // �����߳�
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
    public void errorDeal()
    {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("С����������");
            alert.setHeaderText("�����ж�");
            alert.setContentText("���˳�");
            alert.show();

        leaveAction();

    }
    public void leaveAction() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                if (tempMessage != null||tempMessage.size()!=0) {
                    new MessageClientService().insertMessage(tempMessage);
                    System.out.println("�����˲Ż��뿪");
                }
                if (tempMessageGroup != null) {
                    new MessageClientService().insertMessageGroup(tempMessageGroup);
                    System.out.println("�����˲Ż��뿪");
                }
                new UserClientService().logOut();
                return null;
            }
        };

        Thread thread = new Thread(task);
        thread.start();
    }
    public ImageView getUserImage() {
        return userImage;
    }

    private void selectEmoji(String id)
    {

            messagess.appendText("["+id+"]");
            messagess.positionCaret(messagess.getText().length());

    }
    public void fileReceiveGroup(Message msg)
    {
        Pattern pp=Pattern.compile("\\d{10}");
        Matcher m=pp.matcher(getMy.getAccount());
        boolean mm=m.matches();
        Platform.runLater(()->
        {
            if(referField(msg))
            {
                HBox timebox=new HBox();
                Label timesend=new Label(msg.getSendTime().toString());
                timesend.setStyle("-fx-font-size: 10px;");
                timebox.getChildren().add(timesend);
                timebox.setAlignment(Pos.TOP_CENTER);
                String sender=msg.getSender();
                ImageView headp=new ImageView();
                flowPane1.setHgap(0);
                Image head=null;
                head=new Image(new File(imageB.get(sender)).toURI().toString());
                headp.setImage(head);
                headp.setFitWidth(40);
                headp.setFitHeight(40);
                if(sender.equals(ManageClientToThread.u.getUserAccount()))isMine=true;
                else isMine=false;
                HBox messageBox=new HBox();
                HBox.setHgrow(messageBox, Priority.ALWAYS);
                if(!isMine){
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
                if(isMine){
                    timebox.setStyle("-fx-background-color: white");
                    messageBox.setStyle("-fx-background-color: lightblue");
                }
                messageBox.setPrefWidth(350);
                messageBox.setPadding(new Insets(10, 0, 10, 0));
                last = scrollPane.getVvalue() == 1.0;
                if(flowPane1==null)System.out.println("����ƽ��Ϊ��");
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
                flowPane1.getChildren().add(timebox);
                flowPane1.getChildren().add(messageBox);
            }

        });
        //�Լ���ͷ��ͷ���С40*40��

    }

    private void addEmoji()
    {
        Platform.runLater(()->
        {
            emojiflow.getChildren().clear();
            final int EMOJI_SIZE = 15; // ������ĳߴ�
            emojiflow.setPadding(new Insets(15));
            emojiflow.setHgap(10);
            emojiflow.setVgap(10);
            for (Emoji e:EmojiUtils.array) {

                ImageView img=new ImageView();
                img.setImage(new Image(new File(e.getImagepath()).toURI().toString()));
                System.out.println("���������������");
                img.setFitWidth(EMOJI_SIZE);
                img.setFitHeight(EMOJI_SIZE);
                emojiflow.getChildren().add(img);
                img.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    selectEmoji(e.getId());
                    emojiflow.setVisible(false);
                    emojiflow.setDisable(true);
                    emojiscroll.setDisable(true);
                    emojiscroll.setVisible(false);

                });
            }
        });

    }
    public boolean referField(Message msg)
    {
        boolean mark=false;
        Pattern pp=Pattern.compile("\\d{10}");
        Matcher m=pp.matcher(getMy.getAccount());
        boolean mm=m.matches();
        if(mm)
        {
            if(msg.getGetter().equals(getMy.getAccount())||msg.getSender().equals(getMy.getAccount()))
            {
                mark=true;
            }
        }
        else
        {
            if (msg.getGetter().equals(getMy.getAccount()))
                mark=true;
        }

        return mark;
    }
    public void addBox(Message message1)
    {
        if(referField(message1))
        {
            System.out.println("���ҿ��������Ϣ�Ľ�����"+message1.getGetter());
            System.out.println("���ҿ������������˭"+getMy.getAccount());
            System.out.println("��һ�������������");
            Pattern ppq = Pattern.compile("\\d{10}");
            Matcher  mq = ppq.matcher(getMy.getAccount());
            boolean mmq = mq.matches();
            System.out.println("׼������ͼƬ");
            Platform.runLater(() -> {
                HBox timebox = new HBox();
                Label timesend = new Label(message1.getSendTime().toString());
                timesend.setStyle("-fx-font-size: 10px;");
                timebox.getChildren().add(timesend);
                timebox.setAlignment(Pos.TOP_CENTER);
                String sender = message1.getSender();
                ImageView headp = new ImageView();
                Image head = null;
                System.out.println("�����浽����ʲô��"+imageA);
                System.out.println("����ta��ͷ��" + imageA.get(sender));
                head = new Image(new File(imageA.get(sender)).toURI().toString());
                headp.setImage(head);
                headp.setFitWidth(40);
                headp.setFitHeight(40);
                if (sender.equals(ManageClientToThread.u.getUserAccount()))
                    isMine = true;
                else
                    isMine = false;
                Pattern pp = Pattern.compile("\\d{1,}\\.png");
                Pattern ppp = Pattern.compile("[A-Z]:\\\\.+");
                String[] segments = message1.getContent().split("[\\[\\]]");
                HBox messageBox = new HBox();
                if (!isMine) {
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
                if(isMine){
                    timebox.setStyle("-fx-background-color: white");
                    messageBox.setStyle("-fx-background-color: lightblue");
                    messageBox.setAlignment(Pos.TOP_LEFT);
                }
                messageBox.setPrefWidth(350);
                last = scrollPane.getVvalue() == 1.0;
                if(flowPane1==null)System.out.println("����ƽ��Ϊ��");
                messageBox.setSpacing(0);
                System.out.println("�����������");
                flowPane1.getChildren().add(timebox);
                System.out.println("��Ϣ���Ӽ������");
                flowPane1.getChildren().add(messageBox);
                System.out.println("��Ϣ���Ӽ����������");

            });
        }




}

    public void setUserImage(ImageView userImage) {
        this.userImage = userImage;
    }

    public MenuItem getMi1() {
        return mi1;
    }

    public void setMi1(String text) {
        this.mi1.setText(text);
    }

    public MenuItem getMi3() {
        return mi3;
    }

    public void setMi3(String text) {
        this.mi3.setText(text);
    }

    public MenuItem getMi2() {
        return mi2;
    }

    public void setMi2(String text) {
        this.mi2.setText(text);
    }

    public MenuItem getMi4() {
        return mi4;
    }

    public void setMi4(String text) {
        this.mi4.setText(text);
    }
    //�Լ���ͷ��ͷ���С40*40��
    public void addBoxGroup(Message message1) {
        // Run JavaFX UI update on the JavaFX application thread

        if(referField(message1))
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
                HBox timebox = new HBox();
                Label timesend = new Label(message1.getSendTime().toString());
                timesend.setStyle("-fx-font-size: 10px;");
                timebox.getChildren().add(timesend);
                timebox.setAlignment(Pos.TOP_CENTER);
                String sender = message1.getSender();
                ImageView headp = new ImageView();
                Image head = null;
                System.out.println("�����浽����ʲô��"+imageB);
                System.out.println("����ta��ͷ��" + imageB.get(sender));
                head = new Image(new File(imageB.get(sender)).toURI().toString());
                headp.setImage(head);
                headp.setFitWidth(40);
                headp.setFitHeight(40);
                if (sender.equals(ManageClientToThread.u.getUserAccount()))
                    isMine = true;
                else
                    isMine = false;
                Pattern pp = Pattern.compile("\\d{1,}\\.png");
                Pattern ppp = Pattern.compile("[A-Z]:\\\\.+");
                String[] segments = message1.getContent().split("[\\[\\]]");
                HBox messageBox = new HBox();
                if (!isMine) {
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
                if(isMine){
                    timebox.setStyle("-fx-background-color: white");
                    messageBox.setStyle("-fx-background-color: lightblue");
                    messageBox.setAlignment(Pos.TOP_LEFT);
                }
                messageBox.setPrefWidth(350);
                last = scrollPane.getVvalue() == 1.0;
                if(flowPane1==null)System.out.println("����ƽ��Ϊ��");
                messageBox.setSpacing(0);
                System.out.println("�����������");
                flowPane1.getChildren().add(timebox);
                System.out.println("��Ϣ���Ӽ������");
                flowPane1.getChildren().add(messageBox);
                System.out.println("��Ϣ���Ӽ����������");

            });
        }

    }
    public void refreshFriendListOnline(String account)
    {

            ArrayList<Friend> tempList = new ArrayList<Friend>();
            for (Friend f : observableList) {
                if (f.getAccount().equals(account)) {
                    f.setOnline(false);
                    tempList.add(f);
                }
            }
            observableList.removeAll(tempList);
            observableList.addAll(tempList);
            friendList.setItems(observableList);


        System.out.println("�ѽ���Ա����Ϊ�����߲�������ӵ��б���");
    }
    public void refreshFriendListLogin(String account)
    {

            ArrayList<Friend> tempList = new ArrayList<Friend>();
            for (Friend f : observableList) {
                System.out.println("ѭ����ʲô");
                if (f.getAccount().equals(account)) {
                    f.setOnline(true);
                    tempList.add(f);
                }
            }
            observableList.removeAll(tempList);
            observableList.addAll(tempList);
            friendList.setItems(observableList);



    }
    public void refreshFriendListViewOnline(String account)
    {

            ArrayList<Friend> tempList = new ArrayList<Friend>();
            for (Friend f : observableList1) {
                if (f.getAccount().equals(account)) {
                    f.setOnline(false);
                    tempList.add(f);
                }
            }
            observableList1.removeAll(tempList);
            observableList1.addAll(tempList);
            friendList.setItems(observableList1);


    }
    private void sendMessageUtil()
    {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                String text = messagess.getText();
                messagess.setText("");
                Pattern pp=Pattern.compile("\\d{10}");
                Matcher m=pp.matcher(getMy.getAccount());
                boolean mm=m.matches();
                Message theMessage = new Message();
                theMessage.setSender(ManageClientToThread.u.getUserAccount());
                theMessage.setGetter(getMy.getAccount());
                System.out.println("Ϊʲô����Ϣ��û��");
                theMessage.setSendTime(new Timestamp(new Date().getTime()));
                theMessage.setContent(text);
                theMessage.setMesType("text");
                System.out.println(tempMessage);
                if (mm)
                {
                    tempMessage.add(theMessage);
                }
                else
                {
                    tempMessageGroup.add(theMessage);
                    System.out.println("�ͻ��˲���"+tempMessageGroup);
                }

                ThreadClientConnect tcc= ManageClientToThread.getConn(ManageClientToThread.uOriginal);
                ObjectOutputStream oos= tcc.getDos();
                if(mm)
                {
                    CommonEvent commonEvent=new CommonEvent("judgeIsExistent",imageA.get(theMessage.getSender()));
                    try {
                        oos.writeObject(commonEvent);oos.flush();

                    } catch (IOException e) {
                        e.printStackTrace();System.out.println("������󣬷�����Ϣ");
                    }
                    System.out.println("����˽����Ϣ----------------------------����");
                    addBox(theMessage);
                    System.out.println("˽����Ϣ���");
                }
                else
                {
                    System.out.println("������Ⱥ����Ϣ");
                    CommonEvent commonEvent=new CommonEvent("judgeIsExistent",imageB.get(theMessage.getSender()));
                    System.out.println("Ⱥ����һ��");

                    try {
                        oos.writeObject(commonEvent);oos.flush();
                        System.out.println("Ⱥ����2��");
                    } catch (IOException e) {
                        e.printStackTrace();System.out.println("������󣬷�����Ϣ");
                    }
                    //addBoxGroup(theMessage);
                }
                System.out.println("���Ծ�����");
                new MessageClientService().judgeIsExistent(theMessage);
                // ������ִ����Ĵ����߼�
                return null;
            }
        };

        task.setOnSucceeded(e -> {
            // ����ִ����ɺ�Ĳ��������Ը���UI���

                // ���������UI���
                String result = task.getMessage(); // ��ȡ���񷵻صĽ��
                // ����UI����Ĳ���
        });

        // ��������
        Thread thread = new Thread(task);
        thread.setDaemon(true); // ����Ϊ�ػ��̣߳�����Ӧ�ùرն��ر�
        thread.start();
    }
    public void friendForm(ListView<Friend>listView)
    {
        Platform.runLater(()->
        {
            hidePane.setVisible(false);
            profileIdGroup.setDisable(true);
            profileId.setDisable(false);
        });
        Task<ArrayList<Message>> getFriendsTask = new Task<ArrayList<Message>>() {
            @Override
            protected ArrayList<Message> call() throws Exception {
                messagess.setDisable(false);
                sendButton.setDisable(false);
                messagess.setText("");
                Friend selectedFriend = listView.getSelectionModel().getSelectedItem();
                getMy = selectedFriend;
                System.out.println("����ĺ������ƣ�"+getMy.getNickname());
                accountt.clear();
                accountt.add(getMy.getAccount());
                System.out.println("������˵��˺�"+getMy.getAccount());
                accountt.add(ManageClientToThread.u.getUserAccount());
                System.out.println("�����ҵ��˺���"+accountt);
                //������Դ���һ�£����ж�
                if (tempMessage != null) {
                    new MessageClientService().insertMessage(tempMessage);
                    tempMessage.clear();
                    System.out.println("�����˲Ż����");
                }
                System.out.println("Ϊʲô����ȥ");
                if (tempMessageGroup != null) {
                    System.out.println("�ҾͲ�����");
                    new MessageClientService().insertMessageGroup(tempMessageGroup);
                    tempMessageGroup.clear();
                    System.out.println("�����˲Ż����");
                }

                imageA = new UserClientService().imageAvar(accountt);
                return new MessageClientService().initMessagerecord(ManageClientToThread.u.getUserAccount(), selectedFriend.getAccount());

            }
        };
        // ����������ɺ�Ĵ����߼�
        getFriendsTask.setOnSucceeded(e ->{
            ArrayList<Message> messages = getFriendsTask.getValue();
            // �����߳��и���UI
            Platform.runLater(() -> {
                // ���������е�����
                flowPane1.getChildren().clear();
                // �����ص������¼��䵽�������
            });
            for (Message msg : messages) {
                System.out.println("��һ����Ϣ");
                if (msg.getMesType() == null || msg.getMesType().equals("text")) addBox(msg);
                else if (msg.getMesType().equals("image")) receiveFavor(msg);
                else fileReceive(msg);
            }
            System.out.println("�������˲���");

        });
        // ��������
        Thread thread3 = new Thread(getFriendsTask);
        thread3.setDaemon(true); // ����Ϊ�ػ��̣߳���ѡ��
        thread3.start();

    }
    public void groupForm(ListView<Friend> listView)
    {
        Task<ArrayList<Message>> getFriendsTask = new Task<ArrayList<Message>>() {
            @Override
            protected ArrayList<Message> call() throws Exception {
                Platform.runLater(()->
                {
                    hidePane.setVisible(false);
                    profileIdGroup.setDisable(false);
                    profileId.setDisable(true);
                });
                messagess.setDisable(false);
                sendButton.setDisable(false);
                messagess.setText("");
                Friend selectedFriend2 = listView.getSelectionModel().getSelectedItem();
                getMy = selectedFriend2;
                accountg = new UserClientService().groupSearchAccount(selectedFriend2.getAccount());
                System.out.println("���������˺�");
                //�����Ǽ����������ݣ����Գ��Էֶ����洢�����ȣ���ȥѧϰ��֪ʶ����˳�����洢����ηֶΰ�ť������Ϣ��Ҳ������ô����ǰʮ��
                if (tempMessageGroup != null) {
                    new MessageClientService().insertMessageGroup(tempMessageGroup);
                    tempMessageGroup.clear();
                }
                if (tempMessage != null) {
                    new MessageClientService().insertMessage(tempMessage);
                    tempMessage.clear();
                }
                System.out.println("ͷ��׼��");
                imageB = new UserClientService().imageAvar(accountg);
                return new MessageClientService().initMessageRecordGroup(selectedFriend2.getAccount());
                // ������ִ�м��������¼���ݵĲ���
            }
        };
        getFriendsTask.setOnSucceeded(e -> {
            ArrayList<Message> Groupmessages = getFriendsTask.getValue();
            Platform.runLater(()->
            {
                flowPane1.getChildren().clear();
            });

            for (Message msg : Groupmessages) {
                System.out.println("��һ����Ϣ");
                if (msg.getMesType() == null || msg.getMesType().equals("text")) addBoxGroup(msg);
                else if (msg.getMesType().equals("image")) receiveFavorGroup(msg);
                else fileReceiveGroup(msg);
            }
            System.out.println("�������˲���");
        });
        Thread thread1 = new Thread(getFriendsTask);
        thread1.setDaemon(true); // ����Ϊ�ػ��̣߳���ѡ��
        thread1.start();
        // �������������¼���ݵ�����
    }
    @FXML
    void sendButtonAction(ActionEvent event) {
       sendMessageUtil();

    }
    @FXML
    void changePasswordAction(ActionEvent event) {
        LoginController.variable=true;

            ChatRoomView.chatRoomStage.close();
            try {
                RegisterView.start(new Stage());
            } catch (IOException e) {
                System.out.println("�������⣬�޸���������ʧ��");
            }


    }
    @FXML
    void profileAction(ActionEvent event) {
         ChatRoomView.chatRoomStage.close();
           try {
               ProFileView.start(new Stage());
           } catch (IOException e) {
               e.printStackTrace();
           }

    }
    @FXML
    void messageAction(ActionEvent event) {
        messagePane.setVisible(true);
        messagePane.setDisable(false);
        hidePane.setVisible(true);
        contactpane.setDisable(true);
        contactpane.setVisible(false);
    }
    @FXML
    void logoutAction(ActionEvent event) {
        leaveAction();
    }
    @FXML
    void messageSend(KeyEvent event) {
            if (event.getCode() == KeyCode.ENTER) {
               sendMessageUtil();
            }
    }
    @FXML
    void contactAction(ActionEvent event) {
        messagePane.setVisible(false);
        messagePane.setDisable(true);
        hidePane.setVisible(true);
        contactpane.setDisable(false);
        contactpane.setVisible(true);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        emojiflow.setVisible(false);
        emojiflow.setDisable(true);
        emojiscroll.setDisable(true);
        emojiscroll.setVisible(false);
        hidePane.setVisible(true);
            Task<Void> initializationTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    UserClientService ucs1 = new UserClientService();
                    ucs1.initProfile();
                    ArrayList<String>arrayListAlways=ucs1.initAlways(ManageClientToThread.u.getUserAccount());
                    mi1.setText(arrayListAlways.get(0));
                    mi2.setText(arrayListAlways.get(1));
                    mi3.setText(arrayListAlways.get(2));
                    mi4.setText(arrayListAlways.get(3));
                    // �����û�ͼƬ��Ҫ��JavaFXӦ�ó����I�߳���ִ��
                    Platform.runLater(() -> {
                        userImage.setImage(new Image(new File(ManageClientToThread.u.getUserImage()).toURI().toString()));
                    });
                    observableList = FXCollections.observableArrayList(ucs1.indicatefg());
                    observableList1= FXCollections.observableArrayList(ucs1.indicatefg1());
                    observableList2 = FXCollections.observableArrayList(ucs1.indicatefg2());
                    observableList3 = FXCollections.observableArrayList(ucs1.indicatefg3());
                    Platform.runLater(() -> {
                        groupListView.setCellFactory(param -> new FriendListCellMultiS());
                        applicationaa.setCellFactory(param -> new FriendListCellMultiS());
                        friendList.setCellFactory(param -> new FriendListCellMultiS());
                        friendListView.setCellFactory(param -> new FriendListCellMulti());
                        //�ȰѺ��Ѻ�Ⱥ��Ⱥ�ѳ���
                        friendListView.setItems(observableList1);
                        friendList.setItems(observableList);
                        applicationaa.setItems(observableList2);
                        groupListView.setItems(observableList3);
                    });
                    scrollPane.vvalueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
                        if (last) {
                            scrollPane.setVvalue(1.0);
                            last = false;
                        }
                    });


                    return null;
                }
            };
            // ������ʼ���������
            initializationTask.setOnSucceeded(e -> {
                // ������ִ�г�ʼ����ɺ�Ĳ����������Ҫ����UI���������ʹ��Platform.runLater()
            });
            // ������ʼ������
            Thread thread = new Thread(initializationTask);
            thread.setDaemon(true);
            thread.start();
        groupListView.setOnMouseClicked(event -> {
            // ����һ����̨���������������¼����
            groupForm(groupListView);

        });
        applicationaa.setOnMouseClicked(event -> {
            Task<ArrayList<Message>> getFriendsTask = new Task<ArrayList<Message>>() {
                @Override
                protected ArrayList<Message> call() throws Exception {
                    Platform.runLater(()->
                    {
                       hidePane.setVisible(true);
                    });
                    Friend clickedFriend = applicationaa.getSelectionModel().getSelectedItem();
                    System.out.println(clickedFriend.getNickname()+"--------->������ ");
                    System.out.println(clickedFriend.getAccount());
                    // ����ѡ�еĺ��Ѷ��� clickedFriend
                    if (clickedFriend != null) {
                        getMy = clickedFriend;
                        System.out.println("Clicked Friend: " + clickedFriend.getAccount());
                        Platform.runLater(()->
                        {

                            ChatRoomView.chatRoomStage.close();
                            try {
                                ProFileView.start(new Stage());
                            } catch (IOException e) {
                                e.printStackTrace();
                                System.out.println("�����ʧ��");
                            }
                        });

                        // ���������߼�...
                    }

                    return null;
                }
            };
            // ����������ɺ�Ĵ����߼�
            getFriendsTask.setOnSucceeded(e ->{

                });
            // ��������
            Thread thread2 = new Thread(getFriendsTask);
            thread2.setDaemon(true); // ����Ϊ�ػ��̣߳���ѡ��
            thread2.start();

        });
        friendList.setOnMouseClicked(event -> {
                // ����һ����̨���������������¼����
                friendForm(friendList);
                });
        friendListView.setOnMouseClicked(event ->
        {
            Friend selectedFriend = friendListView.getSelectionModel().getSelectedItem();
            getMy = selectedFriend;
            Pattern pp=Pattern.compile("\\d{10}");
            Matcher m=pp.matcher(getMy.getAccount());
            boolean mm=m.matches();
            if(mm)
            {
                Platform.runLater(()->
                {
                    hidePane.setVisible(false);
                    profileIdGroup.setDisable(true);
                    profileId.setDisable(false);
                });

                Task<ArrayList<Message>> getFriendsTask = new Task<ArrayList<Message>>() {
                    String status=null;
                    @Override
                    protected ArrayList<Message> call() throws Exception {
                       status=new UserClientService().askRelation(getMy.getAccount()+"&"+ManageClientToThread.u.getUserAccount());
                        System.out.println("����ĺ������ƣ�"+getMy.getNickname());
                        if(status.equals("0")||status.equals("5"))
                        {
                            messagess.setText("���ǻ����Ǻ���Ŷ���ȼ�Ϊ���Ѱɣ�������Ͻǵ������㼴�ɼ�Ϊ����");
                            messagess.setDisable(true);
                            sendButton.setDisable(true);
                            docuSend.setDisable(true);
                            photoSend.setDisable(true);

                        }
                        else if(status.equals("2"))
                        {
                            messagess.setText("���ǻ����Ǻ���Ŷ���ȵȴ�����ͬ���");
                            messagess.setDisable(true);
                            sendButton.setDisable(true);
                            docuSend.setDisable(true);
                            photoSend.setDisable(true);
                        }
                        else if(status.equals("3"))
                        {
                            messagess.setText("���ǻ����Ǻ���Ŷ����ͬ�����������ĺ��������");
                            messagess.setDisable(true);
                            sendButton.setDisable(true);
                            docuSend.setDisable(true);
                            photoSend.setDisable(true);
                        }
                        else if(status.equals("4"))
                        {
                            messagess.setText("���ǻ����Ǻ���Ŷ���ȼ�Ϊ���Ѱɣ�������Ͻǵ������㼴�ɼ�Ϊ����");
                            messagess.setDisable(true);
                            sendButton.setDisable(true);
                            docuSend.setDisable(true);
                            photoSend.setDisable(true);

                        }
                        else if(status.equals("1"))
                        {
                            messagess.setText("");
                            messagess.setDisable(false);
                            sendButton.setDisable(false);
                            docuSend.setDisable(false);
                            photoSend.setDisable(false);
                            accountt.clear();
                            accountt.add(getMy.getAccount());
                            System.out.println("������˵��˺�"+getMy.getAccount());
                            accountt.add(ManageClientToThread.u.getUserAccount());
                            System.out.println("�����ҵ��˺���"+accountt);
                            //������Դ���һ�£����ж�
                            if (tempMessage != null) {
                                new MessageClientService().insertMessage(tempMessage);
                                tempMessage.clear();
                                System.out.println("�����˲Ż����");
                            }
                            if (tempMessageGroup != null) {
                                new MessageClientService().insertMessageGroup(tempMessageGroup);
                                tempMessageGroup.clear();
                                System.out.println("�����˲Ż����");
                            }

                            imageA = new UserClientService().imageAvar(accountt);
                            return new MessageClientService().initMessagerecord(ManageClientToThread.u.getUserAccount(), selectedFriend.getAccount());
                        }
                       System.out.println(status+"---->������λ");
                        System.out.println("�ѵ��ǿ�");
                        return null;

                    }
                };
                // ����������ɺ�Ĵ����߼�
                getFriendsTask.setOnSucceeded(e ->{
                    ArrayList<Message> messages = getFriendsTask.getValue();
                    // �����߳��и���UI
                    Platform.runLater(() -> {
                        // ���������е�����
                        flowPane1.getChildren().clear();
                        // �����ص������¼��䵽�������
                    });
                    if(messages!=null)
                    {
                        for (Message msg : messages) {
                            System.out.println("��һ����Ϣ");
                            if (msg.getMesType() == null || msg.getMesType().equals("text")) addBox(msg);
                            else if (msg.getMesType().equals("image")) receiveFavor(msg);
                            else fileReceive(msg);
                        }
                        System.out.println("�������˲���");
                    }

                });
                // ��������
                Thread thread3 = new Thread(getFriendsTask);
                thread3.setDaemon(true); // ����Ϊ�ػ��̣߳���ѡ��
                thread3.start();


            }
            else
            {
                Platform.runLater(()->
                {
                    hidePane.setVisible(false);
                    profileIdGroup.setDisable(false);
                    profileId.setDisable(true);
                });

                Task<ArrayList<Message>> getFriendsTask = new Task<ArrayList<Message>>() {
                    String status=null;
                    @Override
                    protected ArrayList<Message> call() throws Exception {
                        status=new UserClientService().askRelationGroup(getMy.getAccount()+"&"+ManageClientToThread.u.getUserAccount());
                        System.out.println("����ĺ������ƣ�"+getMy.getNickname());
                        if(status.equals("0"))
                        {
                            messagess.setText("�㻹û�м���Ⱥ��Ŷ��������Ͻǵ������㼴�ɼ���Ⱥ��");
                            messagess.setDisable(true);
                            sendButton.setDisable(true);
                            docuSend.setDisable(true);
                            photoSend.setDisable(true);
                        }
                        else
                        {
                            messagess.setText("");
                            messagess.setDisable(false);
                            sendButton.setDisable(false);
                            docuSend.setDisable(false);
                            photoSend.setDisable(false);
                            accountg = new UserClientService().groupSearchAccount(selectedFriend.getAccount());
                            System.out.println("���������˺�");
                            //�����Ǽ����������ݣ����Գ��Էֶ����洢�����ȣ���ȥѧϰ��֪ʶ����˳�����洢����ηֶΰ�ť������Ϣ��Ҳ������ô����ǰʮ��
                            if (tempMessageGroup != null) {
                                new MessageClientService().insertMessageGroup(tempMessageGroup);
                                tempMessageGroup.clear();
                            }
                            if (tempMessage != null) {
                                new MessageClientService().insertMessage(tempMessage);
                                tempMessage.clear();
                            }
                            System.out.println("ͷ��׼��");
                            imageB = new UserClientService().imageAvar(accountg);
                            return new MessageClientService().initMessageRecordGroup(selectedFriend.getAccount());
                        }
                        System.out.println(status+"---->������λ");
                        System.out.println("�ѵ��ǿ�");
                        return null;



                    }
                };
                // ����������ɺ�Ĵ����߼�
                getFriendsTask.setOnSucceeded(e ->{

                    ArrayList<Message> Groupmessages = getFriendsTask.getValue();
                    Platform.runLater(()->
                    {
                        flowPane1.getChildren().clear();
                    });


                    System.out.println("�������˲���");
                    if(Groupmessages!=null)
                    { for (Message msg : Groupmessages) {
                        System.out.println("��һ����Ϣ");
                        if (msg.getMesType() == null || msg.getMesType().equals("text")) addBoxGroup(msg);
                        else if (msg.getMesType().equals("image")) receiveFavorGroup(msg);
                        else fileReceiveGroup(msg);
                    }

                    }

                });
                // ��������
                Thread thread3 = new Thread(getFriendsTask);
                thread3.setDaemon(true); // ����Ϊ�ػ��̣߳���ѡ��
                thread3.start();

            }

        });
        scrollPane.setVvalue(1);
    }
    @FXML
    void imageClicked(MouseEvent event) {
        try {
            ModifyView.start(new Stage());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("������ش���");
        }

    }
    @FXML
    void imageChoose(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

        // Run file chooser in a separate JavaFX thread
        Task<File> fileChooserTask = new Task<File>() {
            @Override
            protected File call() throws Exception {
                return fileChooser.showOpenDialog(ChatRoomView.chatRoomStage);
            }
        };
        fileChooserTask.setOnSucceeded(e -> {

            File selectedFile = fileChooserTask.getValue();
            if (selectedFile != null) {
                String imagePath = selectedFile.getAbsolutePath();
                Message msg = new Message();
                msg.setGetter(getMy.getAccount());
                Pattern pp=Pattern.compile("\\d{10}");
                Matcher m=pp.matcher(msg.getGetter());
                boolean mm=m.matches();
                msg.setSender(ManageClientToThread.u.getUserAccount());
                msg.setMesType("image");
                msg.setContent("D:\\clientreceiver\\" + IdUtils.simpleUUID() + ".jpg");
                msg.setSendTime(new Timestamp(new Date().getTime()));
                try {
                    msg.setTransfer(FileUtils.readFileToByteArray(new File(imagePath)));
                    System.out.println(imagePath);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    System.out.println("��������ļ���ȡʧ��");
                }
                new MessageClientService().sendMessageImage(msg);

                System.out.println("��Ҫ����ͼƬ");
                tempMessage.add(msg);
                if(mm)
                {
                    receiveFavor(msg);
                }
                else
                {
                    receiveFavorGroup(msg);
                }

            }
        });

        fileChooserTask.setOnFailed(e -> {
            // Handle failure
            fileChooserTask.getException().printStackTrace();


        });
        // Execute the task on the JavaFX application thread
        Platform.runLater(fileChooserTask);
    }
    @FXML
    void clickedF(ActionEvent event) {

            String searchQuery = searchFriend.getText().trim();
            if (searchQuery == null || searchQuery.equals("")) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("С����������");
                    alert.setHeaderText("��������Ϊ��");
                    alert.show();
                });
            }
            else
            {
                ObservableList<Friend> searchResults = FXCollections.observableArrayList();
                for(Friend friend:observableList1)
                {
                    if(friend.getAccount().contains(searchQuery)||friend.getNickname().contains(searchQuery))
                    {
                        searchResults.add(friend);
                    }
                }

                friendListView.setItems(searchResults);
            }

    }
    @FXML
    void expression(ActionEvent event) {
        addEmoji();
        emojiflow.setVisible(true);
        emojiflow.setDisable(false);
        emojiscroll.setDisable(false);
        emojiscroll.setVisible(true);

    }
    @FXML
    void fileSend(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                File selectedFile = fileChooser.showOpenDialog(ChatRoomView.chatRoomStage);
                if (selectedFile != null) {
                    String filePath = selectedFile.getAbsolutePath();
                    String fileName = selectedFile.getName();
                    Message msg = new Message();
                    msg.setGetter(getMy.getAccount());
                    msg.setSender(ManageClientToThread.u.getUserAccount());
                    msg.setMesType("file");
                    msg.setContent(fileName);
                    msg.setSendTime(new Timestamp(new Date().getTime()));
                    ThreadClientConnect tcc = ManageClientToThread.getConn(ManageClientToThread.uOriginal);
                    tempMessage.add(msg);
                    Task<ArrayList<Message>> fileSendTask = new Task<ArrayList<Message>>() {
                        @Override
                        protected ArrayList<Message> call() throws Exception {
                            try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
                                long resumePosition=new UserClientService().getPosition();
                                ObjectOutputStream oos = tcc.getDos();
                                CommonEvent commonEvent = new CommonEvent("fileSend", msg);
                                fileInputStream.skip(resumePosition);
                                System.out.println(resumePosition);
                                oos.writeObject(commonEvent);
                                oos.flush();
                                byte[] buffer = new byte[1024*1024];
                                int bytesRead;
                                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                                    FileData customData = new FileData(bytesRead, buffer);
                                    oos.writeObject(customData);
                                    oos.writeObject(resumePosition+bytesRead);oos.flush();
                                    System.out.println(bytesRead);
                                }
                                // ������ɵı��
                                oos.writeObject(null);
                                oos.flush();
                                System.out.println("�������������ⲻ�����ƴ��ļ���������");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return tempMessage;
                        }
                    };
                    fileSendTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent event) {
                            // �ļ���������ɹ���ɺ�Ĳ���
                            ArrayList<Message> result = fileSendTask.getValue();
                            Pattern pp=Pattern.compile("\\d{10}");
                            Matcher m=pp.matcher(msg.getGetter());
                            boolean mm=m.matches();
                            if(mm)
                            {
                                fileReceive(msg);
                            }
                            else
                            {
                                fileReceiveGroup(msg);
                            }


                            // ��������Ҫ�Ĵ���
                        }
                    });
                    // �����������µ��߳�ִ���ļ���������
                    Thread fileSendThread = new Thread(fileSendTask);
                    fileSendThread.setDaemon(true);
                    fileSendThread.start();
                }

            // ��������...



    }
    @FXML
    void menuItem1(ActionEvent event) {
       messagess.appendText(mi1.getText());
        messagess.positionCaret(messagess.getText().length()); // �����λ������Ϊĩβ
    }
    @FXML
    void menuItem2(ActionEvent event) {
        messagess.appendText(mi1.getText());
        messagess.positionCaret(messagess.getText().length()); // �����λ������Ϊĩβ
    }
    @FXML
    void menuItem3(ActionEvent event) {
        messagess.appendText(mi1.getText());
        messagess.positionCaret(messagess.getText().length()); // �����λ������Ϊĩβ
    }
    @FXML
    void menuItem4(ActionEvent event) {
        messagess.appendText(mi1.getText());
        messagess.positionCaret(messagess.getText().length()); // �����λ������Ϊĩβ
    }
    @FXML
    void changeAlways(ActionEvent event) {
        try {
            alwaysView.start(new Stage());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    @FXML
    void creatGroup(ActionEvent event) {

    Platform.runLater(()->
    { ChatRoomView.chatRoomStage.close();
        try {
            GroupCreateView.start(new Stage());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("�����ʧ��");
        }
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("С����������");
        alert.setHeaderText("�������ѡ����Ҫ����ĺ��ѣ���������1��");
        alert.show();

    });

    }
    @FXML
    void profileActionGroup(ActionEvent event) {
        try {
            ChatRoomView.chatRoomStage.close();
            GroupProfileView.start(new Stage());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("�����ʧ��");
        }

    }


}













