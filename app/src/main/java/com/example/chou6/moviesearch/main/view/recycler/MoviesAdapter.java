package com.example.chou6.moviesearch.main.view.recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.example.chou6.moviesearch.main.MVP_Main;
import com.example.chou6.moviesearch.model.MovieModel;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MovieHolder> implements MVP_Main.ProvidedAdapterView, MVP_Main.ProvidedAdapterModel {
    // 아이템 리스트
    private MVP_Main.ProvidedPresenter mPresenter;
    private static Context context;
    private List<MovieModel> movies;

    private boolean isLoading = false;
    private boolean MoreDataAvailable = false;

    private int loadThreshold;
    private int total;

    private String query;

    public MoviesAdapter(Context context, List<MovieModel> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mPresenter.createViewHolder(parent,viewType);
    }

    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        mPresenter.bindViewHolder(holder,position);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public String getQuery() {
        return query;
    }

    public void addMovie(MovieModel m){
        movies.add(m);
    }

    public void clear(){
        movies.clear();
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public MovieModel getItem(int position){
        return movies.get(position);
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getLoadThreshold() {
        return loadThreshold;
    }

    public void setLoadThreshold(int loadThreshold) {
        this.loadThreshold = loadThreshold - 5;
    }

    @Override
    public boolean hasMoreData() {
        return total > movies.size();
    }

    public boolean isMoreDataAvailable() {
        return MoreDataAvailable;
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        MoreDataAvailable = moreDataAvailable;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public void setPresenter(MVP_Main.ProvidedPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }
}
