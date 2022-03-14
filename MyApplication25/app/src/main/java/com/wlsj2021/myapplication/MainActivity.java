package com.wlsj2021.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alipay.sdk.app.PayTask;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.zxing.common.StringUtils;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.components.RxActivity;
import com.trello.rxlifecycle.components.RxFragment;
import com.wlsj2021.myapplication.dagger2.DaggerRegisterComponent;
import com.wlsj2021.myapplication.model.Entity;
import com.wlsj2021.myapplication.net.RegisterService;
import com.wlsj2021.myapplication.presenter.Presenter;
import com.wlsj2021.myapplication.view.CrView;
import com.wlsj2021.myapplication.view.HomeActivity;
import com.wlsj2021.myapplication.view.RegisterActivity;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.crypto.Cipher;
import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import it.sauronsoftware.base64.Base64;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity extends RxActivity {
@BindView(R.id.button)
Button mButton;
@BindView(R.id.button2)
    Button mButton2;

@BindView(R.id.imageView2)
    ImageView mImageView;


    @Inject
    Presenter mPresenter;


    @BindView(R.id.bmapView)
    MapView mMapView;

    private BaiduMap mBaiduMap;


    @BindView(R.id.crView)
    CrView mCrView;

    public void btn_location(View view) {

        mBaiduMap.setMyLocationEnabled(true);

//定位初始化
        LocationClient mLocationClient = new LocationClient(this);

//通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);

//设置locationClientOption
        mLocationClient.setLocOption(option);

//注册LocationListener监听器
        MyLocationListener myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);
//开启地图定位图层
        mLocationClient.start();



    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        mCrView.setText("");
//        Handler mHandler = new Handler() {
//            public void handleMessage(Message msg) {
//                Result result = new Result((String) msg.obj);
//                Toast.makeText(DemoActivity.this, result.getResult(),
//                        Toast.LENGTH_LONG).show();
//            };
//        };
//        final String orderInfo = "info";   // 订单信息
//        Runnable payRunnable = new Runnable() {
//            @Override
//            public void run() {
//                PayTask alipay = new PayTask(DemoActivity.this);
//                Map<String,String> result = alipay.payV2(orderInfo,true);
//                Message msg = new Message();
//                msg.what = SDK_PAY_FLAG;
//                msg.obj = result;
//                mHandler.sendMessage(msg);
//            }
//        };
//        // 必须异步调用
//        Thread payThread = new Thread(payRunnable);
//        payThread.start();



        getMD5String("方法");
        try {
            initkeys();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        HashMap<String,Object> kes = null;
        try {
            kes = initkeys();
        Key key = (Key) kes.get("public_key");
        Key key1 = (Key) kes.get("private_key");
        assert key != null;
        byte[] pubKey = key.getEncoded(); //公钥
        assert key1 != null;
        byte[] priKey = key1.getEncoded(); //私钥

        Log.e("TAG", "公钥: "+ Arrays.toString(Base64.encode(pubKey))+"私钥："+ Arrays.toString(Base64.encode(priKey)), null);

        String str= "加密内容";

        Log.e("TAG", "原文: "+ Arrays.toString(Base64.encode(str.getBytes())), null);

        byte[] code1= new byte[0];

        code1 = encryptByPublickey(str.getBytes(), pubKey);

        Log.e("TAG", "公钥加密后的数据: "+ Arrays.toString(Base64.encode(code1)), null);

        byte[] code2 = new byte[0];

        code2 = decryptByPrivateKey(code1,priKey);

        Log.e("TAG", "私钥解密后的数据: "+ Arrays.toString(Base64.encode(code2)), null);
        } catch (Exception e) {
            e.printStackTrace();
        }



















/**
 *
 *    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" /> <!-- 访问网络，进行地图相关业务数据请求，包括地图数据，路线规划，POI检索等 -->
 *     <uses-permission android:name="android.permission.INTERNET" /> <!-- 获取网络状态，根据网络状态切换进行数据请求网络转换 -->
 *     <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" /> <!-- 读取外置存储。如果开发者使用了so动态加载功能并且把so文件放在了外置存储区域，则需要申请该权限，否则不需要 -->
 *     <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" /> <!-- 写外置存储。如果开发者使用了离线地图，并且数据写在外置存储区域，则需要申请该权限 -->
 *     <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 *     <!-- 这个权限用于进行网络定位 -->
 *     <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
 *     <!-- 这个权限用于访问GPS定位 -->
 *     <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
 *
 *
 *
 */
        RxPermissions rxPermissions = new RxPermissions(this);

        rxPermissions
                .request(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_WIFI_STATE

                )
                .subscribe(granted -> {
                    if (granted) {
                        // All requested permissions are granted
                    } else {
                        // At least one permission is denied
                    }
                });



        mBaiduMap = mMapView.getMap();

        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);

        mBaiduMap.setTrafficEnabled(true);

//        mBaiduMap.setBaiduHeatMapEnabled(true);




        /**
         * 1 继承某view 进行封装
         * 2 将多个view 放在一起 当做一个view
         * 3 直接继承自view 然后。。。
         *
         *
          */
//时间选择器 省市县
        TimePickerView pvTime = new TimePickerBuilder(MainActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                Toast.makeText(MainActivity.this, ""+date, Toast.LENGTH_SHORT).show();
            }
        }).isCyclic(true).


                build();

        pvTime.show();

