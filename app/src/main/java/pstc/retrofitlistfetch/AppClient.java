package pstc.retrofitlistfetch;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Chandrakant on 22-07-2016.
 */
public interface AppClient {
    @GET("/mock/countries.json")
    Call<List<Country>> countries();
}
