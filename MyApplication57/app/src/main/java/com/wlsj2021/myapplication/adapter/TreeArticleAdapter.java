package com.wlsj2021.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SPUtils;
import com.wlsj2021.myapplication.R;
import com.wlsj2021.myapplication.base.utils.Constant;
import com.wlsj2021.myapplication.base.utils.JumpWebUtils;
import com.wlsj2021.myapplication.base.utils.LoginUtils;
import com.wlsj2021.myapplication.base.utils.Utils;
import com.wlsj2021.myapplication.bean.base.Event;
import com.wlsj2021.myapplication.bean.db.Article;
import com.wlsj2021.myapplication.ui.activity.LoginActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: wlsj
 * @date: 2020/01/09
 * Time: 11:56
 */
public class TreeArticleAdapter extends RecyclerView.Adapter<TreeArticleAdapter.TreeArticleHolder> {

    private Context mContext;

    private List<Article> mTreeArticleList = new ArrayList<>();

    private boolean isNightMode;


    public TreeArticleAdapter(Context context, List<Article> articleList) {
        mContext = context;
        mTreeArticleList.addAll(articleList);
        isNightMode = SPUtils.getInstance(Constant.CONFIG_SETTINGS).
                getBoolean(Constant.KEY_NIGHT_MODE, false);
    }

    /**
     * 刷新数据
     *
     * @param articleList
     */
    public void setArticleList(List<Article> articleList) {
        mTreeArticleList.clear();
        mTreeArticleList.addAll(articleList);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public TreeArticleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.article_item, parent, false);
        return new TreeArticleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TreeArticleHolder holder, int position) {
        if (mTreeArticleList != null) {
            Article articleBean = mTreeArticleList.get(position);
            holder.mArticleContent.setText(
                    Html.fromHtml(articleBean.title, Html.FROM_HTML_MODE_COMPACT));
            if (!articleBean.author.equals("")) {
                holder.mArticleAuthor.setText(
                        String.format(mContext.getResources().getString(R.string.article_author),
                                articleBean.author));
            } else {
                holder.mArticleAuthor.setText(
                        String.format(mContext.getResources().getString(R.string.article_author),
                                articleBean.shareUser));
            }
            holder.mArticleDate.setText(articleBean.niceDate);
            String category = String.format(
                    mContext.getResources().getString(R.string.article_category),
                    articleBean.superChapterName, articleBean.chapterName);
            holder.mArticleType.setText(Html.fromHtml(category, Html.FROM_HTML_MODE_COMPACT));

            if (!LoginUtils.isLogin()) {
                holder.mCollectView.setSelected(false);
            } else {
                holder.mCollectView.setSelected(articleBean.collect);
            }

            holder.itemView.setOnClickListener(view -> JumpWebUtils.startWebView(mContext,
                    mTreeArticleList.get(position).title,
                    mTreeArticleList.get(position).link,
                    mTreeArticleList.get(position).articleId,
                    mTreeArticleList.get(position).collect));

            holder.mCollectView.setOnClickListener(view -> {
                if (!articleBean.collect) {
                    Utils.Vibrate(mContext, 50);
                }
                if (!LoginUtils.isLogin()) {
                    Toast.makeText(mContext, "click", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                } else {
                    Event event = new Event();
                    event.target = Event.TARGET_TREE;
                    event.type = articleBean.collect ? Event.TYPE_UNCOLLECT : Event.TYPE_COLLECT;
                    event.data = articleBean.articleId + "";
                    EventBus.getDefault().post(event);
                }

            });
            holder.itemView.getBackground().setColorFilter(
                    mContext.getColor(isNightMode ? R.color.primary_grey_dark : R.color.card_bg), PorterDuff.Mode.SRC_ATOP);
            holder.mArticleDate.setTextColor(mContext.getColor(isNightMode ? R.color.card_bg : R.color.colorGray666));
            holder.mArticleType.setTextColor(mContext.getColor(isNightMode ? R.color.card_bg : R.color.colorGray666));
            holder.mArticleAuthor.setTextColor(mContext.getColor(isNightMode ? R.color.card_bg : R.color.colorGray666));
        }
    }


    @Override
    public int getItemCount() {
        return mTreeArticleList == null ? 0 : mTreeArticleList.size();
    }

    class TreeArticleHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_home_author)
        TextView mArticleAuthor;
        @BindView(R.id.item_home_content)
        TextView mArticleContent;
        @BindView(R.id.item_article_type)
        TextView mArticleType;
        @BindView(R.id.item_home_date)
        TextView mArticleDate;
        @BindView(R.id.item_list_collect)
        ImageView mCollectView;

        public TreeArticleHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
