package pstc.retrofitlistfetch;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;


public class MainActivity extends AppCompatActivity {

    List<Country> countriesList;
    private CustomListAdapter customListAdapter;
    private ListView listView;
    private Button button1;
    private TextView txtTime;
    private static String KEY = "List1";

    private Handler handler1 = new Handler();
    Activity act1;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        listView = (ListView) findViewById(R.id.country_list);
        txtTime = (TextView) findViewById(R.id.lastfetched);
        //DownloadList();
        act1 = this;

        button1 = (Button) findViewById(R.id.refreshButton);
        UpdateLastFetchedTime("update");

        button1.performClick();

        //View header = (View) getLayoutInflater().inflate(R.layout.listview_header,null);
        //listView.addHeaderView(header);
    }

    public void OnRefreshClick(View v){
        button1.setClickable(false);
        button1.setText("Please wait");
        DownloadList();
    }

    public void DownloadList(){
        final AppClient client = ServiceGenerator.createService(AppClient.class);

        final Call<List<Country>> call = client.countries();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    countriesList = call.execute().body();
                    //customListAdapter.notifyDataSetChanged();
                    for(Country country1: countriesList){
                        Log.v("tag1",country1.getCountryName());
                    }

                    handler1.post(new Runnable() {
                        public void run() {
                            AreaComparator areaComparator = new AreaComparator();
                            Collections.sort(countriesList, areaComparator);
                            customListAdapter = new CustomListAdapter(act1, countriesList);
                            Log.v("tag1", Integer.toString(countriesList.size()));
                            listView.setAdapter(customListAdapter);
                            customListAdapter.notifyDataSetChanged();
                            String time1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
                            UpdateLastFetchedTime(time1);
                            AfterRefresh(true);
                        }

                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    countriesList = (List<Country>) CacheObjects.readObject(act1,KEY);

                    handler1.post(new Runnable() {
                        public void run() {
                            Toast.makeText(act1,"Data could not be fetched!",Toast.LENGTH_SHORT).show();
                            if(countriesList!=null) {
                                AreaComparator areaComparator = new AreaComparator();
                                Collections.sort(countriesList, areaComparator);
                                customListAdapter = new CustomListAdapter(act1, countriesList);
                                Log.v("tag1", Integer.toString(countriesList.size()));
                                listView.setAdapter(customListAdapter);
                                customListAdapter.notifyDataSetChanged();
                            }
                            //String time1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
                            //UpdateLastFetchedTime(time1);
                            AfterRefresh(false);
                        }

                    });
                }

            }
        }).start();
    }

    private void UpdateLastFetchedTime(String s){
        String finalString;
        if(s.equals("update")){
            finalString = sharedPreferences.getString(getString(R.string.last_fetched_time),"null");
            if(finalString.equals("null")){
                finalString = "Not yet fetched";
            }
            txtTime.setText(finalString);
        }else{
            sharedPreferences.edit().putString(getString(R.string.last_fetched_time),s).apply();
            txtTime.setText(s);
        }

    }

    private void AfterRefresh(boolean b){
        button1.setText("REFRESH");
        button1.setClickable(true);
        if(b) {
            CacheObjects.writeObject(this, KEY, countriesList);
        }
    }



}
