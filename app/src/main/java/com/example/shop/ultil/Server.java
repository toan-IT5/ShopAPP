package com.example.shop.ultil;

public class Server {
    public static String localHost = "192.168.100.175";
    public static String DuongDan = "http://"+localHost+"/server/getloaisp.php";
//        public static String DuongDan = "http://"+localHost+":3000/producttype";
    public static String DuongDanSanPhamMoiNhat = "http://"+localHost+"/server/getproduct.php";
//    public static String DuongDanSanPhamMoiNhat = "http://"+localHost+":3000/productsnew";

    public static String DuongDanSanPham = "http://"+localHost+"/server/getproductbytype.php?page=";

}
