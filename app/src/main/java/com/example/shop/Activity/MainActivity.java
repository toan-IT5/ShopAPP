package com.example.shop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.shop.Adapter.LoaiSPAdapter;
import com.example.shop.Adapter.SanPhamAdapter;
import com.example.shop.Model.LoaiSP;
import com.example.shop.Model.SanPham;
import com.example.shop.R;
import com.example.shop.ultil.MySingleton;
import com.example.shop.ultil.Server;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    NavigationView navigationView;
    ListView listView;
    DrawerLayout drawerLayout;
    ArrayList<LoaiSP> arrayListLoaiSP;
    LoaiSPAdapter loaiSPAdapter;
    ArrayList<SanPham> arrayListSanPhamMoiNhat;
    SanPhamAdapter sanPhamAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        Actionbar();
        getDuLieuLoaiSP();
        getDuLieuSanPhamMoiNhat();
        ActionViewFliper();
        CaschOnItemListView();
    }

    private void CaschOnItemListView() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position ==0){
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                if (position > 0 && position <= arrayListLoaiSP.size()){
                    Intent intent = new Intent(getApplicationContext(),SanPhamActyvity.class);
                    intent.putExtra("maLoaiSanPham",arrayListLoaiSP.get(position).getId());
                    startActivity(intent);
                }

            }
        });
    }

    private void getDuLieuSanPhamMoiNhat() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest request = new JsonArrayRequest(Server.DuongDanSanPhamMoiNhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                    for (int i = 0; i<response.length(); i++){
                        try {
                           // Toast.makeText(getApplicationContext(),""+response,Toast.LENGTH_LONG).show();
                            JSONObject jsonObject = response.getJSONObject(i);

                            SanPham tmp = new SanPham(jsonObject.getInt("id"),
                                    jsonObject.getString("tensanpham"),
                                    jsonObject.getInt("giasanpham"),
                                    jsonObject.getString("hinhanhsanpham"),
                                    jsonObject.getString("motasanpham"),
                                    jsonObject.getInt("idloaisanpham"));
                            arrayListSanPhamMoiNhat.add(tmp);
                            sanPhamAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Loi gi "+error,Toast.LENGTH_LONG).show();
            }
        });
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }


    private void getDuLieuLoaiSP() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest request = new JsonArrayRequest(Server.DuongDan, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                    arrayListLoaiSP.add(new LoaiSP(0,"Trang chủ","https://khinenthuanhung.vn/wp-content/uploads/2018/02/Home-icon.png"));
                    loaiSPAdapter.notifyDataSetChanged();
                    for (int i = 0; i<response.length(); i++){
                        try {
                           // Toast.makeText(getApplicationContext(),""+response,Toast.LENGTH_LONG).show();
                            JSONObject jsonObject = response.getJSONObject(i);
                            LoaiSP tmp = new LoaiSP();
                            tmp.Id = jsonObject.getInt("id");
                            tmp.TenLoaiSanPham = jsonObject.getString("tenloaisanpham");
                            tmp.HinhAnhLoaiSanPham = jsonObject.getString("hinhanhloaisanpham");
                            arrayListLoaiSP.add(tmp);
                            loaiSPAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Loi gi "+error,Toast.LENGTH_LONG).show();
            }
        });
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void ActionViewFliper() {
        ArrayList<String> arrayListQC= new ArrayList<>();
        arrayListQC.add("https://img.thuthuattinhoc.vn/uploads/2019/01/08/anh-anime-boy-dep-nhat_101905549.jpg");
        arrayListQC.add("https://tinviet365.net/wp-content/uploads/2020/04/T%E1%BB%95ng-h%E1%BB%A3p-nh%E1%BB%AFng-h%C3%ACnh-%E1%BA%A3nh-anime-%C4%91%E1%BA%B9p-nh%E1%BA%A5t-th%E1%BA%BF-gi%E1%BB%9Bi-%E1%BA%A5n-t%C6%B0%E1%BB%A3ng.jpg");
        arrayListQC.add("https://dbk.vn/uploads/ckfinder/images/tranh-anh/anh-anime-nam-29.jpg");
        for (int i =0; i < arrayListQC.size(); i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(arrayListQC.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }

    private void Actionbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.list);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void anhXa() {
        toolbar = findViewById(R.id.toolBarMHC);
        viewFlipper = findViewById(R.id.viewFlipperMHC);
        recyclerView = findViewById(R.id.recyclerViewSanPham);
        navigationView = findViewById(R.id.navigationViewMHC);
        listView = findViewById(R.id.listViewMHC);
        drawerLayout = findViewById(R.id.drawerLayoutMHC);
        arrayListLoaiSP = new ArrayList<>();
        loaiSPAdapter = new LoaiSPAdapter(arrayListLoaiSP,getApplicationContext());
        listView.setAdapter(loaiSPAdapter);
        arrayListSanPhamMoiNhat = new ArrayList<>();
        sanPhamAdapter = new SanPhamAdapter(getApplicationContext(),arrayListSanPhamMoiNhat);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setAdapter(sanPhamAdapter);
    }
}