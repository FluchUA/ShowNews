package com.example.shownews.presenter;

import android.content.Context;
import android.util.Log;

import com.example.shownews.main.NewsAdapter;
import com.example.shownews.main.UInterface;
import com.example.shownews.network.NetworkService;

public class Presenter implements NewsAdapter.RecycleOnClickListener  {

    private NetworkService networkService;
    private UInterface uInterface;
    private Context context;

    public void Init(NetworkService nS, UInterface uInt, Context context_)
    {
        networkService = nS;
        uInterface = uInt;
        context = context_;

        networkService.CallApi();
    }

    public void AdapterInit()
    {
        if (networkService.getArticleList() != null)
        {
            uInterface.SetAdapter(networkService.getArticleList(), this);
        } else Log.e("Error data:  ", "is null");
    }

    @Override
    public void onClick(int positionClickItem) {
        uInterface.ShowDetailsNews(positionClickItem, context);
    }
}
