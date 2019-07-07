package com.example.fifin.sistem_pakar_ginjal;
import java.util.ArrayList;
import java.util.HashMap;

public class DemsterShafer {
    public ArrayList<HashMap<String,Double>> eviden;
    public ArrayList<HashMap<String,Double>> densitas;
    public ArrayList<HashMap<String,Double>> kombinasi;
    public ArrayList<ArrayList<HashMap<String,Double>>> storeKombinasi;
    public HashMap<String,Double> coba;        // store hasil dari densitas1 dan densitas2
    private HashMap<String,Double> densitasBaru;        // store hasil dari densitas1 dan densitas2
    private Double kosong;                             // digunakan jika tidak ada eviden konflik
    private int iterasi;                              // pengulangan untuk rekursif sebanyak panjang dr eviden
    private Double evidenKonflik;                    // jika ada eviden konflik

    public DemsterShafer(ArrayList<HashMap<String,Double>> densitas) {
        this.coba=new HashMap<>();  // initialisasi awal
        // initialisasi awal
        this.eviden =densitas;    // initialisasi awal
        this.densitas =new ArrayList<>();    // initialisasi awal
        this.kombinasi =new ArrayList<>();    // initialisasi awal
        this.storeKombinasi =new ArrayList<>();    // initialisasi awal
        this.iterasi=1;                   // initialisasi awal
//
//        for (String i:densitas.keySet()){
//            HashMap<String,Double> m=new HashMap<>();   //
//            m.put(i,densitas.get(i));                  // memasukkan semua densitas ke dalam arraylist eviden
//            this.eviden.add(m);                       //
//        }
        //menghitung dua densitas dengan rekursif
        this.hitungDuaDensitas(this.eviden.get(this.iterasi-1),this.eviden.get(this.iterasi));
    }

    // method menghitung dua densitas
    private void hitungDuaDensitas(HashMap<String,Double> m1,HashMap<String,Double> m2){
        this.clean();                  // membersihkan densitsbaru, mengembalikan evidenkonflik dan kosong menjadi nol
        // menambahkan himpunan kosong dan plausability
        // menambahkan himpunan kosong dan plausability
//        this.densitas.add(m1);
//        this.densitas.add(m2);
//        if(!m1.containsKey("kosong")){
        m1.put("kosong",this.p(m1));
//        } if(!m2.containsKey("kosong")){
        m2.put("kosong",this.p(m2));
//        }
        for(String keyM1:m1.keySet()){
            for (String keyM2:m2.keySet()){
                // cek kombinasi dgn param key1,value1,key2,value2
                this.cekKombinasi(keyM1,m1.get(keyM1),keyM2,m2.get(keyM2));
            }
        }
        this.storeKombinasi.add(this.kombinasi);
        HashMap<String,Double>d=this.hitungDentitasBaru(); // hitung densitas baru
        this.iterasi++;
        // menghitung dua densitas dgn densitasbaru tiap rekursif, jika iterasi kurang dari panjang eviden
//        if(this.iterasi<this.eviden.size()) this.hitungDuaDensitas(d,this.eviden.get(this.iterasi));
//        else {this.densitas.add(d);}
    }

    // method menghitung densitas baru
    private HashMap<String, Double> hitungDentitasBaru(){
        for(String i:this.densitasBaru.keySet()){
            // simpan pada atribute densitasBaru
            // Rumus menghasilkan densitasBaru = jumlah densitas / evidenKonflik || himpunan kosong

            this.coba.put(i,this.densitasBaru.get(i));
//            HashMap<String,Double> a=new HashMap<>();
//            a.put(i,this.densitasBaru.get(i)/(1-((this.evidenKonflik>0.0)?this.evidenKonflik:this.kosong)));
//            this.densitas.add(a);
            this.densitasBaru.put(i,this.densitasBaru.get(i)/(1-((this.evidenKonflik>0.0)?this.evidenKonflik:this.kosong)));
        }
        this.densitas.add(this.densitasBaru);

        return this.densitasBaru;
    }

    // method menghasilkan kombinasi tiap densitas
    private void cekKombinasi(String key1, Double nilai1, String key2, Double nilai2) {
        String keyBaru="";
        Double nilaiBaru=nilai1*nilai2;

        if(key1.equals("kosong")&&key2.equals("kosong")) { //jika keduanya himpunan kosong
            keyBaru="kosong";
            this.kosong=nilaiBaru;
        }
        else if(key1.equals("kosong")) keyBaru=key2; // jika key1 himpunan kosong keyBaru berisi key2
        else if(key2.equals("kosong")) keyBaru=key1; // jika key2 himpunan kosong keyBaru berisi key1
        else {                                       // jika key1 dan key2 bkn himpunan kosong
            for (String i : key1.split("-")) {
                for (String j : key2.split("-")) {
                    if (i.equals(j)) keyBaru += i+"-"; // mendapatkan himpunan anggota dari key1 dan key2
                }
            }
            if (keyBaru.equals("")) this.evidenKonflik+=nilaiBaru; //jika keyBaru tidak ada anggota maka ada evidenkonflik
            keyBaru=keyBaru.substring(0,keyBaru.length()-1); // menghilangkan tanda "-" pada akhir string
        }
//        HashMap<String,Double> a=new HashMap<>();
//        a.put(keyBaru,nilaiBaru);
//        this.densitas.add(a);
        HashMap<String,Double> hasilKombinasi=new HashMap<>();
        hasilKombinasi.put(key1,nilai1);
        hasilKombinasi.put(key2,nilai2);
        hasilKombinasi.put(keyBaru,nilaiBaru);
        this.kombinasi.clear();
        this.kombinasi.add(hasilKombinasi);

        // jika keyBaru sudah ada pada densitsBaru, tambah nilaiBaru dgn nilai dr keyBaru sebelumnya (seanggota himpunan)
        if (this.densitasBaru.containsKey(keyBaru)) this.densitasBaru.put(keyBaru, (this.densitasBaru.get(keyBaru) + nilaiBaru));
//        else this.densitasBaru.put(keyBaru, nilaiBaru);
            // jika keyBaru ada anggota himpunan
        else if(!keyBaru.equals("")) {

            this.densitasBaru.put(keyBaru, nilaiBaru);
        }

    }

    // method mendaptkan densitsBaru
    public ArrayList<HashMap<String,Double>> getDensitas(){ return this.densitas; }

    public HashMap<String,String> getHasil(){
        HashMap<String,String> hasilAkhir=new HashMap<>();
        for (String hasil:this.densitasBaru.keySet()){
            int h=hasil.split("-").length;
            if(h==1&&!hasil.equals("kosong")){
                hasilAkhir.put("penyakit",hasil);
                hasilAkhir.put("akurasi",this.densitasBaru.get(hasil).toString());
                break;
            }
        }
        return hasilAkhir;
    }

    // method menghasilkan nilai plausabily
    private Double p(HashMap<String,Double> m){
        Double pBaru=0.0;
        for(String i:m.keySet()){
            if(!i.equals("kosong")) pBaru+=m.get(i); //tmbah semua plausability yg ditemukan
        }
        return (1-pBaru);  //rumus plausability
    }

    public ArrayList<ArrayList<HashMap<String, Double>>> getKombinasi() {
        return this.storeKombinasi;
    }

    // method untuk membersihkan atribut dan mengembalikan ke nilai awal
    private void clean(){
        this.densitasBaru=new HashMap<>();
        this.coba.clear();
        this.evidenKonflik=this.kosong=0.0;
    }
      
}
