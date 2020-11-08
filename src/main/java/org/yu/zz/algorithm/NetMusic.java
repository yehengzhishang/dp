package org.yu.zz.algorithm;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.*;

/**
 * 网易云缓存转mp3格式
 * 只能以md5命名,不能匹配合适的歌名信息
 */
public class NetMusic {
    // 缓存目录
    private static final String PATH_INPUT = "/Users/zhe/Desktop/music";
    // mp3保存目录
    private static final String PATH_OUT = "/Users/zhe/Desktop/mp3";

    public static void main(String[] args) throws IOException, InvalidDataException, UnsupportedTagException {
        new NetMusic().star(PATH_INPUT, PATH_OUT);
    }

    /**
     * 异或更改文件
     */
    private static void writeToLocal(String destination, InputStream input)
            throws IOException {
        int index;
        byte[] bytes = new byte[1024];
        byte[] bytes2 = new byte[1024];
        FileOutputStream downloadFile = new FileOutputStream(destination);
        while ((index = input.read(bytes)) != -1) {
            for (int i = 0; i < index; i++) {
                bytes2[i] = (byte) (bytes[i] ^ 0xa3);
            }
            downloadFile.write(bytes2, 0, index);
            downloadFile.flush();
        }
        downloadFile.close();
    }

    /**
     * @param in  保存所有的uc!的dir
     * @param out 转换成mp3后保存的文件夹
     * @throws IOException File 常规异常
     */
    private void star(String in, String out) throws IOException {
        File input = new File(in);
        if (!input.isDirectory()) {
            return;
        }
        File[] children = input.listFiles();
        if (children == null) {
            return;
        }
        for (File child : children) {
            String name = child.getName();
            if (!name.endsWith(".uc!")) {
                continue;
            }
            name = name.replace(".uc!", "");
            System.out.println("正在转换 " + name);
            FileInputStream fis = new FileInputStream(child);
            writeToLocal(out + "/" + name, fis);
        }
    }
}
