package com.wlsj2021.postwang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.wlsj2021.postwang.R;
import com.wlsj2021.postwang.activity.LiveActivity;
import com.wlsj2021.postwang.entity.LiveBean;

import java.util.List;

public class LiveAdapter extends RecyclerView.Adapter<LiveAdapter.ViewHolder> {
    private MyItemClickListener mItemClickListener;
    private List<LiveBean> mLiveList;

    public LiveAdapter(List<LiveBean> fruitList) {
        mLiveList = fruitList;
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_live, parent, false);
        ViewHolder holder = new ViewHolder(view, mItemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        LiveBean liveBean = mLiveList.get(position);
        holder.liveImage.setImageResource(liveBean.getImgId());
        holder.liveTitle.setText(liveBean.getTitle());

    }

    @Override
    public int getItemCount() {
        return mLiveList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView liveImage;
        TextView liveTitle;
        private MyItemClickListener mListener;

        public ViewHolder(View view, MyItemClickListener myItemClickListener) {

            super(view);
            mListener = myItemClickListener;
            itemView.setOnClickListener(this);
            liveImage = (ImageView) view.findViewById(R.id.live_iv);
            liveTitle = (TextView) view.findViewById(R.id.live_tv);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getPosition());
            }
        }
    }

    /**
     * 创建一个回调接口
     */
    public interface MyItemClickListener {
        void onItemClick(View view, int position);
    }

    /**
     * 在activity里面adapter就是调用的这个方法,将点击事件监听传递过来,并赋值给全局的监听
     *
     * @param myItemClickListener
     */
    public void setItemClickListener(MyItemClickListener myItemClickListener) {
        this.mItemClickListener = myItemClickListener;
    }


}