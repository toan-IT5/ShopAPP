package com.example.shop.Model;

public class ProductTypeModel {
    public int Id;
    public String TenLoaiSanPham;
    public String HinhAnhLoaiSanPham;

    public ProductTypeModel(int id, String tenLoaiSanPham, String hinhAnhLoaiSanPham) {
        Id = id;
        TenLoaiSanPham = tenLoaiSanPham;
        HinhAnhLoaiSanPham = hinhAnhLoaiSanPham;
    }

    public ProductTypeModel() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTenLoaiSanPham() {
        return TenLoaiSanPham;
    }

    public void setTenLoaiSanPham(String tenLoaiSanPham) {
        TenLoaiSanPham = tenLoaiSanPham;
    }

    public String getHinhAnhLoaiSanPham() {
        return HinhAnhLoaiSanPham;
    }

    public void setHinhAnhLoaiSanPham(String hinhAnhLoaiSanPham) {
        HinhAnhLoaiSanPham = hinhAnhLoaiSanPham;
    }
}
