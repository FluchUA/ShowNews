package com.example.shownews.network;

import android.util.Log;

import com.example.shownews.network.responce_structures.Article;
import com.example.shownews.network.responce_structures.ResponseStructure;
import com.example.shownews.presenter.Presenter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    private Retrofit mRetrofit;
    private List<Article> articleList;
    private static NetworkService mInstance;
    private static final String BASE_URL = "https://newsapi.org/v2/";
    private static final String API_KEY = "3600bae8eeeb45a5890710465aeed8db";

    private Presenter presenter;

    public static NetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }

    public void Init(Presenter p){
        presenter = p;
    }

    private NetworkService() {

        //Перехват запросов и их логирование
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        //Уровень регистрации перехватчика BODY (Регистрирует строки запроса и ответа, а также их соответствующие заголовки и тела (если они есть))
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(interceptor);

        //Объявление и инициализация Retrofit
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())                                 //Клиент HTTP, используемый для запросов
                .build();                                               //Создать Retrofit экземпляр, используя настроенные значения
    }

    public void CallApi()
    {
        //Создаем объект, при помощи которого будем выполнять запросы
        //В итоге Retrofit будет брать базовый URL из билдера, присоединять к нему остаток пути,
        // который мы указываем в GET в интерфейсе, и тем самым получит полную ссылку.
        APIInterface apiService = mRetrofit.create(APIInterface.class);

        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");

        //Call - вызов метода Retrofit, который отправляет запрос на веб-сервер и возвращает ответ
        Call<ResponseStructure> call = apiService.getNews("bitcoin", formatForDateNow.format(dateNow), API_KEY);

        //enqueue - выполнение запроса асинхронным способом
        //Callback - обменивается ответами с сервера или автономными запросами. В ответ на данный запрос будет вызван один и только один метод.
        call.enqueue(new Callback<ResponseStructure>() {
            @Override
            public void onResponse(Call<ResponseStructure> call, Response<ResponseStructure> response) {    //Данные пришли успешно
                if(response.body() != null) {
                    articleList = response.body().getArticles();
                    presenter.AdapterInit();
                } else Log.i("Articles: ", "not found");
            }

            @Override
            public void onFailure(Call<ResponseStructure> call, Throwable t) {  //Произошла ошибка
                Log.e("Throwable: ", t.toString());
            }
        });
    }

    public List<Article> getArticleList() {
        return articleList;
    }
}
