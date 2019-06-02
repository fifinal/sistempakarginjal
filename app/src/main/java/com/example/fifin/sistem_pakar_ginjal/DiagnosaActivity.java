package com.example.fifin.sistem_pakar_ginjal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DiagnosaActivity extends AppCompatActivity {
    private ListView lvGejala;
    private Button btnDiagnosa;
    private ArrayList<String> selectedItems=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosa);
        lvGejala=(ListView)findViewById(R.id.lv_gejala);
        lvGejala.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        String[] items ={"gejala 1","gejala 2"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.row_checkbox,R.id.cb_tv_gejala,items);
        lvGejala.setAdapter(adapter);

        lvGejala.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getApplicationContext(),((TextView)view).getText().toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnDiagnosa=findViewById(R.id.btn_diagnosa);
        btnDiagnosa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
