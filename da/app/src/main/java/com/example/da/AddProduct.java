package com.example.da;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.da.dulieu.DatabaseHelper;

public class AddProduct extends AppCompatActivity {
    private EditText etProductName;
    private EditText etProductPrice;
    private ImageButton ibProductImage,btnQuayLai,btnGioHang;
    private TextView txtSoLuong,tvChuDe;
    private Button btnAddProduct;
    private ImageView imageView;
    int SELECT_IMAGE_CODE=1;
    private int path;

    private int selectedImageResource = R.drawable.avata_shop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        tvChuDe=findViewById(R.id.tvChuDe);
        tvChuDe.setText("Thêm sản phẩm");
        btnQuayLai=findViewById(R.id.quaylai);
        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AddProduct.this,Admin.class);
                startActivity(intent);
            }
        });
        txtSoLuong=findViewById(R.id.cart_badge_textview);
        txtSoLuong.setVisibility(View.GONE);
        btnGioHang=findViewById(R.id.btnCTGioHang);
        btnGioHang.setVisibility(View.GONE);

        etProductName = findViewById(R.id.etProductName);
        etProductPrice = findViewById(R.id.etProductPrice);
        ibProductImage = findViewById(R.id.ibProductImage);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        imageView=(ImageView) findViewById(R.id.imageSP);

        ibProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT); // Chọn hình ảnh từ thiết bị

                // Để chụp ảnh mới, bạn có thể sử dụng Intent.ACTION_IMAGE_CAPTURE

                startActivityForResult(Intent.createChooser(intent, "Chọn ảnh"),SELECT_IMAGE_CODE );


            }

        });

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = etProductName.getText().toString();
                int productPrice = Integer.parseInt(etProductPrice.getText().toString());
                // Thực hiện thêm sản phẩm vào cơ sở dữ liệu
                addProductToDatabase(productName, productPrice, R.drawable.logoapp);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1)
        {
            Uri uri=data.getData();
            imageView.setImageURI(uri);

            path=encodeImagePath(getPathFromURI(uri));
        }
    }
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }
    public int encodeImagePath(String imagePath) {
        // Mã hóa đường dẫn thành số nguyên
        return imagePath.hashCode();
    }

    public String decodeImagePath(int encodedPath) {
        // Giải mã số nguyên thành đường dẫn
        return String.valueOf(encodedPath);
    }
    private void addProductToDatabase(String productName, int productPrice, int imageResource) {
        // Thêm sản phẩm vào cơ sở dữ liệu
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("ten", productName);
        values.put("gia", productPrice);
        values.put("hinh", imageResource);
        values.put("daban",5);

        long newRowId = db.insert("hinh_anh", null, values);
        if (newRowId != -1) {
            Toast.makeText(this, "Thêm mới thành công", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(AddProduct.this,Admin.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }


}