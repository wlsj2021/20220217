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

//???????????????
        LocationClient mLocationClient = new LocationClient(this);

//??????LocationClientOption??????LocationClient????????????
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // ??????gps
        option.setCoorType("bd09ll"); // ??????????????????
        option.setScanSpan(1000);

//??????locationClientOption
        mLocationClient.setLocOption(option);

//??????LocationListener?????????
        MyLocationListener myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);
//????????????????????????
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
//        final String orderInfo = "info";   // ????????????
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
//        // ??????????????????
//        Thread payThread = new Thread(payRunnable);
//        payThread.start();



        getMD5String("??????");
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
        byte[] pubKey = key.getEncoded(); //??????
        assert key1 != null;
        byte[] priKey = key1.getEncoded(); //??????

        Log.e("TAG", "??????: "+ Arrays.toString(Base64.encode(pubKey))+"?????????"+ Arrays.toString(Base64.encode(priKey)), null);

        String str= "????????????";

        Log.e("TAG", "??????: "+ Arrays.toString(Base64.encode(str.getBytes())), null);

        byte[] code1= new byte[0];

        code1 = encryptByPublickey(str.getBytes(), pubKey);

        Log.e("TAG", "????????????????????????: "+ Arrays.toString(Base64.encode(code1)), null);

        byte[] code2 = new byte[0];

        code2 = decryptByPrivateKey(code1,priKey);

        Log.e("TAG", "????????????????????????: "+ Arrays.toString(Base64.encode(code2)), null);
        } catch (Exception e) {
            e.printStackTrace();
        }



















/**
 *
 *    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" /> <!-- ??????????????????????????????????????????????????????????????????????????????????????????POI????????? -->
 *     <uses-permission android:name="android.permission.INTERNET" /> <!-- ??????????????????????????????????????????????????????????????????????????? -->
 *     <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" /> <!-- ?????????????????????????????????????????????so???????????????????????????so?????????????????????????????????????????????????????????????????????????????? -->
 *     <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" /> <!-- ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????? -->
 *     <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 *     <!-- ???????????????????????????????????? -->
 *     <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
 *     <!-- ????????????????????????GPS?????? -->
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
         * 1 ?????????view ????????????
         * 2 ?????????view ???????????? ????????????view
         * 3 ???????????????view ???????????????
         *
         *
          */
//??????????????? ?????????
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

            mButton.setText("??????");

//        DaggerRegisterComponent.create().inject(this);
            DaggerRegisterComponent.builder().build().inject(this);

            mPresenter.see();

//?????????????????? API 21 DVM ART
            //????????? ?????????
            AnimationDrawable animationDrawable = new AnimationDrawable();
            //?????????  ??????????????????????????????
            animationDrawable.addFrame(getResources().getDrawable(R.drawable.a1), 200);
            animationDrawable.addFrame(getResources().getDrawable(R.drawable.a2), 200);
            animationDrawable.addFrame(getResources().getDrawable(R.drawable.a3), 200);
            animationDrawable.addFrame(getResources().getDrawable(R.drawable.a4), 200);
            animationDrawable.addFrame(getResources().getDrawable(R.drawable.a5), 200);
            animationDrawable.addFrame(getResources().getDrawable(R.drawable.a6), 200);
            animationDrawable.addFrame(getResources().getDrawable(R.drawable.a7), 200);
            //??????????????????????????????
            animationDrawable.setOneShot(false);
            //???imageview?????????????????????
//        mImageView.setImageDrawable(animationDrawable);
            //??????????????????
//        animationDrawable.start();

            //????????????
            //??????????????? ?????????
            AlphaAnimation mAlphaAnimation = new AlphaAnimation(0, 1);
            //???????????????????????? AccelerateInterpolator???????????????
            mAlphaAnimation.setInterpolator(new AccelerateInterpolator());
            //?????????????????????
            mAlphaAnimation.setDuration(2000);
            //??????????????????????????????
            mAlphaAnimation.setRepeatCount(5);
            //???????????????????????????
            mAlphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    Log.e("TAG", "onAnimationStart: ????????????", null);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    Log.e("TAG", "onAnimationEnd: ????????????", null);

                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    Log.e("TAG", "onAnimationRepeat: ????????????", null);

                }
            });
//        mImageView.setAnimation(mAlphaAnimation);
            //????????????
//        mAlphaAnimation.start();
            //????????????
            TranslateAnimation translateAnimation = new TranslateAnimation(0, 200, 0, 200);
            //?????????????????????
            translateAnimation.setInterpolator(new BounceInterpolator());
            translateAnimation.setDuration(2000);
            translateAnimation.setRepeatCount(5);
//        mImageView.setAnimation(translateAnimation);
//        translateAnimation.start();
            //????????????
            RotateAnimation rotateAnimation = new RotateAnimation(0, 200, 0, 200);
            rotateAnimation.setInterpolator(new BounceInterpolator());
            rotateAnimation.setDuration(2000);
            rotateAnimation.setRepeatCount(5);
