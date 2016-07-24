package pstc.retrofitlistfetch;

import java.io.Serializable;

/**
 * Created by Chandrakant on 22-07-2016.
 */
public class Country implements Serializable{
    private String name;
    private int area;

    public Country(){
        this.name = "abc";
        this.area = 123;
    }

    public Country(String c,int a){
        this.name = c;
        this.area = a;
    }

    public String getCountryName(){
        return this.name;
    }

    public int getCountryArea(){
        return this.area;
    }

}
