package com.example.trucks_order.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lmwis on 2019-04-21 15:13
 */
public class StringUtil {
    /**
     * 修改文件名
     * @param fileName
     * @return
     */
    public static String reName(String fileName) {

        String suffix = fileName.substring(fileName.lastIndexOf("."));
        if (fileName.isEmpty() || fileName == null || suffix.isEmpty()) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String date_string = sdf.format(date);
        String toNumber = date_string.replaceAll("[[\\s-:punct:]]","");

        String name = toNumber + suffix;

        return name;
    }
}
