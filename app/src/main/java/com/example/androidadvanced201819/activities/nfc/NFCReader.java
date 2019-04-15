package com.example.androidadvanced201819.activities.nfc;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidadvanced201819.R;
import com.example.androidadvanced201819.activities.adapter.NFCAdapterActivity;
import com.example.androidadvanced201819.model.NFC;

import java.util.Arrays;

public class NFCReader extends AppCompatActivity {

    NfcAdapter adapter;
    private String[][] techListsArray;
    private IntentFilter[] intentFiltersArray;
    private PendingIntent pendingIntent;
    private TextView tagId;
    private NFCAdapterActivity nfcAdapterActivity;
    private Button nfcButton;
    private String nfcId;
    final protected static char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfcreader);

        // Check for available NFC Adapter
        adapter = NfcAdapter.getDefaultAdapter(this);
        if (adapter == null) {
            Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        nfcAdapterActivity = new NFCAdapterActivity(getApplicationContext());
        ListView listView = (ListView) findViewById(R.id.techSupported);
        listView.setAdapter(nfcAdapterActivity);

        pendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            ndef.addDataType("*/*");    /* Handles all MIME based dispatches.
                                       You should specify only the ones that you need. */
        }
        catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("fail", e);
        }
        intentFiltersArray = new IntentFilter[] {ndef, };
        techListsArray = new String[][]{
                new String[]{NfcF.class.getName()},
                new String[]{NfcA.class.getName()},
                new String[]{NfcB.class.getName()},
                new String[]{NfcV.class.getName()}
        };

        nfcButton = (Button) findViewById(R.id.setNFCButton);
        nfcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NFC nfc = new NFC();
                nfc.setNfcId(nfcId);
                Intent resultIntent = new Intent(getApplicationContext(), NFCReader.class);
                resultIntent.putExtra("nfcId", nfc.getNfcId());
                setResult(1, resultIntent);
                finish();
            }
        });
    }

    public void onPause() {
        super.onPause();
        adapter.disableForegroundDispatch(this);
    }

    public void onResume() {
        super.onResume();
        adapter.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techListsArray);
    }

    public void onNewIntent(Intent intent) {
        Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        //do something with tagFromIntent

        tagId = findViewById(R.id.tagId);
        nfcId = bytesToHex(tagFromIntent.getId());
        tagId.setText(nfcId);

        nfcAdapterActivity.updateList(Arrays.asList(tagFromIntent.getTechList()));
        LinearLayout nfcLayout = (LinearLayout) findViewById(R.id.NFCActivityLayout);
        nfcLayout.setVisibility(View.VISIBLE);
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for ( int j = 0; j < bytes.length; j++ ) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
