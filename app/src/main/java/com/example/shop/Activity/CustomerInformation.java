package com.example.shop.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shop.R;
import com.example.shop.ultil.Server;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class CustomerInformation extends AppCompatActivity {
    Button btn_XacNhan, btn_Thoat;
    TextInputLayout til_Name, til_Email,til_Phone;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_information);
        mapping();
        catchButtonEvent();
    }

    private void catchButtonEvent() {
        btn_Thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_XacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = til_Name.getEditText().getText().toString().trim();
                final String email = til_Email.getEditText().getText().toString().trim();
                final String phone = til_Phone.getEditText().getText().toString().trim();

                if (name.length() <= 0){
                    til_Name.setError("Vui lòng nhập tên của bạn!");
                }else til_Name.setError("");
                if (email.length() <= 0){
                    til_Name.setError("Vui lòng nhập tên của bạn!");
                }else til_Name.setError("");
                if (phone.length() <= 0){
                    til_Name.setError("Vui lòng nhập tên của bạn!");
                }else til_Name.setError("");

                if (name.length() > 0 && email.length() > 0 && phone.length() > 0 ){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.CustomerInformationLink, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//                            Toast.makeText(getApplicationContext(), ""+ response, Toast.LENGTH_SHORT).show();
                            if (Integer.parseInt(response) > 0 )
                                postOrderDetail(response);
                            finish();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }){
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("tenkhachhang",name);
                            hashMap.put("sodienthoai",phone);
                            hashMap.put("email",email);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
            }
        });
    }

    private void postOrderDetail(String response){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, Server.OrderDetailsLink, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            if (response.equals ("1")){
                MainActivity.arrayListGioHang.clear ();
                Toast.makeText(getApplicationContext (), "Bạn đã thêm dữ liệu giỏ hàng thành công", Toast.LENGTH_LONG);
                Intent intent = new Intent (getApplicationContext (), MainActivity.class) ;
                startActivity (intent);
                Toast.makeText(getApplicationContext (), "Mời bạn tiếp tục mua hàng", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext (),"Dữ liệu giỏ hàng bạn đã bị lỗi", Toast.LENGTH_LONG).show();
            }}
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                JSONArray jsonArray = new JSONArray();
                for (int i = 0 ; i< MainActivity.arrayListGioHang.size(); i++){
                    JSONObject jsonobject = new JSONObject ();
                    try {
                        jsonobject.put("madonhang", response);
                        jsonobject.put("masanpham", MainActivity.arrayListGioHang.get(i).getIdSanPham());
                        jsonobject.put("tensanpham", MainActivity.arrayListGioHang.get (i).getTenSanPham()) ;
                        jsonobject.put("giasanpham", MainActivity.arrayListGioHang.get (i).getGiaSanPham());
                        jsonobject.put("soluongsanpham", MainActivity.arrayListGioHang.get (i).getSoLuongSanPham());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    jsonArray.put(jsonobject);

                }
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("json", jsonArray.toString());
                return hashMap;
            }
        };
        queue.add(request);
    }

    private void mapping() {
        btn_Thoat = findViewById(R.id.btn_Thoat);
        btn_XacNhan = findViewById(R.id.btn_XacNhan);
        til_Name = findViewById(R.id.til_Name);
        til_Email = findViewById(R.id.til_Email);
        til_Phone = findViewById(R.id.til_Phone);
    }
}