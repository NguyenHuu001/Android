package com.example.da;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DaTT extends AppCompatActivity {
    Button btn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_da_tt);
        Intent intent=getIntent();
        String username=intent.getStringExtra("UserName");
        btn = findViewById(R.id.btn_vetrangtru);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentgh1 = new Intent(DaTT.this, MainActivity.class);
                intentgh1.putExtra("UserName",username);
                startActivity(intentgh1);
            }
        });
    }
}