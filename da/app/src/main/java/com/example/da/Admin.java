package com.example.da;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.da.dulieu.AdminAdapter;
import com.example.da.dulieu.DatabaseHelper;
import com.example.da.dulieu.HinhAnh;

import java.util.ArrayList;

public class Admin extends AppCompatActivity {
    GridView gvHinhAnh;
    ArrayList<HinhAnh> arrayImage;
    ArrayList<HinhAnh> updatedArrayImage;
    ImageButton btnAdd,btnQuayLai,btnGioHang;
    TextView txtSoLuong,tvChuDe;
    com.example.da.dulieu.AdminAdapter AdminAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        tvChuDe=findViewById(R.id.tvChuDe);
        tvChuDe.setText("Quản lý sản phẩm");
        btnQuayLai=findViewById(R.id.quaylai);
        btnQuayLai.setImageResource(R.drawable.baseline_exit_to_app_24);
        btnQuayLai.setBackgroundResource(R.drawable.baseline_exit_to_app_24);
        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Admin.this,Login.class);
                startActivity(intent);
            }
        });
        txtSoLuong=findViewById(R.id.cart_badge_textview);
        txtSoLuong.setVisibility(View.GONE);
        btnGioHang=findViewById(R.id.btnCTGioHang);
        btnGioHang.setVisibility(View.GONE);
        gvHinhAnh=findViewById(R.id.gridViewSp);


        updateGridView();
        
        btnAdd=findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Admin.this,AddProduct.class);
                startActivity(intent);
            }
        });

        

    }

    private void updateGridView() {
        // Đọc dữ liệu từ cơ sở dữ liệu và cập nhật GridView
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                "ten",
                "hinh",
                "gia",
                "soluong",
                "daban"
        };

        Cursor cursor = db.query(
                "hinh_anh",
                projection,
                null,
                null,
                null,
                null,
                null
        );

        updatedArrayImage = new ArrayList<>();
        while (cursor.moveToNext()) {
            String ten = cursor.getString(cursor.getColumnIndexOrThrow("ten"));
            int hinh = cursor.getInt(cursor.getColumnIndexOrThrow("hinh"));
            int gia = cursor.getInt(cursor.getColumnIndexOrThrow("gia"));
            String soluong = cursor.getString(cursor.getColumnIndexOrThrow("soluong"));
            int daban = cursor.getInt(cursor.getColumnIndexOrThrow("daban"));

            updatedArrayImage.add(new HinhAnh(ten, hinh, gia, soluong, daban));
        }

        cursor.close();
        db.close();


        AdminAdapter =new AdminAdapter(this,R.layout.activity_produc_admin,updatedArrayImage);
        gvHinhAnh.setAdapter(AdminAdapter);
    }
}