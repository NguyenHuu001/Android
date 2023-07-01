package com.example.da.dulieu;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.da.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;


public class ProductAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> listName;
    private ArrayList<Integer> listImg;
    private ArrayList<Integer> listPrice;
    private ArrayList<Integer> listSl;
    private LayoutInflater inflater;

    public ProductAdapter(Context context, ArrayList<String> listName, ArrayList<Integer> listImg, ArrayList<Integer> listPrice,ArrayList<Integer>listsl) {
        this.context = context;
        this.listName = listName;
        this.listImg = listImg;
        this.listPrice = listPrice;
        this.listSl=listsl;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listPrice.size();
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        View convertView=inflater.inflate(R.layout.activity_list_item_cart,null);

        TextView txtten=(TextView)convertView.findViewById(R.id.tv_product_name);
        ImageView img=(ImageView) convertView.findViewById(R.id.img_product);
        TextView txtPrice=(TextView) convertView.findViewById(R.id.tv_product_price);
        EditText txtSoLuong=(EditText) convertView.findViewById(R.id.txtSoLuong);

        txtten.setText(listName.get(i));
        txtPrice.setText(listPrice.get(i)+" VNĐ");
        img.setImageResource(listImg.get(i));
        txtSoLuong.setText(Integer.toString(listSl.get(i)));


        int giasp=listPrice.get(i)*listSl.get(i);


        ImageButton cong,tru;
        EditText soluong;
        TextView giaTungLoai;

        giaTungLoai=convertView.findViewById(R.id.giaTungLoai);
        cong=(ImageButton) convertView.findViewById(R.id.tangSoLuong);
        tru=(ImageButton) convertView.findViewById((R.id.giamSoLuong));
        soluong=convertView.findViewById(R.id.txtSoLuong);
        int soluonghangcu=Integer.parseInt(soluong.getText().toString());

        cong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soluonghangcu=Integer.parseInt(soluong.getText().toString());
                int soluonghangmoi=soluonghangcu+1;

                soluong.setText(String.valueOf(soluonghangmoi));

                int giasp=listPrice.get(i)*soluonghangmoi;
                String COUNTRY = "VN";
                String LANGUAGE = "vi";
                String str = NumberFormat.getCurrencyInstance(new Locale(LANGUAGE, COUNTRY)).format(giasp);
                giaTungLoai.setText(str);

            }
        });
        tru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soluonghangcu=Integer.parseInt(soluong.getText().toString());
                int soluonghangmoi=soluonghangcu-1;
                soluong.setText(String.valueOf(soluonghangmoi));

                int giasp=listPrice.get(i)*soluonghangmoi;
                String COUNTRY = "VN";
                String LANGUAGE = "vi";
                String str = NumberFormat.getCurrencyInstance(new Locale(LANGUAGE, COUNTRY)).format(giasp);
                giaTungLoai.setText(str);

            }
        });
        String COUNTRY = "VN";
        String LANGUAGE = "vi";
        String str = NumberFormat.getCurrencyInstance(new Locale(LANGUAGE, COUNTRY)).format(giasp);

        giaTungLoai.setText(str);


        return convertView;
    }
}
