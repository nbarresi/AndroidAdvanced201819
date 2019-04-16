package com.example.androidadvanced201819;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class NFC extends AppCompatActivity {

    private PendingIntent pendingIntent;
    private IntentFilter ndef;
    private IntentFilter[] intentFiltersArray;
    private String[][] techListsArray;
    private boolean shouldHandleNfcs;

    private NfcAdapter adapter;
    private int[] msgs;

    private TextView nfcId;
    private TextView nfca;
    private TextView nfcUltraLight;
    private TextView nfcNdef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nfc);

        nfcId=findViewById(R.id.nfcId);
        nfca=findViewById(R.id.nfca);
        nfcUltraLight=findViewById(R.id.nfcUltraLight);
        nfcNdef=findViewById(R.id.nfcNdef);
        pendingIntent= PendingIntent.getActivity(this, 0,
                new Intent(this, getClass())
                        .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        ndef= new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);

        try{
            ndef.addDataType("*/*");
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("fail", e);
        }

        intentFiltersArray = new IntentFilter[]{ndef,};

        techListsArray = new String[][]{
                new String[]{NfcF.class.getName()},
                new String[]{NfcA.class.getName()},
                new String[]{NfcB.class.getName()},
                new String[]{NfcV.class.getName()}};

        adapter = NfcAdapter.getDefaultAdapter(this);

        if (adapter == null) {
            Toast.makeText(this, "NFC not supported", Toast.LENGTH_LONG).show();
            finish();
        }

        if (adapter!=null) {
            Toast.makeText(this, "Enable NFC before using the app",
                    Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(shouldHandleNfcs)
            adapter.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techListsArray);
    }

    @Override
    protected void onPause() {
        super.onPause();
       if (!adapter.isEnabled()){
           adapter.disableForegroundDispatch(this);
       }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        Tag tag= intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        MifareUltralight uTag = MifareUltralight.get(tag);
        try {
            uTag.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            uTag.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
