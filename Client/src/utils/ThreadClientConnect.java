package utils;
import common.*;
import controller.ChatRoomController;
import javafx.application.Platform;
import transfer.CommonEvent;
import view.ChatRoomView;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ThreadClientConnect extends Thread{
    private Socket client; //在线程里面的得到Socket
    private boolean isRunning;
    private ObjectOutputStream dos;
    private ObjectInputStream dis;
    public static CommonEvent[]ms=new CommonEvent[100];

//保持通信
    public ThreadClientConnect(Socket client) {
        this.client = client;
        isRunning=true;
        try {
            dos=new ObjectOutputStream(client.getOutputStream());
            dis=new ObjectInputStream(client.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("客户端保持通讯失败咯");
            isRunning=false;
        }
    }

    public CommonEvent receive() {
        CommonEvent msg = null;
        try {

                msg = (CommonEvent) dis.readObject();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("客户端接收消息失败");
        } catch (ClassNotFoundException e) {
            System.out.println("客户端接收消息为空");
        }
        return msg;
    }

    @Override
    public void run() {
        //需要无限通讯，做成循环
               while (isRunning) {//没读到数据不报错一直等待   数据
                   System.out.println("客户端线程，等待从服务器端发送消息");
                   CommonEvent h =receive();
                   System.out.println("其实我已经接到了");
                   if(h.getType().equals(MessageType.MESSAGE_PROFILE_SUCCEED)||h.getType().equals(MessageType.MESSAGE_PROFILE_FAIL))
                   {
                       ms[1]=h;
                   }
                   else if(h.getType().equals("searchfgResult"))
                   {
                       ms[2]=h;
                   }
                   else if(h.getType().equals("typeJudge"))
                   {
                       ms[3]=h;
                   }
                   else if(h.getType().equals(MessageType.MESSAGE_SEARCH_SUCCEED)||h.getType().equals(MessageType.MESSAGE_SEARCH_FAIL))
                   {
                       ms[4]=h;
                   }
                   else if(h.getType().equals(MessageType.MESSAGE_APPLY_SUCCEED1)||h.getType().equals(MessageType.MESSAGE_APPLY_FAIL1))
                   {
                       ms[5]=h;
                   }
                   else if(h.getType().equals("fgIndicate"))
                   {
                       ms[6]=h;
                   }
                   else if(h.getType().equals("fgIndicate2"))
                   {
                       ms[7]=h;
                   }
                   else if(h.getType().equals("MESSAGE_PROFILE_SUCCEED")||h.getType().equals("MESSAGE_PROFILE_FAIL"))
                   {
                       ms[8]=h;
                   }
                   else if(h.getType().equals("applyInfo"))
                   {
                       ms[9]=h;
                   }
                   else if(h.getType().equals("sendMessageText"))
                   {
                       ms[10]=h;
                   }
                   else if(h.getType().equals("initMessagerecord"))
                   {
                       ms[11]=h;
                   }
                   else if(h.getType().equals("imageAvar"))
                   {
                       ms[12]=h;
                   }
                   else if(h.getType().equals("initGroup"))
                   {
                       ms[13]=h;
                   }
                   else if(h.getType().equals("fgIndicate3"))
                   {
                       ms[14]=h;
                   }
                   else if(h.getType().equals("groupSearchAccount"))
                   {
                       ms[15]=h;
                   }
                   else if(h.getType().equals("initMessagerecordGroup"))
                   {
                       ms[16]=h;
                   }
                   else if(h.getType().equals("isExistOnly"))
                   {
                       ms[17]=h;
                   }
                   else if(h.getType().equals("modifypasswordonline"))
                   {
                       ms[18]=h;
                   }
                   else if(h.getType().equals("profileGroup"))
                   {
                       ms[19]=h;
                   }
                   else if(h.getType().equals("leaveGroup"))
                   {
                       ms[20]=h;
                   }
                   else if(h.getType().equals("judgeMember"))
                   {
                       ms[21]=h;
                   }
                   else if(h.getType().equals("fgIndicate1"))
                   {
                       ms[22]=h;
                   }
                   else if(h.getType().equals("askRelation"))
                   {
                       ms[23]=h;
                   }
                   else if(h.getType().equals("askRelationGroup"))
                   {
                       ms[24]=h;
                   }
                   else if(h.getType().equals("initAlways"))
                   {
                       ms[25]=h;
                   }
                   else if(h.getType().equals("indicateMemberGroup"))
                   {
                       ms[26]=h;
                   }
                   else if(h.getType().equals("indicatefgCopy"))
                   {
                       ms[27]=h;
                   }
                   else if(h.getType().equals("getPosition"))
                   {
                       ms[28]=h;
                   }
                   else if(h.getType().equals("refreshFriendListLogin"))
                   {
                       Platform.runLater(()->
                       {
                           ChatRoomView.getController().refreshFriendListLogin((String)h.getT());
                       });



                   }
                   else if(h.getType().equals("refreshFriendList"))
                   {
                       Platform.runLater(()->
                       {
                           ChatRoomView.getController().refreshFriendListOnline((String)h.getT());
                       });



                   }
                   else if(h.getType().equals("addMemberGroup"))
                   {
                       Platform.runLater(()->
                       {
                           ChatRoomView.getController().addObservableList3((Friend) h.getT());
                       });



                   }
                   else if(h.getType().equals("substractMemberGroup"))
                   {
                       Platform.runLater(()->
                       {
                           ChatRoomView.getController().removeObservableList3((Friend) h.getT());
                       });



                   }
                   else if(h.getType().equals("quitGroup"))
                   {

                       Platform.runLater(()->
                               {
                                   ChatRoomView.getController().removeObservableList3((Friend) h.getT());
                               });


                   }
                   else if(h.getType().equals("deleteFriend"))
                   {
                       Platform.runLater(()->
                       {
                           ChatRoomView.getController().removeObservableList((String) h.getT());
                       });


                   }
                   else if(h.getType().equals("applyGroup"))
                   {
                       System.out.println("进入申请");
                       System.out.println(h.getT()+"客户端接受处");
                       Platform.runLater(()->
                       {
                           ChatRoomView.getController().addObservableList2((Friend) h.getT());
                       });




                       System.out.println("过来了");
                   }
                   else if(h.getType().equals("approvalGroup"))
                   {
                       Platform.runLater(()->
                       {
                           ChatRoomView.getController().addObservableList3((Friend) h.getT());
                       });

                       System.out.println("过来了");
                   }

                   else if(h.getType().equals("imageSend"))
                   {
                       Message msg=(Message) h.getT();
                       Pattern pp=Pattern.compile("\\d{10}");
                       Matcher mmm=pp.matcher(msg.getGetter());
                       boolean mmq= mmm.matches();
                       if(mmq)
                       {
                           ChatRoomView.getController().receiveFavor((Message) h.getT());
                       }
                       else
                       {
                           ChatRoomView.getController().receiveFavorGroup((Message) h.getT());
                       }


                   }
                   else if(h.getType().equals("warningApply"))
                   {
                       Platform.runLater(()->
                       {
                           ChatRoomView.getController().addObservableList2((Friend)h.getT());
                       });





                   }
                   else if(h.getType().equals("fileReceive"))
                   {
                     Message msg=(Message) h.getT();
                       Pattern pp=Pattern.compile("\\d{10}");
                       Matcher mmm=pp.matcher(msg.getGetter());
                       boolean mmq= mmm.matches();
                       if(mmq)
                       {
                           ChatRoomView.getController().fileReceive((Message) h.getT());
                       }
                       else
                       {
                           ChatRoomView.getController().fileReceiveGroup((Message) h.getT());

                       }



                   }
                   else if(h.getType().equals("warningLittle"))
                   {

                           System.out.println("这里显示好友-----------------------------------------------------------------------------------------《》客户端");
                           System.out.println(((Friend)h.getT()).getNickname());
                           Platform.runLater(()->
                           {
                               ChatRoomView.getController().addObservableList((Friend) h.getT());
                           });


                   }
                   else if (h.getType().equals("defineMember")) {
                       Platform.runLater(()->
                       {
                           System.out.println("看看进来没");
                           ChatRoomView.getController().addObservableList3((Friend) h.getT());
                           ChatRoomView.getController().addObservableList1((Friend)h.getT());
                       });

                   }

                   else if(h.getType().equals("preReceiveFile"))
                   {
                       Message temp=(Message) h.getT();
                       System.out.println("服务器收到啦哈哈哈哈哈");
                       try{
                           FileOutputStream fileOutputStream = new FileOutputStream("D:\\clientreceiver\\"+temp.getContent());
                           while (true) {
                               FileData customData = (FileData) dis.readObject();
                               if (customData == null){
                                   break; // 接收完成的标记
                               }
                               int bytesRead = customData.getNumber();
                               byte[] buffer = customData.getData();
                               fileOutputStream.write(buffer, 0, bytesRead);
                               dos.writeObject("ok");
                               dos.flush();

                           }
                           Long tm=0L;
                           dos.writeObject(tm);
                           dos.flush();
                       } catch (IOException e) {
                           e.printStackTrace();
                       } catch (ClassNotFoundException e) {
                           e.printStackTrace();
                       }
                       System.out.println("接收完成");
                   }
                   else if(h.getType().equals("sendOther")) {
                           Message msg=(Message) h.getT();
                            CommonEvent commonEvent=receive();
                            String image=(String) commonEvent.getT();
                            msg.setTempImage(image);
                           System.out.println("这里是消息接收的地方"+msg.getGetter());
                           ChatRoomController chatRoomController= ChatRoomView.getController();
                           Pattern pp=Pattern.compile("G\\d{10}");
                           Matcher mmm=pp.matcher(msg.getGetter());
                           boolean groupOrPerson=mmm.matches();
                           if (msg.getMesType() == null || msg.getMesType().equals("text"))
                           {
                               System.out.println("发送消息小心心");
                               if(groupOrPerson)
                                   chatRoomController.addBoxGroup(msg);
                               else
                               {
                                   chatRoomController.addBox(msg);
                                   System.out.println("别说自己");
                               }


                           }

                           else if (msg.getMesType().equals("image")) {

                               if(groupOrPerson)
                                   chatRoomController.receiveFavorGroup(msg);
                               else
                                   chatRoomController.receiveFavor(msg);

                           }
                           else
                           {
                               if(groupOrPerson)
                                   chatRoomController.fileReceiveGroup(msg);
                               else
                                   chatRoomController.fileReceive(msg);

                           }
                           System.out.println("接到啦哈哈哈哈哈哈哈哈哈");

                       }
               }
    }

    public Socket getClient() {
        return client;
    }
    public ObjectOutputStream getDos() {
        return dos;
    }
}
