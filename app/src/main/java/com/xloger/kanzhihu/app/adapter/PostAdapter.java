package com.xloger.kanzhihu.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.xloger.kanzhihu.app.R;
import com.xloger.kanzhihu.app.entities.Post;

import java.util.List;

/**
 * Created by xloger on 2015/10/27.
 * Author:xloger
 * Email:phoenix@xloger.com
 */
public class PostAdapter extends AbstractAdapter<Post> {
    public PostAdapter(Context context, List<Post> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            view= LayoutInflater.from(context).inflate(R.layout.item_post,parent,false);
        }else {
            view=convertView;
        }

        ViewHolder holder;
        if (view.getTag() == null) {
            holder=new ViewHolder();
            holder.date= (TextView) view.findViewById(R.id.item_post_date);
            holder.time= (TextView) view.findViewById(R.id.item_post_time);
            holder.name= (TextView) view.findViewById(R.id.item_post_name);
        }else {
            holder= (ViewHolder) view.getTag();
        }

        holder.date.setText(getItem(position).getDate());
        holder.time.setText(getItem(position).getDate());
        holder.name.setText(getItem(position).getName());


        return view;
    }

    private class ViewHolder{
        public TextView date;
        public TextView time;
        public TextView name;
    }
}
