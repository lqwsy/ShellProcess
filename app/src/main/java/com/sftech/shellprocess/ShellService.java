package com.sftech.shellprocess;

import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 版权：咸鱼信息科技有限公司 版权所有
 *
 * @author lqw
 * 创建日期：2020/5/29 23:59
 * 描述：负责执行shell命令，并反馈结果
 */
public class ShellService {

    private ShellExcListener shellExcListener;
    private boolean isRunning;

    public ShellService(ShellExcListener shellExcListener, int port) {
        this.shellExcListener = shellExcListener;
        isRunning = true;
        openSocket(port);
    }

    private void openSocket(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Socket启动，端口：" + port);
            while (isRunning) {
                Socket socket = serverSocket.accept();
                new SocketThread(socket);
            }
        } catch (Exception e) {
            System.out.println("Socket错误：" + Log.getStackTraceString(e));
        }
    }

    public interface ShellExcListener {
        String executeCommand(String command);
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    /**
     * 开启一个线程执行shell命令
     */
    class SocketThread extends Thread {

        private Socket socket;

        public SocketThread(Socket socket) throws Exception {
            this.socket = socket;
            start();
        }

        @Override
        public void run() {
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line = bufferedReader.readLine();
                System.out.println("获取到的shell命令：" + line);
                String result = shellExcListener.executeCommand(line);
                System.out.println("执行shell命令返回结果：" + result);
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                printWriter.print(result);
                printWriter.flush();
                printWriter.close();
                bufferedReader.close();
                socket.close();
            } catch (Exception e) {
                System.out.println("获取socket流错误：" + Log.getStackTraceString(e));
            }
        }
    }
}
