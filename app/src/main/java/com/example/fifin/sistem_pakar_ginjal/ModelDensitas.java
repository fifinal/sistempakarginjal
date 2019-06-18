package com.example.fifin.sistem_pakar_ginjal;

import java.util.HashMap;

public class ModelDensitas {
    private int position;
    private HashMap<String ,Double> densitas;

    public ModelDensitas(int position, HashMap<String, Double> densitas) {
        this.position = position;
        this.densitas = densitas;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public HashMap<String, Double> getDensitas() {
        return densitas;
    }

    public void setDensitas(HashMap<String, Double> densitas) {
        this.densitas = densitas;
    }
}
