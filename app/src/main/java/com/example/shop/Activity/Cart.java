package com.example.shop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.shop.Adapter.CartAdapter;
import com.example.shop.R;

import java.text.DecimalFormat;

public class Cart extends AppCompatActivity {
    SwipeMenuListView lv_GioHang;
    TextView txt_ThongBao ;
    static EditText et_GiaTriGH;
    Button btn_TiepTucMuaHang,btn_MuaHang;
    Toolbar toolBarGH;
    CartAdapter gioHangAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        anhXa();
        ActionToolBar();
        CheckListView();
        EventUltil();
        SwipeMenuCreator();
        EventButton();
    }

    private void EventButton() {
        btn_TiepTucMuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        btn_MuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.arrayListGioHang.size() > 0 ){
                    Intent intent = new Intent(getApplicationContext(), CustomerInformation.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Giỏ hàng của bạn đang trống!",Toast.LENGTH_LONG);
                }
            }
        });
    }

    private void SwipeMenuCreator() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(170);
                // set a icon
                deleteItem.setIcon(R.drawable.delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        lv_GioHang.setMenuCreator(creator);

        lv_GioHang.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        MainActivity.arrayListGioHang.remove(position);
                        gioHangAdapter.notifyDataSetChanged();
                        EventUltil();
                        if (MainActivity.arrayListGioHang.size() <=0) {
                            txt_ThongBao.setVisibility(View.VISIBLE);
                        }else txt_ThongBao.setVisibility(View.INVISIBLE);
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }

    public static void EventUltil() {
        long tongTien = 0;
        for (int i =0; i < MainActivity.arrayListGioHang.size(); i++){
            tongTien += MainActivity.arrayListGioHang.get(i).getGiaSanPham();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        et_GiaTriGH.setText(decimalFormat.format(tongTien)+" Đ");
    }

    private void CheckListView() {
        if (MainActivity.arrayListGioHang.size() <=0){
            gioHangAdapter.notifyDataSetChanged();
            txt_ThongBao.setVisibility(View.VISIBLE);
            lv_GioHang.setVisibility(View.INVISIBLE);
        }else{
            gioHangAdapter.notifyDataSetChanged();
            txt_ThongBao.setVisibility(View.INVISIBLE);
            lv_GioHang.setVisibility(View.VISIBLE);
        }
    }

    private void ActionToolBar() {
        setSupportActionBar(toolBarGH);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBarGH.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void anhXa() {
        lv_GioHang = findViewById(R.id.lv_GioHang);
        txt_ThongBao = findViewById(R.id.txt_ThongBao);
        et_GiaTriGH = findViewById(R.id.et_GiaTriGH);
        btn_TiepTucMuaHang = findViewById(R.id.btn_TiepTucMuaHang);
        btn_MuaHang = findViewById(R.id.btn_MuaHang);
        toolBarGH = findViewById(R.id.toolBarGH);
        gioHangAdapter = new CartAdapter(Cart.this, MainActivity.arrayListGioHang);
        lv_GioHang.setAdapter(gioHangAdapter);

    }
}