package com.jonathandilks.baegley.g52grp_team2_2016_basic_bits;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;


/**
 * Created by sunenhao on 12/03/2017.
 */

public class HomeFragment extends Fragment{
    private TextView home;
    public WebView mWebView;

    public HomeFragment(){}

    @Nullable


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.homefragment, container, false);
        //home = (TextView) rootView.findViewById(R.id.home);
        //home.setText("HHHHHome");

        mWebView = (WebView) rootView.findViewById(R.id.webview);
        mWebView.loadUrl("https://nottingham.ac.uk/news");

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new WebViewClient());

        return rootView;

    }


}
