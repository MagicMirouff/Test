package com.lelivrescolaire.testtechnique.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lelivrescolaire.testtechnique.R;

import java.util.ArrayList;

import adapters.BookAdapter;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import models.Book;
import models.RealmBook;

/**
 * Created by MagicMirouf on 04/04/16.
 */
public class LibraryActivity extends AppCompatActivity {


    private RealmList<RealmBook> books;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        getBooks();
        showBooks();
    }

    private void showBooks() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.activity_library_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2,LinearLayoutManager.VERTICAL,false));
        BookAdapter bookAdapter = new BookAdapter(books,this);
        recyclerView.setAdapter(bookAdapter);
    }

    private void getBooks() {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).deleteRealmIfMigrationNeeded().build();
        Realm realm = Realm.getInstance(realmConfig);
        books = new RealmList<>();
        books.addAll(realm.where(RealmBook.class).findAll());
    }
}
