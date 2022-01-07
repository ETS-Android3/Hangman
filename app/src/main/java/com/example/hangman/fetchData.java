package com.example.hangman;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import java.net.*;
import java.util.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.example.hangman.MainActivity.dataSize;

public class fetchData extends AsyncTask<Void,Void,Void> {
    String data = "";

    @Override
    protected Void doInBackground(Void... voids) {


        //connecting to url
        try {
            URL url = new URL("https://random-word-api.herokuapp.com/word?number=1");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            //fetching word from url
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(line != null){
                line = bufferedReader.readLine();
                if(line != null)
                    data = data + line;
            }
            } catch (MalformedURLException malformedURLException) {
            malformedURLException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }


        return null;
    }

    @Override
    //this function will run successful execution of doinbackground function
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        data = data.split("\"")[1].toString();
        dataSize = data.length();
        MainActivity.data = this.data;
        Log.i("word is:-    ", data);
        MainActivity.addTextView();
        MainActivity.mainLinearLayout.setAlpha(0);
        MainActivity.mainLinearLayout2.setAlpha(1);
    }
}
