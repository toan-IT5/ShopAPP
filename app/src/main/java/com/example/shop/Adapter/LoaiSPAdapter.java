package com.example.shop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shop.Model.LoaiSP;
import com.example.shop.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoaiSPAdapter extends BaseAdapter {
    ArrayList<LoaiSP> arrayList;
    Context context;

    public LoaiSPAdapter(ArrayList<LoaiSP> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder {
        TextView txtTenLoaiSP;
        ImageView imgLoaiSP;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_loaisp, null);
            viewHolder.txtTenLoaiSP = convertView.findViewById(R.id.txtTenLoaiSP);
            viewHolder.imgLoaiSP = convertView.findViewById(R.id.imageLoaiSP);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();

        }
        LoaiSP loaiSP = (LoaiSP) getItem(position);
        viewHolder.txtTenLoaiSP.setText(loaiSP.getTenLoaiSanPham() );
        Picasso.with(context).load(loaiSP.getHinhAnhLoaiSanPham()).placeholder(R.drawable.loading).error(R.drawable.error).into(viewHolder.imgLoaiSP);

        return convertView;
    }
}
