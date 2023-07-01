package com.example.da;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.da.dulieu.GPSTracker;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.xml.transform.Result;

public class ThanhToan extends AppCompatActivity {
    String[] options = {"Tiền mặt", "Ngân hàng", "MOMO"};
    TextView tvTen, tvGia,  tongtien,txtPriceGH;
    ImageView img;
    Button diachi1 ;
    ImageButton img_diachi, imgbtn;
    private double latitude = 0.0;
    private double longitude = 0.0;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final int NOTIFICATION_ID = 1;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);

        txtPriceGH=findViewById(R.id.txtPriceGH);
        String COUNTRY = "VN";
        String LANGUAGE = "vi";
        String str = NumberFormat.getCurrencyInstance(new Locale(LANGUAGE, COUNTRY)).format(40000);
        txtPriceGH.setText(str);
        Spinner();

        Back();
        diachi1 = findViewById(R.id.btn_diachi);
        ngay_gio();
        if (TextUtils.isEmpty(diachi1.toString())) {
            diachi1.setText("Hữu \\t\\t\\t\\n(+84)03********687\\n18 Nguyễn Sáng\\nTây Thạnh, Tân Phú, TP.HCM,..");
        } else {
            diachi();
        }
        setttt();
        chuyentrang();
        tru_so_luong();
        cong_so_luong();
        tinh_TongTien();
        sett1();
        Intent intent=getIntent();
        String username=intent.getStringExtra("UserName");
        Button btn_thanhtoan = findViewById(R.id.btn_thanhtoan);
        btn_thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                senNotification();
                Intent intentgh2 = new Intent(ThanhToan.this, DaTT.class);
                intentgh2.putExtra("UserName",username);
                startActivity(intentgh2);
            }
        });

        imgbtn = findViewById(R.id.img_diachi);
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // senNotification();

                if (ContextCompat.checkSelfPermission(
                        ThanhToan.this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ThanhToan.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_LOCATION_PERMISSION);
                } else {
                    getCurrentAddress();
                    Intent intentgh2 = new Intent(ThanhToan.this, Map.class);
                    intentgh2.putExtra("la",latitude);
                    intentgh2.putExtra("long",longitude);
                    startActivity(intentgh2);
                }

            }
        });
    }
    private void getCurrentAddress() {
        GPSTracker gpsTracker = new GPSTracker(ThanhToan.this);
        Location location = gpsTracker.getLocation();

        if (location != null) {
             latitude = location.getLatitude();
             longitude = location.getLongitude();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentAddress();
            }
        }
    }
    public void senNotification()
    {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.baseline_shopping_cart_24);
        Notification notification = new Notification.Builder(this)
                .setContentTitle("Đã thanh toán")
                .setContentText( ngay_gio())
                .setSmallIcon(R.drawable.facebook)
                .setLargeIcon(bitmap).build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(notificationManager != null)
        {
            notificationManager.notify(NOTIFICATION_ID, notification);
        }


    }
    public void sett1()
    {
        img = findViewById(R.id.anh_mau);
        Intent intent = getIntent();
        String tenSp1 = intent.getStringExtra("tenSp");
        int hinh1 = intent.getIntExtra("Hinh", 0);
        int gia1 = intent.getIntExtra("gia", 0);
        String username=intent.getStringExtra("UserName");
        tvTen.setText(tenSp1);
        String COUNTRY = "VN";
        String LANGUAGE = "vi";
        String str = NumberFormat.getCurrencyInstance(new Locale(LANGUAGE, COUNTRY)).format(gia1);
        tvGia.setText(str);
        img.setImageResource(hinh1);
    }
    public void setttt() {
        Intent intent = getIntent();
        String tenSp = intent.getStringExtra("tenSp");
        int hinh = intent.getIntExtra("Hinh", 0);
        int gia = intent.getIntExtra("gia", 0);
        String username=intent.getStringExtra("UserName");
        tvTen.setText(tenSp);
        String COUNTRY = "VN";
        String LANGUAGE = "vi";
        String str = NumberFormat.getCurrencyInstance(new Locale(LANGUAGE, COUNTRY)).format(gia);
        tvGia.setText(str);
        img.setImageResource(hinh);

        diachi1 = findViewById(R.id.btn_diachi);
        diachi1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvGia = findViewById(R.id.gia_vay);
                setttt();
                Intent intentgh = new Intent(ThanhToan.this, DiaChi.class);
                intentgh.putExtra("tenSp", tvTen.getText().toString());
                intentgh.putExtra("Hinh", hinh);
                intentgh.putExtra("gia", gia);
                intentgh.putExtra("UserName",username);
                startActivity(intentgh);
            }
        });



        img_diachi = findViewById(R.id.icon_phai);
        img_diachi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentgh1 = new Intent(ThanhToan.this, DiaChi.class);
                intentgh1.putExtra("tenSp", tvTen.getText().toString());
                intentgh1.putExtra("Hinh", hinh);
                intentgh1.putExtra("gia", gia);
                intentgh1.putExtra("UserName",username);
                startActivity(intentgh1);
            }
        });


    }

    public void Spinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = findViewById(R.id.my_spinner);
        spinner.setAdapter(adapter);
    }

    public void Back() {
        ImageButton btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public String ngay_gio() {
        TextView txtTimeShip = findViewById(R.id.txt_timeShip);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd - HH:mm");
        Calendar calendar = Calendar.getInstance(); // Lấy thời gian hiện tại
        calendar.add(Calendar.DAY_OF_YEAR, 4); // Cộng thêm 4 ngày
        String currentDateandTime = sdf.format(calendar.getTime()); // Định dạng thời gian mới
        txtTimeShip.setText("Giao hàng trước " + currentDateandTime);
        return  "Giao hàng trước " + currentDateandTime;
    }

    public void tinh_TongTien()
    {
        TextView sl = findViewById(R.id.txtamount);
        int soluong = Integer.parseInt(sl.getText().toString());
        Intent intent = getIntent();
        int gia = intent.getIntExtra("gia", 0);
        tongtien = findViewById(R.id.tongcong);
        if(soluong > 0)
        {
            int tong = (int) (gia * soluong + 40000);
            String COUNTRY = "VN";
            String LANGUAGE = "vi";
            String str = NumberFormat.getCurrencyInstance(new Locale(LANGUAGE, COUNTRY)).format(tong);
            tongtien.setText(str);
        }
        else
        {
            int tong = 0;
            String COUNTRY = "VN";
            String LANGUAGE = "vi";
            String str = NumberFormat.getCurrencyInstance(new Locale(LANGUAGE, COUNTRY)).format(tong);
            tongtien.setText(str);
        }



    }

    public void tru_so_luong()
    {
        TextView sl = findViewById(R.id.txtamount);
        ImageButton tru = findViewById(R.id.img_tru);
        tru.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int soLuong = Integer.parseInt(sl.getText().toString());
                if(soLuong > 0)
                {
                    soLuong = soLuong - 1;
                    sl.setText(Integer.toString(soLuong));
                    tinh_TongTien();
                }

            }
        });
    }
    public void cong_so_luong()
    {
        TextView sl = findViewById(R.id.txtamount);
        ImageButton tru = findViewById(R.id.img_cong);
        tru.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int soLuong = Integer.parseInt(sl.getText().toString());
                if(soLuong >= 0)
                {
                    soLuong = soLuong + 1;
                    sl.setText(Integer.toString(soLuong));
                    tinh_TongTien();
                }

            }
        });
    }


    public void chuyentrang()
    {



    }
    public void diachi ()
    {
        tvTen = findViewById(R.id.ten_vay);
        tvGia = findViewById(R.id.gia_vay);
        img = findViewById(R.id.anh_mau);
        diachi1 = findViewById(R.id.btn_diachi);

        Intent intent = getIntent();
        String diachi = intent.getStringExtra("tttdiachi");
        String sdt = intent.getStringExtra("sdt");
        String ten = intent.getStringExtra("ten");
        String username=intent.getStringExtra("UserName");
        diachi1.setText(ten + "\n" + sdt + "\n" + diachi);

    }

}