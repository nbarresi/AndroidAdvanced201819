package com.example.androidadvanced201819;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


public class AggiungiProfilo extends AppCompatActivity {
    DB db;
    private EditText nome_profilo;
    private Button conferma;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aggiungi_profilo);

        nome_profilo=findViewById(R.id.nome_profilo);
        conferma=findViewById(R.id.conferma);
        db=new DB(this);

        TextView app=findViewById(R.id.App);
        app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), ListaApplicazioni.class);
                startActivity(intent);
            }
        });

        RadioButton GPS=findViewById(R.id.GPS);
        GPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),GpsMaps.class);
                startActivity(intent);
            }
        });

        RadioButton WIFI=findViewById(R.id.WIFI);
        WIFI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ListaWifi.class);
                startActivity(intent);

            }
        });

        conferma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dato=nome_profilo.getText().toString();
                if (nome_profilo.length()!=0){
                    addData(dato);
                    nome_profilo.setText("");
                    Intent intent=new Intent(getApplicationContext(), ListaProfili.class);
                    startActivity(intent);

                }else{
                    toast(getString(R.string.Error_name_user));
                }
            }
        });

    }

    public void addData(String nuovoDato){
        boolean inserisci=db.addData(nuovoDato);

        if (inserisci){
            toast("Dato inserito");
        }else{
            toast("dato non inserito");
        }
    }
    private void toast(String toast){
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }
    private void permissionRequest(){

    }
}
