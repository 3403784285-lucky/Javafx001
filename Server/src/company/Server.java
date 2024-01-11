package company;
import utils.ManageServerToThread;
import utils.ThreadServerConnect;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;
/**
 * 监听端口，等待客户端连接并保持通讯
 */
public class Server {

    //实现长连接和短连接同时连起来：
    private boolean isRunning = true;
    public Server() {
        ExecutorService longconnectionThreadx = new ThreadPoolExecutor(6, 10, 1L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(4), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        ExecutorService shortconnectionThreadx = new ThreadPoolExecutor(6, 10, 1L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(4), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

            Thread shortConn=new Thread(() ->
            { try (ServerSocket shortserver = new ServerSocket(9999)) {
                System.out.println("短连接服务器已启动...等待用户连接");
                while (isRunning) {
                    try {
                        Socket shortclient = shortserver.accept();
                        System.out.println("短连接接客户端-----");
                        shortconnectionThreadx.execute(new Channel(shortclient));
                        System.out.println("已连接");
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("服务器断开连接-----");

                    }
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
            });


            Thread longConn=new Thread(() ->
            {
                try (ServerSocket longserver = new ServerSocket(8888)) {
                    System.out.println("长连接服务器已启动...等待用户连接");
                    while (isRunning) {
                        try {
                            Socket longclient = longserver.accept();
                            System.out.println("长连接接客户端-----");
                            if(!ManageServerToThread.isExist(Channel.u.getUserAccount()))
                            {
                                ThreadServerConnect tsc = new ThreadServerConnect(longclient);
                                Future<?> future = longconnectionThreadx.submit(tsc);
                                // 将用户账号和任务绑定存储
                                ManageServerToThread.addServerThread(Channel.u.getUserAccount(),tsc);
                                System.out.println("已连接");
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("服务器断开连接-----");

                        }
                    }

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }


            });
            //不可以直接要将它阻塞，不然会直接关闭；

        shortConn.start();
        longConn.start();

        try {
            shortConn.join();longConn.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("阻塞失败");
        }



            shortconnectionThreadx.shutdown();
            longconnectionThreadx.shutdown();

        }




    public static void main(String[] args) {

        new Server();


    }
}
