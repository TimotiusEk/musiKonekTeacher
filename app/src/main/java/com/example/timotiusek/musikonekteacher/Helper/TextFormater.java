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

    public static String formatDateSpacing(String date){
        date = date.replace("   "," ");
        date = date.replace("  "," ");

        date = date.replace("Monday", "Senin, ");
        date = date.replace("Tuesday", "Selasa, ");
        date = date.replace("Wednesday", "Rabu, ");
        date = date.replace("Thursday", "Kamis, ");
        date = date.replace("Friday", "Jumat, ");
        date = date.replace("Saturday", "Sabtu, ");
        date = date.replace("Sunday", "Minggu, ");

        date = date.replace("January","Januari");
        date = date.replace("February","Januari");
        date = date.replace("March","Januari");
        date = date.replace("May","Januari");
        date = date.replace("June","Januari");
        date = date.replace("July","Januari");
        date = date.replace("August","Januari");
        date = date.replace("October","Oktober");
        date = date.replace("December","Desember");

        return date;
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

    public static String priceFormatter(int price){

        String pric = String.valueOf(price);

        int count = 0;
        for(int i = pric.length(); i >= 0;i--){

            count++;
            if(count%3 == 0 && i-1 > 0){
                pric = pric.substring(0,i-1)+"."+pric.substring(i-1);
            }

        }


        return pric;

    }

    public static String priceFormatter(double priceDouble){

        int price = (int)priceDouble;

        String pric = String.valueOf(price);

        int count = 0;
        for(int i = pric.length(); i >= 0;i--){

            count++;
            if(count%3 == 0 && i-1 > 0){
                pric = pric.substring(0,i-1)+"."+pric.substring(i-1);
            }

        }


        return pric;

    }

    public static String firstNameSplitter(String name){

        String[] splited = name.split("\\s+");

//        if(splited.length == 0 ){
//            return "";
//        }

        String newString = "";

        for(int i = 0; i < splited.length - 1 ; i++){
            newString = newString + splited[i];
        }

        return newString;

    }

    public static String lastNameSplitter(String name){

        String[] splited = name.split("\\s+");

        if(splited.length == 0 ){
            return name;
        }

        return splited[splited.length-1];
    }

}
