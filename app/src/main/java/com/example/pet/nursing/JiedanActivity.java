package com.example.pet.nursing;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.example.pet.R;

import java.text.DecimalFormat;

public class JiedanActivity extends AppCompatActivity {
    private TextView start;
    private TextView end;
    private TextView name;
    private BaiduMap bm;
    private LocationClient lc;
    MapView mv = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jiedan);
        if (Build.VERSION.SDK_INT >= 21) {   //只有5.0及以上系统才支持，因此这里先进行了一层if判断
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE    //设置为全屏显示，必须这两行代码一起才能生效
                    |View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;  //因为背景为浅色，设置通知栏字体颜色为深色
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);//设置为透明
        }
        mv = (MapView)findViewById(R.id.bmapView);
        start = findViewById(R.id.start);
        end = findViewById(R.id.end);
        name = findViewById(R.id.name);
        start.setText(AddressInfo.START);
        end.setText(AddressInfo.END);
        bm = mv.getMap();
        SetType();
        ScaleControl();
        Position(); // 定位骑手当前位置
    }
    private void SetType() {
        bm.setMapType(BaiduMap.MAP_TYPE_NORMAL);
    }
    private void ScaleControl() {
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(19.0f);
        bm.setMapStatus(msu);
        bm.setMaxAndMinZoomLevel(20.0f,13.0f);
    }

    private void Position() {
        lc = new LocationClient(getApplicationContext());
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},100);
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
                    double wd = 38.003672;//纬度
                    double jd = 114.529189;//经度
                    LatLng point = new LatLng(wd,jd);
                    BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.sell);
                    MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(point);
                    MarkerOptions options = new MarkerOptions().position(point).icon(icon).draggable(true);
                    Marker marker = (Marker) bm.addOverlay(options);
                    TextView contentTV = new TextView(JiedanActivity.this);
                    DecimalFormat df = new DecimalFormat("######0");
                    String distance = df.format(DistanceUtil.getDistance(Point.END, point));
                    contentTV.setText("距接收点"+distance+"米\n还需"+(Integer.parseInt(distance)/100+1)+"分钟");
                    contentTV.setTextColor(Color.BLACK);
                    contentTV.setTextSize(14.0f);
                    contentTV.setPadding(20, 20, 20,0);
                    contentTV.setBackgroundResource(R.drawable.peisong);
                    BitmapDescriptor descriptor = BitmapDescriptorFactory.fromView(contentTV);
                    final InfoWindow window = new InfoWindow(descriptor, marker.getPosition(), -130, null);
                    bm.showInfoWindow(window);
                    //移动地图界面
                    bm.animateMapStatus(update);
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
    public void cancel(View view) {
        Intent i = new Intent(this,CancelActivity.class);
        i.putExtra("订单的id","id");
        startActivity(i);
    }

    public void back(View view) {
        finish();
    }
}
