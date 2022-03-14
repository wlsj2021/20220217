package com.wlsj2021.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.animation.Animation;
import com.baidu.mapapi.animation.Transformation;
import com.baidu.mapapi.map.ArcOptions;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.wlsj2021.myapplication.overlay.WalkingRouteOverlay;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapActivity extends AppCompatActivity {
@BindView(R.id.baidumapView)
    MapView mMapView1;

private LocationClient mLocationClient;
private BaiduMap mBaiduMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);


        RxPermissions rxPermissions = new RxPermissions(this);

        rxPermissions
                .request(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_NETWORK_STATE,
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


        mBaiduMap = mMapView1.getMap();
//
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
//
        mBaiduMap.setTrafficEnabled(true);

        mBaiduMap.setBaiduHeatMapEnabled(true);

        mBaiduMap.setMyLocationEnabled(true);





    }

    public void btn_locat(View view) {

        //定位初始化
        mLocationClient = new LocationClient(this);

//通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("GCJ02"); // 设置坐标类型
        option.setScanSpan(1000);

//设置locationClientOption
        mLocationClient.setLocOption(option);

//注册LocationListener监听器
        MyLocationListener1 myLocationListener = new MyLocationListener1();
        mLocationClient.registerLocationListener(myLocationListener);
//开启地图定位图层
        mLocationClient.start();

        MyLocationConfiguration.LocationMode following = MyLocationConfiguration.LocationMode.NORMAL;

        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.ic_open);


       MyLocationConfiguration mLocationConfiguration = new MyLocationConfiguration(following,true,mCurrentMarker,Color.RED,Color.BLUE);


        mBaiduMap.setMyLocationConfiguration(mLocationConfiguration);

        mMapView1.showScaleControl(false);
//实例化UiSettings类对象
        UiSettings uiSettings = mBaiduMap.getUiSettings();
//通过设置enable为true或false 选择是否显示指南针
        uiSettings.setCompassEnabled(true);

        mBaiduMap.setMaxAndMinZoomLevel(500f , 1000f);

        //定义Maker坐标点
        LatLng point = new LatLng(39.963175, 116.400244);
//构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromAssetWithDpi("Icon_mark1.png");
//构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option1 = new MarkerOptions()
                .position(point)
                .icon(bitmap);
//在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option1);

//构建折线点坐标
        LatLng p1 = new LatLng(39.97923, 116.357428);
        LatLng p2 = new LatLng(39.94923, 116.397428);
        LatLng p3 = new LatLng(39.97923, 116.437428);

        List<LatLng> points = new ArrayList<LatLng>();

        points.add(p1);
        points.add(p2);
        points.add(p3);

//设置折线的属性
        OverlayOptions mOverlayOptions = new PolylineOptions()
                .width(10)
                .color(Color.YELLOW)
                .dottedLine(true)
                .points(points);
//在地图上绘制折线
//mPloyline 折线对象
        Overlay mPolyline = mBaiduMap.addOverlay(mOverlayOptions);

        mPolyline.isVisible();



        BaiduMap.OnPolylineClickListener listener = new BaiduMap.OnPolylineClickListener() {
            //处理Polyline点击逻辑
            @Override
            public boolean onPolylineClick(Polyline polyline) {
                Toast.makeText(MapActivity.this, "点击了", Toast.LENGTH_SHORT).show();
                return true;
            }
        };

//设置Polyline点击监听器
        mBaiduMap.setOnPolylineClickListener(listener);





        // 添加弧线坐标数据
        LatLng p11 = new LatLng(39.97923, 116.357428);//起点
        LatLng p22 = new LatLng(39.94923, 116.397428);//中间点
        LatLng p33 = new LatLng(39.97923, 116.437428);//终点

//构造ArcOptions对象
        OverlayOptions mArcOptions = new ArcOptions()
                .color(Color.RED)
                .width(10)
                .points(p11, p22, p33);

//在地图上显示弧线
        Overlay mArc = mBaiduMap.addOverlay(mArcOptions);
        mArc.isVisible();


        //圆心位置
        LatLng center = new LatLng(39.90923, 116.447428);

//构造CircleOptions对象
        CircleOptions mCircleOptions = new CircleOptions().center(center)
                .radius(5000)
                .fillColor(0xAA0000FF) //填充颜色
                .stroke(new Stroke(5, 0xAA00ff00)); //边框宽和边框颜色

//在地图上显示圆
        Overlay mCircle = mBaiduMap.addOverlay(mCircleOptions);
        mCircle.isVisible();

        //文字覆盖物位置坐标
        LatLng llText = new LatLng(39.86923, 116.397428);

//构建TextOptions对象
        OverlayOptions mTextOptions = new TextOptions()
                .text("决赛圈") //文字内容
                .bgColor(0xAAFFFF00) //背景色
                .fontSize(24) //字号
                .fontColor(0xFFFF00FF) //文字颜色
                .rotate(-30) //旋转角度
                .position(llText);

