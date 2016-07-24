package pstc.retrofitlistfetch;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Chandrakant on 23-07-2016.
 */

public class Fragment1 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState){
        return inflater.inflate(R.layout.fragment1,container,false);
    }
}
