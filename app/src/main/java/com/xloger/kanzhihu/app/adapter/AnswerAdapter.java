package com.xloger.kanzhihu.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.xloger.kanzhihu.app.R;
import com.xloger.kanzhihu.app.entities.Answer;

import java.util.List;

/**
 * Created by xloger on 2015/10/28.
 * Author:xloger
 * Email:phoenix@xloger.com
 */
public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder> {
    private Context context;
    private List<Answer> answerList;

    public AnswerAdapter(List<Answer> answerList, Context context) {
        this.answerList = answerList;
        this.context = context;
    }

    @Override
    public AnswerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_answer,viewGroup,false);
        AnswerViewHolder viewHolder = new AnswerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AnswerViewHolder answerViewHolder, int i) {
        answerViewHolder.title.setText(answerList.get(i).getTitle());
        answerViewHolder.summary.setText(answerList.get(i).getSummary());
    }

    @Override
    public int getItemCount() {
        return answerList==null?0:answerList.size();
    }

    public class AnswerViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView summary;
        private TextView openLink;

        public AnswerViewHolder(View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.item_answer_title);
            summary=(TextView) itemView.findViewById(R.id.item_answer_summary);
            openLink=(TextView) itemView.findViewById(R.id.item_answer_open_link);
        }
    }
}
