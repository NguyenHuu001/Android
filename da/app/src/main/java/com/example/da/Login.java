package com.example.da;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.da.dulieu.DatabaseHelper;

public class Login extends AppCompatActivity {
    Button btnDangnhap,btnDangKy;
    EditText txtPass,txtTenDN;
    CheckBox chckShow;
    private DatabaseHelper dbHelper;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbHelper = new DatabaseHelper(this);

        btnDangnhap=findViewById(R.id.btndangnhap);
        txtPass=findViewById(R.id.txtPass);
        txtTenDN=findViewById(R.id.txtTenDN);

        btnDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tendn=txtTenDN.getText().toString();
                String mk=txtPass.getText().toString();
                DatabaseHelper dbHelper = new DatabaseHelper(Login.this);

                boolean result = dbHelper.checkLogin(tendn,mk);

                if (result) {
                    if (tendn.equals("Admin") && mk.equals("123")) {
                        Intent intent = new Intent(Login.this, Admin.class);
                        startActivity(intent);
                    }
                    else {
                        Intent intent=new Intent(Login.this,MainActivity.class);
                        intent.putExtra("UserName",tendn);
                        startActivity(intent);
                    }

                } else {
                    Toast.makeText(Login.this, "Tên đăng nhập hoặc mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
                }
                
            }
        });
        chckShow=findViewById(R.id.chckShow);
        

        chckShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b)
                {
                    txtPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else
                {
                    txtPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        btnDangKy=findViewById(R.id.btnDangKy);
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });


    }


}