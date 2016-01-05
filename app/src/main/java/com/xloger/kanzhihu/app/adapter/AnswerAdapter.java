package com.xloger.kanzhihu.app.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.xloger.kanzhihu.app.R;
import com.xloger.kanzhihu.app.activities.AnswerActivity;
import com.xloger.kanzhihu.app.cache.CacheTool;
import com.xloger.kanzhihu.app.entities.Answer;

import java.util.List;

/**
 * Created by xloger on 2015/10/28.
 * Author:xloger
 * Email:phoenix@xloger.com
 */
public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder> {
    private Context context;
    private static List<Answer> answerList;
    private static AnswerActivity.OpenLinkCallBack callBack;

    public AnswerAdapter(List<Answer> answerList, Context context,AnswerActivity.OpenLinkCallBack callBack) {
        this.answerList = answerList;
        this.context = context;
        this.callBack=callBack;
    }

    @Override
    public AnswerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_answer,viewGroup,false);
        AnswerViewHolder viewHolder = new AnswerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AnswerViewHolder answerViewHolder, final int i) {
        answerViewHolder.title.setText(answerList.get(i).getTitle());
        answerViewHolder.summary.setText(answerList.get(i).getSummary());

        answerViewHolder.layout.setTag(i);
        answerViewHolder.layout.setOnClickListener(openListener);

        answerViewHolder.author.setText(answerList.get(i).getAuthorname());
        String voteString=answerList.get(i).getVote()+"";
        if (answerList.get(i).getVote()/10000>0) {
            voteString=answerList.get(i).getVote()/1000+"k";
        }
        answerViewHolder.vote.setText(voteString);

//        answerViewHolder.avatar.setTag(answerList.get(i).getAvatar());
        CacheTool.cacheImage(answerViewHolder.avatar,answerList.get(i).getAvatar(),30,30);
    }

    private static View.OnClickListener openListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            callBack.onClick(answerList.get((Integer) v.getTag()));
        }
    };

    @Override
    public int getItemCount() {
        return answerList==null?0:answerList.size();
    }

    public class AnswerViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView summary;
        private TextView openLink;
        private CardView layout;

        private ImageView avatar;
        private TextView author;
        private TextView vote;

        public AnswerViewHolder(View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.item_answer_title);
            summary=(TextView) itemView.findViewById(R.id.item_answer_summary);
//            openLink=(TextView) itemView.findViewById(R.id.item_answer_open_link);
            layout=(CardView)itemView.findViewById(R.id.item_answer_layout);

            avatar=(ImageView)itemView.findViewById(R.id.item_answer_avatar);
            author=(TextView)itemView.findViewById(R.id.item_answer_author);
            vote=(TextView)itemView.findViewById(R.id.item_answer_vote);
        }
    }
}
