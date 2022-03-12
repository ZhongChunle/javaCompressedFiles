package com.zcl.zip_file;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * 学会
 */
public class Test {
    public static void main(String[] args) throws Exception {
        // 测试代码
        // 创建文件对象列表
        // List<File> fileList = new ArrayList<>();
        // 将被读取的文件写入列表中
        // fileList.add(new File("Encryption\\src\\1.text"));
        // fileList.add(new File("Encryption\\src\\2.text"));
        // fileList.add(new File("C:\\Users\\zcl\\Desktop\\文档"));
        // 调用方法压缩文件
        //zip(fileList,new File("my.zip"));
        // zip(fileList,new File("C:\\Users\\zcl\\Desktop\\文档.zip"));

        // 调用解压文件的方法
        unZip(new File("C:\\Users\\zcl\\Desktop\\文档.zip"), new File("C:\\Users\\zcl\\Desktop\\文档2"));
    }

    /**
     * 压缩文件
     *
     * @param fileList 待压缩文件
     * @param desFile  目标压缩文件
     * @throws FileNotFoundException
     */
    public static void zip(List<File> fileList, File desFile) throws Exception {
        // 判断文件不为空的时候
        if (fileList == null || fileList.isEmpty()) {
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
            // 目前该压缩方法近适用于压缩文件，对于文件夹的压缩还是不行
            /*// 表示zip压缩文件条目对象
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
            fileInputStream.close();*/

            compress(file1, zipOutputStream);
        }
        zipOutputStream.close();
        System.out.println("文件压缩成功");
    }

    /**
     * 压缩文件类型的判断方法
     *
     * @param file            待压缩文件对象
     * @param zipOutputStream 压缩文件对象
     */
    private static void compress(File file, ZipOutputStream zipOutputStream) throws Exception {
        // 判断压缩文件是文件还是文件夹
        if (file.isFile()) {
            // 压缩文件
            compressFile(file, file.getName(), zipOutputStream);
        } else {
            // 压缩文件夹
            compressDir(file, file.getName(), zipOutputStream);
        }
    }

    /**
     * 压缩文件夹的方法
     *
     * @param file            文件夹对象
     * @param name            文件夹名称
     * @param zipOutputStream Zip流
     */
    private static void compressDir(File file, String name, ZipOutputStream zipOutputStream) throws Exception {
        // 设置到文件夹的就需要用到递归去遍历压缩
        // 判断是文件夹才去处理
        if (file.isDirectory()) {
            // 列出文件夹对象，所以要创建对象列表
            File[] files = file.listFiles();
            zipOutputStream.putNextEntry(new ZipEntry(name + "/"));
            zipOutputStream.closeEntry();
            // 文件对象为空的时候
            if (files != null) {
                // 遍历文件对象
                for (File f : files) {
                    if (f.isFile()) {
                        // 如果是文件直接就调用压缩文件就行了
                        compressFile(f, name + "/" + f.getName(), zipOutputStream);
                    } else {
                        // 不是文件就递归继续搜索
                        compressDir(f, name + "/" + f.getName(), zipOutputStream);
                    }
                }
            }
        }
    }

    /**
     * 压缩文件类型的方法
     *
     * @param file            文件对象
     * @param name            文件名称
     * @param zipOutputStream zip流
     */
    private static void compressFile(File file, String name, ZipOutputStream zipOutputStream) throws Exception {
        // 表示zip压缩文件条目对象
        ZipEntry zipEntry = new ZipEntry(name);
        // 将zip条目放到压缩文件对象中
        zipOutputStream.putNextEntry(zipEntry);
        // 创建读写文件内容
        FileInputStream fileInputStream = new FileInputStream(file);
        // 读取文件内容
        byte[] bytes = new byte[1024];
        int len = 0;
        // 循环读取文件内容
        while ((len = fileInputStream.read(bytes)) != -1) {
            // 讲读取的内容写到zip
            zipOutputStream.write(bytes, 0, len);
        }
        // 关闭指定的流
        zipOutputStream.closeEntry(); // 关闭条目
        fileInputStream.close();
    }

    /**
     * 解压缩包文件
     *
     * @param zipFile  被加压文件
     * @param destFile 解压存放文件的位置
     */
    public static void unZip(File zipFile, File destFile) throws Exception {
        // 判断解压文件是否空
        if (zipFile == null) {
            System.out.println("被解压的文件为空");
            return;
        }
        // 判断被存放的文件夹是一个文件
        if (destFile.isFile()) {
            System.out.println("存放解压文件的是一个文夹！");
            return;
        }
        // 判断c存放加压文件夹不存在
        if (!destFile.exists()) {
            destFile.mkdirs(); //创建多级文件目录
        }

        // 读取解压文件
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile));
        ZipEntry zipEntry;
        // 解压文件也是要一条目一条目的去读取
        while ((zipEntry = zipInputStream.getNextEntry()) != null) {
            // 拿到文件的名称
            String name = zipEntry.getName();
            // 创建一个目标文件
            File f = new File(destFile, name);
            // 判断是一个文件夹就跳过循环
            if(zipEntry.isDirectory()){
                f.mkdirs(); // 创建多级目录
                continue; // 跳过循环
            }
            // 将文件写到目标文件
            FileOutputStream fileOutputStream = new FileOutputStream(f);
            byte[] bytes = new byte[1024];
            int len;
            // 读取解压文件
            while ((len = zipInputStream.read(bytes)) != -1) {
                // 写出文件
                fileOutputStream.write(bytes);
            }
            zipInputStream.closeEntry(); // 关闭条目
            fileOutputStream.close(); // 关闭写出文件的流
        }
        // 关闭读取zip文件的流
        zipInputStream.close();
        System.out.println("文件解压成功");
    }

}
