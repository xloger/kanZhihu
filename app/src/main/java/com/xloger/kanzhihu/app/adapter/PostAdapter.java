package com.xloger.kanzhihu.app.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.xloger.kanzhihu.app.Constants;
import com.xloger.kanzhihu.app.R;
import com.xloger.kanzhihu.app.activities.MainActivity;
import com.xloger.kanzhihu.app.entities.Post;
import com.xloger.kanzhihu.app.sql.ReadDB;

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
    private final ReadDB readDB;

    public PostAdapter(Context context, List<Post> list,MainActivity.MainClickCallBack clickCallBack) {
        super(context, list);
        this.clickCallBack=clickCallBack;
        readDB = new ReadDB(context);

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
            holder.click= (RelativeLayout) view.findViewById(R.id.item_post_click);
            holder.cardView=(CardView)view.findViewById(R.id.item_post_card_view);
            holder.readTag=(TextView)view.findViewById(R.id.item_post_read_tag);
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

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCallBack.onClick(position,getItem(position));
            }
        });

        //未读标识
        String date=getItem(position).getDate();
        date=date.replaceAll("-","");
        boolean isRead = readDB.isRead(date, getItem(position).getName());
        holder.readTag.setVisibility(View.VISIBLE);
        if (isRead){
            holder.readTag.setVisibility(View.INVISIBLE);
        }

        return view;
    }

    private static class ViewHolder{
        public TextView date;
        public TextView name;
        public TextView excerpt;
        public RelativeLayout click;
        public CardView cardView;
        public TextView readTag;
    }
}
