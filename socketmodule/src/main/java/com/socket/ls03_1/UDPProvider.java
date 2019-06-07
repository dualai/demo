package com.socket.ls03_1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * UDP 提供者
 */
public class UDPProvider {
    public static void main(String[] args) throws IOException {
        System.out.println("UDPProvider Started");
        //作为接收者，指定一个端口用户数据接收
        DatagramSocket ds = new DatagramSocket(20000);

        // 构建接收实体
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

        System.out.println("UDPProvider receive from ip :"+ip + "\tport:"+port+"\tdata:"+data);

        // 构建一份回送数据的
        String responseData = "Receive data with len:"+dataLen;
        byte[] responseDataBytes = responseData.getBytes();
        DatagramPacket responsePacket = new DatagramPacket(responseDataBytes,responseDataBytes.length,
                receivePack.getAddress(),
                receivePack.getPort()
                );
//        DatagramPacket responsePacket = new DatagramPacket(responseDataBytes,responseDataBytes.length,
//                receivePack.getSocketAddress()
//                );

        ds.send(responsePacket);
        System.out.println("UDPProvider finished...");
        ds.close();


    }
}
