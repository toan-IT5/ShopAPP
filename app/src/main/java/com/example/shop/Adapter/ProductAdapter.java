package com.example.shop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop.Activity.ProductDetails;
import com.example.shop.Model.ProductModel;
import com.example.shop.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ItemHolder> {
    Context context;
    ArrayList<ProductModel> arrayListSanPham;

    public ProductAdapter(Context context, ArrayList<ProductModel> arrayListSanPham) {
        this.context = context;
        this.arrayListSanPham = arrayListSanPham;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_san_pham_moi,null);
        ItemHolder itemHolder = new ItemHolder(view);

        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        ProductModel sanPham = arrayListSanPham.get(position);
        holder.txtTenSanPham.setMaxLines(2);
        holder.txtTenSanPham.setEllipsize(TextUtils.TruncateAt.END);
        holder.txtTenSanPham.setText(sanPham.getTenSanPham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGiaSanPham.setText("Giá: "+ decimalFormat.format(sanPham.getGiaSanPham())+ "Đ" );
        Picasso.with(context).load(sanPham.getHinhAnhSanPham()).placeholder(R.drawable.loading).error(R.drawable.error).into(holder.imgHinhAnhSanPham);
    }

    @Override
    public int getItemCount() {
        return arrayListSanPham.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imgHinhAnhSanPham;
        public TextView txtTenSanPham, txtGiaSanPham;

        public ItemHolder(View itemView){
            super(itemView);
            imgHinhAnhSanPham = itemView.findViewById(R.id.imgSanPham);
            txtTenSanPham = itemView.findViewById(R.id.txtTenSanPham);
            txtGiaSanPham = itemView.findViewById(R.id.txtGiaSanPham);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent view = new Intent();
                    Intent intent = new Intent(context, ProductDetails.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("thongtinsanpham", arrayListSanPham.get(getLayoutPosition()));
                    Toast.makeText(context,""+getLayoutPosition(), Toast.LENGTH_LONG).show();
                    context.startActivity(intent);
                }
            });
        }
    }
}
