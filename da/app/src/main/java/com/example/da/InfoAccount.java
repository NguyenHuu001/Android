package com.example.da;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.da.dulieu.Contract;
import com.example.da.dulieu.DatabaseHelper;

public class InfoAccount extends AppCompatActivity {
    TextView txtUsser,txtEmail;
    Button btnDoiMK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_account);

        txtUsser=findViewById(R.id.textViewUsername);
        txtEmail=findViewById(R.id.textViewEmail);
        btnDoiMK=findViewById(R.id.btnDoiMK);
        Intent intent=getIntent();
        String username=intent.getStringExtra("UserName");
        String[] accountInfo=getLoginAccountInfo(username);

        String loginUsername = accountInfo[0];
        String Email = accountInfo[1];

        txtUsser.setText("Tài khoản: "+loginUsername);
        txtEmail.setText("Email đăng ký: "+Email);
        btnDoiMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(InfoAccount.this,ChangePassword.class);
                intent1.putExtra("UserName",username);
                startActivity(intent1);
            }
        });
    }

    public String[] getLoginAccountInfo(String username) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] projection = {
                Contract.Account.COLUMN_USERNAME,
                Contract.Account.COLUMN_DISPLAY_NAME
        };
        String selection = Contract.Account.COLUMN_USERNAME + " = ?";
        String[] selectionArgs = { username };

        Cursor cursor = db.query(
                Contract.Account.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        String[] accountInfo = new String[2]; // Mảng chứa thông tin tài khoản
        if (cursor.moveToFirst()) {
            accountInfo[0] = cursor.getString(cursor.getColumnIndexOrThrow(Contract.Account.COLUMN_USERNAME));
            accountInfo[1] = cursor.getString(cursor.getColumnIndexOrThrow(Contract.Account.COLUMN_DISPLAY_NAME));
        }

        cursor.close();
        return accountInfo;
    }
}