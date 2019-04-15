package org.its.login.activities;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.androidadvanced201819.R;
import org.its.login.adapters.NFCListRecyclerAdapter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NFCActivity extends AppCompatActivity {

    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private IntentFilter[] intentFiltersArray;
    private String[][] techListsArray;
    private boolean shouldHandleNfcs = false;
    private Button btnDisplay;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private NFCListRecyclerAdapter nfcListRecyclerAdapter;
    private Tag tag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
        recyclerView = (RecyclerView) findViewById(R.id.rv_nfc);
        recyclerView.setHasFixedSize(true);
        setImpostaNFCButtonListener();
        setRecyclerViewLayoutManager();

        nfcAdapter = NfcAdapter.getDefaultAdapter(getApplicationContext());
        if (nfcAdapter != null){
            if (nfcAdapter.isEnabled())
                shouldHandleNfcs = true;
            startNFCPedingIntent();
        } else{
            mockTag();
            nfcNotSupported();
        }
    }

    private void nfcNotSupported() {
        new android.support.v7.app.AlertDialog.Builder(this)
                .setTitle("NFC NON PRESENTE")
                .setMessage("Impossibile trovare la tecnologia nfc, probabilmente il tuo dispositivo non è compatibile")

                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent returnNFCIntent = new Intent();
                        setResult(RESULT_CANCELED, returnNFCIntent);
                        finish();

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();


    }

    public void startNFCPedingIntent() {
        pendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass())
                        .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter ndef = new IntentFilter(nfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            ndef.addDataType("*/*");
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("fail", e);
        }

        intentFiltersArray = new IntentFilter[]{ndef,};
        techListsArray = new String[][]{
                new String[]{NfcF.class.getName()},
                new String[]{NfcA.class.getName()},
                new String[]{NfcB.class.getName()},
                new String[]{NfcV.class.getName()}
        };

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        setTagId(tag.getId().toString());
        setRecyclerAdapter(tag.getTechList());
    }

    private void mockTag() {
        //mock
        Class tagClass = Tag.class;
        try {
            Method createMockTagMethod = tagClass.getMethod("createMockTag", byte[].class, int[].class, Bundle[].class);
            final int TECH_NFC_A = 1;
            final String EXTRA_NFC_A_SAK = "sak";    // short (SAK byte value)
            final String EXTRA_NFC_A_ATQA = "atqa";  // byte[2] (ATQA value)

            final int TECH_NFC_B = 2;
            final String EXTRA_NFC_B_APPDATA = "appdata";    // byte[] (Application Data bytes from ATQB/SENSB_RES)
            final String EXTRA_NFC_B_PROTINFO = "protinfo";  // byte[] (Protocol Info bytes from ATQB/SENSB_RES)

            final int TECH_ISO_DEP = 3;
            final String EXTRA_ISO_DEP_HI_LAYER_RESP = "hiresp";  // byte[] (null for NfcA)
            final String EXTRA_ISO_DEP_HIST_BYTES = "histbytes";  // byte[] (null for NfcB)

            final int TECH_NFC_F = 4;
            final String EXTRA_NFC_F_SC = "systemcode";  // byte[] (system code)
            final String EXTRA_NFC_F_PMM = "pmm";        // byte[] (manufacturer bytes)

            final int TECH_NFC_V = 5;
            final String EXTRA_NFC_V_RESP_FLAGS = "respflags";  // byte (Response Flag)
            final String EXTRA_NFC_V_DSFID = "dsfid";           // byte (DSF ID)

            final int TECH_NDEF = 6;
            final String EXTRA_NDEF_MSG = "ndefmsg";              // NdefMessage (Parcelable)
            final String EXTRA_NDEF_MAXLENGTH = "ndefmaxlength";  // int (result for getMaxSize())
            final String EXTRA_NDEF_CARDSTATE = "ndefcardstate";  // int (1: read-only, 2: read/write, 3: unknown)
            final String EXTRA_NDEF_TYPE = "ndeftype";            // int (1: T1T, 2: T2T, 3: T3T, 4: T4T, 101: MF Classic, 102: ICODE)

            final int TECH_NDEF_FORMATABLE = 7;

            final int TECH_MIFARE_CLASSIC = 8;

            final int TECH_MIFARE_ULTRALIGHT = 9;
            final String EXTRA_MIFARE_ULTRALIGHT_IS_UL_C = "isulc";  // boolean (true: Ultralight C)

            final int TECH_NFC_BARCODE = 10;
            final String EXTRA_NFC_BARCODE_BARCODE_TYPE = "barcodetype";  // int (1: Kovio/ThinFilm)

            Bundle nfcaBundle = new Bundle();
            nfcaBundle.putByteArray(EXTRA_NFC_A_ATQA, new byte[]{ (byte)0x44, (byte)0x00 }); //ATQA for Type 2 tag
            nfcaBundle.putShort(EXTRA_NFC_A_SAK , (short)0x00); //SAK for Type 2 tag

            Bundle ndefBundle = new Bundle();
            ndefBundle.putInt(EXTRA_NDEF_MAXLENGTH, 48); // maximum message length: 48 bytes
            ndefBundle.putInt(EXTRA_NDEF_CARDSTATE, 1); // read-only
            ndefBundle.putInt(EXTRA_NDEF_TYPE, 2); // Type 2 tag

            NdefMessage myNdefMessage =  null; // create an NDEF message
            ndefBundle.putParcelable(EXTRA_NDEF_MSG, myNdefMessage);  // add an NDEF message

            byte[] tagId = new byte[] { (byte)0x3F, (byte)0x12, (byte)0x34, (byte)0x56, (byte)0x78, (byte)0x90, (byte)0xAB };

            Tag mockTag = (Tag)createMockTagMethod.invoke(null,
                    tagId,                                     // tag UID/anti-collision identifier (see Tag.getId() method)
                    new int[] { TECH_NFC_A, TECH_NDEF },       // tech-list
                    new Bundle[] { nfcaBundle, ndefBundle });  // array of tech-extra bundles, each entry maps to an entry in the tech-list

            setTagId(mockTag.getId().toString());
           setRecyclerAdapter(mockTag.getTechList());

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (shouldHandleNfcs)
            nfcAdapter.enableForegroundDispatch(this, pendingIntent,
                    intentFiltersArray, techListsArray);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (shouldHandleNfcs)
            nfcAdapter.disableForegroundDispatch(this);
    }


    private void setImpostaNFCButtonListener(){
        btnDisplay = (Button) findViewById(R.id.imposta_nfc);
        btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                Intent returnNFCIntent = new Intent();
                returnNFCIntent.putExtra("ADD_NFC_REQUEST_CODE",tag.getId().toString());
                setResult(RESULT_OK, returnNFCIntent);
                finish();
            }
        });
    }

    public void setRecyclerViewLayoutManager() {
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setRecyclerAdapter(String[] techList) {
        nfcListRecyclerAdapter = new NFCListRecyclerAdapter(techList);
        recyclerView.setAdapter(nfcListRecyclerAdapter);
    }

    private void setTagId(String tagId) {
        TextView idTextView = findViewById(R.id.NFCId);
        idTextView.setText(tagId);
    }

}