//        mImageView.setAnimation(rotateAnimation);
//        rotateAnimation.start();
            //????????????
            ScaleAnimation scaleAnimation = new ScaleAnimation(0.5f, 2, 0.5f, 2);
            scaleAnimation.setInterpolator(new BounceInterpolator());
            scaleAnimation.setDuration(2000);
            scaleAnimation.setRepeatCount(5);
//        mImageView.setAnimation(scaleAnimation);
//        scaleAnimation.start();
            //????????????
            AnimationSet animationSet = new AnimationSet(true);
            //??????????????????
            animationSet.setDuration(5000);
            //????????????????????????
            animationSet.addAnimation(mAlphaAnimation);
            animationSet.addAnimation(rotateAnimation);
            animationSet.addAnimation(scaleAnimation);
            animationSet.addAnimation(translateAnimation);
            //???????????????????????????
            animationSet.setRepeatCount(10);
//        mImageView.startAnimation(animationSet);
//        animationSet.start();


//        ????????????ValueAnimator
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
            //????????????????????????  values??????????????????
            ObjectAnimator translate = ObjectAnimator.ofFloat(mImageView, "translationX", 0, -100, 0, 1, 0.5f, 1, 0, 50);
            translate.setInterpolator(new DecelerateInterpolator());
            translate.setDuration(2000);
            translate.setRepeatCount(10);
//        translate.start();

            //???????????? ??????
            ObjectAnimator scale = ObjectAnimator.ofFloat(mImageView, "scaleY", 0, -100, 0, 1, 0.5f, 1, 0, 50);
            scale.setInterpolator(new DecelerateInterpolator());
            scale.setDuration(200);
            scale.setRepeatCount(1);
//        scale.start();

            //???????????? ??????
            ObjectAnimator rotate = ObjectAnimator.ofFloat(mImageView, "rotationY", 0, -100, 0, 1, 0.5f, 1, 0, 50);
            rotate.setInterpolator(new DecelerateInterpolator());
            rotate.setDuration(2000);
            rotate.setRepeatCount(1);
//        rotate.start();

            //????????????
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(2000);
            animatorSet.setInterpolator(new BounceInterpolator());
//        animatorSet.play(rotate);
//        animatorSet.playTogether(
//                translate,scale,rotate,alpha
//        );
            //????????????????????? with???????????? after???????????? before????????????
            animatorSet.play(rotate).with(alpha).after(scale).before(translate);

//        animatorSet.start();
        }
//MD5 Base64
    public static String getMD5String(String str) {
        try {
            // ????????????MD5??????????????????
            MessageDigest md = MessageDigest.getInstance("MD5");
            // ??????md5??????
            md.update(str.getBytes());
            // digest()??????????????????md5 hash??????????????????8?????????????????????md5 hash??????16??????hex?????????????????????8????????????
            // BigInteger????????????8????????????????????????16???hex??????????????????????????????????????????????????????hash???
            //??????byte??????????????????????????????2????????????????????????2???8????????????16???2?????????
            String s = new BigInteger(1, md.digest()).toString(16);
            Log.e("TAG", "getMD5String: "+s,null);
            System.out.println(s);
            return s;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
//RSA??????????????????
    public static HashMap<String,Object> initkeys() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(512);
        KeyPair pair =keyPairGenerator.generateKeyPair();
        PublicKey aPublic = pair.getPublic(); //??????
        PrivateKey aPrivate = pair.getPrivate(); //??????
        HashMap<String,Object> keys = new HashMap<>();
        keys.put("public_key",aPublic);
        keys.put("private_key",aPrivate);
        Log.e("TAG", "initkeys: "+aPublic+aPrivate, null);
        return keys;
    }

    /**
     * ????????????
     * @return
     */
    public static byte[] encryptByPublickey(byte[] data,byte[] publickey) throws Exception {

        KeyFactory keyFactory= KeyFactory.getInstance("RSA"); //??????key??????
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publickey); //X509EncodedKeySpec??????????????????
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);//????????????
        Cipher cipher=Cipher.getInstance(keyFactory.getAlgorithm());//Cipher??????????????????
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * ????????????
     * @param data ???????????????
     * @param key ??????
     * @return byte[] ????????????
     * */
    public static byte[] decryptByPrivateKey(byte[] data,byte[] key) throws Exception{
        //????????????
        PKCS8EncodedKeySpec pkcs8KeySpec=new PKCS8EncodedKeySpec(key);
        KeyFactory keyFactory=KeyFactory.getInstance("RSA");
        //????????????
        PrivateKey privateKey= keyFactory.generatePrivate(pkcs8KeySpec);
        //????????????
        Cipher cipher=Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }


    public void btn_cr_view(View view) {


    }


    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView ???????????????????????????????????????
            if (location == null || mMapView == null){
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // ?????????????????????????????????????????????????????????0-360
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

//OkHttpClient ????????? ??????????????????
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
//                        String s = "??????????????????token??????body?????????????????????";
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
            //??????????????? ???????????????????????????????????????body????????????json????????????????????????
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