package com.example.da;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.da.dulieu.Contract;
import com.example.da.dulieu.DatabaseHelper;

import java.text.NumberFormat;
import java.util.Locale;

public class infoItem extends AppCompatActivity {
    TextView tvTen,tvGia,tvSoLuong,txtSoLuong;
    RatingBar danhgia;
    ImageView img;
    ImageButton btnGioHang,btnCTGioHang,btnHome;
    Button btnThem;
    TextView tvCount,tvChuDe;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_item);

         dbHelper=new DatabaseHelper(infoItem.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        btnHome=findViewById(R.id.quaylai);
        btnHome.setImageResource(R.drawable.baseline_home_24);
        btnHome.setBackgroundResource(R.drawable.baseline_home_24);


        tvTen=(TextView)findViewById(R.id.tvTenSpInfoItem);
        tvGia=(TextView) findViewById(R.id.tvGiaInfoItem);
        tvSoLuong=(TextView) findViewById(R.id.tvSoLuongInfoItem);
        img=(ImageView)findViewById(R.id.imgInfoItem);
        danhgia=(RatingBar)findViewById(R.id.danhgiaCT) ;


        tvCount=findViewById(R.id.cart_badge_textview);



        Intent intent=getIntent();
        String ten=intent.getStringExtra("tenSp");
        String soluongban=intent.getStringExtra("soLuong");
        int hinh=intent.getIntExtra("Hinh",0);
        int giasp=intent.getIntExtra("gia",0);
        int sao=intent.getIntExtra("danhGia",0);
        String username=intent.getStringExtra("UserName");
        danhgia.setNumStars(sao);
        tvTen.setText(ten);
        tvSoLuong.setText(soluongban);
        String COUNTRY = "VN";
        String LANGUAGE = "vi";
        String str = NumberFormat.getCurrencyInstance(new Locale(LANGUAGE, COUNTRY)).format(giasp);

        tvGia.setText(str);
        img.setImageResource(hinh);

        txtSoLuong=findViewById(R.id.cart_badge_textview);
        String countQuery = "SELECT COUNT(*) FROM Cart WHERE username = '" + username + "' AND confirm  = 0";
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();
        String count = cursor.getString(0);
        cursor.close();

        txtSoLuong.setText(count);

        btnGioHang=findViewById(R.id.btnGioHang);
        btnGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(infoItem.this,CartListAcitity.class);
                intent1.putExtra("Hinh",hinh);
                intent1.putExtra("tenSp",ten);
                intent1.putExtra("gia",giasp);
                intent1.putExtra("UserName",username);
                addToCart(hinh,ten,giasp,1,"Unknow",username);
                startActivity(intent1);
            }
        });
        tvChuDe=(TextView)findViewById(R.id.tvChuDe);
        tvChuDe.setText("Chi tiết sản phẩm");


        btnThem=findViewById(R.id.btnMuaHangInfoItem);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intentMh=new Intent(infoItem.this,ThanhToan.class);
                intentMh.putExtra("Hinh",hinh);
                intentMh.putExtra("tenSp",ten);
                intentMh.putExtra("gia",giasp);
                intentMh.putExtra("UserName",username);
               startActivity(intentMh);

            }
        });
        btnCTGioHang=findViewById(R.id.btnCTGioHang);
        btnCTGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(infoItem.this,CartListAcitity.class);
                intent1.putExtra("UserName",username);
                startActivity(intent1);
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  =new Intent(infoItem.this,MainActivity.class);
                intent.putExtra("UserName",username);
                startActivity(intent);
            }
        });


    }
    private void addToCart(int image, String productName, int price, int quantity, String address, String username) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        String selection = Contract.Cart.COLUMN_PRODUCT_NAME + " = ? AND " + Contract.Cart.COLUMN_USERNAME + " = ?";
        String[] selectionArgs = { productName, username };
        Cursor cursor = db.query(
                Contract.Cart.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        if (cursor.moveToFirst()) {
            // nếu sản phẩm đã có trong giỏ hàng, tăng số lượng lên 1
            int currentQuantity = cursor.getInt(cursor.getColumnIndexOrThrow(Contract.Cart.COLUMN_QUANTITY));
            int newQuantity = currentQuantity + 1;
            ContentValues values = new ContentValues();
            values.put(Contract.Cart.COLUMN_QUANTITY, newQuantity);
            db.update(Contract.Cart.TABLE_NAME, values, selection, selectionArgs);
            Toast.makeText(this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
        } else {
            // nếu sản phẩm chưa có trong giỏ hàng, thêm sản phẩm mới
            ContentValues values = new ContentValues();
            values.put(Contract.Cart.COLUMN_IMAGE, image);
            values.put(Contract.Cart.COLUMN_PRODUCT_NAME, productName);
            values.put(Contract.Cart.COLUMN_PRICE, price);
            values.put(Contract.Cart.COLUMN_QUANTITY, quantity);
            values.put(Contract.Cart.COLUMN_ADDRESS, address);
            values.put(Contract.Cart.COLUMN_USERNAME, username);
            db.insert(Contract.Cart.TABLE_NAME, null, values);
            Toast.makeText(this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
    }


}