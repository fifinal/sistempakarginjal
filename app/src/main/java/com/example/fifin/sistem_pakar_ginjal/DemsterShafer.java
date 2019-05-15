package com.example.fifin.sistem_pakar_ginjal;
import java.util.ArrayList;
import java.util.HashMap;

public class DemsterShafer {
    private ArrayList<HashMap<String,Double>> eviden;
    private HashMap<String,Double> densitasBaru;
    private Double kosong;
    private int iterasi;

    public DemsterShafer(HashMap<String,Double> densitas) {
        this.densitasBaru=new HashMap<>();
        this.eviden =new ArrayList<>();
        this.iterasi=1;

        for (String i:densitas.keySet()){
            HashMap<String,Double> m=new HashMap<>();
            m.put(i,densitas.get(i));
            m.put("kosong",this.p(densitas.get(i)));
            eviden.add(m);
        }
        this.hitungDuaDensitas(eviden.get(iterasi-1),eviden.get(iterasi));
    }

    private void hitungDuaDensitas(HashMap<String,Double> m1,HashMap<String,Double>m2){
        this.densitasBaru.clear();
        for(String keyM1:m1.keySet()){
            for (String keyM2:m2.keySet()){
                cekKombinasi(keyM1,m1.get(keyM1),keyM2,m2.get(keyM2));
            }
        }
        this.hitungDentitasBaru();
        this.iterasi++;
        if(this.iterasi<this.eviden.size()) this.hitungDuaDensitas(this.densitasBaru,eviden.get(iterasi));
    }

    private void hitungDentitasBaru(){
        for(String i:this.densitasBaru.keySet()){
           this.densitasBaru.put(i,this.densitasBaru.get(i)/this.p(this.kosong));
        }
    }

    public HashMap<String,Double> getDensitasBaru(){ return this.densitasBaru; }

    private void cekKombinasi(String key1, Double nilai1, String key2, Double nilai2) {

        String[] k1=key1.split("-");
        String[] k2=key2.split("-");
        String keyBaru="";

        if(k1.length==0&&k2.length==0) keyBaru="kosong";
        else if(k1.length==0) keyBaru=key2;
        else if(k2.length==0) keyBaru=key1;
        else {
            for (String i : k1) {
                for (String j : k2) {
                    if (i.equals(j)) keyBaru += i;
                }
            }
            if (keyBaru.equals("")) keyBaru="kosong";
        }

        Double nilaiBaru=nilai1*nilai2;
        // menentukan nilai kosong ???
        if (this.densitasBaru.containsKey(keyBaru)) this.densitasBaru.put(keyBaru, this.densitasBaru.get(keyBaru) + nilaiBaru);
        else this.densitasBaru.put(keyBaru, nilaiBaru);
    }

    private Double p(Double m){ return (1-m); }
}
