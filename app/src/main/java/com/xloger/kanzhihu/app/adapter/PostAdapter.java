package com.xloger.kanzhihu.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.xloger.kanzhihu.app.Constants;
import com.xloger.kanzhihu.app.R;
import com.xloger.kanzhihu.app.activities.MainActivity;
import com.xloger.kanzhihu.app.entities.Post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xloger on 2015/10/27.
 * Author:xloger
 * Email:phoenix@xloger.com
 */
public class PostAdapter extends AbstractAdapter<Post> {

    private final Map<String, String> map= Constants.nameMap;
    private MainActivity.MainClickCallBack clickCallBack;

    public PostAdapter(Context context, List<Post> list,MainActivity.MainClickCallBack clickCallBack) {
        super(context, list);
        this.clickCallBack=clickCallBack;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
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
            holder.name= (TextView) view.findViewById(R.id.item_post_name);
            holder.excerpt= (TextView) view.findViewById(R.id.item_post_excerpt);
            holder.click= (LinearLayout) view.findViewById(R.id.item_post_click);
        }else {
            holder= (ViewHolder) view.getTag();
        }

        holder.date.setVisibility(View.GONE);
        String lastDate=null;
        if (position!=0){
            lastDate=getItem(position-1).getDate();
        }
        String nowDate=getItem(position).getDate();
        if(lastDate==null||!lastDate.equals(nowDate)){
            holder.date.setVisibility(View.VISIBLE);
        }


        holder.date.setText(getItem(position).getDate());
        holder.name.setText(map.get(getItem(position).getName()));
        holder.excerpt.setText(getItem(position).getExcerpt());

        holder.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCallBack.onClick(getItem(position));
            }
        });


        return view;
    }

    private class ViewHolder{
        public TextView date;
        public TextView name;
        public TextView excerpt;
        public LinearLayout click;
    }
}
