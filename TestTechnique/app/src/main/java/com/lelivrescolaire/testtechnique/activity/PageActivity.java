package com.lelivrescolaire.testtechnique.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.lelivrescolaire.testtechnique.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import models.Book;
import models.RealmBook;
import models.RealmPage;

/**
 * Created by MagicMirouf on 04/04/16.
 */
public class PageActivity extends BaseActivity {

    public static final String PAGE_ID = "PAGE_ID";
    private boolean D = true;

    private RealmPage page;
    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        getPage(getIntent().getExtras().getInt(PAGE_ID));
        showPage();
    }

    /**
     *  Webview configuration and start
     */
    private void showPage() {
        webView = (WebView) findViewById(R.id.activity_page_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        // Bonus
        webView.addJavascriptInterface(new WebAppInterface(this),"Android");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (D)
                    Log.d("onPageFinished",url);
                webView.loadUrl("javascript:displayLineNumbers()");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (D)
                    Log.d("shouldOverrideUrlLoadin",url);

                if (url.startsWith("native://")) {
                    String data = null;
                    try {
                        data = URLDecoder.decode(url.replace("native://",""),"UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    showAlertView(data);
                    return true;
                } else {
                    return super.shouldOverrideUrlLoading(view, url);
                }
            }
        });

        // load page
        new AsyncRead().execute("page.html");
    }

    private void getPage(int id) {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).deleteRealmIfMigrationNeeded().build();
        Realm realm = Realm.getInstance(realmConfig);
        page = realm.where(RealmPage.class).equalTo("id",id).findFirst();
    }

    /**
     *  Async read of html page and display
     */
    private class AsyncRead extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... params) {
            return loadJSONFromAsset(params[0]);
        }
        @Override
        protected void onPostExecute(String html) {
            super.onPostExecute(html);
            // load html content
            html = html.replace("TEXT_CONTENT",page.getContent());
            webView.loadDataWithBaseURL("",html,"text/html","UTF-8",null);
        }
    }


    /**
     *  JAva interface with Webview
     */
    public class WebAppInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        WebAppInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void showMessage(String data) {
            showAlertView(data);
        }
    }

    /**
     *  Show Message (data)
     */
    private void showAlertView(String data) {
        // Show Dialog Alert
        AlertDialog.Builder builder = new AlertDialog.Builder(PageActivity.this);
        builder.setMessage(data);
        builder.create().show();
    }

}
