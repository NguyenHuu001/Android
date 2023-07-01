package com.example.da;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.da.dulieu.AdminAdapter;
import com.example.da.dulieu.DatabaseHelper;
import com.example.da.dulieu.HinhAnh;
import com.example.da.dulieu.hinhAnhAdapter;

import java.util.ArrayList;

public class producAdmin extends AppCompatActivity {
    ImageView pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produc_admin);

        pic=findViewById(R.id.picture1);
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });
    }


}