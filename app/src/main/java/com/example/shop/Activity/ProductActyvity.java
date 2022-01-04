package com.example.shop.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shop.Adapter.ProductsByIDAdapter;
import com.example.shop.Model.ProductModel;
import com.example.shop.R;
import com.example.shop.ultil.MySingleton;
import com.example.shop.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductActyvity extends AppCompatActivity {
    int page = 1;
    Toolbar toolbarSP;
    ListView lv_SanPham;
    ProductsByIDAdapter sanPhamByIDAdapter;
    ArrayList<ProductModel> arrayListSanPham;
    int idType = 0;
    View footerView;
    boolean isLoading = false;
    mHandler mHandler;
    boolean limitData = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        mapping();
        getIDType();
        ActionToolBar();
        getProduct(page);
        LoadMoreData();
    }
    // Tạo memu giỏ hàng
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.menuGH:
                Intent intent = new Intent(getApplicationContext(), Cart.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    //Load thêm dữ liệu cho listView
    private void LoadMoreData() {
        lv_SanPham.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ProductDetails.class);
                intent.putExtra("thongtinsanpham", arrayListSanPham.get(position));
                startActivity(intent);
            }
        });
        lv_SanPham.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount !=0 && isLoading == false && limitData == false){
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    // lấy dữ liệu
    private void getProduct(int Page) {
        String url = Server.ProductLink + page;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null && response.length() > 2){
                    lv_SanPham.removeFooterView(footerView);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i<jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            ProductModel tmp = new ProductModel(jsonObject.getInt("id"),
                                    jsonObject.getString("tensanpham"),
                                    jsonObject.getInt("giasanpham"),
                                    jsonObject.getString("hinhanhsanpham"),
                                    jsonObject.getString("motasanpham"),
                                    jsonObject.getInt("idloaisanpham"));
                            arrayListSanPham.add(tmp);
                            sanPhamByIDAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else {
                    limitData = true;
                    lv_SanPham.removeFooterView(footerView);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("idloaisanpham", ""+idType);
                return param;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    // Tạo và bắt sự kiện nút trở về
    private void ActionToolBar() {
        setSupportActionBar(toolbarSP);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarSP.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    //Lấy dữ liệu loại sản phẩm từ MainActivity

    private void getIDType() {
        idType = getIntent().getExtras().getInt("maLoaiSanPham");
        CharSequence tmp = getIntent().getExtras().getString("tenLoaiSanPham");
        toolbarSP.setTitle(tmp);
    }

    //Tìm ID dữ liệu
    private void mapping() {

        toolbarSP = findViewById(R.id.toolBarSanPham);
        lv_SanPham = findViewById(R.id.lv_SanPham);
        arrayListSanPham = new ArrayList<>();
        sanPhamByIDAdapter = new ProductsByIDAdapter(getApplicationContext(), arrayListSanPham);
        lv_SanPham.setAdapter(sanPhamByIDAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.progressbar,null);
        mHandler = new mHandler();
    }
    public class mHandler extends Handler{
        @Override
        public void handleMessage (Message msg){
            switch (msg.what){
                case 0:
                    lv_SanPham.addFooterView(footerView);
                    break;
                case 1:
                    getProduct(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    // tạo luồng để load dữ liệu
    public class ThreadData extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message =mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }
}