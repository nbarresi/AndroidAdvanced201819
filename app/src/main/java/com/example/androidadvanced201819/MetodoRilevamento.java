package com.example.androidadvanced201819;

class MetodoRilevamento {
    private int id;
    private String tipo;
    private String valore;

    public MetodoRilevamento(int id, String tipo, String valore) {
        this.id = id;
        this.tipo = tipo;
        this.valore = valore;
    }

    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public String getValore() {
        return valore;
    }
}
