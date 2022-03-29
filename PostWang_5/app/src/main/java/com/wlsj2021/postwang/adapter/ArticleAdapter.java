package com.wlsj2021.postwang.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;



import com.wlsj2021.postwang.R;
import com.wlsj2021.postwang.activity.LoginActivity;
import com.wlsj2021.postwang.base.Constant;
import com.wlsj2021.postwang.base.Event;
import com.wlsj2021.postwang.base.JumpWebUtils;
import com.wlsj2021.postwang.base.LoginUtils;
import com.wlsj2021.postwang.base.Utils;
import com.wlsj2021.postwang.entity.db.Article;
import com.wlsj2022.wlsj.store.SharePreferencesStore;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


/**
 * Created with Android Studio.
 * Description: 首页文章adapter
 *
 * @author: wlsj
 * @date: 2019/12/19
 * Time: 18:30
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleHolder> {

    /**
     * Item type
     */
    private static final int TYPE_HEADER = 0;

    private static final int TYPE_NORMAL = 1;

    private Context mContext;

    private List<Article> mArticleList = new ArrayList<>();

    private View mHeaderView;

    /**
     * RecyclerView是否向上滑动
     */
    private boolean isPullingUp = false;

    /**
     * RecyclerView是否向下滑动
     */
    private boolean isPullingDown = false;

    /**
     * item从上向下滑动入场动画
     */
    private Animation bottomInAnimation;

    /**
     * item从下向上滑动入场动画
     */
    private Animation topInAnimation;

    /**
     * 是否为夜间模式
     */
    private boolean isNightMode;

    public ArticleAdapter(Context context, List<Article> articleList) {
        mContext = context;
        mArticleList.addAll(articleList);
        isNightMode = SharePreferencesStore.getInstance(Constant.CONFIG_SETTINGS).
                getBoolean(Constant.KEY_NIGHT_MODE, false);
    }

    /**
     * 为上滑刷新做准备
     *
     * @param articleList
     */
    public void setArticleList(List<Article> articleList) {
        mArticleList.clear();
        mArticleList.addAll(articleList);
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) {
            return TYPE_NORMAL;
        }
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_NORMAL;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
    }

    @NonNull
    @Override
    public ArticleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new ArticleHolder(mHeaderView);
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.article_item, parent, false);
        return new ArticleHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ArticleHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) {
            return;
        }
        final int realPosition = getRealPosition(holder);
        if (mArticleList != null) {
            Article articleBean = mArticleList.get(realPosition);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                holder.mArticleContent.setText(
                        Html.fromHtml(articleBean.title, Html.FROM_HTML_MODE_COMPACT));
            }
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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                holder.mArticleType.setText(Html.fromHtml(category, Html.FROM_HTML_MODE_COMPACT));
            }

            if (articleBean.isTop) {
                holder.mTopView.setVisibility(View.VISIBLE);
            }
            if (articleBean.isFresh) {
                holder.mNewView.setVisibility(View.VISIBLE);
            }

            holder.mQuestionView.setVisibility(View.VISIBLE);
            holder.mQuestionView.setText(articleBean.superChapterName);

            if (!LoginUtils.isLogin()) {
                holder.mCollectView.setSelected(false);
            } else {
                holder.mCollectView.setSelected(articleBean.collect);
            }

            holder.itemView.setOnClickListener(view -> JumpWebUtils.startWebView(mContext,
                    mArticleList.get(realPosition).title,
                    mArticleList.get(realPosition).link,
                    mArticleList.get(realPosition).articleId,
                    mArticleList.get(realPosition).collect));
            holder.mCollectView.setOnClickListener(view -> {
                if (!articleBean.collect) {
                    Utils.Vibrate(mContext, 50);
                }
                if (!LoginUtils.isLogin()) {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                } else {
                    Event event = new Event();
                    event.target = Event.TARGET_HOME;
                    event.type = articleBean.collect ? Event.TYPE_UNCOLLECT : Event.TYPE_COLLECT;
                    event.data = articleBean.articleId + "";
                    EventBus.getDefault().post(event);
                }

            });

            holder.itemView.getBackground().setColorFilter(mContext.getColor(
                    isNightMode ? R.color.primary_grey_dark : R.color.card_bg), PorterDuff.Mode.SRC_ATOP);
            holder.mArticleDate.setTextColor(mContext.getColor(isNightMode ? R.color.card_bg : R.color.colorGray666));
            holder.mArticleType.setTextColor(mContext.getColor(isNightMode ? R.color.card_bg : R.color.colorGray666));
            holder.mArticleAuthor.setTextColor(mContext.getColor(isNightMode ? R.color.card_bg : R.color.colorGray666));

        }
    }

    private int getRealPosition(ArticleHolder articleHolder) {
        int position = articleHolder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemCount() {
        if (mArticleList == null) {
            return 0;
        }
        return mHeaderView == null ? mArticleList.size() : mArticleList.size() + 1;
    }

    class ArticleHolder extends RecyclerView.ViewHolder {
        TextView mArticleAuthor;
        TextView mArticleContent;
        TextView mArticleType;
        TextView mArticleDate;
        ImageView mCollectView;
        TextView mTopView;
        TextView mNewView;
        TextView mQuestionView;

        public ArticleHolder(@NonNull View itemView) {
            super(itemView);
            if (itemView == mHeaderView) {
                return;
            }

            mArticleAuthor = itemView.findViewById(R.id.item_home_author);
            mArticleContent = itemView.findViewById(R.id.item_home_content);
            mArticleType = itemView.findViewById(R.id.item_article_type);
            mArticleDate = itemView.findViewById(R.id.item_home_date);
            mCollectView = itemView.findViewById(R.id.item_list_collect);
            mTopView = itemView.findViewById(R.id.item_home_top_article);
            mNewView = itemView.findViewById(R.id.item_home_new);
            mQuestionView = itemView.findViewById(R.id.item_home_question);




//            ButterKnife.bind(this, itemView);
        }
    }


}
