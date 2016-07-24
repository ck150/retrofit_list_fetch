package pstc.retrofitlistfetch;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Chandrakant on 23-07-2016.
 */
public class CustomListAdapter extends BaseAdapter {

    private Activity activity;
    private List<Country> countryItems;
    private LayoutInflater inflater;


    public CustomListAdapter(Activity activity, List<Country> countryItems) {
        this.activity = activity;
        this.countryItems = countryItems;
    }

    @Override
    public int getCount() {
        return countryItems.size();
    }

    @Override
    public Object getItem(int position) {
        return countryItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.listview_item, null);

        TextView countryText = (TextView) convertView.findViewById(R.id.country_id);
        TextView areaText = (TextView) convertView.findViewById(R.id.area_id);

        Country c = countryItems.get(position);
        Log.v("tag1",Integer.toString(c.getCountryArea()));
        countryText.setText(c.getCountryName());
        areaText.setText(Integer.toString(c.getCountryArea()));

        return convertView;
    }
}
