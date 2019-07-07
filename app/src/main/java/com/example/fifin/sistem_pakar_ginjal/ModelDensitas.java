package com.example.fifin.sistem_pakar_ginjal;

import android.app.UiAutomation;

import java.util.HashMap;

public class ModelDensitas {
    private int position;
    private String penyakit;
    private Double bobot;

    public ModelDensitas(int position, String penyakit, Double bobot) {
        this.position = position;
        this.penyakit = penyakit;
        this.bobot = bobot;
    }

    public String getPenyakit() {
        return penyakit;
    }

    public void setPenyakit(String penyakit) {
        this.penyakit = penyakit;
    }

    public Double getBobot() {
        return bobot;
    }

    public void setBobot(Double bobot) {
        this.bobot = bobot;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

}
