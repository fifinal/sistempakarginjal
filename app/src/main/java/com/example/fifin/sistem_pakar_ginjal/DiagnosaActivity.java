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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DiagnosaActivity extends AppCompatActivity {
    private ListView lvGejala;
    private TextView tvLoad;
    private Button btnDiagnosa;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference gejala=db.collection("gejala");
    private ArrayList<ModelDensitas> listGejala=new ArrayList<>();
    ArrayList<ModelDensitas> gejalaTerpilih=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosa);
        tvLoad=(TextView) findViewById(R.id.tv_load);
        lvGejala=(ListView)findViewById(R.id.lv_gejala);
        btnDiagnosa=(Button) findViewById(R.id.btn_diagnosa);
        lvGejala.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

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
                            break;
                        }
                    }
                }
//        int size=gejalaTerpilih.size();
//                Toast.makeText(getApplicationContext(),String.valueOf(size)+gejalaTerpilih.get(position).getBobot().toString(),Toast.LENGTH_SHORT).show();
            }
        });

        btnDiagnosa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gejalaTerpilih.size() <= 1) {
                    Toast.makeText(getApplicationContext(), "Pilih Minimal 2 gejala", Toast.LENGTH_LONG).show();
                } else {
                    int panjangGejalaTerpilih=gejalaTerpilih.size();
                    String[] penyakit=new String[panjangGejalaTerpilih];
                    double[] bobot=new double[panjangGejalaTerpilih];

                    for (int i=0;i<panjangGejalaTerpilih; i++) {
                       penyakit[i]=gejalaTerpilih.get(i).getPenyakit();
                       bobot[i]=gejalaTerpilih.get(i).getBobot();
                    }

                    Intent intent = new Intent(DiagnosaActivity.this, HasilActivity.class);
                    intent.putExtra(HasilActivity.KODE_GEJALA,penyakit);
                    intent.putExtra(HasilActivity.BOBOT, bobot);
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
                for (QueryDocumentSnapshot doc:queryDocumentSnapshots){
//                    List<String> a=doc.get("penyakit");
//                    ModelDensitas modelDensitas=doc.toObject(ModelDensitas.class);
                    ModelDensitas modelDensitas=new ModelDensitas(i,doc.getString("penyakit"),doc.getDouble("bobot"));
                    listGejala.add(modelDensitas);

                    items[i]=doc.getString("gejala");
                    i++;
                }
                tvLoad.setVisibility(View.GONE);
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.row_checkbox,R.id.cb_tv_gejala,items);
//                adapter.setDropDownViewResource(R.layout.row_checkbox);
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