//在地图上显示文字覆盖物
        Overlay mText = mBaiduMap.addOverlay(mTextOptions);
        mText.isVisible();


        //构造Icon列表
// 初始化bitmap 信息，不用时及时 recycle
        BitmapDescriptor bdA = BitmapDescriptorFactory.fromAssetWithDpi("Icon_mark1.png");
        BitmapDescriptor bdB = BitmapDescriptorFactory.fromAssetWithDpi("Icon_mark2.png");
        BitmapDescriptor bdC = BitmapDescriptorFactory.fromAssetWithDpi("Icon_mark3.png");


        ArrayList<BitmapDescriptor> giflist = new ArrayList<BitmapDescriptor>();
        giflist.add(bdA);
        giflist.add(bdB);
        giflist.add(bdC);
//Marker位置坐标
        LatLng llD = new LatLng(39.906965, 116.401394);
        LatLng llC = new LatLng(50.906965, 116.401394);
//构造MarkerOptions对象
        MarkerOptions ooD = new MarkerOptions()
                .position(llD)
                .icons(giflist)
                .zIndex(0)
                .period(20);//定义刷新的帧间隔

//在地图上展示包含帧动画的Marker
        Overlay mMarkerD = (Marker) (mBaiduMap.addOverlay(ooD));
        mMarkerD.isVisible();

/**
 * 不会动 TODO
 */

        Marker marker = (Marker) mBaiduMap.addOverlay(ooD);

        //通过LatLng列表构造Transformation对象
        Transformation mTransforma = new Transformation(llC, llD, llC);

//动画执行时间
        mTransforma.setDuration(500);

//动画重复模式
        mTransforma.setRepeatMode(Animation.RepeatMode.RESTART);

//动画重复次数
        mTransforma.setRepeatCount(1);

//根据开发需要设置动画监听
        mTransforma.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationEnd() {
            }

            @Override
            public void onAnimationCancel() {
            }

            @Override
            public void onAnimationRepeat() {

            }
        });

//设置动画
        marker.setAnimation(mTransforma);

//开启动画
        marker.startAnimation();

        MarkerOptions ooA = new MarkerOptions()
                .position(llText)
                .icon(bdA);
//设置掉下动画
        ooA.animateType(MarkerOptions.MarkerAnimateType.drop);

        PoiSearch mPoiSearch = PoiSearch.newInstance();

        OnGetPoiSearchResultListener onGetPoiSearchResultListener = new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                Log.e("TAG", "onGetPoiResult: "+poiResult.getAllPoi(), null);

            }
            @Override
            public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

                Log.e("TAG", "PoiDetailSearchResult: "+poiDetailSearchResult, null);

            }
            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
                Log.e("TAG", "PoiIndoorResult: "+poiIndoorResult, null);


            }
            //废弃
            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }
        };

        mPoiSearch.setOnGetPoiSearchResultListener(onGetPoiSearchResultListener);

        mPoiSearch.searchInCity(new PoiCitySearchOption()
                .city("天津") //必填
                .keyword("美食") //必填
                .pageNum(2));


        RoutePlanSearch mSearch = RoutePlanSearch.newInstance();
        OnGetRoutePlanResultListener onGetRoutePlanResultListener = new OnGetRoutePlanResultListener() {
            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
                //创建WalkingRouteOverlay实例
                WalkingRouteOverlay overlay = new WalkingRouteOverlay(mBaiduMap);
                if (walkingRouteResult.getRouteLines().size() > 0) {
                    //获取路径规划数据,(以返回的第一条数据为例)
                    //为WalkingRouteOverlay实例设置路径数据
                    overlay.setData(walkingRouteResult.getRouteLines().get(0));
                    //在地图上绘制WalkingRouteOverlay
                    overlay.addToMap();
                }
            }

            @Override
            public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

            }

            @Override
            public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

            }

            @Override
            public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {

            }

            @Override
            public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

            }

            @Override
            public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

            }
        };
        mSearch.setOnGetRoutePlanResultListener(onGetRoutePlanResultListener);

        PlanNode stNode = PlanNode.withCityNameAndPlaceName("天津", "天津八维学校");
        PlanNode enNode = PlanNode.withCityNameAndPlaceName("太原", "五台山");

        mSearch.walkingSearch((new WalkingRoutePlanOption())
                .from(stNode)
                .to(enNode));


    }

    public class MyLocationListener1 extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null || mMapView1 == null){
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            Log.e("TAG", "onReceiveLocation: "+locData.longitude+locData.latitude,null );
        }
    }
    @Override
    protected void onResume() {
        mMapView1.onResume();
        super.onResume();
    }
}