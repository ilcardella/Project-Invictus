package it.cardella.projectinvictus;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import it.cardella.projectinvictus.data.Item;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class FeedDetailActivity extends Activity implements ImageGetter{

	private TextView title;
	//private TextView contentWV;
	private WebView contentWV;
	
	@SuppressLint({ "ResourceAsColor", "NewApi" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feed_detail);
		
		title = (TextView) findViewById(R.id.title_detail_textview);
		//contentWV = (TextView) findViewById(R.id.content_detail_textview);
		contentWV = (WebView) findViewById(R.id.content_detail_webview);
		
		Item item = (Item) getIntent().getExtras().getSerializable("item");
		
		title.setTextColor(getResources().getColor(R.color.DarkRed));
		title.setText(item.getTitle());
		
		//contentWV.setMovementMethod(new ScrollingMovementMethod());
		//contentWV.setVerticalScrollBarEnabled(true);
		//contentWV.setCompoundDrawablePadding(10);
		//contentWV.setPadding(10, 10, 10, 10);
		//contentWV.setTextIsSelectable(true);
		//contentWV.setText(Html.fromHtml(item.getContent(), this, null));
		WebSettings settings= contentWV.getSettings();
		settings.setBuiltInZoomControls(true);
		settings.setDefaultFontSize(12);
		
		settings.setDisplayZoomControls(false);
		contentWV.setWebChromeClient(new WebChromeClient());
		
		contentWV.setInitialScale(0);
		String text = "<html><body style=\"text-align:justify\"> %s </body></Html>";
		contentWV.loadData(String.format(text, item.getContent()), "text/html", "utf-8");
		
		
	}

	@Override
	public Drawable getDrawable(String source) {
		LevelListDrawable d = new LevelListDrawable();
        Drawable empty = getResources().getDrawable(R.drawable.ic_launcher);
        d.addLevel(0, 0, empty);
        d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());

        //new ImageGetterAsyncTask(d, this).execute(source);

        return d;
	}
/*	
	private class ImageGetterAsyncTask extends AsyncTask<String, Void, Bitmap>{

		private LevelListDrawable drawable;
		private Context context;
		
		public ImageGetterAsyncTask(LevelListDrawable d, Context c){
			this.drawable = d;
			this.context = c;
		}
		
		@Override
		protected Bitmap doInBackground(String... params) {
			String source = params[0];
			try {
                InputStream is = new URL(source).openStream();
                return BitmapFactory.decodeStream(is);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
		}
		
		@Override
        protected void onPostExecute(Bitmap bitmap) {
			if (bitmap != null) {
                BitmapDrawable d = new BitmapDrawable(context.getResources(), bitmap);
                drawable.addLevel(1, 1, d);
                drawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                drawable.setLevel(1);
                // i don't know yet a better way to refresh TextView
                // mTv.invalidate() doesn't work as expected
                CharSequence t = contentWV.getText();
                contentWV.setText(t);
            }
		}
		
	}
*/
}
