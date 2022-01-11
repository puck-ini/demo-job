package org.zchzh.file.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @author zengchzh
 * @date 2021/9/26
 */

@Slf4j
public class MD5Util {


    public static String getMd5(byte[] bytes) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(bytes);
            return new BigInteger(1, digest).toString(16);
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static String getMd5(InputStream stream) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = stream.read(buffer, 0, 1024)) != -1) {
                messageDigest.update(buffer, 0, length);
            }
            BigInteger bigInt = new BigInteger(1, messageDigest.digest());
            System.out.println("文件md5值：" + bigInt.toString(16));
            return bigInt.toString(16);
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

//
//    public static void main(String[] args) throws FileNotFoundException {
//        File file = new File("D:\\testdata\\test.txt");
//        FileInputStream fileInputStream = new FileInputStream(file);
//        System.out.println(getMd5(fileInputStream));
//    }
}
