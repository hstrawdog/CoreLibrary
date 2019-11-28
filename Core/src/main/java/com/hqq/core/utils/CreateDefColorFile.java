package com.hqq.core.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: huangqiqiang
 * 在此写用途
 * @FileName: com.hqq.core.utils.CreateDefColorFile.java
 * @emain: 593979591@qq.com
 * @date: 2019-04-24 20:32
 */
public class CreateDefColorFile {

    /**
     * 生成文件路径
     */
    private static String path = "./Core/src/main/res/values/def_text_style.xml";

    /**
     * 文件路径+名称
     */
    private static String filenameTemp;

    public static void main(String[] args) {

        List<String> list = new ArrayList<>();
        list.add("333");
        list.add("666");
        list.add("999");
        list.add("main");
        list.add("red");
        list.add("white");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<resources>\n\n");
        stringBuilder.append("    <style name=\"def_text\">\n" +
                "        <item name=\"android:includeFontPadding\">false</item>\n" +
                "    </style>\n\n");

        for (int i = 16; i < 72; i = i + 2) {
            stringBuilder.append("    <style name=\"def_text." + i + "\">\n");
            stringBuilder.append("        <item name=\"android:textSize\">@dimen/x" + i + "</item>\n");
            stringBuilder.append("    </style>\n\n");
        }
        for (int i = 16; i < 47; i = i + 2) {
            for (String color : list) {
                stringBuilder.append("    <style name=\"def_text." + i + "." + color + "\">\n");
                stringBuilder.append("        <item name =\"android:textColor\">@color/color_" + color + "</item>\n");
                stringBuilder.append("    </style>\n\n");
            }
        }
        stringBuilder.append("\n</resources>");
        createFile(stringBuilder.toString());
    }


    /**
     * 创建文件
     *
     * @param filecontent 文件内容
     * @return 是否创建成功，成功则返回true
     */
    public static boolean createFile(String filecontent) {
        Boolean bool = false;
        filenameTemp = path;//文件路径+名称+文件类型
        File file = new File(filenameTemp);
        try {
            //如果文件不存在，则创建新的文件
            if (!file.exists()) {
                file.createNewFile();
                bool = true;
                System.out.println("success create file,the file is " + filenameTemp);
                //创建文件成功后，写入内容到文件里
                writeFileContent(filenameTemp, filecontent);
            } else {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write("");
                fileWriter.flush();
                fileWriter.close();
                //创建文件成功后，写入内容到文件里
                writeFileContent(filenameTemp, filecontent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bool;
    }

    /**
     * 向文件中写入内容
     *
     * @param filepath 文件路径与名称
     * @param newstr   写入的内容
     * @return
     * @throws IOException
     */
    public static boolean writeFileContent(String filepath, String newstr) throws IOException {
        Boolean bool = false;
        String filein = newstr + "\r\n";//新写入的行，换行
        String temp = "";

        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        FileOutputStream fos = null;
        PrintWriter pw = null;
        try {
            File file = new File(filepath);//文件路径(包括文件名称)
            //将文件读入输入流
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuffer buffer = new StringBuffer();

            //文件原有内容
            for (int i = 0; (temp = br.readLine()) != null; i++) {
                buffer.append(temp);
                // 行与行之间的分隔符 相当于“\n”
                buffer = buffer.append(System.getProperty("line.separator"));
            }
            buffer.append(filein);

            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(buffer.toString().toCharArray());
            pw.flush();
            bool = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //不要忘记关闭
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return bool;
    }
}
