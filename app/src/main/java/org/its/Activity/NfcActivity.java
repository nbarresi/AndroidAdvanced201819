package org.its.Activity;

import android.app.Activity;
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
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.androidadvanced201819.R;
import com.google.gson.Gson;

import org.its.UI.NfcArrayAdapter;
import org.its.db.entities.Nfc;
import org.its.utilities.ResultsCode;
import org.its.utilities.StringCollection;

import java.util.Arrays;
import java.util.List;

public class NfcActivity extends Activity {
    private boolean shouldHandleNfcs;
    private IntentFilter[] intentFiltersArray;
    private String[][] techListsArray;
    private PendingIntent pendingIntent;
    private NfcAdapter nfcAdapter;
    private Button button;
    private List<String> techList;
    private TextView tagIdView;
    private Nfc nfc;
    private ListView listView;
    private LinearLayout preScan;
    private LinearLayout nfcResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent receivedIntent = getIntent();

        setContentView(R.layout.nfc_layout);
        nfcAdapter = NfcAdapter.getDefaultAdapter(getApplicationContext());
        button = (Button) findViewById(R.id.impostaWifi);
        preScan = (LinearLayout) findViewById(R.id.nfcPreScan);
        nfcResult = (LinearLayout) findViewById(R.id.nfcResult);
        listView = (ListView) findViewById(R.id.nfc_list_view);
        tagIdView = (TextView) findViewById(R.id.tag_id);

        final boolean isAnUpdate = receivedIntent.getBooleanExtra(StringCollection.isUpdate, false);

        if (isAnUpdate) {
            try {
                nfc = new Gson().fromJson(receivedIntent.getStringExtra(StringCollection.nfcObject), Nfc.class);
                if (nfc != null && nfc.getId() != null) {
                    techList = nfc.getTechList();
                    tagIdView.setText(nfc.getId());
                    changeView();
                }
            }catch (Exception e){}
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();

                nfc = new Nfc(tagIdView.getText().toString(), techList);
                returnIntent.putExtra(ResultsCode.NFC_RESULT, nfc);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
        shouldHandleNfcs = nfcAdapter != null && nfcAdapter.isEnabled();
        if (shouldHandleNfcs) {
            pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

            IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);

            try {
                ndef.addDataType("*/*");
            } catch (IntentFilter.MalformedMimeTypeException e) {
                throw new RuntimeException("fail", e);
            }
            intentFiltersArray = new IntentFilter[]{ndef};
            techListsArray = new String[][]{

                    new String[]{NfcF.class.getName()},
                    new String[]{NfcA.class.getName()},
                    new String[]{NfcB.class.getName()},
                    new String[]{NfcV.class.getName()}

            };

        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        byte[] id = tag.getId();
        techList = Arrays.asList(tag.getTechList());
        if (techList.size() > 0) {
            tagIdView.setText(bytesToHexString(id));
            changeView();
            button.setEnabled(true);

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (shouldHandleNfcs)
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techListsArray);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (shouldHandleNfcs)
            nfcAdapter.disableForegroundDispatch(this);
    }

    private String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }

        char[] buffer = new char[2];
        for (int i = 0; i < src.length; i++) {
            buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
            buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
            System.out.println(buffer);
            stringBuilder.append(buffer);
            if (i != src.length - 1)
                stringBuilder.append(":");
        }

        return stringBuilder.toString();
    }

    private void changeView() {
        NfcArrayAdapter adapter = new NfcArrayAdapter(getApplicationContext(), techList);
        listView.setAdapter(adapter);
        preScan.setVisibility(View.GONE);
        nfcResult.setVisibility(View.VISIBLE);
    }
}
