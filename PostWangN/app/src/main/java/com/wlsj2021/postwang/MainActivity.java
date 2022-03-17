package com.wlsj2021.postwang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.wlsj2021.postwang.adapter.LiveAdapter;
import com.wlsj2021.postwang.entity.LiveBean;
import com.wlsj2021.postwang.fragment.LiveFragment;
import com.wlsj2021.postwang.fragment.SortFragment;
import com.wlsj2021.postwang.fragment.VideoFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportFragmentManager().beginTransaction().add(R.id.layout_container, new LiveFragment()).commitAllowingStateLoss();

        mTabLayout = findViewById(R.id.tab_layout);

        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.layout_container, new LiveFragment()).addToBackStack(null).commitAllowingStateLoss();

                } else if (tab.getPosition() == 1) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.layout_container, new VideoFragment()).addToBackStack(null).commitAllowingStateLoss();

                } else if (tab.getPosition() == 2) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.layout_container, new SortFragment()).addToBackStack(null).commitAllowingStateLoss();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }



//
//    //替换Fragment的函数
//    public void replaceFragment(Fragment fragment) {
//        //得到一个碎片管理器的实例
//        FragmentManager manager = getSupportFragmentManager();
//        //得到一个碎片事务的实例并开启这个事务
//        FragmentTransaction transaction = manager.beginTransaction();
//        //将容器中的Fragment替换为指定的Fragment
//        transaction.replace(R.id.layout_container, fragment);
//        //添加回退栈
//        transaction.addToBackStack(null);
//        //提交事务
//        transaction.commitAllowingStateLoss();
//    }
}