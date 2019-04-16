package com.example.androidadvanced201819.activities;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidadvanced201819.R;
import com.example.androidadvanced201819.adapter.NfcTechAdapter;

public class NfcActivity extends AppCompatActivity {

    public static final String EXTRA_NFC_TAG = "nfcTagId";

    public NfcAdapter mNfcAdapter;
    private IntentFilter[] intentFiltersArray;
    private String[][] techListsArray;
    private PendingIntent pendingIntent;
    private Tag tag;
    private ListView listView;
    private TextView tagId;
    private Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);

        tagId = findViewById(R.id.tagId);
        listView = findViewById(R.id.listViewTech);
        mNfcAdapter = NfcAdapter.getDefaultAdapter(NfcActivity.this);
        pendingIntent = PendingIntent.getActivity(this,0,new Intent(this,getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),0);
        confirmButton = (Button) findViewById(R.id.confirmNfc);

        if(!mNfcAdapter.isEnabled()){
            Toast.makeText(NfcActivity.this, "Attiva NFC", Toast.LENGTH_LONG).show();
        }

        IntentFilter nDef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try{
            nDef.addDataType("*/*");
        } catch (IntentFilter.MalformedMimeTypeException e){
            throw new RuntimeException("fail",e);
        }

        intentFiltersArray = new IntentFilter[]{nDef};
        
        techListsArray = new String [][]{
                new String[]{NfcF.class.getName()},
                new String[]{NfcA.class.getName()},
                new String[]{NfcB.class.getName()},
                new String[]{NfcV.class.getName()}
        };

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToCreate = new Intent();
                backToCreate.putExtra(EXTRA_NFC_TAG, tag.getId());
                setResult(CreateProfileActivity.REQUEST_NFC, backToCreate);
                finish();
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        tagId.setText(tag.getId().toString());
        NfcTechAdapter nfcTechAdapter= new NfcTechAdapter(this,tag.getTechList());
        listView.setAdapter(nfcTechAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mNfcAdapter.enableForegroundDispatch(this,pendingIntent,intentFiltersArray,techListsArray);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mNfcAdapter.disableForegroundDispatch(this);
    }
}
