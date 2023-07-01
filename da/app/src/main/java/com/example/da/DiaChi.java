package com.example.da;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.da.infoItem;

public class DiaChi extends AppCompatActivity {
    String[] options = {"VN +84", "US +1"};
    String[] options1 = {"Việt Nam", "Mỹ"};
    String[] options3 = {"TP.HCM", "Hà Nội"};
    String[] options4 = {"Los", "Cali"};
    Button btn ,btnAuto;
    TextView tvTen, tvGia, tvSoLuong;
    ImageView img;

    private LocationManager locationManager;
    private LocationListener locationListener;


    EditText edittxt_diachi, sodienthoai, nhapten;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_chi);

        Intent intent = getIntent();
        String tenSp = intent.getStringExtra("tenSp");
        int hinh = intent.getIntExtra("Hinh", 0);
        int gia = intent.getIntExtra("gia", 0);
        String username=intent.getStringExtra("UserName");
        Spinner();
        cbb_DiaChi();
        khuvuc_selec();


//        btnAuto=findViewById(R.id.btnAutoGetAdd);
//        btnAuto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent1=new Intent(DiaChi.this,laydiachi.class);
//                startActivity(intent1);
//            }
//        });

        btn = findViewById(R.id.btn_luu);
        sodienthoai = findViewById(R.id.txt_sdt);
        edittxt_diachi = findViewById(R.id.edittxt_diachi);
        nhapten = findViewById(R.id.txt_nhapThongTin);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(DiaChi.this, ThanhToan.class);
                intent2.putExtra("tttdiachi", edittxt_diachi.getText().toString());
                intent2.putExtra("sdt", sodienthoai.getText().toString());
                intent2.putExtra("ten", nhapten.getText().toString());
                intent2.putExtra("tenSp", tenSp);
                intent2.putExtra("Hinh", hinh);
                intent2.putExtra("gia", gia);
                intent2.putExtra("UserName",username);
                startActivity(intent2);

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

    public int cbb_DiaChi() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, options1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = findViewById(R.id.cbb_quoctich);
        spinner.setAdapter(adapter);
        if(spinner.toString() == "Việt Nam")
        {
            return  1;
        }
        else {
            return 2;
        }
    }


    public void cbb_khuvuc() {
        Spinner spinnerQuocTich = findViewById(R.id.cbb_quoctich);
        String selectedQuocTich = spinnerQuocTich.getSelectedItem().toString();

        ArrayAdapter<String> adapter = null;
        if(selectedQuocTich.equals("Việt Nam"))
        {
            adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, options3);
        }
        else if(selectedQuocTich.equals("Mỹ"))
        {
            adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, options4);
        }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = findViewById(R.id.cbb_khuvuc);
        spinner.setAdapter(adapter);

    }


    public void khuvuc_selec()
    {
        Spinner spinnerQuocTich = findViewById(R.id.cbb_quoctich);
        spinnerQuocTich.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cbb_khuvuc();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Không làm gì khi không có gì được chọn
            }
        });
    }

    public void diachi()
    {
        infoItem myInfoItem = new infoItem();
        btn = findViewById(R.id.btn_luu);
        sodienthoai = findViewById(R.id.txt_sdt);
        edittxt_diachi = findViewById(R.id.edittxt_diachi);
        nhapten = findViewById(R.id.txt_nhapThongTin);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(DiaChi.this, ThanhToan.class);
                intent2.putExtra("tttdiachi", edittxt_diachi.getText().toString());
                intent2.putExtra("sdt", sodienthoai.getText().toString());
                intent2.putExtra("ten", nhapten.getText().toString());
                intent2.putExtra("tenSp3", tvTen.getText().toString());

                startActivity(intent2);

            }
        });
    }


}