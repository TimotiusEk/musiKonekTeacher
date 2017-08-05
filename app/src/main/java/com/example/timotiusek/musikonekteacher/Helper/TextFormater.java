package com.example.timotiusek.musikonekteacher.Helper;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wilbe on 29/07/2017.
 */

public class TextFormater {

    public static String format(int num){
        return "Paket "+num+ " kali pertemuan";
    }

    public static String formatCourse(String type){
        return "Kursus "+type;
    }

    public static String  formatTime(String timeStamp){

        timeStamp = timeStamp.replace("T"," ");
        timeStamp = timeStamp.replace("Z"," ");

        Log.d("ASDF",timeStamp);

        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss ");
        Date date = null;
        try {
            date = dt.parse(timeStamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dt1 = new SimpleDateFormat("dd MMMM");
        SimpleDateFormat dt2 = new SimpleDateFormat("HH:mm");
//        SimpleDateFormat dt1 = new SimpleDateFormat("dd, MMMMM HH:mm:ss");
        Log.d("ASDF",dt1.format(date));

        return dt1.format(date)+ " " + dt2.format(date);

//        return timeStamp;
    }

}
