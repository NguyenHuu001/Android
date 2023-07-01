package com.example.da;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.da.dulieu.Contract;
import com.example.da.dulieu.DatabaseHelper;
import com.example.da.dulieu.ProductAdapter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

public class CartListAcitity extends AppCompatActivity {
    ListView lv;
    TextView tvChuDe,tvcart_badge_textview;
    ImageButton btnQuayLai,btnCTGioHang;
    DatabaseHelper dbHelper;
    ArrayList<String> tenSanPhamList = new ArrayList<>();
    ArrayList<Integer> giaSanPhamList = new ArrayList<>();
    ArrayList<Integer> hinhAnhSanPhamList = new ArrayList<>();
    ArrayList<Integer> soluongSanPhamList = new ArrayList<>();
    Button btnThanhToan;

    String[] listTen = new String[100];
    int[] listGia = new int[100];
    int[] listHinh = new int[100];
    TextView tvFinalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list_acitity);

        tvChuDe=findViewById(R.id.tvChuDe);
        tvChuDe.setText("Chi tiết giỏ hàng");
        btnCTGioHang=findViewById(R.id.btnCTGioHang);
        btnCTGioHang.setVisibility(View.INVISIBLE);
        tvcart_badge_textview=findViewById(R.id.cart_badge_textview);
        tvcart_badge_textview.setVisibility(View.INVISIBLE);

        dbHelper=new DatabaseHelper(CartListAcitity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Intent intent=getIntent();
        String ten=intent.getStringExtra("tenSp");

        int hinh=intent.getIntExtra("Hinh",0);
        int giasp=intent.getIntExtra("gia",0);
        String tendn=intent.getStringExtra("UserName");
        btnQuayLai=findViewById(R.id.quaylai);
        btnQuayLai.setImageResource(R.drawable.baseline_home_24);
        btnQuayLai.setBackgroundResource(R.drawable.baseline_home_24);
        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CartListAcitity.this,MainActivity.class);
                intent.putExtra("UserName",tendn);

                startActivity(intent);
            }
        });
        lv=findViewById(R.id.listCart);
        layDanhSachSanPhamTrongGioHang(tendn,tenSanPhamList,giaSanPhamList,hinhAnhSanPhamList,soluongSanPhamList);
        ProductAdapter productAdapter=new ProductAdapter(getApplicationContext(),tenSanPhamList,hinhAnhSanPhamList,giaSanPhamList,soluongSanPhamList);
        lv.setAdapter(productAdapter);

        tvFinalPrice=findViewById(R.id.txt_final_price);
        int count=lv.getAdapter().getCount();
        double totalPrice = 0;
        Locale locale = new Locale("vi", "VN"); // Đặt locale thành Việt Nam
        Currency currency = Currency.getInstance("VND"); // Đặt đơn vị tiền tệ là VNĐ

        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale); // Tạo đối tượng NumberFormat với định dạng tiền tệ
        numberFormat.setCurrency(currency);
        for(int i=0;i<count;i++)
        {
            View view=lv.getAdapter().getView(i,null,lv);
            TextView tvGiaSp=view.findViewById(R.id.giaTungLoai);
            String giaString=tvGiaSp.getText().toString();
            double gia= 0;
            try {
                gia = numberFormat.parse(giaString).doubleValue();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            totalPrice += gia;
        }
        String COUNTRY = "VN";
        String LANGUAGE = "vi";
        String str = NumberFormat.getCurrencyInstance(new Locale(LANGUAGE, COUNTRY)).format(totalPrice);
        tvFinalPrice.setText(str);

        btnThanhToan=findViewById(R.id.btn_order);
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(CartListAcitity.this,MainActivity.class);
                upDate(tendn);
                intent1.putExtra("UserName",tendn);
                startActivity(intent1);
            }
        });
    }
    public void layDanhSachSanPhamTrongGioHang(String tenDangNhap, ArrayList<String> listTen, ArrayList<Integer> listGia, ArrayList<Integer> listHinh,ArrayList<Integer> listSl) {
        DatabaseHelper dbHelper=new DatabaseHelper(CartListAcitity.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                Contract.Cart.COLUMN_PRODUCT_NAME,
                Contract.Cart.COLUMN_PRICE,
                Contract.Cart.COLUMN_IMAGE,
                Contract.Cart.COLUMN_QUANTITY
        };
        String selection = Contract.Cart.COLUMN_USERNAME + " = ? AND " +
                Contract.Cart.COLUMN_CONFIRMED + " = ?";
        String[] selectionArgs = { tenDangNhap, "0" };
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
    public void upDate(String username) {
        DatabaseHelper dbHelper = new DatabaseHelper(CartListAcitity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            // Cập nhật cột CONFIRMED thành 1 cho các hàng có username tương ứng
            ContentValues values = new ContentValues();
            values.put(Contract.Cart.COLUMN_CONFIRMED, 1);
            String selection = Contract.Cart.COLUMN_USERNAME + " = ?";
            String[] selectionArgs = { username };
            int count = db.update(Contract.Cart.TABLE_NAME, values, selection, selectionArgs);

            db.setTransactionSuccessful();
            Toast.makeText(this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Thanh toán thất bại", Toast.LENGTH_SHORT).show();
        } finally {
            db.endTransaction();
        }
    }






}