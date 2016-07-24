package pstc.retrofitlistfetch;

import java.util.Comparator;

/**
 * Created by Chandrakant on 23-07-2016.
 */
public class AreaComparator implements Comparator<Country> {

    @Override
    public int compare(Country lhs, Country rhs) {
        int lhi,rhi;
        if(lhs == null || rhs == null){
            return 0;
        }else{
            lhi = lhs.getCountryArea();
            rhi = rhs.getCountryArea();
            if(lhi < rhi){
                return -1;
            }else{
                return 1;
            }
        }

    }
}
