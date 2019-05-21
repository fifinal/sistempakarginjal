package com.example.fifin.sistem_pakar_ginjal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView txtJudul=(TextView)findViewById(R.id.txt_judul);
        TextView txtJudul2=(TextView)findViewById(R.id.txt_judul2);
        HashMap<String, Double> densitas= new HashMap<>();

        densitas.put("p1-p2-p3",0.8);
        densitas.put("p1",0.7);
        densitas.put("p1-p3",0.4);
        DemsterShaferTest Df=new DemsterShaferTest(densitas);
        String h=Df.getHasil().get("akurasi");
        String hasil="";
        String hasil2="";
        for(String i:Df.coba.keySet()){
            hasil+=i+(" = "+Df.coba.get(i).toString());
        }
        for(String i:Df.getDensitasBaru().keySet()){

            hasil2+=i+" : "+Df.getDensitasBaru().get(i).toString();
        }
        txtJudul.setText(hasil);
        txtJudul2.setText(hasil2);

    }
}
