package com.wlsj2021.myapplication.view;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.trello.rxlifecycle.ActivityEvent;
import com.wlsj2021.myapplication.R;
import com.wlsj2021.myapplication.msg.EventBusMsg;
import com.wlsj2021.myapplication.presenter.RegisterPresenter;
import com.wlsj2021.myapplication.presenter.RegisterPresenterIml;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;
import com.yzq.zxinglibrary.encode.CodeCreator;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

//6
// 在支持路由的页面上添加注解(必选)
// 这里的路径需要注意的是至少需要有两级，/xx/xx
@Route(path = "/register/activity")
public class RegisterActivity extends AppCompatActivity implements RegisterView {


    @Autowired(name = "money")
    public String money;
    @Autowired(name = "ddd")
    boolean ddd;
    @BindView(R.id.editText_username)
    EditText editTextUsername;
    @BindView(R.id.editText2)
    EditText etPsw;
    @BindView(R.id.et_repsw)
    EditText etRepsw;
    @BindView(R.id.imageView)
    ImageView mImageView;

    private RegisterPresenter mPresenter;
    private EventBusMsg mEventBusMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        getLifecycle().addObserver(new RegisterPresenterIml(this));


        RxPermissions rxPermissions = new RxPermissions(this);

        rxPermissions
                .request(Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .subscribe(granted -> {
                    if (granted) {
                        // All requested permissions are granted
                    } else {
                        // At least one permission is denied
                    }
                });


        ARouter.getInstance().inject(this);

        Log.e("TAG", "传递过来的数据: " + money + ddd, null);

        mPresenter = new RegisterPresenterIml(this);

//        Toast.makeText(this, "-----"+money, Toast.LENGTH_SHORT).show();

        mPresenter.registerMe(editTextUsername.getText().toString(), etPsw.getText().toString(), etRepsw.getText().toString());


    }

    @Override
    public void registerSuccess() {

    }

    @Override
    public void registerFail() {

    }

    @Override
    public void showDialog() {

    }

    @Override
    public void hideDialog() {

    }

    @Override
    public void navigate() {

    }

    @Override
    public void updateData() {

    }

    //2RXJava内存优化
    @Override
    public <T> Observable.Transformer<T, T> bindToLifecycle() {
        return this.bindToLifecycle();
    }
    //4RXJava内存优化
    @Override
    protected void onDestroy() {


        mPresenter.onDestroy();

        super.onDestroy();
    }

    public void sent_btn(View view) {
        Log.e("TAG", "sent_btn: ", null);


        //EventBus发消息
        EventBus.getDefault().post(new EventBusMsg("1", "换头像"));


        //生成二维码
        Bitmap logo = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        Bitmap bitmap = CodeCreator.createQRCode("哈哈哈", 400, 400, logo);
        mImageView.setImageBitmap(bitmap);


        //扫描二维码
        Intent intent = new Intent(RegisterActivity.this, CaptureActivity.class);
        startActivityForResult(intent, 101);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 扫描二维码/条码回传
        if (requestCode == 101 && resultCode == RESULT_OK) {
            if (data != null) {

                String content = data.getStringExtra(Constant.CODED_CONTENT);
                Toast.makeText(this, "ddddd" + content, Toast.LENGTH_SHORT).show();
                Log.e("TAG", "onActivityResult: " + content, null);
            }
        }
    }
}

