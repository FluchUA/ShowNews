package com.example.shownews.main;

import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;
import com.example.shownews.network.responce_structures.Article;
import com.example.shownews.presenter.Presenter;

import java.util.List;

public class UInterface {

    RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private Presenter presenter;

    public UInterface(RecyclerView newsList_, Presenter p){
        presenter = p;
        recyclerView = newsList_;
    }

    public void SetAdapter(List<Article> articleList, NewsAdapter.RecycleOnClickListener rOnClick)
    {
        newsAdapter = new NewsAdapter(articleList.size(), articleList, rOnClick);
        recyclerView.setAdapter(newsAdapter);
    }

    public void ShowDetailsNews(int posItemOfList, Context context) {

        Intent intent = new Intent("android.intent.action.SecondActivity");
        intent.putExtra("sent_data", newsAdapter.getPageUrl(posItemOfList));
        context.startActivity(intent);
    }
}
