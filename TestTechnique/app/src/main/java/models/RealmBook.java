package models;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by MagicMirouf on 04/04/16.
 */
public class RealmBook extends RealmObject {
    @PrimaryKey
    private int id;
    private String title;
    private String cover;
    private RealmList<RealmPage> pages;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public RealmList<RealmPage> getPages() {
        return pages;
    }

    public void setPages(RealmList<RealmPage> pages) {
        this.pages = pages;
    }
}
