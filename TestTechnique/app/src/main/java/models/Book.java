package models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by MagicMirouf on 04/04/16.
 */
public class Book {

    private int id;
    private String title;
    private String cover;
    private ArrayList<Page> pages;

    /*
    * The best way is to use RealmObject extension and use createOrUpdateFromJson() function
     */

    public Book(JSONObject jsonObject){
        try {
            this.id = jsonObject.getInt("id");
        } catch (JSONException e) {
            this.id = -1;
        }
        try {
            this.title = jsonObject.getString("title");
        } catch (JSONException e) {
            this.title = null;
        }
        try {
            this.cover = jsonObject.getString("cover");
        } catch (JSONException e) {
            this.cover = null;
        }
        try {
            JSONArray pages_array = jsonObject.getJSONArray("pages");
            pages = new ArrayList<>(pages_array.length());
            for(int i = 0; i < pages_array.length();i++){
                try {
                    pages.add(new Page(pages_array.getJSONObject(i)));
                } catch (JSONException e){
                    pages.add(null);
                }
            }
        } catch (JSONException e) {
            this.pages = null;
        }
    }

    public RealmBook toRealm(){
        RealmBook rBook = new RealmBook();
        rBook.setCover(this.cover);
        rBook.setId(this.id);
        RealmList<RealmPage> rPages = new RealmList<>();
        for (Page page : pages) {
            rPages.add(page.toRealm());
        }
        rBook.setPages(rPages);
        rBook.setTitle(this.title);
        return rBook;
    }

}
