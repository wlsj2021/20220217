package com.wlsj2021.myapplication.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.wlsj2021.myapplication.R;

import java.util.ArrayList;

public class LoginRecyclerViewAdapter extends RecyclerView.Adapter<LoginRecyclerViewAdapter.MyLoginTVHolder> {

        private final LayoutInflater mLayoutInflater;
        private final Context mContext;
        private final ArrayList<String> mData;

        public LoginRecyclerViewAdapter(Context context) {
            mLayoutInflater = LayoutInflater.from(context);
            mContext = context;

            mData = new ArrayList<>();
            for (int i = 0; i < 40; i++) {
                mData.add("item " + i);
            }
        }

        @Override
        public LoginRecyclerViewAdapter.MyLoginTVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyLoginTVHolder(mLayoutInflater.inflate(R.layout.activity_login_rv_item, parent, false));
        }

        @Override
        public void onBindViewHolder(final LoginRecyclerViewAdapter.MyLoginTVHolder holder, int pos) {
            holder.mTextView.setText(mData.get(pos));
        }

        @Override
        public int getItemCount() {
            return mData == null ? 0 : mData.size();
        }

       public static class MyLoginTVHolder extends RecyclerView.ViewHolder {

            TextView mTextView;

           MyLoginTVHolder(View itemView) {
                super(itemView);
                mTextView = (TextView) itemView.findViewById(R.id.textView_login_rv);
            }



    }


}
