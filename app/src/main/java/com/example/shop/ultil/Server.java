package com.example.shop.ultil;

public class Server {
    public static String localHost = "192.168.100.175";
    public static String ProductTypeLink = "http://"+localHost+"/server/getloaisp.php";
//    public static String DuongDan = "http://"+localHost+":3000/producttype";
    public static String LatestProductLinks = "http://"+localHost+"/server/getproduct.php";
//    public static String DuongDanSanPhamMoiNhat = "http://"+localHost+":3000/productsnew";

    public static String ProductLink = "http://"+localHost+"/server/getproductbytype.php?page=";
    public static String CustomerInformationLink = "http://"+localHost+"/server/thongtinkhachhang.php";
    public static String OrderDetailsLink = "http://"+localHost+"/server/chitietdonhang.php";

}
