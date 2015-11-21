package com.example.linus.chipdemo.chipsedittextlibrary;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by linus on 21/11/15.
 */
public class ChipMaker {
    public void create(final Context context, final ChipsTextview ch, ListView listview, ArrayList<ChipsItem> arrValueData) {

        ChipsAdapter chipsAdapter = new ChipsAdapter(context, arrValueData);
        listview.setAdapter(chipsAdapter);
        ch.setMyAdapter(chipsAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                ChipsItem chipsitem = (ChipsItem) parent.getItemAtPosition(position);
//                Toast.makeText(context,
//                        "Clicked on Row: " + chipsitem.getTitle(),
//                        Toast.LENGTH_LONG).show();
                String currData = ch.getText().toString();
                if (!currData.contains(chipsitem.getTitle())) {
                    if (currData.contains(",")) {
                        ch.setText(currData.substring(0, currData.lastIndexOf(",") + 1) + chipsitem.getTitle() + ",");
                    } else {
                        ch.setText(chipsitem.getTitle() + ",");
                    }
                } else {
                    ch.setText(currData.replaceAll(chipsitem.getTitle() + ",", ""));
                }
                ch.setChips();
            }
        });
    }
}
