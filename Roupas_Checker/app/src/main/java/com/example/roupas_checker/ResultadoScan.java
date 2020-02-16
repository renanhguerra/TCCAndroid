package com.example.roupas_checker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

public class ResultadoScan extends AppCompatActivity {

    HashMap<String, int[]> mapRoupasResultado;
    int[] arrayresultadoFragment = new int[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_scan);



        ListView list=(ListView)findViewById(R.id.listResultados);

        Intent intent = getIntent();
        mapRoupasResultado = (HashMap<String, int[]>)intent.getSerializableExtra("hashMapResultado");

        Log.i("hash map new activity resultado: ", (Arrays.asList(mapRoupasResultado).toString()));

        for (Map.Entry<String,int[]> pair : mapRoupasResultado.entrySet()) {
            System.out.println("Dale");
            System.out.println(pair.getKey());
            arrayresultadoFragment = pair.getValue();
            System.out.println("uno" + arrayresultadoFragment[0]);
            System.out.println("dos" + arrayresultadoFragment[1]);
            System.out.println("tres" + arrayresultadoFragment[2]);
        }

        RoupasAdapter adapter = new RoupasAdapter(mapRoupasResultado);
        list.setAdapter(adapter);

        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
            sleep(3000);
            r.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

    public void ScanClick(View view){
        finish();
    }
}
