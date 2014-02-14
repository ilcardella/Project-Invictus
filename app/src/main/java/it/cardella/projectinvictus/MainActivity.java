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

import it.cardella.projectinvictus.data.Item;
import it.cardella.projectinvictus.util.ItemListAdapter;
import it.cardella.projectinvictus.util.RssReader;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

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
	
	static Map<String, List<Item>> itemsHashMap = new HashMap<String, List<Item>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_about:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle(R.string.app_name);
                dialog.setIcon(R.drawable.logo_launcher);
                dialog.setMessage("App created by Alberto Cardellini.");
                dialog.show();

                return true;

            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = new DummySectionFragment();
            Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position);
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
		
		ItemListAdapter itemListAdapter;
		ListView listView;
        int position;
        View rootView;
        LinearLayout pBarLinearLayout;
        boolean running_task = false;
        boolean isConnected = false;
		
		public DummySectionFragment() {
		}

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            position = getArguments().getInt(ARG_SECTION_NUMBER);

            if(isOnline()){

                isConnected = true;
                if(!itemsHashMap.containsKey(URL_ARRAY[position])){
                        GetXMLTask task = new GetXMLTask();
                        task.execute(URL_ARRAY[position]);
                        running_task = true;
                }
            }else{
                Toast.makeText(getActivity(), "Connessione Assente", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

            rootView = inflater.inflate(R.layout.fragment_main_dummy,
                    container, false);
            pBarLinearLayout = (LinearLayout) rootView.findViewById(R.id.progressBarLinearLayout);
            listView = (ListView) rootView.findViewById(R.id.feed_listView);
            if(isConnected){
                setItemListAdapter();
            }

            return rootView;
		}

        public boolean isOnline() {
            ConnectivityManager cm =
                    (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                return true;
            }
            return false;
        }

        private void setItemListAdapter(){
            if(running_task)
            {
                listView.setVisibility(View.GONE);
                pBarLinearLayout.setVisibility(View.VISIBLE);
            }
            else
            {
                pBarLinearLayout.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                itemListAdapter = new ItemListAdapter(getActivity(), R.layout.listview_row_layout, itemsHashMap.get(URL_ARRAY[position]));
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
            }

        }

        private class GetXMLTask extends
                AsyncTask<String, Void, List<Item>> {

            @Override
            protected List<Item> doInBackground(String... urls) {
                List<Item> items = new ArrayList<Item>();

                for (String url : urls) {
                    String currentUrl = url;
                    String xml = getXmlFromUrl(url);

                    InputStream stream = new ByteArrayInputStream(xml.getBytes());
                    items = RssReader.getItems(stream);
                }

                return items;
            }

            @Override
            protected void onPostExecute(List<Item> items) {
                itemsHashMap.put(URL_ARRAY[position], items);
                running_task = false;
                if(getActivity() != null)
                {
                    setItemListAdapter();
                }
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
                    ex.printStackTrace();
                }
                return output.toString();

            }
        }// End of AsycTask class

	} // Fine classe Fragment


}// End of Activity
