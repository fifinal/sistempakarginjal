package com.example.fifin.sistem_pakar_ginjal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class DiagnosaActivity extends AppCompatActivity {
    private ListView lvGejala;
    private Button btnDiagnosa;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference gejala=db.collection("gejala");
    private ArrayList<ModelDensitas> listGejala=new ArrayList<>();
    ArrayList<ModelDensitas> gejalaTerpilih=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosa);
        lvGejala=(ListView)findViewById(R.id.lv_gejala);
        btnDiagnosa=(Button) findViewById(R.id.btn_diagnosa);
        lvGejala.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        String items[] =new String[]{"list gejala 1","list gejala 2","list gejala 3","list gejala 4","list gejala 5","list gejala 6","list gejala 7","list gejala 8","list gejala 9","list gejala 10","list gejala 11","list gejala 12","list gejala 13","list gejala 14"};

        getGejala();
        lvGejala.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ModelDensitas densitas=listGejala.get(position);
                if(((CheckedTextView)view).isChecked()==true){
                    gejalaTerpilih.add(densitas);
                }else{
                    for(int i=0;i<gejalaTerpilih.size();i++){
                        if(gejalaTerpilih.get(i).getPosition()==position){
                            gejalaTerpilih.remove(i);
                        }
                    }
                }

                Toast.makeText(getApplicationContext(),gejalaTerpilih.size(),Toast.LENGTH_SHORT).show();
            }
        });
        btnDiagnosa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gejalaTerpilih.size() <= 1) {
                    Toast.makeText(getApplicationContext(), "Pili Minimal @ gejala", Toast.LENGTH_LONG).show();
                } else {


//                HashMap<String, Double> densitas= new HashMap<>();
                    ArrayList<HashMap<String, Double>> p = new ArrayList<>();
//                densitas.put("p1",0.7);
//                p.add(densitas);
//                densitas= new HashMap<>();
//                densitas.put("p1-p3",0.4);
//                p.add(densitas);
//                densitas= new HashMap<>();
//                densitas.put("p1-p2-p3",0.8);
//                p.add(densitas);
                    for (ModelDensitas m : gejalaTerpilih) {
                        p.add(m.getDensitas());
                    }
                    DemsterShaferTest Df = new DemsterShaferTest(p);
//        String h=Df.getHasil().get("akurasi");
//        for(String i:Df.coba.keySet()){
//            hasil+=i+(" = "+Df.coba.get(i).toString());
//        }
                    String[] hasil2 = new String[Df.densitas.size()];
                    int i = 0;
                    for (HashMap<String, Double> d : Df.densitas) {

                        String hasil = "";
                        for (String k : d.keySet()) {

                            hasil += k + " : " + d.get(k).toString() + "\n";
                        }
                        hasil2[i] = hasil;
                        i++;

                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.row_checkbox, R.id.cb_tv_gejala, hasil2);
                    adapter.setDropDownViewResource(R.layout.row_checkbox);

                    lvGejala.setAdapter(adapter);
                    Intent intent = new Intent(DiagnosaActivity.this, HasilActivity.class);
                    intent.putExtra(HasilActivity.KODE_GEJALA, new String[]{});
                    intent.putExtra(HasilActivity.BOBOT, new Double[]{});
                    startActivity(intent);
                }
            }
        });
    }
    private void getGejala(){
        gejala.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String items[] =new String[queryDocumentSnapshots.size()];
                int i=0;
                for (DocumentSnapshot doc:queryDocumentSnapshots){

                    HashMap<String,Double> densitas=new HashMap<>();
                    densitas.put(doc.getString("penyakit"),doc.getDouble("bobot"));
                    ModelDensitas modelDensitas=new ModelDensitas(i,densitas);
                    listGejala.add(modelDensitas);

                    items[i]=doc.getString("gejala");
                    i++;
                }

                ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.row_checkbox,R.id.cb_tv_gejala,items);
                adapter.setDropDownViewResource(R.layout.row_checkbox);

                lvGejala.setAdapter(adapter);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
