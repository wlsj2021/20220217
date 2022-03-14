package com.wlsj2022.recyclerviewim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<Message> mDatas;
    public MessageAdapter(Context context,List<Message> mDatas) {
        this.mContext =context;
        this.mDatas =mDatas;
        mLayoutInflater =LayoutInflater.from(mContext);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==Message.TYPE_RIGHT){
            return new MeItemViewHolder(mLayoutInflater.inflate(R.layout.item_right,parent,false)) ;
        }else{
            return new OtherItemViewHolder(mLayoutInflater.inflate(R.layout.item_left,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MeItemViewHolder){
            ((MeItemViewHolder) holder).imageView.setImageResource(R.mipmap.ic_launcher);
            ((MeItemViewHolder) holder).textView.setText("我说什么"+mDatas.get(position).getText());
        }else if(holder instanceof OtherItemViewHolder){
            ((OtherItemViewHolder) holder).imageView.setImageResource(R.mipmap.ic_launcher);
            ((OtherItemViewHolder) holder).textView.setText("他说什么"+mDatas.get(position).getText());
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mDatas.get(position).getMsgType();
    }

    static class MeItemViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView imageView;
        public MeItemViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_right);
            imageView = (ImageView) itemView.findViewById(R.id.iv_right);
        }
    }
    static class OtherItemViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView imageView;
        public OtherItemViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_left);
            imageView = (ImageView) itemView.findViewById(R.id.iv_left);
        }
    }
}
