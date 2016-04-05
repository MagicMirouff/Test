package com.lelivrescolaire.testtechnique.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import com.lelivrescolaire.testtechnique.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import models.Book;

public class SplashScreenActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new AsyncRead().execute("books.json");
    }


    /**
     *  Async read and conversion of book collection to books objects
     */
    private class AsyncRead extends AsyncTask<String,Void,ArrayList<Book>> {
        @Override
        protected ArrayList<Book> doInBackground(String... params) {
            String json = loadJSONFromAsset(params[0]);
            ArrayList<Book> books = new ArrayList<>();
            try {
                JSONArray array = new JSONArray(json);
                JSONObject object;
                for (int i = 0; i < array.length(); i++) {
                    object = array.getJSONObject(i);
                    books.add(new Book(object));
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
            return books;
        }
        @Override
        protected void onPostExecute(ArrayList<Book> books) {
            super.onPostExecute(books);
            // Keep books
            saveBooks(books);
            // Launch Library
            Intent intent = new Intent(SplashScreenActivity.this,LibraryActivity.class);
            startActivity(intent);
        }
    }

    private void saveBooks(ArrayList<Book> books) {
        // Create a RealmConfiguration which is to locate Realm file in package's "files" directory.
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).deleteRealmIfMigrationNeeded().build();
        // Get a Realm instance for this thread
        Realm realm = Realm.getInstance(realmConfig);

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(books.get(0).toRealm());
        realm.copyToRealmOrUpdate(books.get(1).toRealm());
        realm.commitTransaction();
    }


}
