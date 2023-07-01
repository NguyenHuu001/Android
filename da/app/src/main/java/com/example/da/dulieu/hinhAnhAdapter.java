package com.example.da.dulieu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.da.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class hinhAnhAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private int layout;
    private List<HinhAnh> hinhAnhList;
    private List<HinhAnh> hinhAnhListOld;

    public hinhAnhAdapter(Context context, int layout, List<HinhAnh> hinhAnhList) {
        this.context = context;
        this.layout = layout;
        this.hinhAnhList = hinhAnhList;
        this.hinhAnhListOld=hinhAnhList;
    }

    @Override
    public int getCount() {
        return hinhAnhList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public Filter getFilter() {
        Filter filter=new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String search=charSequence.toString();
                if(search.isEmpty())
                {
                    hinhAnhList=hinhAnhListOld;
                }
                else {
                    List<HinhAnh> list=new ArrayList<>();
                    for (HinhAnh hinhAnh:hinhAnhListOld)
                    {
                        if(hinhAnh.getTenSp().toLowerCase().contains(search.toLowerCase()))
                        {
                            list.add(hinhAnh);
                        }
                    }
                    hinhAnhList=list;
                }
                FilterResults filterResults=new FilterResults();
                filterResults.values=hinhAnhList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                hinhAnhList=(List<HinhAnh>) filterResults.values;
                notifyDataSetChanged();


            }
        };
        return filter;
    }

    private class ViewHolder
    {
        ImageView imgHinh;
        TextView tvTen;
        TextView gia;
        TextView soLuong;
        RatingBar danhGia;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null)
        {
            holder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(layout,null);
            holder.imgHinh=(ImageView) view.findViewById(R.id.picture1);
            holder.tvTen=(TextView) view.findViewById(R.id.tvTenSp);
            holder.gia=(TextView) view.findViewById(R.id.tvGia);
            holder.soLuong=(TextView) view.findViewById(R.id.tvsoluong);
            holder.danhGia=(RatingBar) view.findViewById(R.id.ratingBar1);
            view.setTag(holder);
        }
        else
        {
            holder= (ViewHolder) view.getTag();
        }
        HinhAnh hinhAnh=hinhAnhList.get(i);
        holder.imgHinh.setImageResource(hinhAnh.getHinh());
        holder.tvTen.setText(hinhAnh.getTenSp());
        String COUNTRY = "VN";
        String LANGUAGE = "vi";
        String str = NumberFormat.getCurrencyInstance(new Locale(LANGUAGE, COUNTRY)).format(hinhAnh.getGia());
        holder.gia.setText(str);
       holder.soLuong.setText(hinhAnh.getSoLuong());
        holder.danhGia.setNumStars(hinhAnh.getDanhGia());
        return view;
    }
}
