package com.xloger.kanzhihu.app.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.xloger.kanzhihu.app.Constants;
import com.xloger.kanzhihu.app.tasks.SimpleTask;
import com.xloger.kanzhihu.app.tasks.TaskCallBack;
import com.xloger.kanzhihu.app.tasks.TaskResult;
import com.xloger.kanzhihu.app.utils.MyLog;
import com.xloger.kanzhihu.app.utils.StreamUtil;

/**
 * Created by xloger on 2015/11/4.
 * Author:xloger
 * Email:phoenix@xloger.com
 */
public class CacheTool {

    private CacheTool(){

    }

    public static void cacheImage(final View view, final String url){
        cacheImage(view,url,150,150);
    }

    public static void cacheImage(final View view, final String url, final int toWidth, final int toHeight){
        Bitmap bitmap=null;
        ImageCache imageCache = ImageCache.getInstance();
        bitmap = imageCache.get(url);
        if (bitmap == null) {
            FileCache fileCache = FileCache.getInstance();
            byte[] bytes = fileCache.load(url);
            if (bytes == null||bytes.length==0) {
//                view.setTag(url);
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
            setBackGround(view,bitmap,url);
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

                    setBackGround(view,bitmap,url);
                }
            }
        }
    }


    private static void setBackGround(View view,Bitmap bitmap,String url){
        //TODO WeakReference
        if (view!=null&&bitmap!=null){
            Object tag = view.getTag();
            boolean match=true; //判断是否匹配View
            if (tag != null&&tag instanceof String) {
                String str= (String) tag;
                match=str.equals(url);
            }

            if (match){
                if (view instanceof ImageView){
                    ((ImageView) view).setImageBitmap(bitmap);
                }else {
                    view.setBackgroundDrawable(new BitmapDrawable(bitmap));
                }
            }



        }
    }

    /**
     * 针对图片数据进行转换缩小
     * @param data 实际数据
     * @param toWidth 转换后的宽度
     * @param toHeight 转换后的高度
     * @return 缩小后的Bitmap
     */
    private static Bitmap loadScaledBitmap(byte[] data,int toWidth,int toHeight){
        Bitmap ret=null;
        //1. 只获取尺寸(使用Options来设置)
        //对象内部的变量会传给底层解码
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeByteArray(data,0,data.length,options);

        int picWidth=options.outWidth;
        int picHeight=options.outHeight;

        int wSize=picWidth/toWidth;
        int hSize=picHeight/toHeight;
        int toSize=Math.max(wSize,hSize);
        int logSize= (int) (Math.log(toSize)/Math.log(2));
        int endSize= (int) Math.pow(2,logSize);

        //2. 缩小图片
        options.inJustDecodeBounds=false;
        options.inSampleSize=endSize; //图片采样比率,2的次方为参数效率最高
//        options.inPreferredConfig=Bitmap.Config.RGB_565;
        ret=BitmapFactory.decodeByteArray(data,0,data.length,options);

        MyLog.i("实际宽度：" + picWidth + "，实际长度：" + picHeight + ",toSize:" + toSize + "，endSize:" + endSize);

        return ret;
    }
}
