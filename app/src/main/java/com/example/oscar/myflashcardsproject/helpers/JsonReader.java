package com.example.oscar.myflashcardsproject.helpers;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Oscar on 18/04/2017.
 */

public class JsonReader {

    public static String loadJSONFromAssets(Context context, String filePath){
        String json = null;
        try{
            InputStream inputStream = context.getAssets().open(filePath);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        }catch (IOException ex){
            ex.printStackTrace();
        }

        return json;
    }
}
