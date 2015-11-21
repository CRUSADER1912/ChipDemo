package com.example.linus.chipdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.linus.chipdemo.chipsedittextlibrary.ChipMaker;
import com.example.linus.chipdemo.chipsedittextlibrary.ChipsItem;
import com.example.linus.chipdemo.chipsedittextlibrary.ChipsTextview;

import java.util.ArrayList;

public class MainActivity extends Activity  {

	ListView listview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final ChipsTextview ch= (ChipsTextview) findViewById(R.id.chipsMultiAutoCompleteTextview1);
		listview = (ListView) findViewById(R.id.listview);

		String[] countries = getResources().getStringArray(R.array.country);
//		TypedArray imgs = getResources().obtainTypedArray(R.array.flags);


		ArrayList<ChipsItem> arrValueData = new ArrayList<ChipsItem>();


		for(int i=0;i<countries.length;i++){
			arrValueData.add(new ChipsItem(countries[i],0/*imgs.getResourceId(i, -1)*/ ));
			Log.i("Main Activity", arrValueData.get(i).getTitle() + " = " + arrValueData.get(i).getImageid());
		}

		Log.i("MainActivity", "Array :" + arrValueData.size());


		new ChipMaker().create(this,ch,listview,arrValueData);




//		ChipsAdapter chipsAdapter = new ChipsAdapter(this, arrValueData);
////		chipsAdapter.registerDataSetObserver(new PopupDataSetObserver(ch, chipsAdapter));
//		listview.setAdapter(chipsAdapter);
//		ch.setMyAdapter(chipsAdapter);
//
//		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//			public void onItemClick(AdapterView<?> parent, View view,
//									int position, long id) {
//				// When clicked, show a toast with the TextView text
//				ChipsItem chipsitem = (ChipsItem) parent.getItemAtPosition(position);
//				Toast.makeText(getApplicationContext(),
//						"Clicked on Row: " + chipsitem.getTitle(),
//						Toast.LENGTH_LONG).show();
//				String currData = ch.getText().toString();
//				if (!currData.contains(chipsitem.getTitle())) {
//					if (currData.contains(",")) {
//						ch.setText(currData.substring(0, currData.lastIndexOf(",") + 1) + chipsitem.getTitle() + ",");
//					} else {
//						ch.setText(chipsitem.getTitle() + ",");
//					}
//				} else {
//					ch.setText(currData.replaceAll(chipsitem.getTitle() + ",",""));
//				}
//				ch.setChips();
//			}
//		});


	}

}
