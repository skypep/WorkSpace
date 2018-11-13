package com.toro.helper.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.toro.helper.R;
import com.toro.helper.base.ToroActivity;
import com.toro.helper.modle.data.ToroDataModle;

/**
 * Create By liujia
 * on 2018/11/12.
 **/
public class PrivacyPolicyActivity extends ToroActivity implements View.OnClickListener {

    private ScrollView localLayout;
    private WebView webView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.privacy_policy_activity);

        localLayout = findViewById(R.id.local_layout);
        webView = (WebView)findViewById(R.id.webview);
        progressBar = findViewById(R.id.progress);

        findViewById(R.id.agreen).setOnClickListener(this);
        findViewById(R.id.un_agreen).setOnClickListener(this);
        showWebView();
    }

    private void showLocalText() {
        progressBar.setVisibility(View.GONE);
        webView.setVisibility(View.GONE);
        localLayout.setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.content)).setText(ToroDataModle.getInstance().getLocalData().getPrivatyPolicyContent(this));
    }

    private void showWebView() {
        progressBar.setVisibility(View.VISIBLE);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                //网络未连接
                showLocalText();
            }

            //处理网页加载失败时
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                //6.0以上执行
                showLocalText();//显示错误页面
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                }
            }


            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (title.contains("404")){
                    showLocalText();
                }
            }
        });
        webView.loadUrl("http://120.78.174.86:8080/kinship/kinship.html");
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent();
        intent.setClass(context,PrivacyPolicyActivity.class);
        return intent;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.agreen:
                ToroDataModle.getInstance().getLocalData().setAgreenPrivacyPolicy(true);
                finish();
                startActivity(LoginActivity.newIntent(PrivacyPolicyActivity.this));
                break;
            case R.id.un_agreen:
                finish();
        }
    }
}
