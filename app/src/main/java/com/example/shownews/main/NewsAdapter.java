package com.example.shownews.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shownews.R;
import com.example.shownews.network.responce_structures.Article;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private int newsItemsCount;
    private List<Article> articleList;
    private RecycleOnClickListener recycleOnClickListener;

    public NewsAdapter(int itemsCount, List<Article> articles, RecycleOnClickListener rOnClick)
    {
        articleList = articles;
        newsItemsCount = itemsCount;
        recycleOnClickListener = rOnClick;
    }

    public void setArticleList(List<Article> articleList) {

        this.articleList = articleList;
    }

    //ViewGroup - RecycleView
    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext(); //Получить контекст RecycleView
        int idLayoutListItem = R.layout.news_item;  //Получить id xml файла

        //Из содержимого xml-файла создать View-элемент (idLayoutListItem - какой элемент и parent - куда поместить)
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(idLayoutListItem, parent, false);

        return new NewsViewHolder(view, recycleOnClickListener);
    }

    //Обновить данные новым ViewHolder (при прокрутке)
    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        Article article = articleList.get(position);

        //else - если не указана дата, указать это
        if (article.getPublishedAt() != null) holder.textDate.setText(article.getPublishedAt().substring(0, 10)); else
            holder.textDate.setText("Date not specified");

        //else - если не указано заглавие, указать это
        if (article.getTitle() != null) holder.textTitle.setText("Title: " + article.getTitle());
            else holder.textTitle.setText("Title: not specified");

        //else - если актор не указан, указать это
        if (article.getAuthor() != null) holder.textAutor.setText("Autor: " + article.getAuthor()); else
            holder.textAutor.setText("Autor: not specified");

        //Если нет ссылки на картинку, загрузить картинку из ресурсов
        if (article.getUrlToImage() != null)
        {
            Glide.with(holder.itemView.getContext())
                    .load(article.getUrlToImage())
                    .into(holder.img);
        } else holder.img.setImageResource(R.drawable.image_not_found);
    }

    //Общее кол. элементов в списке
    @Override
    public int getItemCount() {
        return newsItemsCount;
    }

    //Обертка для news_item, позволяющая в дальнейшем заного его переиспользовать
    class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textDate;
        private TextView textAutor;
        private TextView textTitle;
        private ImageView img;

        private RecycleOnClickListener recycleOnClickListener;

        public NewsViewHolder(@NonNull View itemView, RecycleOnClickListener rOnClick) { //itemView - сгенерированный обьект news_item
            super(itemView);

            img = itemView.findViewById(R.id.imageView);
            textDate = itemView.findViewById(R.id.textViewDate);
            textAutor = itemView.findViewById(R.id.textViewAutor);
            textTitle = itemView.findViewById(R.id.textViewTitle);

            recycleOnClickListener = rOnClick;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            recycleOnClickListener.onClick(getAdapterPosition());
        }
    }

    public String getPageUrl(int posItem)
    {
        return articleList.get(posItem).getUrl();
    }

    public interface RecycleOnClickListener{
        void onClick(int positionClickItem);
    }
}
