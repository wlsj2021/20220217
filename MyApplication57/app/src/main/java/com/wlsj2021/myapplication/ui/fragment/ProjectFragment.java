package com.wlsj2021.myapplication.ui.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.ColorUtils;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.wlsj2021.myapplication.R;
import com.wlsj2021.myapplication.base.fragment.BaseFragment;
import com.wlsj2021.myapplication.base.utils.Constant;
import com.wlsj2021.myapplication.base.utils.Utils;
import com.wlsj2021.myapplication.bean.base.Event;
import com.wlsj2021.myapplication.bean.db.ProjectClassify;
import com.wlsj2021.myapplication.contract.project.Contract;
import com.wlsj2021.myapplication.presenter.project.ProjectPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;

import static com.blankj.utilcode.util.ColorUtils.getColor;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: wlsj
 * @date: 2019/12/19
 * Time: 17:17
 */
public class ProjectFragment extends BaseFragment<Contract.IProjectView, ProjectPresenter> implements Contract.IProjectView {

    @BindView(R.id.project_tab)
    SlidingTabLayout mSlidingTabLayout;

    @BindView(R.id.project_divider)
    View mDivider;

    @BindView(R.id.project_viewpager)
    ViewPager mViewPager;

    private ArrayList<Fragment> mFragmentSparseArray = new ArrayList<>();

    private Context mContext;

    public static ProjectFragment getInstance() {
        ProjectFragment fragment = new ProjectFragment();
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.project_fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void init() {
        mContext = getActivity().getApplicationContext();
        initStatusBar();
        initTabColor();
        mPresenter.loadProjectClassify();
        setChildViewVisibility(View.VISIBLE);
        mViewPager.setOffscreenPageLimit(2);
    }

    private void initTabColor() {
        mSlidingTabLayout.setDividerColor(Utils.getColor(mContext));
        mSlidingTabLayout.setIndicatorColor(Utils.getColor(mContext));
    }

    private void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getActivity().getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        if (ColorUtils.calculateLuminance(getColor(R.color.white)) >= 0.5) {
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        initStatusBar();
    }

    @Override
    protected ProjectPresenter createPresenter() {
        return new ProjectPresenter();
    }

    @Override
    public void onLoadProjectClassify(List<ProjectClassify> projectClassifies) {
        List<String> tabNames = projectClassifies
                .stream()
                .map(projectClassify -> projectClassify.name)
                .collect(Collectors.toList());
        projectClassifies.stream().forEach(projectClassify -> {
            ProjectListFragment projectListFragment = new ProjectListFragment(projectClassify.categoryId);
            mFragmentSparseArray.add(projectListFragment);
        });

        mViewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return mFragmentSparseArray.get(position);
            }

            @Override
            public int getCount() {
                return tabNames == null ? 0 : tabNames.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return tabNames.get(position);
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    @Override
    public void onRefreshProjectClassify(List<ProjectClassify> projectClassifies) {
        onLoadProjectClassify(projectClassifies);
    }


    private void setChildViewVisibility(int visibility) {
        mSlidingTabLayout.setVisibility(visibility);
        mDivider.setVisibility(visibility);
        mViewPager.setVisibility(visibility);
    }

    @Override
    public void onLoading() {
    }

    @Override
    public void onLoadFailed() {
    }

    @Override
    public void onLoadSuccess() {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event.target == Event.TARGET_PROJECT) {
            if (event.type == Event.TYPE_REFRESH_COLOR) {
                initTabColor();
            }
        }
    }
}
