package com.example.shop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shop.Adapter.GioHangAdapter;
import com.example.shop.R;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class GioHang extends AppCompatActivity {
    ListView lv_GioHang;
    TextView txt_ThongBao ;
    EditText et_GiaTriGH;
    Button btn_TiepTucMuaHang,btn_MuaHang;
    Toolbar toolBarGH;
    GioHangAdapter gioHangAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        anhXa();
        ActionToolBar();
        CheckListView();
        EventUltil();
    }

    private void EventUltil() {
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
        btn_TiepTucMuaHang = findViewById(R.id.btn_MuaHang);
        btn_MuaHang = findViewById(R.id.btn_MuaHang);
        toolBarGH = findViewById(R.id.toolBarGH);
        gioHangAdapter = new GioHangAdapter(GioHang.this, MainActivity.arrayListGioHang);
        lv_GioHang.setAdapter(gioHangAdapter);

    }
}