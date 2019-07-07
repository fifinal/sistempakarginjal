package com.example.fifin.sistem_pakar_ginjal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class HasilActivity extends AppCompatActivity {

    public static final String KODE_GEJALA = "KODE_GEJALA";
    public static final String BOBOT = "BOBOT";
    private TextView tvPenyakit,tvAkurasi,tvSolusi;
    private Button btnTampil;
    private ListView lvHasil;
    private ImageView imgHome;
    private DemsterShaferTest demsterShafer;
    private ArrayList<HashMap<String, Double>> gejalaTerpilih;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference solusi=db.collection("solusi");
    private CollectionReference penyakit=db.collection("penyakit");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil);
        tvPenyakit=(TextView)findViewById(R.id.tv_penyakit);
        tvAkurasi=(TextView)findViewById(R.id.tv_akurasi);
        tvSolusi=(TextView)findViewById(R.id.tv_solusi);
        btnTampil=(Button) findViewById(R.id.btn_tampil);
        lvHasil=(ListView) findViewById(R.id.lv_hasil);
        imgHome=(ImageView) findViewById(R.id.img_home);
        demsterShafer = new DemsterShaferTest();

        hitungDf();
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(HasilActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });
        btnTampil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvHasil.setVisibility(View.VISIBLE);
                tampilkanPerhitungan();
            }

        });
    }
    private void hitungDf(){
        gejalaTerpilih = new ArrayList<>();
        String[] kodeGejala=getIntent().getStringArrayExtra(KODE_GEJALA);
        double[] bobot=getIntent().getDoubleArrayExtra(BOBOT);

        for(int i=0; i<kodeGejala.length;i++){
            HashMap<String,Double> densitas=new HashMap<>();
            densitas.put(kodeGejala[i], bobot[i]);
            gejalaTerpilih.add(densitas);
        }
        demsterShafer.run(gejalaTerpilih);

        String akurasi=demsterShafer.getHasil().get("akurasi");
        String idpenyakit=demsterShafer.getHasil().get("penyakit");
        Double persenAkurasi = (Double.parseDouble(akurasi)*100);

        tvAkurasi.setText(persenAkurasi.toString()+" %");
//        penyakit.document(idpenyakit).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                String namaPenyakit=documentSnapshot.getString("nama_penyakit");
//                tvPenyakit.setText(namaPenyakit);
//            }
//        });

    }
    private void tampilkanPerhitungan(){
        String items[] =new String[demsterShafer.storeKombinasi.size()];
        ArrayList<HashMap<String,Double>> densitasBaru=demsterShafer.getDensitas();
        int i=0;

        Toast.makeText(getApplicationContext(), String.valueOf(demsterShafer.storeKombinasi.size()),Toast.LENGTH_LONG).show();

        for (ArrayList<HashMap<String,Double>> d:demsterShafer.storeKombinasi){
            String iterasi="Kombinasi "+(i+1);
            for (HashMap<String,Double> kombinasi:d){
                String hasil="";
                for (String densitas:kombinasi.keySet()){
                    hasil+=densitas+" : "+kombinasi.get(densitas)+"\n";
                }
                iterasi=iterasi+"\n"+hasil;
            }
            iterasi=iterasi+"\nDensitas Baru";

            String hasilDensitas="";
            for (String densitas: densitasBaru.get(i).keySet()){
                hasilDensitas+=densitas+" : "+densitasBaru.get(i).get(densitas)+"\n";
            }
            iterasi=iterasi+"\n"+hasilDensitas;

            items[i]=iterasi;
            i++;
//            Toast.makeText(getApplicationContext(),items[0],Toast.LENGTH_LONG).show();
        }

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,items);
        lvHasil.setAdapter(adapter);
    }

}
