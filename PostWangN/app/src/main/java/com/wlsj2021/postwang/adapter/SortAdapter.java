package com.wlsj2021.postwang.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.wlsj2021.postwang.R;
import com.wlsj2021.postwang.entity.LiveBean;
import com.wlsj2021.postwang.entity.SortBean;

import java.util.List;

public class SortAdapter extends RecyclerView.Adapter<SortAdapter.ViewHolder> {

    private List<SortBean> mSortList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView sortImage;
        TextView sortTitle;
        TextView sortNum;
        TextView sortFlow;

        public ViewHolder(View view) {
            super(view);
            sortImage = (ImageView) view.findViewById(R.id.short_img);
            sortTitle = (TextView) view.findViewById(R.id.short_title);
            sortNum = (TextView) view.findViewById(R.id.short_num);
            sortFlow = (TextView) view.findViewById(R.id.short_flow);
        }

    }

    public SortAdapter(List<SortBean> sortList) {
        mSortList = sortList;
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sort, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        SortBean sortBean = mSortList.get(position);
        holder.sortImage.setImageResource(sortBean.getImg());
        holder.sortTitle.setText(sortBean.getTitle());
        holder.sortNum.setText(sortBean.getNum());
        holder.sortFlow.setText(sortBean.getFlow());
    }

    @Override
    public int getItemCount() {
        return mSortList.size();
    }
}