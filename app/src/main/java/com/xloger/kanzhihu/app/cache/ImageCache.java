package com.xloger.kanzhihu.app.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xloger on 2015/11/4.
 * Author:xloger
 * Email:phoenix@xloger.com
 */
public class ImageCache {
    private static ImageCache imageCache;

    private LruCache<String,Bitmap> lruCache;
    private Map<String,SoftReference<Bitmap>> mapCache;

    private ImageCache(){
        lruCache=new LruCache<String, Bitmap>(30);
        mapCache=new HashMap<String, SoftReference<Bitmap>>();
    }

    public static ImageCache getInstance(){
        if (imageCache == null) {
            imageCache=new ImageCache();
        }
        return imageCache;
    }

    public void put(String url,Bitmap bitmap){
        if (url != null&&bitmap!=null) {
            lruCache.put(url,bitmap);
            mapCache.put(url,new SoftReference<Bitmap>(bitmap));
        }
    }

    public Bitmap get(String url){
        Bitmap ret = null;
        if (url != null) {
            ret=lruCache.get(url);
            if (ret == null) {
                SoftReference<Bitmap> reference = mapCache.get(url);
                if (reference != null) {
                    ret= reference.get();
                    if (ret == null) {
                        mapCache.remove(url);
                    }else {
                        lruCache.put(url,ret);
                    }
                }
            }
        }

        return ret;
    }

}
