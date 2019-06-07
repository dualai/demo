package com.socket.ls02_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException
    {
        Socket socket = new Socket();
        socket.setSoTimeout(3000);

        //连接本地服务端
        socket.connect(new InetSocketAddress(Inet4Address.getLocalHost(),2000),3000);

        System.out.println("已发起服务器连接，并进入后续流程~");
        System.out.println("客户端信息："+socket.getLocalAddress()+" P:"+socket.getLocalPort());
        System.out.println("服务器信息:" + socket.getInetAddress()+" P:"+socket.getPort());

        try{
            //发送接收数据
            todo(socket);
        }catch (Exception ex){
            System.out.println("异常关闭...");
        }

        // 释放资源
        socket.close();
        System.out.println("客户端已经退出");
    }

    private static void todo(Socket client) throws IOException{
        //键盘输入流
        InputStream in = System.in;
        BufferedReader inpupt = new BufferedReader(new InputStreamReader(in));

        //得到socket输出流，并且转化为打印流
        OutputStream outputStream = client.getOutputStream();
        //字节打印流
        PrintStream socketPrintStream = new PrintStream(outputStream);

        //得到服务器端输入流
        InputStream inputStream = client.getInputStream();
        BufferedReader socketBufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        boolean flag = true;
        do {
            //键盘读取一行
            String str = inpupt.readLine();
            //发送数据
            socketPrintStream.println(str);
            socketPrintStream.flush();

            //从服务器读取一行
            String echo = socketBufferedReader.readLine();
            if ("bye".equalsIgnoreCase(echo)) {
                flag = false;
            }else{
                System.out.println(echo);
            }
        }while (flag);

        //资源释放
        socketPrintStream.close();
        socketBufferedReader.close();
    }
}
