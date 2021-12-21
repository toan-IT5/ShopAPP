package com.example.shop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.shop.Model.SanPham;
import com.example.shop.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ChiTietSanPham extends AppCompatActivity {
    Toolbar toolbarChitiet;
    ImageView imgChitiet;
    TextView txtten, txtgia, txtmota;
    Spinner spinner;
    Button btndatmua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        anhXa();
        actionToolBar();
        getInformation();
        catchEventSpinner();
    }

    private void catchEventSpinner() {
        Integer[] soLuong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, soLuong);
        spinner.setAdapter(arrayAdapter);
    }

    @SuppressLint("SetTextI18n")
    private void getInformation() {

        SanPham sanpham = (SanPham) getIntent().getSerializableExtra("thongtinsanpham");

        txtten.setText(sanpham.getTenSanPham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtgia.setText ("Giá : " + decimalFormat.format(sanpham.getGiaSanPham()) + " Đ");
        txtmota.setText (sanpham.getMoTaSanPham());
        Picasso.with(getApplicationContext()).load(sanpham.getHinhAnhSanPham()).placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(imgChitiet);
    }

    private void actionToolBar() {
        setSupportActionBar(toolbarChitiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChitiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void anhXa() {
        toolbarChitiet =findViewById (R.id.tb_ChiTietSanPhamCTSP);
        imgChitiet = findViewById(R.id.img_HinhAnhCTSP);
        txtten =  findViewById(R.id.txt_TenSanPhamCTSP) ;
        txtgia =  findViewById(R.id.txt_GiaCTSP);
        txtmota =  findViewById(R.id.txt_MoTaSanPhamCTSP);
        spinner = findViewById(R.id.spinner_CTSP);
        btndatmua = findViewById(R.id.btn_DatHang);

    }
}