package com.example.fifin.sistem_pakar_ginjal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton btnDiagnosa, btnInfo,btnAplikasi,btnPakar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnDiagnosa=(ImageButton) findViewById(R.id.btn_diagnosa);
        btnInfo=(ImageButton) findViewById(R.id.btn_info);
        btnAplikasi=(ImageButton) findViewById(R.id.btn_aplikasi);
        btnPakar=(ImageButton) findViewById(R.id.btn_pakar);

        btnDiagnosa.setOnClickListener(this);
        btnInfo.setOnClickListener(this);
        btnAplikasi.setOnClickListener(this);
        btnPakar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.btn_diagnosa:
                intent=new Intent(MainActivity.this,DiagnosaActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_info:
                intent=new Intent(MainActivity.this,InfoActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_aplikasi:
                intent=new Intent(MainActivity.this,MyAppActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_pakar:
                intent=new Intent(MainActivity.this,PakarActivity.class);
                startActivity(intent);
                break;
        }
    }
}
