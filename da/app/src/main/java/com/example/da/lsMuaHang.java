package com.example.da;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.da.dulieu.Contract;
import com.example.da.dulieu.DatabaseHelper;
import com.example.da.dulieu.ProductAdapter;

import java.util.ArrayList;

public class lsMuaHang extends AppCompatActivity {
    TextView txtSoLuong,tvChuDe;
    ListView lv;
    ImageButton btnCTGioHang,btnQuayLai;
    ArrayList<String> tenSanPhamList = new ArrayList<>();
    ArrayList<Integer> giaSanPhamList = new ArrayList<>();
    ArrayList<Integer> hinhAnhSanPhamList = new ArrayList<>();
    ArrayList<Integer> soluongSanPhamList = new ArrayList<>();
    Button btnMuaTiep;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ls_mua_hang);

        btnMuaTiep=findViewById(R.id.btnMuaTiep);
        tvChuDe=findViewById(R.id.tvChuDe);
        tvChuDe.setText("Lịch sử mua hàng");
        btnQuayLai=findViewById(R.id.quaylai);
        txtSoLuong=findViewById(R.id.cart_badge_textview);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String tenDangNhap;
        Intent intent=getIntent();
        tenDangNhap=intent.getStringExtra("UserName");
        String countQuery = "SELECT COUNT(*) FROM Cart WHERE username = '" + tenDangNhap + "' AND confirm  = 0";
        Cursor cursor1 = db.rawQuery(countQuery, null);
        cursor1.moveToFirst();
        int count = cursor1.getInt(0);
        cursor1.close();
        txtSoLuong.setText(String.valueOf(count));

        btnCTGioHang=findViewById(R.id.btnCTGioHang);
        btnCTGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(lsMuaHang.this,CartListAcitity.class);
                intent.putExtra("UserName",tenDangNhap);
                startActivity(intent);
            }
        });

        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(lsMuaHang.this,MainActivity.class);
                intent.putExtra("UserName",tenDangNhap);
                startActivity(intent);
            }
        });
        lv=findViewById(R.id.listCart);
        layDanhSachSanPhamTrongGioHang(tenDangNhap,tenSanPhamList,giaSanPhamList,hinhAnhSanPhamList,soluongSanPhamList);
        ProductAdapter productAdapter=new ProductAdapter(getApplicationContext(),tenSanPhamList,hinhAnhSanPhamList,giaSanPhamList,soluongSanPhamList);
        lv.setAdapter(productAdapter);

        btnMuaTiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(lsMuaHang.this,MainActivity.class);
                intent.putExtra("UserName",tenDangNhap);
                startActivity(intent);
            }
        });
    }
    public void layDanhSachSanPhamTrongGioHang(String tenDangNhap, ArrayList<String> listTen, ArrayList<Integer> listGia, ArrayList<Integer> listHinh,ArrayList<Integer> listSl) {
        DatabaseHelper dbHelper=new DatabaseHelper(lsMuaHang.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                Contract.Cart.COLUMN_PRODUCT_NAME,
                Contract.Cart.COLUMN_PRICE,
                Contract.Cart.COLUMN_IMAGE,
                Contract.Cart.COLUMN_QUANTITY
        };
        String selection = Contract.Cart.COLUMN_USERNAME + " = ? AND " +
                Contract.Cart.COLUMN_CONFIRMED + " = ?";
        String[] selectionArgs = { tenDangNhap, "1" };
        Cursor cursor = db.query(
                Contract.Cart.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            String tenSanPham = cursor.getString(cursor.getColumnIndexOrThrow(Contract.Cart.COLUMN_PRODUCT_NAME));
            int giaSanPham = cursor.getInt(cursor.getColumnIndexOrThrow( Contract.Cart.COLUMN_PRICE));
            int hinhAnhSanPham = cursor.getInt(cursor.getColumnIndexOrThrow(  Contract.Cart.COLUMN_IMAGE));
            int sl=cursor.getInt(cursor.getColumnIndexOrThrow(Contract.Cart.COLUMN_QUANTITY));
            listTen.add(tenSanPham);
            listGia.add(giaSanPham);
            listHinh.add(hinhAnhSanPham);
            listSl.add(sl);
        }
        cursor.close();
    }
}