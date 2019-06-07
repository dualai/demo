package com.socket.ls03_1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/***
 * UDP提供者
 */
public class UDPSearher {
    public static void main(String[] args) throws IOException {
        System.out.println("UDPSearher Started");
        //作为搜索方，无需指定端口
        DatagramSocket ds = new DatagramSocket();


        // 构建一份请求数据
        String requestData = "Helloworld...";
        byte[] requestDataBytes = requestData.getBytes();
        DatagramPacket requestPacket = new DatagramPacket(requestDataBytes,requestDataBytes.length);
        //本机20000端口号
        requestPacket.setAddress(InetAddress.getLocalHost());
        requestPacket.setPort(20000);
        ds.send(requestPacket);

        // 构建接收
        final byte[] buf = new byte[512];
        DatagramPacket receivePack = new DatagramPacket(buf, buf.length);

        //接收
        ds.receive(receivePack); //堵住

        // 打印接收到的信息与发送者的信息
        // 发送者的IP地址
        String ip = receivePack.getAddress().getHostAddress();
        int port = receivePack.getPort();

        //拿到数据大小
        int dataLen = receivePack.getLength();

        String data = new String(receivePack.getData(),0,dataLen);

        System.out.println("UDPSearch receive from ip :"+ip + "\tport:"+port+"\tdata:"+data);

        System.out.println("UDPSearch finished...");
        ds.close();
    }
}
