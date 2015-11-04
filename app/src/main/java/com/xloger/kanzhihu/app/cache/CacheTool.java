package com.xloger.kanzhihu.app.cache;

import android.graphics.Bitmap;
import android.view.View;
import com.xloger.kanzhihu.app.Constants;
import com.xloger.kanzhihu.app.tasks.SimpleTask;
import com.xloger.kanzhihu.app.tasks.TaskCallBack;
import com.xloger.kanzhihu.app.tasks.TaskResult;
import com.xloger.kanzhihu.app.utils.StreamUtil;

/**
 * Created by xloger on 2015/11/4.
 * Author:xloger
 * Email:phoenix@xloger.com
 */
public class CacheTool {

    private CacheTool(){

    }

    public static void cacheImage(final View view, final String url, final int toWidth, final int toHeight){
        Bitmap bitmap=null;
        ImageCache imageCache = ImageCache.getInstance();
        bitmap = imageCache.get(url);
        if (bitmap == null) {
            FileCache fileCache = FileCache.getInstance();
            byte[] bytes = fileCache.load(url);
            if (bytes == null||bytes.length==0) {
                view.setTag(url);
                SimpleTask task=new SimpleTask(new TaskCallBack() {
                    @Override
                    public void onTaskFinish(TaskResult taskResult) {
                        onSimpleTaskFinish(taskResult,view,url,toWidth,toHeight);
                    }
                });
                task.execute(url);
            }else {
                bitmap = loadScaledBitmap(bytes, toWidth, toHeight);
                imageCache.put(url,bitmap);
            }
        }

        if (bitmap != null) {
            setBackGround(view,bitmap);
        }

    }

    private static void onSimpleTaskFinish(TaskResult taskResult,View view,String url,int toWidth,int toHeight){
        if (taskResult != null&&taskResult.action== Constants.ACTION_SIMPLE_TASK) {
            if (taskResult.data != null) {
                byte[] bytes= (byte[]) taskResult.data;
                if (bytes.length!=0) {
                    FileCache fileCache = FileCache.getInstance();
                    fileCache.save(url,bytes);

                    Bitmap bitmap = loadScaledBitmap(bytes, toWidth, toHeight);

                    ImageCache imageCache = ImageCache.getInstance();
                    imageCache.put(url,bitmap);

                    setBackGround(view,bitmap);
                }
            }
        }
    }


    private static void setBackGround(View view,Bitmap bitmap){
        //TODO WeakReference
    }

    private static Bitmap loadScaledBitmap(byte[] data,int toWidth,int toHeight){
        Bitmap ret = null;
        return ret;
    }
}
