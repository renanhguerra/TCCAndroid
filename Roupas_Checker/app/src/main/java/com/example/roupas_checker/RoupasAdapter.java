package com.example.roupas_checker;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

public class RoupasAdapter extends BaseAdapter {
    private final ArrayList mData;
    int[] arrayresultadoFragment = new int[3];

    public RoupasAdapter(Map<String, int[]> map) {
        mData = new ArrayList();
        mData.addAll(map.entrySet());
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Map.Entry<String, int[]> getItem(int position) {
        return (Map.Entry) mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO implement you own logic with ID
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View result;

        if (convertView == null) {
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_view_roupas, parent, false);
        } else {
            result = convertView;
        }

        Map.Entry<String, int[]> item = getItem(position);

        // TODO replace findViewById by ViewHolder
        ((TextView) result.findViewById(android.R.id.text1)).setText(item.getKey());
        arrayresultadoFragment = item.getValue();
        System.out.println("uno" + arrayresultadoFragment[0]);
        System.out.println("dos" + arrayresultadoFragment[1]);
        System.out.println("tres" + arrayresultadoFragment[2]);
        ((TextView) result.findViewById(android.R.id.text2)).setText("Qtd gravada: " + arrayresultadoFragment[0] + " | Qtd escaneada: " + arrayresultadoFragment[1]);
        if (arrayresultadoFragment[2] == 0) {
            result.setBackgroundColor(Color.GREEN);
        } else if (arrayresultadoFragment[2] == 1) {
            result.setBackgroundColor(Color.YELLOW);
        } else if (arrayresultadoFragment[2] == 2) {
            result.setBackgroundColor(Color.RED);
        } else if (arrayresultadoFragment[2] == 3) {
            result.setBackgroundColor(Color.BLUE);
        }
        //((TextView) result.findViewById(android.R.id.text3)).setText();
        //((TextView) result.findViewById(android.R.id.text4)).setText(arrayresultadoFragment[2]);

        return result;
    }
}
