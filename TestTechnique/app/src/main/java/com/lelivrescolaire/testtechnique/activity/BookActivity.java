package com.lelivrescolaire.testtechnique.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lelivrescolaire.testtechnique.R;

import adapters.BookAdapter;
import adapters.PageAdapter;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import models.Book;
import models.RealmBook;

/**
 * Created by MagicMirouf on 04/04/16.
 */
public class BookActivity extends AppCompatActivity {

    public static final String BOOK_ID = "BOOK_ID";

    private RealmBook book;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBook(getIntent().getExtras().getInt(BOOK_ID));
        setContentView(R.layout.activity_book);
        setPages();
    }

    private void setPages() {
        // create Recycler View
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.activity_book_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        PageAdapter pageAdapter = new PageAdapter(book.getPages(),this);
        recyclerView.setAdapter(pageAdapter);
    }

    private void getBook(int id) {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).deleteRealmIfMigrationNeeded().build();
        Realm realm = Realm.getInstance(realmConfig);
        book = realm.where(RealmBook.class).equalTo("id",id).findFirst();
    }




}
