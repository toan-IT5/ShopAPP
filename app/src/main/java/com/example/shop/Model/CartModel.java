package com.example.shop.Model;

public class CartModel {
    public int idSanPham;
    public String tenSanPham;
    public long giaSanPham;
    public String hinhSanPham;
    public int SoLuongSanPham;

    public CartModel() {
    }

    public CartModel(int idSanPham, String tenSanPham, long giaSanPham, String hinhSanPham, int soLuongSanPham) {
        this.idSanPham = idSanPham;
        this.tenSanPham = tenSanPham;
        this.giaSanPham = giaSanPham;
        this.hinhSanPham = hinhSanPham;
        SoLuongSanPham = soLuongSanPham;
    }

    public int getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(int idSanPham) {
        this.idSanPham = idSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public long getGiaSanPham() {
        return giaSanPham;
    }

    public void setGiaSanPham(long giaSanPham) {
        this.giaSanPham = giaSanPham;
    }

    public String getHinhSanPham() {
        return hinhSanPham;
    }

    public void setHinhSanPham(String hinhSanPham) {
        this.hinhSanPham = hinhSanPham;
    }

    public int getSoLuongSanPham() {
        return SoLuongSanPham;
    }

    public void setSoLuongSanPham(int soLuongSanPham) {
        SoLuongSanPham = soLuongSanPham;
    }

}