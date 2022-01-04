package com.example.shop.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.shop.Adapter.ProductTypeAdapter;
import com.example.shop.Adapter.ProductAdapter;
import com.example.shop.Model.CartModel;
import com.example.shop.Model.ProductTypeModel;
import com.example.shop.Model.ProductModel;
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
    ArrayList<ProductTypeModel> arrayListLoaiSP;
    ProductTypeAdapter productTypeAdapter;
    ArrayList<ProductModel> arrayListSanPhamMoiNhat;
    ProductAdapter sanPhamAdapter;
    public static ArrayList<CartModel> arrayListGioHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapping();
        Actionbar();
        getProductType();
        getTheLatestProducts();
        ActionViewFliper();
        CaschOnItemListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuGH:
                Intent intent = new Intent(getApplicationContext(), Cart.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
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
                if (position > 0 && position < arrayListLoaiSP.size()-1 ){
                    Intent intent = new Intent(getApplicationContext(), ProductActyvity.class);
                    intent.putExtra("maLoaiSanPham",arrayListLoaiSP.get(position).getId());
                    intent.putExtra("tenLoaiSanPham",arrayListLoaiSP.get(position).getTenLoaiSanPham());
                    startActivity(intent);
                }
                if (position == arrayListLoaiSP.size()-1){
                    Intent intent = new Intent(MainActivity.this, StoreInformation.class);
                    startActivity(intent);
                }

            }
        });
    }

    private void getTheLatestProducts() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest request = new JsonArrayRequest(Server.LatestProductLinks, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                    for (int i = 0; i<response.length(); i++){
                        try {
                           // Toast.makeText(getApplicationContext(),""+response,Toast.LENGTH_LONG).show();
                            JSONObject jsonObject = response.getJSONObject(i);

                            ProductModel tmp = new ProductModel(jsonObject.getInt("id"),
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

    private void getProductType() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest request = new JsonArrayRequest(Server.ProductTypeLink, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                    arrayListLoaiSP.add(new ProductTypeModel(0,"Trang chủ","https://khinenthuanhung.vn/wp-content/uploads/2018/02/Home-icon.png"));
                    productTypeAdapter.notifyDataSetChanged();
                    for (int i = 0; i<response.length(); i++){
                        try {
                           // Toast.makeText(getApplicationContext(),""+response,Toast.LENGTH_LONG).show();
                            JSONObject jsonObject = response.getJSONObject(i);
                            ProductTypeModel tmp = new ProductTypeModel();
                            tmp.Id = jsonObject.getInt("id");
                            tmp.TenLoaiSanPham = jsonObject.getString("tenloaisanpham");
                            tmp.HinhAnhLoaiSanPham = jsonObject.getString("hinhanhloaisanpham");
                            arrayListLoaiSP.add(tmp);
                            productTypeAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    arrayListLoaiSP.add(new ProductTypeModel(arrayListLoaiSP.size(),"Địa chỉ", "https://img.icons8.com/external-kiranshastry-lineal-color-kiranshastry/344/4a90e2/external-map-logistic-delivery-kiranshastry-lineal-color-kiranshastry.png"));
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
        arrayListQC.add("https://cellphones.com.vn/sforum/wp-content/uploads/2020/08/OPPO-F17-1.jpg");
        arrayListQC.add("https://photo-cms-sggp.zadn.vn/w580/Uploaded/2021/yfsgf/2020_09_24/hinh11_mzad.jpg");
        arrayListQC.add("https://cellphones.com.vn/sforum/wp-content/uploads/2019/05/Honor-20-Pro-lo-anh-quang-cao-1.jpg");
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

    private void mapping() {
        toolbar = findViewById(R.id.toolBarMHC);
        viewFlipper = findViewById(R.id.viewFlipperMHC);
        recyclerView = findViewById(R.id.recyclerViewSanPham);
        navigationView = findViewById(R.id.navigationViewMHC);
        listView = findViewById(R.id.listViewMHC);
        drawerLayout = findViewById(R.id.drawerLayoutMHC);
        arrayListLoaiSP = new ArrayList<>();
        productTypeAdapter = new ProductTypeAdapter(arrayListLoaiSP,getApplicationContext());
        listView.setAdapter(productTypeAdapter);
        arrayListSanPhamMoiNhat = new ArrayList<>();
        sanPhamAdapter = new ProductAdapter(getApplicationContext(),arrayListSanPhamMoiNhat);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        RecyclerView.ItemDecoration itemDecoration1 = new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.addItemDecoration(itemDecoration1);
        recyclerView.setAdapter(sanPhamAdapter);
        if( arrayListGioHang == null)
            arrayListGioHang = new ArrayList<>();
    }
}