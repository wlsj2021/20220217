package com.wlsj2021.myapplication.view;

import android.Manifest;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.wlsj2021.myapplication.R;
import com.wlsj2021.myapplication.adapter.LoginRecyclerViewAdapter;

import butterknife.BindView;

public class LoginActivity extends AppCompatActivity {
@BindView(R.id.rv_login)
    RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
//       mRecyclerView.setAdapter(LoginRecyclerViewAdapter);




        RxPermissions rxPermissions = new RxPermissions(this);

        rxPermissions
                .request(Manifest.permission.CAMERA,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .subscribe(granted -> {
                    if (granted) {
                        // All requested permissions are granted
                    } else {
                        // At least one permission is denied
                    }
                });




    }
}