//
//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate_anim);
//        mImageView.startAnimation(animation);
////
//        AnimatedVectorDrawableCompat background =(AnimatedVectorDrawableCompat) mImageView.getDrawable();
//        background.start();

//        Drawable drawable = mImageView.getDrawable();
//        if (drawable instanceof AnimatedVectorDrawableCompat) {
//            ((AnimatedVectorDrawableCompat) drawable).start();
//        }
//

//        final Drawable drawable = mImageView.getDrawable();
//        if (drawable instanceof Animatable) {
//            ((Animatable) drawable).start();

//
//        AnimatedVectorDrawable drawable1 =(AnimatedVectorDrawable) mImageView.getDrawable();
//        drawable1.start();
//

//        mImageView.setBackgroundResource(R.drawable.draw_anim);
//        AnimationDrawable animationDrawable1 = (AnimationDrawable) mImageView.getBackground();
//        animationDrawable1.start();

//        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.object_anim);
//        animator.setTarget(mImageView);
//        animator.start();
//
//
//        AnimatedVectorDrawable drawable = (AnimatedVectorDrawable)mImageView.getBackground();
//
//        drawable.start();


            mButton2.setText("sdsd");

            mButton.setText("点击");

//        DaggerRegisterComponent.create().inject(this);
            DaggerRegisterComponent.builder().build().inject(this);

            mPresenter.see();

//复杂度度高的 API 21 DVM ART
            //帧动画 实例化
            AnimationDrawable animationDrawable = new AnimationDrawable();
            //添加帧  添加每一帧显示的时间
            animationDrawable.addFrame(getResources().getDrawable(R.drawable.a1), 200);
            animationDrawable.addFrame(getResources().getDrawable(R.drawable.a2), 200);
            animationDrawable.addFrame(getResources().getDrawable(R.drawable.a3), 200);
            animationDrawable.addFrame(getResources().getDrawable(R.drawable.a4), 200);
            animationDrawable.addFrame(getResources().getDrawable(R.drawable.a5), 200);
            animationDrawable.addFrame(getResources().getDrawable(R.drawable.a6), 200);
            animationDrawable.addFrame(getResources().getDrawable(R.drawable.a7), 200);
            //设置帧动画的播放次数
            animationDrawable.setOneShot(false);
            //给imageview设置对应的动画
//        mImageView.setImageDrawable(animationDrawable);
            //动画开始播放
//        animationDrawable.start();

            //补间动画
            //透明度动画 实例化
            AlphaAnimation mAlphaAnimation = new AlphaAnimation(0, 1);
            //给动画添加差值器 AccelerateInterpolator加速差值器
            mAlphaAnimation.setInterpolator(new AccelerateInterpolator());
            //给动画设置时间
            mAlphaAnimation.setDuration(2000);
            //给动画设置播放的次数
            mAlphaAnimation.setRepeatCount(5);
            //给动画添加监听事件
            mAlphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    Log.e("TAG", "onAnimationStart: 动画开始", null);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    Log.e("TAG", "onAnimationEnd: 动画结束", null);

                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    Log.e("TAG", "onAnimationRepeat: 动画重复", null);

                }
            });
//        mImageView.setAnimation(mAlphaAnimation);
            //动画开始
//        mAlphaAnimation.start();
            //平移动画
            TranslateAnimation translateAnimation = new TranslateAnimation(0, 200, 0, 200);
            //添加回弹差值器
            translateAnimation.setInterpolator(new BounceInterpolator());
            translateAnimation.setDuration(2000);
            translateAnimation.setRepeatCount(5);
