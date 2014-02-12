package it.cardella.projectinvictus;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import it.cardella.projectinvictus.R;
import it.cardella.projectinvictus.data.Item;
import it.cardella.projectinvictus.util.ItemListAdapter;
import it.cardella.projectinvictus.util.RssReader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends FragmentActivity {

	private static final String ALLENAMENTO_URL = "http://www.projectinvictus.it/category/allenamento/feed";
	private static final String ALIMENTAZIONE_URL = "http://www.projectinvictus.it/category/alimentazione/feed";
	private static final String COACHING_URL = "http://www.projectinvictus.it/category/coaching/feed";
	private static final String VIDEO_URL = "http://www.projectinvictus.it/category/video-tutorial/feed";
	private static final String SFIDE_URL = "http://www.projectinvictus.it/category/sfide/feed";
	private static final String ANATOMIA_URL = "http://www.projectinvictus.it/category/anatomia-e-fisiologia/feed";
	private static final String NEWS_URL = "http://www.projectinvictus.it/category/news/feed";
	private static final String[] URL_ARRAY = { ALLENAMENTO_URL,
			ALIMENTAZIONE_URL, ANATOMIA_URL, VIDEO_URL, SFIDE_URL, NEWS_URL,
			COACHING_URL };

	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;

	ProgressDialog dialog;
	
	Map<String, List<Item>> feedHashMap = new HashMap<String, List<Item>>();
	
	ItemListAdapter itemListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		dialog = new ProgressDialog(this);
		dialog.setMessage("Downloading...");
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
		
		// TODO lanciare un task per ogni sezione
		GetXMLTask task = new GetXMLTask();
		task.execute(URL_ARRAY);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO riprendere i dati salvati e aggiornare la view
		super.onRestoreInstanceState(savedInstanceState);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO salvare gli items nel Bundle
		super.onSaveInstanceState(outState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);

			if (!feedHashMap.isEmpty()) {
				List<Item> list = new ArrayList<Item>();
				switch (position) {
				case 0:
					list = feedHashMap.get(URL_ARRAY[0]);
					for (int i = 0; i < list.size(); i++)
						args.putSerializable("item" + i, list.get(i));
					break;
				case 1:
					list = feedHashMap.get(URL_ARRAY[1]);
					for (int i = 0; i < list.size(); i++)
						args.putSerializable("item" + i, list.get(i));
					break;
				case 2:
					list = feedHashMap.get(URL_ARRAY[2]);
					for (int i = 0; i < list.size(); i++)
						args.putSerializable("item" + i, list.get(i));
					break;
				case 3:
					list = feedHashMap.get(URL_ARRAY[3]);
					for (int i = 0; i < list.size(); i++)
						args.putSerializable("item" + i, list.get(i));
					break;
				case 4:
					list = feedHashMap.get(URL_ARRAY[4]);
					for (int i = 0; i < list.size(); i++)
						args.putSerializable("item" + i, list.get(i));
					break;
				case 5:
					list = feedHashMap.get(URL_ARRAY[5]);
					for (int i = 0; i < list.size(); i++)
						args.putSerializable("item" + i, list.get(i));
					break;
				case 6:
					list = feedHashMap.get(URL_ARRAY[6]);
					for (int i = 0; i < list.size(); i++)
						args.putSerializable("item" + i, list.get(i));
					break;
				default:
					System.out.println("ERROR. Position overflow");
					break;
				}
			}

			fragment.setArguments(args);

			return fragment;
		}

		@Override
		public int getCount() {
			return 7;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			case 3:
				return getString(R.string.title_section4).toUpperCase(l);
			case 4:
				return getString(R.string.title_section5).toUpperCase(l);
			case 5:
				return getString(R.string.title_section6).toUpperCase(l);
			case 6:
				return getString(R.string.title_section7).toUpperCase(l);
			}
			return null;
		}
	}

	public static class DummySectionFragment extends Fragment {

		public static final String ARG_SECTION_NUMBER = "section_number";
		
		private ItemListAdapter itemListAdapter;
		private List<Item> list = new ArrayList<Item>();
		private ListView listView;
		
		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// genero la lista degli Item che sono stati passati al fragment
			for (int i = 0; i < getArguments().size(); i++) {
				Item item = (Item) getArguments().getSerializable("item" + i);
				if (item != null) {
					list.add(item);
				}
			}
						
			View rootView = inflater.inflate(R.layout.fragment_main_dummy,
					container, false);

			itemListAdapter = new ItemListAdapter(getActivity(), R.layout.listview_row_layout, list);
			listView = (ListView) rootView.findViewById(R.id.feed_listView);
			listView.setAdapter(itemListAdapter);
			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position,
						long id) {
					// lancio una nuova activity con l'item
					Item item = (Item) parent.getItemAtPosition(position);
					Intent intent = new Intent(getActivity(), FeedDetailActivity.class);
					intent.putExtra("item", item);
					startActivity(intent);
				}
			});

			return rootView;
		}

	}

	private class GetXMLTask extends
			AsyncTask<String, Void, Map<String, List<Item>>> {

		private String currentUrl = "";

		@Override
		protected Map<String, List<Item>> doInBackground(String... urls) {
			Map<String, List<Item>> items = new HashMap<String, List<Item>>();
			String xml = null;

			for (String url : urls) {
				currentUrl = url;
				xml = getXmlFromUrl(url);

				InputStream stream = new ByteArrayInputStream(xml.getBytes());
				items.put(currentUrl, RssReader.getItems(stream));
			}

			return items;
		}

		@Override
		protected void onPostExecute(Map<String, List<Item>> items) {
			feedHashMap = items;
			updateUI();
		}

		private String getXmlFromUrl(String urlString) {
			StringBuffer output = new StringBuffer("");
			try {
				InputStream stream = null;
				URL url = new URL(urlString);
				URLConnection connection = url.openConnection();

				HttpURLConnection httpConnection = (HttpURLConnection) connection;
				httpConnection.setRequestMethod("GET");
				httpConnection.connect();

				if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
					stream = httpConnection.getInputStream();

					BufferedReader buffer = new BufferedReader(
							new InputStreamReader(stream));
					String s = "";
					while ((s = buffer.readLine()) != null)
						output.append(s);
				}

			} catch (Exception ex) {
				// TODO gestire la non connessione a internet
				ex.printStackTrace();
			}
			return output.toString();

		}
	}// End of AsycTask class

	private void updateUI() {
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		dialog.dismiss();
	}

}// End of Activity
