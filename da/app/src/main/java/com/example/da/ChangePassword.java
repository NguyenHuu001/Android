package com.example.da;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.da.dulieu.Contract;
import com.example.da.dulieu.DatabaseHelper;

public class ChangePassword extends AppCompatActivity {

    TextView txtTenDN;
    EditText txtMKCu, txtMKMoi, txtNhapLaiMKMoi;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        txtTenDN=findViewById(R.id.tvTenDN);
        txtMKCu=findViewById(R.id.editTextOldPassword);
        txtMKMoi=findViewById(R.id.editTextNewPassword);
        txtNhapLaiMKMoi=findViewById(R.id.editTextConfirmPassword);
        btnSave=findViewById(R.id.buttonSave);



        Intent intent=getIntent();
        String tenDN=intent.getStringExtra("UserName");
        txtTenDN.setText("Tài khoản: "+tenDN);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtCu=txtMKCu.getText().toString();
                if(txtMKMoi.getText().toString().equals(txtNhapLaiMKMoi.getText().toString()))
                {
                    DatabaseHelper dbHelper = new DatabaseHelper(ChangePassword.this);

                    boolean result = dbHelper.checkLogin(tenDN,txtCu);

                    if (result) {
                        updatePassword(tenDN,txtNhapLaiMKMoi.getText().toString());
                        Intent intent1=new Intent(ChangePassword.this,Login.class);
                        startActivity(intent1);

                    } else {
                        Toast.makeText(ChangePassword.this, "Kiểm tra lại mật khẩu cữ", Toast.LENGTH_SHORT).show();

                    }
                }
                else
                {
                    Toast.makeText(ChangePassword.this, "Mật khẩu nhập lại không đúng", Toast.LENGTH_SHORT).show();
                }



            }
        });

    }
    public void updatePassword(String username, String newPassword) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Contract.Account.COLUMN_PASSWORD, newPassword);

        String selection = Contract.Account.COLUMN_USERNAME + " = ?";
        String[] selectionArgs = { username };

        db.update(Contract.Account.TABLE_NAME, values, selection, selectionArgs);
    }
}