//        mImageView.setAnimation(translateAnimation);
//        translateAnimation.start();
            //旋转动画
            RotateAnimation rotateAnimation = new RotateAnimation(0, 200, 0, 200);
            rotateAnimation.setInterpolator(new BounceInterpolator());
            rotateAnimation.setDuration(2000);
            rotateAnimation.setRepeatCount(5);
//        mImageView.setAnimation(rotateAnimation);
//        rotateAnimation.start();
            //缩放动画
            ScaleAnimation scaleAnimation = new ScaleAnimation(0.5f, 2, 0.5f, 2);
            scaleAnimation.setInterpolator(new BounceInterpolator());
            scaleAnimation.setDuration(2000);
            scaleAnimation.setRepeatCount(5);
//        mImageView.setAnimation(scaleAnimation);
//        scaleAnimation.start();
            //动画集合
            AnimationSet animationSet = new AnimationSet(true);
            //设置集合时间
            animationSet.setDuration(5000);
            //添加集合中的动画
            animationSet.addAnimation(mAlphaAnimation);
            animationSet.addAnimation(rotateAnimation);
            animationSet.addAnimation(scaleAnimation);
            animationSet.addAnimation(translateAnimation);
            //设置集合的播放次数
            animationSet.setRepeatCount(10);
//        mImageView.startAnimation(animationSet);
//        animationSet.start();


//        属性动画ValueAnimator
            ObjectAnimator alpha = ObjectAnimator.ofFloat(mImageView, "alpha", 0, 1, 0, 1, 0.5f, 1, 0, 5);
            alpha.setInterpolator(new AccelerateDecelerateInterpolator());
            alpha.setDuration(2000);
            alpha.setRepeatCount(10);
            alpha.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
//        alpha.start();
            //属性动画平移动画  values可以设置多个
            ObjectAnimator translate = ObjectAnimator.ofFloat(mImageView, "translationX", 0, -100, 0, 1, 0.5f, 1, 0, 50);
            translate.setInterpolator(new DecelerateInterpolator());
            translate.setDuration(2000);
            translate.setRepeatCount(10);
//        translate.start();

            //属性动画 缩放
            ObjectAnimator scale = ObjectAnimator.ofFloat(mImageView, "scaleY", 0, -100, 0, 1, 0.5f, 1, 0, 50);
            scale.setInterpolator(new DecelerateInterpolator());
            scale.setDuration(200);
            scale.setRepeatCount(1);
//        scale.start();

            //属性动画 旋转
            ObjectAnimator rotate = ObjectAnimator.ofFloat(mImageView, "rotationY", 0, -100, 0, 1, 0.5f, 1, 0, 50);
            rotate.setInterpolator(new DecelerateInterpolator());
            rotate.setDuration(2000);
            rotate.setRepeatCount(1);
//        rotate.start();

            //动画合集
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(2000);
            animatorSet.setInterpolator(new BounceInterpolator());
//        animatorSet.play(rotate);
//        animatorSet.playTogether(
//                translate,scale,rotate,alpha
//        );
            //属性动画的集合 with同时播放 after之后播放 before之前播放
            animatorSet.play(rotate).with(alpha).after(scale).before(translate);

//        animatorSet.start();
        }
//MD5 Base64
    public static String getMD5String(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8位字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            //一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方）
            String s = new BigInteger(1, md.digest()).toString(16);
            Log.e("TAG", "getMD5String: "+s,null);
            System.out.println(s);
            return s;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
//RSA生成公钥私钥
    public static HashMap<String,Object> initkeys() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(512);
        KeyPair pair =keyPairGenerator.generateKeyPair();
        PublicKey aPublic = pair.getPublic(); //公钥
        PrivateKey aPrivate = pair.getPrivate(); //私钥
        HashMap<String,Object> keys = new HashMap<>();
        keys.put("public_key",aPublic);
        keys.put("private_key",aPrivate);
        Log.e("TAG", "initkeys: "+aPublic+aPrivate, null);
        return keys;
    }

    /**
     * 公钥加密
     * @return
     */
    public static byte[] encryptByPublickey(byte[] data,byte[] publickey) throws Exception {

        KeyFactory keyFactory= KeyFactory.getInstance("RSA"); //获得key工厂
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publickey); //X509EncodedKeySpec公钥的解码类
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);//获得公钥
        Cipher cipher=Cipher.getInstance(keyFactory.getAlgorithm());//Cipher负责加密解密
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * 私钥解密
     * @param data 待解密数据
     * @param key 密钥
     * @return byte[] 解密数据
     * */
    public static byte[] decryptByPrivateKey(byte[] data,byte[] key) throws Exception{
        //取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec=new PKCS8EncodedKeySpec(key);
        KeyFactory keyFactory=KeyFactory.getInstance("RSA");
        //生成私钥
        PrivateKey privateKey= keyFactory.generatePrivate(pkcs8KeySpec);
        //数据解密
        Cipher cipher=Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }


    public void btn_cr_view(View view) {


    }


    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null || mMapView == null){
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
        }
    }



    private void call() {




    //Each Call from the created GitHubService can make a synchronous or asynchronous HTTP request to the remote webserver.
//        Call<Entity>mRegisterCall  = registerService.myRegisterCall4("ASD123456","1234567","1234567");
//
//        mRegisterCall.enqueue(new Callback<Entity>() {
//            @Override
//            public void onResponse(Call<Entity> call, Response<Entity> response) {
//
//                Log.e("ATG", "onResponse: "+response, null);
//            }
//
//            @Override
//            public void onFailure(Call<Entity> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
//


    }
