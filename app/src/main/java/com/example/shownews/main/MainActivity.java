package com.example.shownews.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.shownews.R;
import com.example.shownews.network.NetworkService;
import com.example.shownews.presenter.Presenter;

public class MainActivity extends AppCompatActivity {

    private RecyclerView newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsList = (RecyclerView) findViewById(R.id.recycleV);

        //Установить тип распределения элементов в RecyclerView (списком)
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        newsList.setLayoutManager(linearLayoutManager);

        //Если размер списка заранее известен, тогда размер RecyclerView останится неизменным
        newsList.setHasFixedSize(true);

        Presenter presenter = new Presenter();
        UInterface uInterface = new UInterface(newsList, presenter);
        NetworkService networkService = NetworkService.getInstance();

        networkService.Init(presenter);
        presenter.Init(networkService, uInterface, this);
    }
}