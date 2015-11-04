package com.xloger.kanzhihu.app.cache;

import android.content.Context;
import android.os.Environment;
import com.xloger.kanzhihu.app.utils.EncryptUtil;
import com.xloger.kanzhihu.app.utils.StreamUtil;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by xloger on 2015/11/4.
 * Author:xloger
 * Email:phoenix@xloger.com
 */
public class FileCache {
    private static FileCache fileCache;
    private Context context;

    private FileCache(Context context){
        this.context=context;
    }

    public static void createInstance(Context context){
        if (fileCache == null) {
            fileCache=new FileCache(context);
        }
    }

    public static FileCache getInstance(){
        if (fileCache == null) {
            throw new IllegalStateException("FileCache没有初始化成功");
        }
        return fileCache;
    }

    public void save(String url,byte[] data){
        if (url!=null&&data!=null&&data.length>0){
            File folder = getCacheFolder();
            String mapFile = mapFile(url);
            File file=new File(folder,mapFile);

            FileOutputStream fout=null;
            try {
                fout=new FileOutputStream(file);
                fout.write(data);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                StreamUtil.close(fout);
            }
        }
    }

    public byte[] load(String url){
        byte[] ret = null;
        if (url != null) {
            File folder = getCacheFolder();
            String mapFile = mapFile(url);
            File file=new File(folder,mapFile);

            if (file.exists()&&file.canRead()){
                FileInputStream fin=null;
                try {
                    fin=new FileInputStream(file);
                    ret=StreamUtil.readStream(fin);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }finally {
                    StreamUtil.close(fin);
                }
            }

        }

        return ret;
    }

    /**
     * 将网址映射为唯一的文件名
     */
    private static String mapFile(String url){
        String ret = null;
        if (url != null) {
            try {
                MessageDigest digest = MessageDigest.getInstance("MD5");
                byte[] bytes = digest.digest(url.getBytes());
                ret = EncryptUtil.toHex(bytes);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

        }

        return ret;
    }

    /**
     * 获取缓存文件夹。支持外部存储跟内部存储
     */
    private File getCacheFolder(){
        File ret = null;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)){
            ret=context.getExternalCacheDir();
        }else {
            ret=context.getCacheDir();
        }

        return ret;
    }
}
