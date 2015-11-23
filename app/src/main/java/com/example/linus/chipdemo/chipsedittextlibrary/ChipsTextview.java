package com.example.linus.chipdemo.chipsedittextlibrary;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.linus.chipdemo.R;

import java.util.Locale;

public class ChipsTextview extends EditText {

	private final String TAG = "ChipsMultiAutoCompleteTextview";

	ChipsAdapter adapter;

	/* Constructor */
	public ChipsTextview(Context context) {
		super(context);
		init(context);
	}

	/* Constructor */
	public ChipsTextview(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	/* Constructor */
	public ChipsTextview(Context context, AttributeSet attrs,
						 int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	/* set listeners for item click and text change */
	public void init(Context context){
		addTextChangedListener(textWather);
	}
	/*TextWatcher, If user type any country name and press comma then following code will regenerate chips */
	private TextWatcher textWather = new TextWatcher() {

		String before ="";
		String after = "";

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			if(count >=1){
				if(s.charAt(start) == ',')
					setChips(); // generate chips
			}
		}
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,int after) {
			Log.d("Checking data before", "Value : " + s.toString() + " start " + start + " count " + count + " after " + after);
			before = s.toString();
		}
		@Override
		public void afterTextChanged(Editable s) {
			after=getText().toString();
			String text = getText().toString().toLowerCase(Locale.getDefault());
			if(text.contains(",")){
				text = text.substring(text.lastIndexOf(",") + 1,text.length());
			}
			Log.d("SearchString", "Value : "+text);
			adapter.filter(text);
			checkDataValidty(before,after);
		}
	};

	void checkDataValidty(String before, String after){
		if(before.length() > after.length()){
			if(before.endsWith(",") && after.length() > 0){
				setText(after.substring(0,after.lastIndexOf(",") + 1));
				setChips();
			}
		}
	}
	/*This function has whole logic for chips generate*/
	public void setChips(){
		if(getText().toString().contains(",")) // check comman in string
		{

			SpannableStringBuilder ssb = new SpannableStringBuilder(getText());
			// split string wich comma
			String chips[] = getText().toString().trim().split(",");
			int x =0;
			// loop will generate ImageSpan for every country name separated by comma
			for(String c : chips){
				// inflate chips_edittext layout
				LayoutInflater lf = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
				TextView textView = (TextView) lf.inflate(R.layout.chips_edittext, null);
				textView.setText(c); // set text
				int image = /*((ChipsAdapter) getAdapter())*/adapter.getImage(c);
				textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, image, 0);
				// capture bitmapt of genreated textview
				int spec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
				textView.measure(spec, spec);
				textView.layout(0, 0, textView.getMeasuredWidth(), textView.getMeasuredHeight());
				Bitmap b = Bitmap.createBitmap(textView.getWidth(), textView.getHeight(),Bitmap.Config.ARGB_8888);
				Canvas canvas = new Canvas(b);
				canvas.translate(-textView.getScrollX(), -textView.getScrollY());
				textView.draw(canvas);
				textView.setDrawingCacheEnabled(true);
				Bitmap cacheBmp = textView.getDrawingCache();
				Bitmap viewBmp = cacheBmp.copy(Bitmap.Config.ARGB_8888, true);
				textView.destroyDrawingCache();  // destory drawable
				// create bitmap drawable for imagespan
				BitmapDrawable bmpDrawable = new BitmapDrawable(viewBmp);
				bmpDrawable.setBounds(0, 0, bmpDrawable.getIntrinsicWidth(), bmpDrawable.getIntrinsicHeight());
				// create and set imagespan
				ssb.setSpan(new ImageSpan(bmpDrawable),x ,x + c.length() , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

				ClickableSpan clickableSpan = new ClickableSpan() {
					@Override
					public void onClick(View textView) {
						TextView tv = (TextView) textView;
						// TODO add check if tv.getText() instanceof Spanned
						Spanned s = (Spanned) tv.getText();
						int start = s.getSpanStart(this);
						int end = s.getSpanEnd(this);
						Log.d("TAG", "onClick [" + s.subSequence(start, end) + "]");
						tv.setText(tv.getText().toString().replaceAll("" + s.subSequence(start, end + 1), ""));
						updateCheckDataSet(s.subSequence(start, end).toString(),adapter);
//						setChips();
					}
				};

				setMovementMethod(LinkMovementMethod.getInstance());

				ssb.setSpan(clickableSpan,x ,x + c.length() , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

				x = x+ c.length() +1;
			}
			// set chips span
			setText(ssb);
			// move cursor to last
			setSelection(getText().length());
		}


	}

	private void updateCheckDataSet(String charSequence, ChipsAdapter adapter) {
		for (int i = 0 ; i < adapter.getCount(); i++){
			if(adapter.getItem(i).getTitle().equalsIgnoreCase(charSequence)){
				adapter.getItem(i).setSelected(false);
			}
		}
		adapter.notifyDataSetChanged();
	}


	public void setMyAdapter(ChipsAdapter adapter) {
		this.adapter = adapter;
	}

		
	
	
}
