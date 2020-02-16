package com.example.roupas_checker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

public class ScanRoupas extends AppCompatActivity {

    ArrayList<String> filelist;
    String[] roupasIds;
    EditText roupasEscaneadas;
    TextView textoStatus;
    Button validar;
    HashMap<String, Integer> mapRoupasToCheck;
    HashMap<String, int[]> mapResultado = new HashMap<String, int[]>();
    //List<Integer> listResultados = new ArrayList<>();
    int arrayResultados[] = new int[3];

    String strKey;

    int[] arrayresultadoFragment = new int[3];

    int arrayIncrementer;

    HashMap<String,Integer> mapRoupasEscaneadas = new HashMap<String,Integer>();

    //variavel para controlar se foi encontrado algum erro e deve ir para outra activity
    int errorControl = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_roupas);

        roupasEscaneadas = (EditText) findViewById(R.id.txtRoupasEscaneadas);
        validar = (Button) findViewById(R.id.btnValidar);

//        filelist =  (ArrayList<String>) getIntent().getSerializableExtra("array");

        Intent intent = getIntent();
        mapRoupasToCheck = (HashMap<String, Integer>)intent.getSerializableExtra("hashMap");

        Log.i("hash map new activity: ", (Arrays.asList(mapRoupasToCheck).toString()));

        textoStatus = (TextView) findViewById(R.id.txtStatus);
        textoStatus.setBackgroundColor(Color.GRAY);


        KeyboardVisibilityEvent.setEventListener(this, new KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean isOpen) {
                if(isOpen){
                    UIUtil.hideKeyboard(ScanRoupas.this);
                }
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        roupasEscaneadas.getEditableText().clear();
        mapRoupasEscaneadas.clear();
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        textoStatus.setText("");
        textoStatus.setBackgroundColor(Color.GRAY);
    }

    public void resetClick(View view) {
        textoStatus.setText("");
        textoStatus.setBackgroundColor(Color.GRAY);
        mapRoupasEscaneadas.clear();
        roupasEscaneadas.getEditableText().clear();
    }

    public void scanClick (View view) {

        String multiLines = roupasEscaneadas.getText().toString();
        Log.i("array no inicio", multiLines);
        String delimiter = "\n";
        roupasIds = null;
        mapRoupasEscaneadas.clear();
        roupasIds = multiLines.split("\\r\\n|\\n|\\r");

        //atribui todos os codigos em um hashmap
        for(int i = 0; i< roupasIds.length; i++){
            if (mapRoupasEscaneadas.containsKey(roupasIds[i].substring(0,16))) {
                mapRoupasEscaneadas.put(roupasIds[i].substring(0,16), mapRoupasEscaneadas.get(roupasIds[i].substring(0,16)) + 1);
            } else {
                //key does not exists
                mapRoupasEscaneadas.put(roupasIds[i].substring(0,16), 1);
            }
            Log.i("loop roupa id: ", roupasIds[0]);
        }


        Log.i("array no final: ", roupasIds[0]);
        Log.i("array no scaneadooooooo: ", (Arrays.asList(mapRoupasEscaneadas).toString()));

        for (Map.Entry<String,Integer> pair : mapRoupasEscaneadas.entrySet()) {
            arrayResultados = new int[arrayResultados.length];


            System.out.println(pair.getKey());
            System.out.println(pair.getValue());
            strKey = pair.getKey();

            if (mapRoupasToCheck.containsKey(strKey)) {
                if (mapRoupasToCheck.get(pair.getKey()) == pair.getValue()){
                    //   Log.i("sera que vai: ", mapRoupasToCheck.get(pair.getKey()).toString());
                    System.out.println("mesma quantidade");
                    arrayResultados[0]=mapRoupasToCheck.get(pair.getKey());
                    arrayResultados[1]=pair.getValue();
                    arrayResultados[2]=0;
                    Log.i("array de resultados: ", (Arrays.asList(arrayResultados).toString()));
                    mapResultado.put(strKey, arrayResultados);
                    Log.i("map de resultados: ", (Arrays.asList(mapResultado).toString()));

                } else {
                    System.out.println("quantidade diferente");
                    arrayResultados[0]=mapRoupasToCheck.get(pair.getKey());
                    arrayResultados[1]=pair.getValue();
                    arrayResultados[2]=1;
                    Log.i("array de resultados: ", (Arrays.asList(arrayResultados).toString()));
                    mapResultado.put(strKey, arrayResultados);
                    Log.i("map de resultados: ", (Arrays.asList(mapResultado).toString()));
                    errorControl = 1;
                }
            } else {
                System.out.println("item nao encontrado no lote criado");
                arrayResultados[0]= 0;
                arrayResultados[1] = pair.getValue();
                arrayResultados[2] = 2;
                Log.i("array de resultados: ", (Arrays.asList(arrayResultados).toString()));
                mapResultado.put(strKey, arrayResultados);
                errorControl = 1;
            }
        }

        for (Map.Entry<String,Integer> pair : mapRoupasToCheck.entrySet()) {

            if(!mapRoupasEscaneadas.containsKey(pair.getKey())) {
                System.out.println("item nao encontrado no lote escaneado");
                arrayResultados = new int[arrayResultados.length];
                arrayResultados[0] = mapRoupasToCheck.get(pair.getKey());
                arrayResultados[1] = 0;
                arrayResultados[2] = 3;
                mapResultado.put(pair.getKey(), arrayResultados);
                errorControl = 1;
            }
        }



        if (errorControl == 1) {
            Intent intent = new Intent(ScanRoupas.this, ResultadoScan.class);
            intent.putExtra("hashMapResultado", mapResultado);
            startActivity(intent);
            errorControl = 0;
        } else {

            textoStatus.setText("OK!");
            textoStatus.setBackgroundColor(Color.GREEN);
            roupasEscaneadas.getEditableText().clear();
            try {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                r.play();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
