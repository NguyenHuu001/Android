package com.example.da;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.da.dulieu.DatabaseHelper;
import com.example.da.dulieu.HinhAnh;
import com.example.da.dulieu.hinhAnhAdapter;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    GridView gvHinhAnh;
    ArrayList<HinhAnh> arrayImage;
    ArrayList<HinhAnh> updatedArrayImage;
    hinhAnhAdapter anhAdapter;
    TextView txtSearch,txtSoLuong;
    ImageButton btnTimKiem,btnQuayLai,btnCTGioHang;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    private ImageSlider imageSlider;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else if (item.getItemId() == android.R.id.home) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageSlider=findViewById(R.id.ImageSlider);
        ArrayList<SlideModel>slideModels=new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.image_1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image_2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image_3, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image_4, ScaleTypes.FIT));

        imageSlider.setImageList(slideModels,ScaleTypes.FIT);

        String tenDangNhap;
        Intent intent=getIntent();
        tenDangNhap=intent.getStringExtra("UserName");

        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setVisibility(View.GONE);



        Toolbar toolbar = findViewById(R.id.qwe);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home:
                    {

                        navigationView.setVisibility(View.GONE);
                        break;
                    }
                    case R.id.dangxuat:
                    {
                        Intent intent=new Intent(MainActivity.this,Login.class);
                        startActivity(intent);

                        break;
                    }
                    case R.id.thongtin:
                    {
                        Intent intent=new Intent(MainActivity.this,InfoAccount.class);
                        intent.putExtra("UserName",tenDangNhap);
                        startActivity(intent);
                        break;
                    }
                    case R.id.doimatkhau:
                    {
                        Intent intent=new Intent(MainActivity.this,ChangePassword.class);
                        intent.putExtra("UserName",tenDangNhap);
                        startActivity(intent);
                        break;
                    }
                    case R.id.lsMuaHang:
                    {
                        Intent intent=new Intent(MainActivity.this,lsMuaHang.class);
                        intent.putExtra("UserName",tenDangNhap);
                        startActivity(intent);
                        break;
                    }
                }
                return false;
            }
        });

        gvHinhAnh=findViewById(R.id.gridViewSp);

        txtSoLuong=findViewById(R.id.cart_badge_textview);
        updateGridView();
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        updateGridView();

        String countQuery = "SELECT COUNT(*) FROM Cart WHERE username = '" + tenDangNhap + "' AND confirm  = 0";
        Cursor cursor1 = db.rawQuery(countQuery, null);
        cursor1.moveToFirst();
        int count = cursor1.getInt(0);
        cursor1.close();
        txtSoLuong.setText(String.valueOf(count));

        gvHinhAnh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getApplicationContext(),infoItem.class);
                HinhAnh select=updatedArrayImage.get(i);
                intent.putExtra("Hinh",select.getHinh());
                intent.putExtra("tenSp",select.getTenSp());
                intent.putExtra("gia",select.getGia());
                intent.putExtra("soLuong",select.getSoLuong());
                intent.putExtra("danhGia",select.getDanhGia());
                intent.putExtra("UserName",tenDangNhap);

                startActivity(intent);
            }
        });
        txtSearch=findViewById(R.id.txtTimKiem);
        btnTimKiem=findViewById(R.id.btnSearch);
        btnQuayLai=findViewById(R.id.quaylai);
        btnQuayLai.setImageResource(R.drawable.baseline_density_medium_24);
        btnQuayLai.setBackgroundResource(R.drawable.baseline_density_medium_24);
        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigationView.setVisibility(View.VISIBLE);
            }
        });

        btnTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tuKhoa = txtSearch.getText().toString();
                anhAdapter.getFilter().filter(tuKhoa);
                gvHinhAnh.setAdapter(anhAdapter);
            }

        });
        btnCTGioHang=findViewById(R.id.btnCTGioHang);
        btnCTGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,CartListAcitity.class);
                intent.putExtra("UserName",tenDangNhap);
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

        // Cập nhật GridView với dữ liệu mới
        anhAdapter=new hinhAnhAdapter(this,R.layout.grid_view_item,updatedArrayImage);
        gvHinhAnh.setAdapter(anhAdapter);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }

    }
}