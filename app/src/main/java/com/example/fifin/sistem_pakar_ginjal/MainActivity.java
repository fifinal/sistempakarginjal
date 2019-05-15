package com.example.fifin.sistem_pakar_ginjal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HashMap<String, Double> densitas= new HashMap<>();
        densitas.put("p1",0.9);
        densitas.put("p1-p3",0.8);
        densitas.put("p1-p2",0.2);
        DemsterShafer Df=new DemsterShafer(densitas);
        Df.getDensitasBaru();

    }
}
