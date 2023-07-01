package com.example.da;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class list_item_cart extends AppCompatActivity {
    ImageButton cong,tru;
    EditText soluong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item_cart);



        cong=(ImageButton) findViewById(R.id.tangSoLuong);
        tru=(ImageButton) findViewById((R.id.giamSoLuong));
        soluong=findViewById(R.id.txtSoLuong);
        int soluonghangcu=Integer.parseInt(soluong.getText().toString());

        cong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soluonghangcu=Integer.parseInt(soluong.getText().toString());
                int soluonghangmoi=soluonghangcu+1;

                soluong.setText(String.valueOf(soluonghangmoi));
            }
        });
        tru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soluonghangcu=Integer.parseInt(soluong.getText().toString());
                int soluonghangmoi=soluonghangcu-1;
                soluong.setText(String.valueOf(soluonghangmoi));
            }
        });
    }
}