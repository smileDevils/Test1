package com.hkk.cloudtv.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class CommUtils {

    Logger logger = LoggerFactory.getLogger(CommUtils.class);

    private static final java.text.SimpleDateFormat dateFormat = new

            java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 24小时制

    public static void main(String[] args) {
       /* LinkedList<Integer> number = new LinkedList<>();
        number.add(1);
        number.add(5);
        number.add(6);
        number.add(8);
        number.add(7);

        Collections.sort(number);
        System.out.println(number);
        Collections.reverse(number);
        System.out.println(number);*/


   /*     File file1 = new File("C:\\Users\\46075\\Desktop\\20210323040120436\\20210406BuYD1A");
        if(!file1 .exists()) {
            file1.mkdirs();//创建目录
            System.out.println("测试文件夹不存在");
        }
*/

     /*   boolean flag = delFileTs("C:\\Users\\46075\\Desktop\\20210323040120436\\20210406BuYD1A");
        System.out.println(flag);*/
        /**
         * new Comparator<String>(){
         *             public int compare(String o1,String o2){
         *                 return o1.compareTo(o2);
         *             }
         *         }
         */
     /*   number.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return 0;
            }
        });*/
    }

    public static final String randomString(int length) {
        char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz" + "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ")
                .toCharArray();
        if (length < 1) {
            return "";
        }
        Random randGen = new Random();
        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];// nextInt:这里是一个方法的重载，参数的内容是指定范围
        }
        return new String(randBuffer);
    }

    public static String formatTime(String format, Object v) {
        if (v == null) {
            return null;
        }
        if (format.equals("")) {
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(v);
    }

    public static Date formatDate(String s) {
        Date d = null;
        try {
            d = dateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }


    public static String getRtmp(String ip, String bindCode){
        if(ip != null && !ip.equals("")){
            if(bindCode != null && !bindCode.equals("")){
                String rtmp = "http://" + ip + "/hls/" + bindCode;
                return rtmp;
            }
        }
        return null;
    }

    public static String getObsRtmp(String ip){
        if(ip != null && !ip.equals("")){
                String rtmp = "rtmp://" + ip + "/hls" ;
                return rtmp;
        }
        return null;
    }



    /**
     * 修改目录权限
     *
     * @param dirPath  目录
     * @param value 权限值
     */
    public static String filePermisession(String dirPath, String value) {
        Runtime runtime = Runtime.getRuntime();
        String command = "chmod " + value + dirPath;
        try {
            Process process = runtime.exec(command);
            process.waitFor();

            int existValue = process.exitValue();
            if (existValue != 0) {
                System.out.println("Change file permission failed");
                return "Change file permission failed " + existValue;
            }
            return "Successfully";
        } catch (Exception e) {
            return "Command execute failed";
        }
    }





}
