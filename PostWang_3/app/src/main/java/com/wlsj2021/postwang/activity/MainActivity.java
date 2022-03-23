package com.wlsj2021.postwang.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.wlsj2021.postwang.R;
import com.wlsj2021.postwang.base.Constant;
import com.wlsj2021.postwang.base.Event;
import com.wlsj2021.postwang.base.Utils;
import com.wlsj2021.postwang.fragment.HomeFragment;
import com.wlsj2021.postwang.fragment.MeFragment;
import com.wlsj2021.postwang.fragment.ParentSquareFragment;
import com.wlsj2021.postwang.fragment.ProjectFragment;
import com.wlsj2021.postwang.fragment.WeChatFragment;
import com.wlsj2022.wlsj.load.LoadingView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class MainActivity extends AppCompatActivity {

    private static final int INDEX_HOMEPAGE = 0;
    private static final int INDEX_PROJECT = 1;
    private static final int INDEX_SQUARE = 2;
    private static final int INDEX_WE_CHAT = 3;
    private static final int INDEX_ME = 4;

    private SparseArray<Fragment> mFragmentSparseArray = new SparseArray<>();

    private Fragment mCurrentFragment;

    private Fragment mLastFragment;

    private int mLastIndex = -1;

    private long mExitTime = 0;

    private Context mContext;


    BottomNavigationView mBottomNavigationView;

    LoadingView mLoadingView;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // recreate时保存当前页面位置
        outState.putInt("index", mLastIndex);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // 恢复recreate前的页面
        mLastIndex = savedInstanceState.getInt("index");
        switchFragment(mLastIndex);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 解决Fragment需要一个构造函数的问题
        super.onCreate(null);
        setContentView(R.layout.activity_main);
        mBottomNavigationView = findViewById(R.id.navigation_bottom);
        mLoadingView = findViewById(R.id.loading_view);



        EventBus.getDefault().register(this);
        mContext = getApplicationContext();
        initBottomNavigation();
        // 判断当前是recreate还是新启动
        if (savedInstanceState == null) {
            switchFragment(INDEX_HOMEPAGE);
        }
        mBottomNavigationView.setItemIconTintList(Utils.getColorStateList(mContext));
        mBottomNavigationView.setItemTextColor(Utils.getColorStateList(mContext));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initBottomNavigation() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(
                menuItem -> {
                    switch (menuItem.getItemId()) {
                        case R.id.menu_home:
                            switchFragment(INDEX_HOMEPAGE);
                            return true;
                        case R.id.menu_project:
                            switchFragment(INDEX_PROJECT);
                            return true;
                        case R.id.menu_square:
                            switchFragment(INDEX_SQUARE);
                            return true;
                        case R.id.menu_wechat:
                            switchFragment(INDEX_WE_CHAT);
                            return true;
                        case R.id.menu_me:
                            switchFragment(INDEX_ME);
                            return true;
                        default:
                            return false;
                    }
                });
//        showBadgeView(4, 6);
    }


    private void switchFragment(int index) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 将当前显示的fragment和上一个需要隐藏的fragment分别加上tag, 并获取出来
        // 给fragment添加tag,这样可以通过findFragmentByTag找到存在的fragment，不会出现重复添加
        mCurrentFragment = fragmentManager.findFragmentByTag("fragment" + index);
        mLastFragment = fragmentManager.findFragmentByTag("fragment" + mLastIndex);
        // 如果位置不同
        if (index != mLastIndex) {
            if (mLastFragment != null) {
                transaction.hide(mLastFragment);
            }
            if (mCurrentFragment == null) {
                mCurrentFragment = getFragment(index);
                transaction.add(R.id.container, mCurrentFragment, "fragment" + index);
            } else {
                transaction.show(mCurrentFragment);
            }
        }

        // 如果位置相同或者新启动的应用
        if (index == mLastIndex) {
            if (mCurrentFragment == null) {
                mCurrentFragment = getFragment(index);
                transaction.add(R.id.container, mCurrentFragment, "fragment" + index);
            }
        }
        transaction.commit();
        mLastIndex = index;
    }

    private Fragment getFragment(int index) {
        Fragment fragment = mFragmentSparseArray.get(index);
        if (fragment == null) {
            switch (index) {
                case INDEX_HOMEPAGE:
                    fragment = HomeFragment.getInstance();
                    break;
                case INDEX_PROJECT:
                    fragment = ProjectFragment.getInstance();
                    break;
                case INDEX_SQUARE:
                    fragment = ParentSquareFragment.getInstance();
                    break;
                case INDEX_WE_CHAT:
                    fragment = WeChatFragment.getInstance();
                    break;
                case INDEX_ME:
                    fragment = MeFragment.getInstance();
                    break;
                default:
                    break;
            }
            mFragmentSparseArray.put(index, fragment);
        }
        return fragment;
    }

    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        if (curTime - mExitTime > Constant.EXIT_TIME) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = curTime;
        } else {
            super.onBackPressed();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event.target == Event.TARGET_MAIN) {
            if (event.type == Event.TYPE_REFRESH_COLOR) {
                mBottomNavigationView.setItemIconTintList(Utils.getColorStateList(mContext));
                mBottomNavigationView.setItemTextColor(Utils.getColorStateList(mContext));
            } else if (event.type == Event.TYPE_CHANGE_DAY_NIGHT_MODE) {
                recreate();
            } else if (event.type == Event.TYPE_START_ANIMATION) {
                mLoadingView.setVisibility(View.VISIBLE);
                mLoadingView.startTranglesAnimation();
            } else if (event.type == Event.TYPE_STOP_ANIMATION) {
                mLoadingView.setVisibility(View.GONE);
            }
        }
    }
}
