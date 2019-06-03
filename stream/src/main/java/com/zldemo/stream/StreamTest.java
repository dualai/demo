package com.zldemo.stream;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class StreamTest {
    public static void main(String[] args) {

        /***
         * 1、读取键盘流
         *   int read()  //返回ASCII码。若,返回值=-1，说明没有读取到任何字节读取工作结束。
         *          int read(byte[] b)//读入多个字节到缓冲区b中返回值是读入的字节数
         */
//        int b;
//        System.out.println("please input something...");
//        try {
//            while ((b = System.in.read()) != -1) {
//                System.out.print((char)b);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        /**
         * 2.标准输入输出
         */
//        //从字节流转化成字符流
//        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
//        // 创建缓冲区阅读器从键盘逐行读入数据
//        BufferedReader bufferedIn = new BufferedReader(inputStreamReader);
//        System.out.println("Unix: ctrl-d or ctrl-c exit"
//                + "\nWindows: ctrl-z exit");
//        String s;

//        try {
//            // 读一行数据，并标准输出至显示器
//            s = bufferedIn.readLine();
//            while (s != null){
//                System.out.println("Read1: "+s);
//                s = bufferedIn.readLine();
//                System.out.println("Read2: "+s);
//            }
//            bufferedIn.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            // 读一行数据，并标准输出至显示器
//            while ((s = bufferedIn.readLine()) != null) {
//                System.out.println("s:"+s);
//            }
//        bufferedIn.close();
//        }catch (IOException ex){
//            ex.printStackTrace();
//        }


//        /***
//         * 每次读一个字节，全部读完再打印
//         */
//        try{
//            FileInputStream inputStream = new FileInputStream("D:\\zl_space\\android_demo\\demos\\stream\\src\\main\\java\\com\\zldemo\\stream\\Test.java");
//            int startPos = 0;
//            int readByteCountEveryTime = 1;
//            byte[] buffer = new byte[1024];
//            int readCount = 0;
//            while (inputStream.read(buffer,startPos,readByteCountEveryTime) != 0){
//                readCount++;
//                startPos += readByteCountEveryTime;
//                System.out.println("readCount:"+readCount);
//            }
//            System.out.println(new java.lang.String(buffer));
//            inputStream.close();
//        }catch (IOException ex){
//            ex.printStackTrace();
//        }


//        /**
//         * 一次性全部读完
//         */
//        try{
//            FileInputStream inputStream = new FileInputStream("D:\\zl_space\\android_demo\\demos\\stream\\src\\main\\java\\com\\zldemo\\stream\\Test.java");
//            int startPos = 0;
//            int readByteCountEveryTime = 1024;
//            byte[] buffer = new byte[readByteCountEveryTime];
//            while (inputStream.read(buffer,startPos,readByteCountEveryTime) != 0){
//            }
//            System.out.println(new java.lang.String(buffer));
//            inputStream.close();
//        }catch (IOException ex){
//            ex.printStackTrace();
//        }

//        /**
//         * 借助缓冲区来读
//         */
//        try{
//            FileInputStream inputStream = new FileInputStream("D:\\zl_space\\android_demo\\demos\\stream\\src\\main\\java\\com\\zldemo\\stream\\Test.java");
//            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
//            byte[] dataBuffer = new byte[1024];
//            while ((bufferedInputStream.read(dataBuffer)) != 0){
//
//            }

//            System.out.println(new String(dataBuffer));
//            bufferedInputStream.close();
//        }catch (IOException ex){
//            ex.printStackTrace();
//        }

        /**
         * 读取键盘，写入文件
         */
//        try {
//            System.out.println("please Input from Keyboard");
//            int count, n = 512;
//            byte buffer[] = new byte[n];
//            count = System.in.read(buffer);
//            FileOutputStream wf = new FileOutputStream("D:\\zl_space\\android_demo\\demos\\stream\\src\\main\\java\\com\\zldemo\\stream\\TestOutput.txt");
//            wf.write(buffer, 0, count);
//            wf.close(); // 当流写操作结束时，调用close方法关闭流。
//            System.out.println("Save to the write.txt");
//        } catch (IOException IOe) {
//            System.out.println("File Write Error!");
//        }

        /**
         * 拷贝文件
         */
//        try{
//            FileInputStream inputStream = new FileInputStream(
//                    "D:\\zl_space\\android_demo\\demos\\stream\\src\\main\\java\\com\\zldemo\\stream\\WillCopy.txt"
//            );
//            FileOutputStream outputStream = new FileOutputStream(
//                    "D:\\zl_space\\android_demo\\demos\\stream\\src\\main\\java\\com\\zldemo\\stream\\CopyDest.txt"
//            );
//
//            int b;
//            while ((b = inputStream.read()) != -1){
//                outputStream.write(b);
//            }
//
//            inputStream.close();
//            outputStream.close();
//
//        }catch (IOException e){
//            e.printStackTrace();
//        }


        /***
         * 拷贝文件
         */
//        try{
//            FileInputStream inputStream = new FileInputStream(
//                    "D:\\zl_space\\android_demo\\demos\\stream\\src\\main\\java\\com\\zldemo\\stream\\WillCopy.txt"
//            );
//            System.out.println(inputStream.available()); //available() 如果堵塞，会返回0
//            FileOutputStream outputStream = new FileOutputStream(
//                    "D:\\zl_space\\android_demo\\demos\\stream\\src\\main\\java\\com\\zldemo\\stream\\CopyDest.txt"
//            );
//
//            byte[] buffer = new byte[1024];
//            int count = 0;
//            while ((count = inputStream.read(buffer)) != 0){
//                outputStream.write(buffer);
//            }
//            inputStream.close();
//            outputStream.close();
//
//        }catch (IOException e){
//            e.printStackTrace();
//        }

        /**
         * 键盘的输入流读入内存，然后写入文件中
         */
//        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
//        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//        FileWriter fileWriter = null;
//        BufferedWriter bufferedWriter = null;
//        try {
//            fileWriter = new FileWriter(
//                    "D:\\zl_space\\android_demo\\demos\\stream\\src\\main\\java\\com\\zldemo\\stream\\CopyDest2.txt"
//            );
//            bufferedWriter = new BufferedWriter(fileWriter);
//            String s = null;
//            while ((s = bufferedReader.readLine()) != null){
//                bufferedWriter.write(s,0,s.length());
//                bufferedWriter.flush();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                bufferedReader.close();
//                if(bufferedWriter != null){
//                    bufferedReader.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

//        try {
//            System.out.println("内存中采用unicode字符编码：");
//            char c = '好';
//            int lowBit = c & 0xFF;
//            int highBit = (c & 0xFF00) >> 8;
//            System.out.println("" + lowBit + "   " + highBit);
//            String s = "好";
//            System.out.println("本地操作系统默认字符编码：");
//            readBuff(s.getBytes());
//            System.out.println("采用GBK字符编码：");
//            readBuff(s.getBytes("GBK"));
//            System.out.println("采用UTF-8字符编码：");
//            readBuff(s.getBytes("UTF-8"));
//        }catch (IOException ex){
//            ex.printStackTrace();
//        }

//        try {
//      /*
//        in为输入流，count为输入流的字节数，读取输入流的字节数直到可以确认为整个输入流的字节数
//      */
//            InputStream in = new FileInputStream("");
//            int count = 0;
//            while (count < 1024) { //1024是已知的输入流的字节数
//                count = in.available();//该方法返回可估算从这个输入流中可无阻塞读取剩余的字节数
//            }
//            byte[] message = new byte[count];
//            int readCount = 0; // 已经成功读取的字节的个数
//            while (readCount < count) {
//                readCount += in.read(message, readCount, count - readCount);
//            }
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }


        /**
         * 自己做个byte缓冲数组
         */
        //第一步:定位文件所在的位置并新建一个文件用于确定复制的文件的文件名
        File infile = new File("/Users/dxy/sync/infile.jpg");
        File outfile = new File("/Users/dxy/sync/outfile.jpg");
        try {
            //第二步:建立文件流管道
            FileInputStream fis = new FileInputStream(infile);
            FileOutputStream fos = new FileOutputStream(outfile);
            //第三步:操作文件流
            //第一种方式, 一次读一个字节, 会循环该文件的字节数
            int length1 = fis.available();
            for(int i=0;i<length1;i++){
                fos.write(fis.read());
            }
            //第二种方式, 一次读取多个字节
            //建一个byte数值用于临时成块存放字节文件,建议使用2的n次方,这样计算机的处理效率相对会高一些
            byte[] tmp = new byte[8192] ;
            //设置读取的次数
            int length2 = fis.available()/8192;
            for (int i = 0; i < length2; i++) {
                fis.read(tmp);
                fos.write(tmp);
            }
            //处理最后一次少读取的部分
            int size = fis.read(tmp) ;  //read()函数在读取的时候，自动会移位....
            //第一个参数还是byte数组形成的临时缓冲区,第二个参数是从数组的哪里开始向文件写,第三个参数是写多少。
            fos.write(tmp, 0, size) ;
            //最后关闭文件流
            fis.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private static void readBuff(byte[] buff) throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(buff);
        int data;
        while ((data = in.read()) != -1) System.out.print(data + "  ");
        System.out.println();
        in.close();
    }
}
