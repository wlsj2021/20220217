package com.wlsj2021.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SPUtils;
import com.wlsj2021.myapplication.R;
import com.wlsj2021.myapplication.base.utils.Constant;
import com.wlsj2021.myapplication.base.utils.Utils;
import com.wlsj2021.myapplication.bean.square.TreeData;
import com.wlsj2021.myapplication.ui.activity.TreeListActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: wlsj
 * @date: 2020/01/08
 * Time: 11:26
 */
public class TreeAdapter extends RecyclerView.Adapter<TreeAdapter.TreeHolder> {

    private Context mContext;

    private List<TreeData.DataBean> mTreeBeans;

    /**
     * 是否为夜间模式
     */
    private boolean isNightMode;

    public void setBeans(TreeData treeData) {
        mTreeBeans = treeData.getData();
    }

    public TreeAdapter(Context context) {
        mContext = context;
        isNightMode = SPUtils.getInstance(Constant.CONFIG_SETTINGS).
                getBoolean(Constant.KEY_NIGHT_MODE, false);
    }

    @NonNull
    @Override
    public TreeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_navigation, parent, false);
        return new TreeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TreeHolder holder, int position) {
        if (mTreeBeans != null) {
            List<TreeData.DataBean.ChildrenBean> childrenBeans = mTreeBeans.get(position).getChildren();
            holder.mItemTitle.setText(Html.fromHtml(mTreeBeans.get(position).getName(), Html.FROM_HTML_MODE_COMPACT));
            holder.mTagFlowLayout.setAdapter(new TagAdapter(childrenBeans) {
                @Override
                public View getView(FlowLayout parent, int position, Object o) {
                    TextView tagView = (TextView) LayoutInflater.from(mContext).inflate(R.layout.flow_layout, parent, false);
                    tagView.setText(childrenBeans.get(position).getName());
                    tagView.setTextColor(Utils.randomColor());
                    holder.mTagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                        @Override
                        public boolean onTagClick(View view, int position, FlowLayout parent) {
                            Intent intent = new Intent(mContext, TreeListActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra(Constant.KEY_TREE_CID, childrenBeans.get(position).getId());
                            intent.putExtra(Constant.KEY_TITLE, childrenBeans.get(position).getName());
                            mContext.startActivity(intent);
                            return true;
                        }
                    });
                    return tagView;
                }
            });
            holder.itemView.getBackground().setColorFilter(
                    mContext.getColor(isNightMode ? R.color.primary_grey_dark : R.color.card_bg), PorterDuff.Mode.SRC_ATOP);

        }
    }

    @Override
    public int getItemCount() {
        return mTreeBeans == null ? 0 : mTreeBeans.size();
    }

    class TreeHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_system_title)
        TextView mItemTitle;
        @BindView(R.id.item_system_flowlayout)
        TagFlowLayout mTagFlowLayout;

        public TreeHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
