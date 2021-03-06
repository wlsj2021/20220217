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

        //???????????????
        mLocationClient = new LocationClient(this);

//??????LocationClientOption??????LocationClient????????????
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // ??????gps
        option.setCoorType("GCJ02"); // ??????????????????
        option.setScanSpan(1000);

//??????locationClientOption
        mLocationClient.setLocOption(option);

//??????LocationListener?????????
        MyLocationListener1 myLocationListener = new MyLocationListener1();
        mLocationClient.registerLocationListener(myLocationListener);
//????????????????????????
        mLocationClient.start();

        MyLocationConfiguration.LocationMode following = MyLocationConfiguration.LocationMode.NORMAL;

        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.ic_open);


       MyLocationConfiguration mLocationConfiguration = new MyLocationConfiguration(following,true,mCurrentMarker,Color.RED,Color.BLUE);


        mBaiduMap.setMyLocationConfiguration(mLocationConfiguration);

        mMapView1.showScaleControl(false);
//?????????UiSettings?????????
        UiSettings uiSettings = mBaiduMap.getUiSettings();
//????????????enable???true???false ???????????????????????????
        uiSettings.setCompassEnabled(true);

        mBaiduMap.setMaxAndMinZoomLevel(500f , 1000f);

        //??????Maker?????????
        LatLng point = new LatLng(39.963175, 116.400244);
//??????Marker??????
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromAssetWithDpi("Icon_mark1.png");
//??????MarkerOption???????????????????????????Marker
        OverlayOptions option1 = new MarkerOptions()
                .position(point)
                .icon(bitmap);
//??????????????????Marker????????????
        mBaiduMap.addOverlay(option1);

//?????????????????????
        LatLng p1 = new LatLng(39.97923, 116.357428);
        LatLng p2 = new LatLng(39.94923, 116.397428);
        LatLng p3 = new LatLng(39.97923, 116.437428);

        List<LatLng> points = new ArrayList<LatLng>();

        points.add(p1);
        points.add(p2);
        points.add(p3);

//?????????????????????
        OverlayOptions mOverlayOptions = new PolylineOptions()
                .width(10)
                .color(Color.YELLOW)
                .dottedLine(true)
                .points(points);
//????????????????????????
//mPloyline ????????????
        Overlay mPolyline = mBaiduMap.addOverlay(mOverlayOptions);

        mPolyline.isVisible();



        BaiduMap.OnPolylineClickListener listener = new BaiduMap.OnPolylineClickListener() {
            //??????Polyline????????????
            @Override
            public boolean onPolylineClick(Polyline polyline) {
                Toast.makeText(MapActivity.this, "?????????", Toast.LENGTH_SHORT).show();
                return true;
            }
        };

//??????Polyline???????????????
        mBaiduMap.setOnPolylineClickListener(listener);





        // ????????????????????????
        LatLng p11 = new LatLng(39.97923, 116.357428);//??????
        LatLng p22 = new LatLng(39.94923, 116.397428);//?????????
        LatLng p33 = new LatLng(39.97923, 116.437428);//??????

//??????ArcOptions??????
        OverlayOptions mArcOptions = new ArcOptions()
                .color(Color.RED)
                .width(10)
                .points(p11, p22, p33);

//????????????????????????
        Overlay mArc = mBaiduMap.addOverlay(mArcOptions);
        mArc.isVisible();


        //????????????
        LatLng center = new LatLng(39.90923, 116.447428);

//??????CircleOptions??????
        CircleOptions mCircleOptions = new CircleOptions().center(center)
                .radius(5000)
                .fillColor(0xAA0000FF) //????????????
                .stroke(new Stroke(5, 0xAA00ff00)); //????????????????????????

//?????????????????????
        Overlay mCircle = mBaiduMap.addOverlay(mCircleOptions);
        mCircle.isVisible();

        //???????????????????????????
        LatLng llText = new LatLng(39.86923, 116.397428);

//??????TextOptions??????
        OverlayOptions mTextOptions = new TextOptions()
                .text("?????????") //????????????
                .bgColor(0xAAFFFF00) //?????????
                .fontSize(24) //??????
                .fontColor(0xFFFF00FF) //????????????
                .rotate(-30) //????????????
                .position(llText);

//?????????????????????????????????
        Overlay mText = mBaiduMap.addOverlay(mTextOptions);
        mText.isVisible();


        //??????Icon??????
// ?????????bitmap ???????????????????????? recycle
        BitmapDescriptor bdA = BitmapDescriptorFactory.fromAssetWithDpi("Icon_mark1.png");
        BitmapDescriptor bdB = BitmapDescriptorFactory.fromAssetWithDpi("Icon_mark2.png");
        BitmapDescriptor bdC = BitmapDescriptorFactory.fromAssetWithDpi("Icon_mark3.png");


        ArrayList<BitmapDescriptor> giflist = new ArrayList<BitmapDescriptor>();
        giflist.add(bdA);
        giflist.add(bdB);
        giflist.add(bdC);
//Marker????????????
        LatLng llD = new LatLng(39.906965, 116.401394);
        LatLng llC = new LatLng(50.906965, 116.401394);
//??????MarkerOptions??????
        MarkerOptions ooD = new MarkerOptions()
                .position(llD)
                .icons(giflist)
                .zIndex(0)
                .period(20);//????????????????????????

//????????????????????????????????????Marker
        Overlay mMarkerD = (Marker) (mBaiduMap.addOverlay(ooD));
        mMarkerD.isVisible();

/**
 * ????????? TODO
 */

        Marker marker = (Marker) mBaiduMap.addOverlay(ooD);

        //??????LatLng????????????Transformation??????
        Transformation mTransforma = new Transformation(llC, llD, llC);

//??????????????????
        mTransforma.setDuration(500);

//??????????????????
        mTransforma.setRepeatMode(Animation.RepeatMode.RESTART);

//??????????????????
        mTransforma.setRepeatCount(1);

//????????????????????????????????????
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

//????????????
        marker.setAnimation(mTransforma);

//????????????
        marker.startAnimation();

        MarkerOptions ooA = new MarkerOptions()
                .position(llText)
                .icon(bdA);
//??????????????????
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
            //??????
            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }
        };

        mPoiSearch.setOnGetPoiSearchResultListener(onGetPoiSearchResultListener);

        mPoiSearch.searchInCity(new PoiCitySearchOption()
                .city("??????") //??????
                .keyword("??????") //??????
                .pageNum(2));


        RoutePlanSearch mSearch = RoutePlanSearch.newInstance();
        OnGetRoutePlanResultListener onGetRoutePlanResultListener = new OnGetRoutePlanResultListener() {
            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
                //??????WalkingRouteOverlay??????
                WalkingRouteOverlay overlay = new WalkingRouteOverlay(mBaiduMap);
                if (walkingRouteResult.getRouteLines().size() > 0) {
                    //????????????????????????,(?????????????????????????????????)
                    //???WalkingRouteOverlay????????????????????????
                    overlay.setData(walkingRouteResult.getRouteLines().get(0));
                    //??????????????????WalkingRouteOverlay
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

        PlanNode stNode = PlanNode.withCityNameAndPlaceName("??????", "??????????????????");
        PlanNode enNode = PlanNode.withCityNameAndPlaceName("??????", "?????????");

        mSearch.walkingSearch((new WalkingRoutePlanOption())
                .from(stNode)
                .to(enNode));


    }

    public class MyLocationListener1 extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView ???????????????????????????????????????
            if (location == null || mMapView1 == null){
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // ?????????????????????????????????????????????????????????0-360
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