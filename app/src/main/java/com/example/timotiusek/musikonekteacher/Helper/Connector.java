package com.example.timotiusek.musikonekteacher.Helper;

/**
 * Created by wilbe on 26/07/2017.
 */

public class Connector {

    //SERVER IP
    private static String URL = "http://162.212.152.65:3000";


//    private static String URL = "http://192.168.0.14:3000";
//    private static String URL = "http://192.168.4.25:3000";
    public static String getURL(){
        return URL;
    }

}
