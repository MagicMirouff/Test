package com.lelivrescolaire.testtechnique.activity;

import android.support.v7.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by MagicMirouf on 04/04/16.
 */
public class BaseActivity extends AppCompatActivity {

    /** Get file in String from assets
     * @param filename in assets folder
     * @return
     */
    public String loadJSONFromAsset(String filename) {
        String json = null;
        try {
            InputStream is = this.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
