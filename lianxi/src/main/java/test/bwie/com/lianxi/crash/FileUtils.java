package test.bwie.com.lianxi.crash;

import android.os.Environment;

import java.io.File;



public class FileUtils {
    public static String SDPATH = Environment.getExternalStorageDirectory() + "/ACrash/";//文件夹路径
    public static File file = new File(SDPATH);

    public static void CreateDir() {
        if (!file.exists()) {//创建文件夹
            file.mkdirs();
        }
    }

    /**
     * 检查路径是否存在
     *
     * @param path
     * @return
     */
    public static boolean checkFilePathExists(String path) {
        return new File(path).exists();
    }
}
