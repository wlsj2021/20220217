package com.wlsj2021.myapplication.videos;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 上下滑动切换适配器
 */
public abstract class VideoPlayAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> implements OnPageChangeListener {

}