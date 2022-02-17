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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SPUtils;
import com.wlsj2021.myapplication.R;
import com.wlsj2021.myapplication.base.utils.Constant;
import com.wlsj2021.myapplication.base.utils.JumpWebUtils;
import com.wlsj2021.myapplication.base.utils.LoginUtils;
import com.wlsj2021.myapplication.base.utils.Utils;
import com.wlsj2021.myapplication.bean.base.Event;
import com.wlsj2021.myapplication.bean.db.Share;
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
 * @date: 2020/01/19
 * Time: 22:49
 */
public class MeShareAdapter extends RecyclerView.Adapter<MeShareAdapter.MeShareHolder> {

    private Context mContext;
    private List<Share> mShareList = new ArrayList<>();

    private boolean isNightMode;

    public void setShareList(List<Share> shareList) {
        mShareList.clear();
        mShareList.addAll(shareList);
        notifyDataSetChanged();
    }

    public MeShareAdapter(Context context, List<Share> shareList) {
        mContext = context;
        mShareList.addAll(shareList);
        isNightMode = SPUtils.getInstance(Constant.CONFIG_SETTINGS).
                getBoolean(Constant.KEY_NIGHT_MODE, false);
    }

    @NonNull
    @Override
    public MeShareHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.article_item, parent, false);
        return new MeShareHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeShareHolder holder, int position) {
        if (mShareList != null) {
            Share share = mShareList.get(position);
            holder.mArticleContent.setText(
                    Html.fromHtml(share.title, Html.FROM_HTML_MODE_COMPACT));
            if (!share.author.equals("")) {
                holder.mArticleAuthor.setText(
                        String.format(mContext.getResources().getString(R.string.article_author),
                                share.author));
            } else {
                holder.mArticleAuthor.setText(
                        String.format(mContext.getResources().getString(R.string.article_author),
                                Utils.getDecodeName(LoginUtils.getLoginUser())));
            }
            holder.mArticleDate.setText(share.niceDate);
            String category = String.format(
                    mContext.getResources().getString(R.string.collect_category),
                    share.chapterName);
            holder.mArticleType.setText(Html.fromHtml(category, Html.FROM_HTML_MODE_COMPACT));

            holder.itemView.setOnClickListener(v -> JumpWebUtils.startWebView(mContext,
                    share.title,
                    share.link,
                    share.articleId,
                    share.isCollect));

            if (!LoginUtils.isLogin()) {
                holder.mCollectView.setSelected(false);
            } else {
                holder.mCollectView.setSelected(share.isCollect);
            }

            holder.mCollectView.setOnClickListener(v -> {
                if (!share.isCollect) {
                    Utils.Vibrate(mContext, 50);
                }
                if (!LoginUtils.isLogin()) {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                } else {
                    Event event = new Event();
                    event.target = Event.TARGET_ME_SHARE;
                    event.type = share.isCollect ? Event.TYPE_UNCOLLECT : Event.TYPE_COLLECT;
                    event.data = share.articleId + "";
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
        return mShareList != null ? mShareList.size() : 0;
    }

    class MeShareHolder extends RecyclerView.ViewHolder {
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

        MeShareHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
