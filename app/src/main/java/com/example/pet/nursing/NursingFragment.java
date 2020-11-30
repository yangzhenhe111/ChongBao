package com.example.pet.nursing;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.pet.R;
import com.example.pet.my.order.MyOrderActivity;

public class NursingFragment extends Fragment {

    private TextView ordertv;
    private View root;
    private BaiduMap bm;
    private LocationClient lc;
    MapView mv = null;
    private Button pos;
    private LinearLayout slin;
    private LinearLayout elin;
    private TextView neworder;
    private TextView starttv;
    private TextView endtv;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SDKInitializer.initialize(getContext().getApplicationContext());
        root = inflater.inflate(R.layout.fragment_nursing, container, false);
        mv = (MapView) root.findViewById(R.id.bmapView);
        bm = mv.getMap();
        setViews();
        setListener();
        SetType();
        ScaleControl();
        SellerPostion();//显示店铺的位置
        Position(); // 定位用户当前位置
        return root;
    }

    private void SetType() {
        bm.setMapType(BaiduMap.MAP_TYPE_NORMAL);
    }

    private void ScaleControl() {
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(18.0f);
        bm.setMapStatus(msu);
        bm.setMaxAndMinZoomLevel(19.0f,13.0f);
    }


    private void SellerPostion() {
        LatLng point = new LatLng(38.001171, 114.531944);
        BitmapDescriptor img = BitmapDescriptorFactory.fromResource(R.drawable.shop);
        MarkerOptions options = new MarkerOptions()
                .position(point)
                .icon(img);
        bm.addOverlay(options);
    }

    private void Position() {
        lc = new LocationClient(getActivity().getApplicationContext());
        NursingFragment.this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults[0] == 0){
            LocationClientOption option = new LocationClientOption();
            option.setOpenGps(true);
            option.setCoorType("bd09ll");
            option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
            lc.setLocOption(option);
            lc.registerLocationListener(new BDAbstractLocationListener() {
                @Override
                public void onReceiveLocation(BDLocation bdLocation) {
                    double wd = bdLocation.getLatitude();//纬度
                    double jd = bdLocation.getLongitude();//经度
                    LatLng point = new LatLng(wd,jd);
                    MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(point);
                    //移动地图界面
                    bm.animateMapStatus(update);
                    MyLocationConfiguration configuration = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.COMPASS,true,BitmapDescriptorFactory.fromResource(R.drawable.marker));//使用默认小图标
                    bm.setMyLocationConfiguration(configuration);
                    bm.setMyLocationEnabled(true);
                    MyLocationData data = new MyLocationData.Builder()
                            .latitude(wd)
                            .longitude(jd)
                            .build();
                    bm.setMyLocationData(data);
                }
            });
            lc.start();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        bm.setMyLocationEnabled(true);
        if (!lc.isStarted()){
            lc.start();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        lc.stop();
        bm.setMyLocationEnabled(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mv.onDestroy();
    }
    @Override
    public void onResume() {
        super.onResume();
        mv.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mv.onPause();
    }

    private void setViews() {
        pos = root.findViewById(R.id.position);
        slin = root.findViewById(R.id.start);
        elin = root.findViewById(R.id.end);
        neworder = root.findViewById(R.id.neworder);
        starttv = root.findViewById(R.id.starttv);
        starttv.setText(AddressInfo.START);
        endtv = root.findViewById(R.id.endtv);
        endtv.setText(AddressInfo.END);
        ordertv = root.findViewById(R.id.ordertv);
    }

    class Clicklistener implements View.OnClickListener {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.position:
                    Position();
                    break;
                case R.id.start:
                    StartWhere();
                    break;
                case R.id.end:
                    EndWhere();
                    break;
                case R.id.neworder:
                    NewOrder();
                    break;
                case R.id.ordertv:
                    Myorder();
                    break;
            }
        }
    }

    private void Myorder() { //我的订单页面
        Intent i = new Intent(getActivity(), MyOrderActivity.class);
        startActivity(i);
    }

    private void setListener() {
        Clicklistener l = new Clicklistener();
        pos.setOnClickListener(l);
        slin.setOnClickListener(l);
        elin.setOnClickListener(l);
        neworder.setOnClickListener(l);
    }
    public void StartWhere(){ //设置起点页面
        Intent i = new Intent(getActivity(),StartActivity.class);
        startActivity(i);
    }
    public void EndWhere(){ //设置终点页面
        Intent i = new Intent(getActivity(),EndActivity.class);
        startActivity(i);
    }
    public void NewOrder(){  //提交订单页面
        Intent i = new Intent(getActivity(),NeworderActivity.class);
        startActivity(i);
    }

}
