package com.xloger.kanzhihu.app.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ShareActionProvider;
import com.xloger.kanzhihu.app.R;

public class WebActivity extends Activity {

    private WebView webView;
    private String title;
    private String url;
    private String summary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        webView = (WebView) findViewById(R.id.web_view);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        summary =intent.getStringExtra("summary");
        url = intent.getStringExtra("url");

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(false);
        }


        webView.getSettings().setJavaScriptEnabled(true);

        webView.loadUrl(url);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        String shareTxt="【"+title+"】"+summary+url+" （本消息来自看知乎App）";

        getMenuInflater().inflate(R.menu.menu_web,menu);
        MenuItem shareItem=menu.findItem(R.id.web_share);
        if (shareItem != null) {

            Intent shareIntent=new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT,shareTxt);

            //1.普通分享模式
//            startActivity(shareIntent);

            /*
            在设置隐藏分享历史的时候遇到一个报错：No preceding call to #readHistoricalData
            查询资料后未解决，故不隐藏。
            参考资料：http://stackoverflow.com/questions/15755465/how-to-hide-the-share-action-which-use-most-icon-near-the-share-action-provide/17290249#17290249
            http://stackoverflow.com/questions/13395601/android-shareactionprovider-with-no-history
             */

            //2.系统自带的分享模块
            ShareActionProvider shareProvider = (ShareActionProvider)shareItem.getActionProvider();
//            ShareActionProvider.OnShareTargetSelectedListener listener = new ShareActionProvider.OnShareTargetSelectedListener() {
//                public boolean onShareTargetSelected(
//                        ShareActionProvider source, Intent intent) {
//                    startActivity(intent);
//                    return true;
//                }
//            };
//            shareProvider.setShareHistoryFileName(null);
//            shareProvider.setOnShareTargetSelectedListener(listener);


            shareProvider.setShareIntent(shareIntent);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    // 设置回退
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



}
