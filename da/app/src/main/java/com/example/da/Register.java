package com.example.da;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.da.dulieu.Contract;
import com.example.da.dulieu.DatabaseHelper;

public class Register extends AppCompatActivity {
    Button btnDKDangNhap,btnDKDangKy;
    EditText txtDKTDN,txtDKPass,txtDKRePass,txtDKTen;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dbHelper = new DatabaseHelper(this);

        btnDKDangNhap=findViewById(R.id.btnDKDangNhap);
        btnDKDangKy=findViewById(R.id.btnDKTaoTaiKhoan);

        txtDKTDN=(EditText) findViewById(R.id.txtDKTenDN);
        txtDKPass=(EditText)findViewById(R.id.txtDKPass);
        txtDKRePass=(EditText)findViewById(R.id.txtDKRePass);
        txtDKTen=(EditText)findViewById(R.id.txtDKTen);

        txtDKPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        txtDKRePass.setTransformationMethod(PasswordTransformationMethod.getInstance());

        btnDKDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtDKPass.getText().toString().equals(txtDKRePass.getText().toString()))
                {
                    addAccount(txtDKTen.getText().toString(),txtDKTDN.getText().toString(),txtDKPass.getText().toString());

                    Intent intent=new Intent(Register.this, Login.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(Register.this, "Mật khẩu nhập lại không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDKDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Register.this,Login.class);
                startActivity(intent);
            }
        });
    }
    private void addAccount(String username, String displayName, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Contract.Account.COLUMN_USERNAME, username);
        values.put(Contract.Account.COLUMN_DISPLAY_NAME, displayName);
        values.put(Contract.Account.COLUMN_PASSWORD, password);

        long id = db.insert(Contract.Account.TABLE_NAME, null, values);
        Toast.makeText(this, "Thêm mới tài khoản thành công", Toast.LENGTH_SHORT).show();
    }
}