package models;

import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by MagicMirouf on 04/04/16.
 */
public class Page {

    private int id;
    private String title;
    private String cover;
    private String content;

    public Page(JSONObject jsonObject){
        Gson gson = new Gson();
        Page page = gson.fromJson(jsonObject.toString(),Page.class);
        this.id = page.id;
        this.title = page.title;
        this.cover = page.cover;
        this.content = page.content;
    }

    public RealmPage toRealm() {
        RealmPage realmPage = new RealmPage();
        realmPage.setId(this.id);
        realmPage.setTitle(this.title);
        realmPage.setContent(this.content);
        realmPage.setCover(this.cover);
        return realmPage;
    }
}
