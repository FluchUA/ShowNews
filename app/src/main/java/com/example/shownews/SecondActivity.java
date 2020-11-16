package com.example.shownews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.shownews.main.MainActivity;

public class SecondActivity extends AppCompatActivity {

    private WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Получаем сообщение из объекта intent
        Intent intent = getIntent();
        String urlPage = intent.getExtras().get("sent_data").toString();

        web = (WebView)findViewById(R.id.webView);
        WebSettings ws = web.getSettings(); //Получаем настройки и теперь можем ими манипулировать ниже
        ws.setJavaScriptEnabled(true);  //что бы js был включен в нашем браузере
        web.loadUrl(urlPage);   //Ссылка на сайт
        web.setWebViewClient(new WebViewClient());  //Ссылки отрыватся будут в приложении а не в браузере
    }

    //Зарезерфированная функция перехода на страничку назад в браузере
    @Override
    public  void onBackPressed() {
        if (web.canGoBack()) web.goBack(); else //Если можно вернутся назад то возвращаемся иначе ссылаемся на род. класс
            super.onBackPressed();
    }
}