@OnClick(R.id.imageView)void  imv(){

}
    @OnClick(R.id.button2)void btn2(){


        Log.e("TAG", "1111: ",null );

//OkHttpClient 封装加 自定义拦截器
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .callTimeout(100,TimeUnit.SECONDS)
                .writeTimeout(100,TimeUnit.SECONDS)
                .readTimeout(100,TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor())
                .addInterceptor(new TokenHeaderInterceptor())
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        Request request = chain.request();
//                        Request.Builder builder = request.newBuilder();
//                        String s = "服务器返回的token？从body里？？？？？？";
//                        request = builder.addHeader("token",s)
//                                .post(RequestBody.create(MediaType.parse(""),bodyToString(request.body()))).build();
//
//
//                        Log.e("TAG", "intercept: "+request, null);
//                        return chain.proceed(request);
//                    }
//                })


                .build();





//RXBUS RXORM
//Retrofit turns your HTTP API into a Java interface.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
//The Retrofit class generates an implementation of the GitHubService interface.
        RegisterService registerService = retrofit.create(RegisterService.class);






        registerService.myRegisterCall4("SAD123456","123456789","123456789")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(this.<Entity>bindToLifecycle())
                .subscribe(new Subscriber<Entity>() {
                    @Override
                    public void onCompleted() {
                        Log.e("TAG", "onCompleted: ",null );

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Entity entity) {
                        Log.e("TAG", "onNext: "+entity.getData().getToken(),null );
                    }
                });


    }
    public class TokenHeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException{
            //从本地取得 本地的是请求登录接口的时候body里面返回json里面字段对应的值
            String token = "token";
            Request originalRequest = chain.request();
            return chain.proceed(originalRequest);

        }

    }





                @OnClick(R.id.button) void btn(){

                ARouter.getInstance().build("/register/activity")
                .withString("money","0.2e3d")
                .withBoolean("ddd",false)
                .navigation(this, new NavigationCallback() {
                    @Override
                    public void onFound(Postcard postcard) {
                        Log.e("TAG", "onFound: "+postcard,null );
                    }

                    @Override
                    public void onLost(Postcard postcard) {
                        Log.e("TAG", "onLost: "+postcard,null );

                    }

                    @Override
                    public void onArrival(Postcard postcard) {
                        Log.e("TAG", "onArrival: "+postcard,null );

                    }

                    @Override
                    public void onInterrupt(Postcard postcard) {
                        Log.e("TAG", "onInterrupt: "+postcard,null );

                    }
                });



    }

//    public void arouter_btn(View view) {
//

//    }



    public class xxx implements Interceptor{

        @NotNull
        @Override
        public Response intercept(@NotNull Chain chain) throws IOException {


            return null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Observable.interval(1, TimeUnit.SECONDS).compose(this.bindUntilEvent(ActivityEvent.PAUSE));
    }

    @Override
    protected void onStop() {
        super.onStop();
        Observable.interval(1, TimeUnit.SECONDS).compose(this.bindUntilEvent(ActivityEvent.STOP));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Observable.interval(1, TimeUnit.SECONDS).compose(this.bindUntilEvent(ActivityEvent.DESTROY));

    }
    private static String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    public void btn_home(View view) {
        startActivity(new Intent(this, HomeActivity.class));
    }

    public void btn_Register(View view) {
        startActivity(new Intent(this, RegisterActivity.class));

    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();

    }

}