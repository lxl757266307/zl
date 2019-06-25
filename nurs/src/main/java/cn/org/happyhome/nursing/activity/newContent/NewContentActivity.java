package cn.org.happyhome.nursing.activity.newContent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.org.happyhome.library.BuildConfig;
import cn.org.happyhome.library.utils.ActivityStack;
import cn.org.happyhome.library.utils.StatusBarUtils;
import cn.org.happyhome.library.utils.ToolUitls;
import cn.org.happyhome.nursing.R;

public class NewContentActivity extends Activity implements View.OnClickListener {
    private WebView webview;
    private ImageView imgBack;
    private TextView txtTile;
    private TextView txtBack;
    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().pushActivity(this);
        StatusBarUtils.setStatusBar(this, false, true);
        setContentView(R.layout.activity_news_content);
        String content = this.getIntent().getStringExtra("content");
        if (BuildConfig.DEBUG) Log.d("NewContentActivity", content);
        this.webview = findViewById(R.id.web_view);
        txtTile = findViewById(R.id.txt_title);
        imgBack = findViewById(R.id.img_back);
        txtBack = findViewById(R.id.txt_back);
        txtTile.setText("新闻内容");
        imgBack.setOnClickListener(this);
        txtBack.setOnClickListener(this);
        initWebView(content);

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView(String content) {
        //不跳转到其他浏览器
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webview.setWebChromeClient(new WebChromeClient());
        webview.getSettings().setLightTouchEnabled(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadDataWithBaseURL(null, getNewContent(content), "text/html;", "utf-8", null);
    }

    private String getNewContent(String htmltext) {

        Document doc = Jsoup.parse(htmltext);
        Elements elements = doc.getElementsByTag("img");
        for (Element element : elements) {
            element.attr("width", "100%").attr("height", "auto");
        }
        Log.d("VACK", doc.toString());
        return doc.toString();
    }


    //在页面销毁的时候将webView移除
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        ll_root.removeView(webView);
        webview.stopLoading();
        webview.removeAllViews();
        webview.destroy();
        webview = null;
    }

    @Override
    protected void onStop() {
        super.onStop();
        ActivityStack.getInstance().removeActivity(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }



}
