package com.example.shop.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shop.Model.GioHangModel;
import com.example.shop.Model.SanPham;
import com.example.shop.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ChiTietSanPham extends AppCompatActivity {
    Toolbar toolbarChitiet;
    ImageView imgChitiet;
    TextView txtten, txtgia, txtmota;
    Button btndatmua;
    long giaCTSP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        anhXa();
        actionToolBar();
        getInformation();
        catchEventButton();
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
                Intent intent = new Intent(getApplicationContext(), GioHang.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void catchEventButton() {
        btndatmua.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                    SanPham sanPham= getInformation();
                    boolean exit = false;
                    if (MainActivity.arrayListGioHang.size() > 0){
                        for (int i = 0 ; i < MainActivity.arrayListGioHang.size() ; i++){
                            if (MainActivity.arrayListGioHang.get(i).idSanPham == sanPham.getID()){
                                MainActivity.arrayListGioHang.get(i).setSoLuongSanPham(MainActivity.arrayListGioHang.get(i).getSoLuongSanPham() +1);
                                MainActivity.arrayListGioHang.get(i).setGiaSanPham(MainActivity.arrayListGioHang.get(i).getSoLuongSanPham() * sanPham.GiaSanPham);
                                exit = true;
                            }
                        }
                        if (exit == false)
                            MainActivity.arrayListGioHang.add(new GioHangModel(sanPham.getID(),sanPham.getTenSanPham(), sanPham.getGiaSanPham(), sanPham.getHinhAnhSanPham(),1));
                    }else {

                        MainActivity.arrayListGioHang.add(new GioHangModel(sanPham.getID(),sanPham.getTenSanPham(), sanPham.getGiaSanPham(), sanPham.getHinhAnhSanPham(),1));
                    }
                    Intent intent = new Intent(getApplicationContext(), GioHang.class);
                    startActivity(intent);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private SanPham getInformation() {

        SanPham sanpham = (SanPham) getIntent().getSerializableExtra("thongtinsanpham");

        txtten.setText(sanpham.getTenSanPham());
        giaCTSP = sanpham.getGiaSanPham();
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtgia.setText ("Giá : " + decimalFormat.format(sanpham.getGiaSanPham()) + " Đ");
        txtmota.setText (sanpham.getMoTaSanPham());
        Picasso.with(getApplicationContext()).load(sanpham.getHinhAnhSanPham()).placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(imgChitiet);
        return sanpham;
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
        btndatmua = findViewById(R.id.btn_DatHang);

    }
}