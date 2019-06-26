package com.example.fifin.sistem_pakar_ginjal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class HasilActivity extends AppCompatActivity {

    public static final String KODE_GEJALA = "KODE_GEJALA";
    public static final String BOBOT = "BOBOT";
    private TextView tvPenyakit,tvAkurasi,tvSolusi;
    private Button btnTampil;
    private ListView lvHasil;
    private DemsterShaferTest demsterShafer;
    private ArrayList<HashMap<String, Double>> gejalaTerpilih;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil);
        tvPenyakit=(TextView)findViewById(R.id.tv_penyakit);
        tvAkurasi=(TextView)findViewById(R.id.tv_akurasi);
        tvSolusi=(TextView)findViewById(R.id.tv_solusi);
        btnTampil=(Button) findViewById(R.id.btn_tampil);
        lvHasil=(ListView) findViewById(R.id.lv_hasil);

        hitungDf();
        btnTampil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
    private void hitungDf(){
        gejalaTerpilih = new ArrayList<>();
        String[] kodeGejala=getIntent().getStringArrayExtra(KODE_GEJALA);
        double[] bobot=getIntent().getDoubleArrayExtra(BOBOT);

        for(int i=0; i<kodeGejala.length;i++){

            HashMap<String,Double> densitas=new HashMap<>();
            densitas.put(kodeGejala[i],bobot[i]);
            gejalaTerpilih.add(densitas);
        }

        demsterShafer = new DemsterShaferTest(gejalaTerpilih);
    }

}
