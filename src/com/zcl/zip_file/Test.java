package com.zcl.zip_file;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 学会
 */
public class Test {
    public static void main(String[] args) {

    }

    /**
     * 压缩文件
     * @param fileList 待压缩文件
     * @param desFile 目标压缩文件
     * @throws FileNotFoundException
     */
    public static void zip(List<File> fileList,File desFile) throws Exception {
        // 判断文件不为空的时候
        if(fileList == null || fileList.isEmpty()){
            return;
        }
        File file;
        // 压缩到文件的目标目录
        OutputStream out = new FileOutputStream(desFile);
        // 创建压缩文件对象
        ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(out));
        zipOutputStream.setLevel(5); // 设置压缩等级 0-9  等级越高内存压缩越小
        // 便利待压缩文件
        for (File file1 : fileList) {
            // 表示zip压缩文件条目对象
            ZipEntry zipEntry = new ZipEntry(file1.getName());
            // 将zip条目放到压缩文件对象中
            zipOutputStream.putNextEntry(zipEntry);
            // 创建读写文件内容
            FileInputStream fileInputStream = new FileInputStream(file1);
            // 读取文件内容
            byte[] bytes = new byte[1024];
            int len = 0;
            // 循环读取文件内容
            while ((len = fileInputStream.read(bytes)) != -1){
                // 讲读取的内容写到zip
                zipOutputStream.write(bytes,0,len);
            }
            // 关闭指定的流
            zipOutputStream.closeEntry();
            fileInputStream.close();
        }
        zipOutputStream.close();


    }
}
