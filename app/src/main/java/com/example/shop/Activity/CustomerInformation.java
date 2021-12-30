package com.example.shop.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shop.R;
import com.example.shop.ultil.Server;
import com.google.android.material.textfield.TextInputLayout;

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

    private void postOrderDetail(){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, Server.OrderDetailsLink, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            
        };
    }

    private void mapping() {
        btn_Thoat = findViewById(R.id.btn_Thoat);
        btn_XacNhan = findViewById(R.id.btn_XacNhan);
        til_Name = findViewById(R.id.til_Name);
        til_Email = findViewById(R.id.til_Email);
        til_Phone = findViewById(R.id.til_Phone);
    }
}