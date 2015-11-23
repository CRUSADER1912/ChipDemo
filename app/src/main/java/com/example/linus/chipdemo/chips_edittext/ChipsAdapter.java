package com.example.linus.chipdemo.chips_edittext;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.linus.chipdemo.R;

import java.util.ArrayList;
import java.util.Locale;

public class ChipsAdapter extends BaseAdapter  {

	private ArrayList<ChipsItem> items;
	private ArrayList<ChipsItem> arraylist;
	private ArrayList<ChipsItem> suggestions;
	private Context ctx;
	private LayoutInflater inflater;
	private String TAG = this.getClass().getSimpleName();
	CompoundButton.OnCheckedChangeListener listener;

	public ChipsAdapter(Context context,ArrayList<ChipsItem> items, CompoundButton.OnCheckedChangeListener listener) {
		super();
		this.listener = listener;
		this.ctx = context;
		this.items = items;
		this.suggestions = items;//new ArrayList<ChipsItem>();
		this.arraylist = new ArrayList<ChipsItem>();
		this.arraylist.addAll(suggestions);

	}

	@Override
	public int getCount() {
		return suggestions.size();
	}

	@Override
	public ChipsItem getItem(int position) {
		return suggestions.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public int getImage(String title){
		Log.i(TAG, "Title " + title);
		int img = android.R.drawable.sym_def_app_icon;//R.drawable.android;
		for(int i=0;i<items.size();i++){
			if(items.get(i).getTitle().trim().toLowerCase().startsWith(title.trim().toLowerCase())){
				img = items.get(i).getImageid();
				Log.i(TAG, "Found " + title);
				break;
			}
		}
		
		return img;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh;
		View view = convertView;
		if (view == null) {
			if (inflater == null)
				inflater = LayoutInflater.from(ctx);
			view = inflater.inflate(R.layout.chips_adapter, null);
			vh = new ViewHolder();
			vh.img = (ImageView) view.findViewById(R.id.imageView1);
			vh.tv = (TextView) view.findViewById(R.id.textView1);
			vh.chkBox = (CheckBox) view.findViewById(R.id.chkbox_tick);

			view.setTag(vh);
		} else {
			vh = (ViewHolder) view.getTag();
		}

		Log.i(TAG, suggestions.get(position).getTitle() +  " = " + suggestions.get(position).getImageid());
		vh.img.setImageResource(suggestions.get(position).getImageid());
		vh.tv.setText(suggestions.get(position).getTitle());
		vh.chkBox.setChecked(suggestions.get(position).isSelected());
		vh.chkBox.setTag(position);
		vh.chkBox.setOnCheckedChangeListener(listener);

		return view;
	}

	class ViewHolder {
		TextView tv;
		ImageView img;
		CheckBox chkBox;
	}
//
//	@Override
//	public Filter getFilter() {
//		return nameFilter;
//	}

//	Filter nameFilter = new Filter() {
//
//		@Override
//		public CharSequence convertResultToString(Object resultValue) {
////			Log.i(TAG, "convertResultToString :" + resultValue);
//			String str = ((ChipsItem) resultValue).getTitle();
//			return str;
//		}
//
//		@Override
//		protected FilterResults performFiltering(CharSequence constraint) {
//			Log.d("filter", "" + constraint);
//			FilterResults filterResults = new FilterResults();
//			if (constraint != null) {
//
//				suggestions.clear();
//				try {
//					for (int i = 0; i < items.size(); i++) {
//
//						if (items.get(i).getTitle().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
////							Log.d("filter", "Found --- " + items.get(i).getTitle());
//							suggestions.add(items.get(i));
//						}
//
//					}
//				} catch (Exception e) {
//				}
//				filterResults.values = suggestions;
//				filterResults.count = suggestions.size();
//			}
//			return filterResults;
//		}
//
//		@Override
//		protected void publishResults(CharSequence constraint,
//				FilterResults results) {
////			Log.i(TAG, "publish results " + results.count);
//
//			if (results != null && results.count > 0) {
//				notifyDataSetChanged();
//			} else {
//				notifyDataSetInvalidated();
//			}
//
//		}
//	};

	// Filter Class
	public void filter(String charText) {
		charText = charText.toLowerCase(Locale.getDefault());
		suggestions.clear();
		if (charText.length() == 0) {
			suggestions.addAll(arraylist);
		}
		else
		{
			for (ChipsItem wp : arraylist)
			{
				if (wp.getTitle().toLowerCase(Locale.getDefault()).contains(charText))
				{
					suggestions.add(wp);
				}
			}
		}
		notifyDataSetChanged();
	}

}
