package com.example.roupas_checker;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> ar = new ArrayList<String>();
    String[] roupasIds;
    EditText c;
    Button b;

    Map<String,Integer> mapRoupas = new HashMap<String,Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        c = (EditText) findViewById(R.id.txtCodigos);
        b = (Button) findViewById(R.id.btnGravar);

        KeyboardVisibilityEvent.setEventListener(this, new KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean isOpen) {
                if(isOpen){
                    UIUtil.hideKeyboard(MainActivity.this);
                }
            }
        });

        c.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = c.getInputType(); // backup the input type
                c.setInputType(InputType.TYPE_NULL); // disable soft input
                c.onTouchEvent(event); // call native handler
                c.setInputType(inType); // restore input type
                return true; // consume touch even
            }
        });


    }


    public void gravarButtonClick(View view) {
        String multiLines = c.getText().toString();
        Log.i("array no inicio", multiLines);
        String delimiter = "\n";

        roupasIds = null;
        mapRoupas.clear();
        roupasIds = multiLines.split("\\r\\n|\\n|\\r");

        for(int i = 0; i< roupasIds.length; i++){
            if (mapRoupas.containsKey(roupasIds[i].substring(0,16))) {
                mapRoupas.put(roupasIds[i].substring(0,16), mapRoupas.get(roupasIds[i].substring(0,16)) + 1);
            } else {
                //key does not exists
                mapRoupas.put(roupasIds[i].substring(0,16), 1);
            }
            Log.i("loop roupa id: ", roupasIds[0]);
        }
        //Log.i("array no final: ", roupasIds[0]);
        Log.i("array no final: ", (Arrays.asList(mapRoupas).toString()));

        Intent intent = new Intent(MainActivity.this, ScanRoupas.class);
        intent.putExtra("hashMap", (Serializable) mapRoupas);
        startActivity(intent);
    }


}
