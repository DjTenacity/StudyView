package com.gdj.myview.utils;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2017/8/24.
 */

public class SubStringUtils {

    /**
     * 判断是否是一个中文汉字
     *
     * @param c 字符
     * @return true表示是中文汉字，false表示是英文字母
     * @throws UnsupportedEncodingException 使用了JAVA不支持的编码格式
     */
    public static boolean isChineseChar(char c)
            throws UnsupportedEncodingException {

        // 如果字节数大于1，是汉字
        // 以这种方式区别英文字母和中文汉字并不是十分严谨，但在这个题目中，这样判断已经足够了
        return String.valueOf(c).getBytes("utf-8").length > 1;
    }

    /**
     * 计算当前String字符串所占的总Byte长度
     *
     * @param args 要截取的字符串
     * @return 返回值int型，字符串所占的字节长度，如果args为空或者“”则返回0
     * @throws UnsupportedEncodingException
     */
    public static int getStringByteLenths(String args) throws UnsupportedEncodingException {
        return args != null && args != "" ? args.getBytes("utf-8").length : 0;
    }

    /**
     * 获取与字符串每一个char对应的字节长度数组
     *
     * @param args 要计算的目标字符串
     * @return int[]
     * 数组类型，返回与字符串每一个char对应的字节长度数组
     * @throws UnsupportedEncodingException
     */
    public static int[] getByteLenArrays(String args) throws UnsupportedEncodingException {
        char[] strlen = args.toCharArray();
        int[] charlen = new int[strlen.length];
        for (int i = 0; i < strlen.length; i++) {
            charlen[i] = String.valueOf(strlen[i]).getBytes("utf-8").length;
        }
        return charlen;
    }

    /**
     * 按字节截取字符串 ，指定截取起始字节位置与截取字节长度
     *
     * @param orignal 要截取的字符串
     * @param count   截取Byte长度；
     * @return 截取后的字符串
     * @throws UnsupportedEncodingException 使用了JAVA不支持的编码格式
     */
    public static String substringByte(String orignal, int start, int count) {


        //如果目标字符串为空，则直接返回，不进入截取逻辑；
        if (orignal == null || "".equals(orignal)) return orignal;

        //截取Byte长度必须>0
        if (count <= 0) return orignal;

        //截取的起始字节数必须比
        if (start < 0) start = 0;

        //目标char Pull buff缓存区间；
        StringBuffer buff = new StringBuffer();

        try {

            //截取字节起始字节位置大于目标String的Byte的length则返回空值
            if (start >= getStringByteLenths(orignal)) return null;

            // int[] arrlen=getByteLenArrays(orignal);
            int len = 0;

            char c;

            //遍历String的每一个Char字符，计算当前总长度
            //如果到当前Char的的字节长度大于要截取的字符总长度，则跳出循环返回截取的字符串。
            for (int i = 0; i < orignal.toCharArray().length; i++) {

                c = orignal.charAt(i);

                //当起始位置为0时候
                if (start == 0) {

                    len += String.valueOf(c).getBytes("utf-8").length;
                    if (len <= count) buff.append(c);
                    else break;

                } else {

                    //截取字符串从非0位置开始
                    len += String.valueOf(c).getBytes("utf-8").length;
                    if (len >= start && len <= start + count) {
                        buff.append(c);
                    }
                    if (len > start + count) break;

                }
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //返回最终截取的字符结果;
        //创建String对象，传入目标char Buff对象
        return new String(buff);
    }

    /**
     * 截取指定长度字符串
     *
     * @param orignal 要截取的目标字符串
     * @param count   指定截取长度
     * @return 返回截取后的字符串
     */
    public static String substringByte(String orignal, int count) {
        return substringByte(orignal, 0, count);
    }


    public static void main(String[] args) {
        // 原始字符串
        String s = "11111111成功12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
//        String s = "我ydj爱JAVA";

        System.out.println("原始字符串：" + s);

        try {
            System.out.println("原始字符串字节长度：" + s.getBytes("utf-8").length);
            System.out.println("原始字符串字符长度：" + s.length());
            System.out.println("截取前6位：" + SubStringUtils.substringByte(s, 20));
            System.out.println("截取前6位：" + SubStringUtils.substringByte(s, 7, 20));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }


    /**
     * 截取带小数点的
     *
     * @param number
     */
    public static String splitNumber(String number) {

        String newNum = "";
        if (TextUtils.isEmpty(number)){
            return "" ;
        }

        if (number.contains(".")) {

            String first = number.substring(0, number.indexOf("."));
            String second = number.substring(number.indexOf("."), number.length());
            if (!TextUtils.isEmpty(first) && Integer.valueOf(first) > 0) {
                newNum = first.replaceFirst("^0*", "") + second;
            } else {
                newNum = number.replace(first, "0");
            }
        } else {
            newNum = number;
        }


        LogUtils.e("newNum = " + newNum);

        return newNum;
    }


}
