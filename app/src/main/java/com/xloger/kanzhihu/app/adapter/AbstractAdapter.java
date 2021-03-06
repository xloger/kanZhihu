package com.xloger.kanzhihu.app.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by xloger on 2015/10/27.
 * Author:xloger
 * Email:phoenix@xloger.com
 */

/**
 * 基础适配器<br/>
 * 使用方法：继承AbstractAdapter并指定List的类型<br/>
 * 只需要实现getView()方法即可
 * @param <T>
 */
public abstract class AbstractAdapter<T> extends BaseAdapter {
    protected Context context;
    protected List<T> list;

    public AbstractAdapter(Context context, List<T> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        int ret = 0;
        if (list != null) {
            ret=list.size();
        }
        return ret;
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
