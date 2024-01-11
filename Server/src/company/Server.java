package company;
import utils.ManageServerToThread;
import utils.ThreadServerConnect;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;
/**
 * �����˿ڣ��ȴ��ͻ������Ӳ�����ͨѶ
 */
public class Server {

    //ʵ�ֳ����ӺͶ�����ͬʱ��������
    private boolean isRunning = true;
    public Server() {
        ExecutorService longconnectionThreadx = new ThreadPoolExecutor(6, 10, 1L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(4), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        ExecutorService shortconnectionThreadx = new ThreadPoolExecutor(6, 10, 1L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(4), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

            Thread shortConn=new Thread(() ->
            { try (ServerSocket shortserver = new ServerSocket(9999)) {
                System.out.println("�����ӷ�����������...�ȴ��û�����");
                while (isRunning) {
                    try {
                        Socket shortclient = shortserver.accept();
                        System.out.println("�����ӽӿͻ���-----");
                        shortconnectionThreadx.execute(new Channel(shortclient));
                        System.out.println("������");
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("�������Ͽ�����-----");

                    }
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
            });


            Thread longConn=new Thread(() ->
            {
                try (ServerSocket longserver = new ServerSocket(8888)) {
                    System.out.println("�����ӷ�����������...�ȴ��û�����");
                    while (isRunning) {
                        try {
                            Socket longclient = longserver.accept();
                            System.out.println("�����ӽӿͻ���-----");
                            if(!ManageServerToThread.isExist(Channel.u.getUserAccount()))
                            {
                                ThreadServerConnect tsc = new ThreadServerConnect(longclient);
                                Future<?> future = longconnectionThreadx.submit(tsc);
                                // ���û��˺ź�����󶨴洢
                                ManageServerToThread.addServerThread(Channel.u.getUserAccount(),tsc);
                                System.out.println("������");
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("�������Ͽ�����-----");

                        }
                    }

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }


            });
            //������ֱ��Ҫ������������Ȼ��ֱ�ӹرգ�

        shortConn.start();
        longConn.start();

        try {
            shortConn.join();longConn.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("����ʧ��");
        }



            shortconnectionThreadx.shutdown();
            longconnectionThreadx.shutdown();

        }




    public static void main(String[] args) {

        new Server();


    }
}
